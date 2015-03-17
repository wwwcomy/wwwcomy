package com.iteye.wwwcomy.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

public class MyCacheEventListener extends CacheEventListenerAdapter {

    @Override
    public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
        System.out.println("Cache put, key:" + element.getKey() + ",object key:" + element.getObjectKey()
                + ",object value:" + element.getObjectValue());
    }

    @Override
    public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
        System.out.println("Cache updated, key:" + element.getKey() + ",object key:" + element.getObjectKey()
                + ",object value:" + element.getObjectValue());
    }

}
