package com.rrs.core.redis.hash;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public JsonMapper(){
        this.objectMapper = new ObjectMapper();
    }

    public String toJson(Object obj){
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJson(String value,Class<T> clazz){
        try {
            return this.objectMapper.readValue(value,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObjectMapper getMapper(){
        return this.objectMapper;
    }


}
