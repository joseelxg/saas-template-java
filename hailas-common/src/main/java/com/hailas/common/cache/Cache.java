package com.hailas.common.cache;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface Cache {
    String get(String key);
    void set(String key, String value);
    void set(String key, String value, int seconds);
    Long incr(String key);
    void append(String key, String value);
    void delete(String key);
    void setAdd(String key, String... value);
    Set<String> getSetAllValues(String key);
    boolean setContain(String key, String value);
    void setRemove(String key, String... value);
    void batchDelete(String pattern);
    Jedis getJedis();
    List<Map<String,String>> getKeys(String pattern);
    boolean exists(String key);
    boolean sismember(String key, String member);
    void zadd(String key, double score, String member);
    long zcount(String key, double min, double max);
}
