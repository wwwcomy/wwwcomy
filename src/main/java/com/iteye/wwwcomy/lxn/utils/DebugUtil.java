package com.iteye.wwwcomy.lxn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugUtil {
	final static Logger logger = LoggerFactory.getLogger(DebugUtil.class);

	public static void debug(Object o) {
		logger.debug(o.toString());
	}

	public static void info(Object o) {
		logger.info(o.toString());
	}

	public static void warn(Object o) {
		logger.warn(o.toString());
	}

	public static void error(Object o) {
		logger.error(o.toString());
	}
	
	public static void main(String[] args) {
		debug("test1");
		info("test1");
		warn("test1");
		error("test1");
	}
}
