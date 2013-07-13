package com.iteye.wwwcomy.servlet.smack;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jivesoftware.smack.packet.Message;

public class WebClient {

	private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	private static ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> msgMap = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>>();

	static {
		map.put("a", new Object());
		map.put("b", new Object());
	}

	public static ConcurrentHashMap<String, Object> getMap() {
		return map;
	}

	public static void setMap(ConcurrentHashMap<String, Object> map) {
		WebClient.map = map;
	}

	public static ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> getMsgMap() {
		return msgMap;
	}

	public static void setMsgMap(ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> msgMap) {
		WebClient.msgMap = msgMap;
	}
}
