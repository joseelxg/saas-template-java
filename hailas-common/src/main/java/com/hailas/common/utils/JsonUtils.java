package com.hailas.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by wulin on 2016/12/29.
 */
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public static JsonNode format(Class view, Object object){
        try {
            String json = mapper.writerWithView(view).writeValueAsString(object);
            return mapper.readTree(json);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }

    public static String toString(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> valueType){
        try {
            return mapper.readValue(json,valueType);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }


    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        try {
            return mapper.readValue(json,typeReference);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }
}
