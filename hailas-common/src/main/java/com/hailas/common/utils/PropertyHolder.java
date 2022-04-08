package com.hailas.common.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 属性文件持有器
 * Created by wulin on 2016/12/2.
 */
public class PropertyHolder {
    private final Properties properties;

    public PropertyHolder(String resource) {

        Reader in = null;
        try {
            in = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource),"utf-8");
        } catch (UnsupportedEncodingException e) {
            //ignore
        }
        if (in==null){
            throw new IllegalArgumentException("can not find the resource by : " + resource);
        }
        properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException("can not find the resource by : " + resource);
        }
    }

    public String get(String key){
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)){
            return "";
        }else {
            return value.trim();
        }
    }

    public Properties getProperties(){
        return properties;
    }
}
