package com.iteye.wwwcomy.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache Hello World. @see TestEhCache2 to find using EhCache in cluster mode.
 * 
 * @author xingnan.liu
 */
public class TestEhCache {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws InterruptedException {
        CacheManager manager = CacheManager.create("target/classes/ehcache.xml");

        Cache cache = manager.getCache("testC");
        cache.put(new Element("testcache1", "testCacheContent1"));
        cache.put(new Element("testcache2", "testCacheContent2"));
        cache.put(new Element("testcache3", "testCacheContent3"));
        cache.put(new Element("testcache4", "testCacheContent4"));
        System.out.println("Cache in Memory: " + cache.getMemoryStoreSize());
        System.out.println("Cache in Disk: " + cache.getDiskStoreSize());
        cache.remove("testcache1");
        System.out.println("After Del. Cache in Memory: " + cache.getMemoryStoreSize());
        System.out.println("After Del. Cache in Disk: " + cache.getDiskStoreSize());
        List l = cache.getKeys();
        for (Object a : l)
            System.out.println("Cache is '" + cache.get(a) + "'");
        Thread.sleep(5000);
    }

}
