package com.hailas.common.cache;

/**
 *
 * Created by wulin on 2017/5/5.
 */
public class Caches {

    private static Cache cache;

    public static void init(){
        if ("redis".equals(Config.CACHE_TYPE())){
            cache = new Redis();
        }
    }

    public static void init(String host,int port,int maxTotal,int minIdle,String pwd){
        cache = new Redis(host,port,maxTotal,minIdle,pwd);
    }

    public static Cache getInstance(){
        return cache;
    }
}
