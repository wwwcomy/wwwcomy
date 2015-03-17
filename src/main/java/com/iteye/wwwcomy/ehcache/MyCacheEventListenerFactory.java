/*
 * File: $RCSfile$
 *
 * Copyright (c) 2001-2011 by Wincor Nixdorf International GmbH,
 * Heinz-Nixdorf-Ring 1, 33106 Paderborn, Germany
 *
 * This software is the confidential and proprietary information
 * of Wincor Nixdorf.
 *
 * You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Wincor Nixdorf.
 *
 */

package com.iteye.wwwcomy.ehcache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class MyCacheEventListenerFactory extends CacheEventListenerFactory {
    @Override
    public CacheEventListener createCacheEventListener(Properties properties) {
        return new MyCacheEventListener();
    }
}

/**
 * History:
 * 
 * $Log$
 * 
 */
