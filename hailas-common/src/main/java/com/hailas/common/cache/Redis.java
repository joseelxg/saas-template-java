package com.hailas.common.cache;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Redis implements Cache {

    private JedisPool pool;

    public Redis(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Config.REDIS_MAX_TOTAL());
        config.setMaxIdle(Config.REDIS_MAX_IDLE());
        config.setMinIdle(Config.REDIS_MIN_IDLE());
        config.setMaxWaitMillis(Config.REDIS_MAX_WAIT_MILLIS);
        config.setTestOnBorrow(Config.REDIS_TEST_ON_BORROW);
        config.setTestOnReturn(Config.REDIS_TEST_ON_RETURN);
        config.setTimeBetweenEvictionRunsMillis(Config.REDIS_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        config.setTestWhileIdle(Config.REDIS_TEST_WHILE_IDLE);
        config.setNumTestsPerEvictionRun(Config.REDIS_NUM_TESTS_PER_EVICTIONRUN);
        if (StringUtils.isNotBlank(Config.REDIS_PWD())){
            pool = new JedisPool(config, Config.REDIS_HOST(), Config.REDIS_PORT(), 10000, Config.REDIS_PWD());
        }else {
            pool = new JedisPool(config, Config.REDIS_HOST(), Config.REDIS_PORT());
        }
    }

    public Redis(String host,int port,int maxTotal,int minIdle,String pwd){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        if (StringUtils.isNotBlank(pwd)){
            pool = new JedisPool(config, host, port, 10000, pwd);
        }else {
            pool = new JedisPool(config, host, port);
        }
    }

    private Jedis getResource(){
        return pool.getResource();
    }

    private void releseResource(Jedis jedis){
        if (jedis!=null) {
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = getResource();
        try{
            return jedis.get(key);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = getResource();
        try{
            jedis.set(key,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void set(String key, String value, int seconds) {
        Jedis jedis = getResource();
        try{
            jedis.setex(key,seconds,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = getResource();
        try{
            return jedis.incr(key);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void append(String key, String value) {
        Jedis jedis = getResource();
        try{
            jedis.append(key,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void delete(String key) {
        Jedis jedis = getResource();
        try{
            jedis.del(key);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void setAdd(String key, String... value) {
        Jedis jedis = getResource();
        try{
            jedis.sadd(key,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public Set<String> getSetAllValues(String key) {
        Jedis jedis = getResource();
        try{
            Set<String> set = jedis.smembers(key);
            return set;
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public boolean setContain(String key, String value) {
        Jedis jedis = getResource();
        try{
            return jedis.sismember(key,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void setRemove(String key, String... value) {
        Jedis jedis = getResource();
        try{
            jedis.srem(key,value);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void batchDelete(String pattern) {
        Jedis jedis = getResource();
        try{
            Set<String> keys = jedis.keys(pattern);
            Iterator<String> it = keys.iterator();
            while (it.hasNext()){
                jedis.del(it.next());
            }
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public List<Map<String, String>> getKeys(String pattern) {
        List<Map<String,String >> list = new ArrayList<>();
        Jedis jedis = getResource();
        try{
            Set<String> keys = jedis.keys(pattern);
            Iterator<String> it = keys.iterator();
            while (it.hasNext()){
                String key = it.next();
                Map<String,String> keyValue = new HashMap<>();
                keyValue.put("key",key);
                keyValue.put("value",jedis.get(key));
                list.add(keyValue);
            }
            return list;
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public Jedis getJedis() {
        Jedis jedis = getResource();
        return jedis;
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = getResource();
        try{
            return jedis.exists(key);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public boolean sismember(String key, String member){
        Jedis jedis = getResource();
        try{
            return jedis.sismember(key, member);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public void zadd(String key, double score, String member){
        Jedis jedis = getResource();
        try{
            jedis.zadd(key,score,member);
        }finally {
            releseResource(jedis);
        }
    }

    @Override
    public long zcount(String key, double min, double max){
        Jedis jedis = getResource();
        try{
            return jedis.zcount(key,min,max);
        }finally {
            releseResource(jedis);
        }
    }
}
