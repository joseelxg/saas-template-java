package com.hailas.common.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2016/11/29.
 */
public class RedisSessionFactory {

    private JedisPool pool;

    public RedisSessionFactory(){
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
        pool = new JedisPool(config, Config.REDIS_HOST(), Config.REDIS_PORT());
    }

    private Jedis getResource(){
        return pool.getResource();
    }

    private void releseResource(Jedis jedis){
        if (jedis!=null) {
            jedis.close();
        }
    }
}
