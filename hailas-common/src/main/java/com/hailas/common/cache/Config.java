package com.hailas.common.cache;

import com.hailas.common.utils.PropertyHolder;

/**
 * Created by wulin on 2016/12/24.
 */
public class Config {
    private static String CACHE_TYPE;
    private static String REDIS_HOST;
    private static int REDIS_PORT;
    private static String REDIS_PWD;
    private static int REDIS_MAX_TOTAL;
    private static int REDIS_MAX_IDLE;
    private static int REDIS_MIN_IDLE;
    //当池内没有返回对象时，最大等待时间
    public static final int REDIS_MAX_WAIT_MILLIS = 10000;
    //当调用borrow Object方法时，是否进行有效性检查
    public static final boolean REDIS_TEST_ON_BORROW = true;
    //当调用return Object方法时，是否进行有效性检查
    public static final boolean REDIS_TEST_ON_RETURN = true;
    //“空闲链接”检测线程，检测的周期，毫秒数。如果为负值，表示不运行“检测线程”。默认为-1.
    public static final int REDIS_TIME_BETWEEN_EVICTION_RUNS_MILLIS=30000;
    //向调用者输出“链接”对象时，是否检测它的空闲超时；
    public static final boolean REDIS_TEST_WHILE_IDLE = true;
    //对于“空闲链接”检测线程而言，每次检测的链接资源的个数。默认为3.
    public static final int REDIS_NUM_TESTS_PER_EVICTIONRUN = 50;

    public static void init(String filePath){
        PropertyHolder holder = new PropertyHolder(filePath);
        CACHE_TYPE = holder.get("CACHE_TYPE");
        REDIS_HOST = holder.get("REDIS_HOST");
        REDIS_PORT = Integer.parseInt(holder.get("REDIS_PORT"));
        REDIS_PWD = holder.get("REDIS_PWD");
        REDIS_MAX_TOTAL = Integer.parseInt(holder.get("REDIS_MAX_TOTAL"));
        REDIS_MAX_IDLE = Integer.parseInt(holder.get("REDIS_MAX_IDLE"));
        REDIS_MIN_IDLE = Integer.parseInt(holder.get("REDIS_MIN_IDLE"));
        Caches.init();
    }

    public static String CACHE_TYPE() {
        return CACHE_TYPE;
    }

    public static String REDIS_HOST() {
        return REDIS_HOST;
    }

    public static int REDIS_PORT() {
        return REDIS_PORT;
    }

    public static String REDIS_PWD() {
        return REDIS_PWD;
    }

    public static int REDIS_MAX_TOTAL() {
        return REDIS_MAX_TOTAL;
    }

    public static int REDIS_MAX_IDLE() {
        return REDIS_MAX_IDLE;
    }

    public static int REDIS_MIN_IDLE() {
        return REDIS_MIN_IDLE;
    }
}
