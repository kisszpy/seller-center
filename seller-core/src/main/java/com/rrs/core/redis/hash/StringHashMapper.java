package com.rrs.core.redis.hash;

import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public class StringHashMapper<T> {

    private final JsonMapper jsonMapper;

    private final JavaType mapType;

    private final JavaType userType;

    private static final boolean FILTER_NULL_MAPPER = true;


    public StringHashMapper(Class<T> type) {
        this.jsonMapper = new JsonMapper();
        this.mapType = this.jsonMapper.getMapper().getTypeFactory().constructParametricType(HashMap.class,new Class[]{String.class,String.class});
        this.userType = this.jsonMapper.getMapper().constructType(type);
    }

    public T fromHash(Map<String,String> hashMap){
        return this.jsonMapper.getMapper().convertValue(hashMap,this.userType);
    }

    public Map<String,String> toHash(Object obj){
      return toHash(obj,true);
    }

    public Map<String,String> toHash(Object obj,boolean isFilterNull){
        Map<String,String> map =  this.jsonMapper.getMapper().convertValue(obj, mapType);
        for(String key : map.keySet()){
            if(StringUtils.isEmpty(map.get(key)) && isFilterNull){
                map.remove(key);
            }
        }
        return map;
    }




}
