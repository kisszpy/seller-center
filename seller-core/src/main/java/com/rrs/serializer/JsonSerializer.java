package com.rrs.serializer;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created by zhaop01 on 2015/4/8.
 */
public class JsonSerializer {


    private final ObjectMapper objectMapper;

    public JsonSerializer(){
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

    }

    public <T> String serialize(T entity){
        try {
            return this.objectMapper.writeValueAsString(entity);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;

    }
    public <T> T deserialize(String value,Class<T> entityClass){
        try {
            return this.objectMapper.readValue(value,entityClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
