package com.rrs.utils;

import com.google.common.base.CaseFormat;
import com.rrs.annotation.ObjectKey;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public class KeyUtils {

    private static final String KEY_METHOD_NAME = "getId";

    private static final String KEY_SPLIT = ":";

    private static final String REDIS_SESSION_KEY_PREFIX="redis-session:";

    private static final String REDIS_SESSION_VALUE_PREFIX="redis-session-value:";

    private static final String REDIS_COOKIE_KEY = "redis-session-id";

    public static String getCookieKey(){
        return REDIS_COOKIE_KEY;
    }

    public static String redisSessionValue(String id){
        return


                REDIS_SESSION_VALUE_PREFIX+id;
    }

    /**
     * redis session key 前缀
     * @param id
     * @return
     */
    public static String redisSessionKey(String id){
        return REDIS_SESSION_KEY_PREFIX+id;
    }

    /**
     * 获取类对象的注解
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getObjectKey(Class<T> entityClass,T entity){
        ObjectKey key = entityClass.getAnnotation(ObjectKey.class);
        if(StringUtils.isEmpty(key)){
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN,entityClass.getSimpleName())+KEY_SPLIT+getKey(entityClass,entity);
        }else{
            return key.value()+KEY_SPLIT+getKey(entityClass,entity);
        }
    }
    public static <T> String getKey(Class<T> entityClass,T entity){
        Method m = ReflectionUtils.findMethod(entityClass,KEY_METHOD_NAME);
        Assert.notNull(m,"the key method is not null! please check your entity!");
        try {
            return m.invoke(entity).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
