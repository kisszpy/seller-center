package com.rrs.seller.core.base;

import com.google.common.collect.Lists;
import com.rrs.constants.Constants;
import com.rrs.utils.KeyUtils;
import com.rrs.core.redis.hash.StringHashMapper;
import com.rrs.serializer.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public class RedisBaseDao<T> {

    private final Class<T> entityClass;

    private final StringHashMapper<T> stringHashMapper;

    private final JsonSerializer jsonSerializer;

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisBaseDao() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.stringHashMapper = new StringHashMapper<T>(entityClass);
        jsonSerializer = new JsonSerializer();
    }
    /**
     * 保存对象到redis中
     * @param entity
     */
    public void setHash(T entity){
        this.redisTemplate.opsForHash().putAll(KeyUtils.getObjectKey(entityClass,entity),this.stringHashMapper.toHash(entity));
    }

    public void setHash(String key,T entity,long aliveTime){
        this.redisTemplate.opsForHash().putAll(key,this.stringHashMapper.toHash(entity));
        this.redisTemplate.opsForHash().getOperations().expire(key,aliveTime,TimeUnit.SECONDS);
    }

    /**
     * json形式存放对象
     * @param key
     * @param entity
     * @param aliveTime
     */
    public void set(final String key,final T entity,final long aliveTime){
       /* System.out.println("before:" + this.jsonSerializer.serialize(entity));

        byte[] bytes = this.redisTemplate.opsForValue().getOperations().getValueSerializer().serialize(entity);
        this.redisTemplate.opsForValue().set(key, bytes, aliveTime);*/
        final String value = this.jsonSerializer.serialize(entity);
        this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    // connection.set(key.getBytes("utf-8"),value.getBytes("UTF-8"));
                    connection.setEx(key.getBytes(Constants.DEFAULT_CHAR_SET),aliveTime,value.getBytes(Constants.DEFAULT_CHAR_SET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 获取对象
     * @param key
     * @return
     */
    public T getEntity(final String key){
        String value = (this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes(Constants.DEFAULT_CHAR_SET)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        })).toString();
        return jsonSerializer.deserialize(value,entityClass);
    }

    /**
     * 放置某个string对象
     * @param key
     * @param value
     */
    public void set(String key ,String value,long aliveTime){
        this.redisTemplate.opsForValue().set(key, value, aliveTime, TimeUnit.SECONDS);
    }

    /**
     * 放置某个string对象
     * @param key
     * @param value
     */
    public void set(String key ,String value){
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取某个string对象
     * @param key
     * @return
     */
    public String get(String key){
        return this.redisTemplate.opsForValue().get(key).toString();
    }

    /**
     * 是否存在某个key
     * @param key
     * @return
     */
    public boolean exists(String key){
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 获取hash对象
     * @param key
     * @return
     */
    public T getHash(String key){
        return this.stringHashMapper.fromHash(this.redisTemplate.opsForHash().entries(key));
    }


    /**
     * 元素放置到最前面
     * @param key
     * @param entities
     */
    public void leftPush(String key , T ... entities){
        for(T entity : entities){
            String jsonValue = this.jsonSerializer.serialize(entity);
            this.redisTemplate.opsForList().leftPushAll(key,jsonValue);
        }
    }

    /**
     * 元素放置到最后面
     * @param key
     * @param entities
     */
    public void rightPush(String key,T ... entities){
        for(T entity : entities){
            String jsonValue = this.jsonSerializer.serialize(entity);
            this.redisTemplate.opsForList().rightPushAll(key, jsonValue);
        }
    }

    /**
     * 按照index获取list中的值
     * @param key
     * @param index
     * @return
     */
    public T getIndex(String key,long index){
        return jsonSerializer.deserialize(this.redisTemplate.opsForList().index(key,index).toString(),entityClass);
    }

    /**
     * 获取所有list
     * @param key
     * @return
     */
    public List<T> getAll(String key){
        List<T> list = Lists.newArrayListWithCapacity(10);
        Iterator<String> iterator =  this.redisTemplate.opsForList().range(key,0,this.redisTemplate.opsForList().size(key)-1).iterator();
        while(iterator.hasNext()){
            String value = iterator.next();
            list.add(jsonSerializer.deserialize(value,entityClass));
        }
        return list;
    }

    /**
     * 删除一个值
     * @param key
     */
    public void removeValue(String key){
        this.redisTemplate.opsForValue().getOperations().delete(key);
    }

    /**
     * 删除一个hash对象
     * @param key
     */
    public void removeHashValue(String key){
        this.redisTemplate.opsForHash().getOperations().delete(key);
    }

    /**
     * 删除一个list对象
     * @param key
     */
    public void removeListValue(String key){
        this.redisTemplate.opsForList().getOperations().delete(key);
    }

    /**
     * 删除list列表中某一个对象
     * @param key
     * @param index
     */
    public void removeListIndex(String key,long index){
        String value = this.redisTemplate.opsForList().index(key,index).toString();
        this.redisTemplate.opsForList().remove(key,index,value);
    }

    /**
     * 获取最左边的元素，并移除
     * @param key
     * @return
     */
    public T leftPop(String key){
        return this.jsonSerializer.deserialize(this.redisTemplate.opsForList().leftPop(key).toString(),entityClass);
    }
    /**
     * 获取最右边的元素，并移除
     * @param key
     * @return
     */
    public T rightPop(String key){
        return this.jsonSerializer.deserialize(this.redisTemplate.opsForList().rightPop(key).toString(),entityClass);
    }

    /**
     * 获取list的长度
     * @param key
     * @return
     */
    public long getListSize(String key){
        return this.redisTemplate.opsForList().size(key);
    }

    /**
     * 设置Key过期时间
     * @param key
     * @param aliveTime
     */
    public void setExpiredTime(String key,long aliveTime){
        this.redisTemplate.expire(key,aliveTime,TimeUnit.SECONDS);
    }






}
