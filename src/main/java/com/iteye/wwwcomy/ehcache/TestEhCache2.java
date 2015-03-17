package com.iteye.wwwcomy.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * Use in cluster mode, to check whether cache could be synchronized from other app.
 * 
 * @author xingnan.liu
 */
public class TestEhCache2 {

    public static void main(String[] args) throws InterruptedException {
        CacheManager manager = CacheManager.create("target/classes/ehcache2.xml");

        Cache cache = manager.getCache("testC");
        while (true) {
            Thread.sleep(2000);
            System.out.println("Cache in Memory: " + cache.getMemoryStoreSize());
        }
    }

}
