package com.iteye.wwwcomy.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache Hello World. @see TestEhCache2 to find using EhCache in cluster mode.
 * 
 * For updating element in cache. when running TestEhCache2 with listener, run TestEhcache then TestEhCche3, then we
 * could see the output.
 * 
 * @author xingnan.liu
 */
public class TestEhCache3 {

    public static void main(String[] args) throws InterruptedException {
        CacheManager manager = CacheManager.create("target/classes/ehcache.xml");

        Cache cache = manager.getCache("testC");
        for (int i = 0; i < 20; i++) {
            cache.put(new Element("testcache1", "testCacheContent" + i));
        }
        System.exit(0);
    }

}
