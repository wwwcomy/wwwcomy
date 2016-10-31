package com.iteye.wwwcomy.lxn.nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioEchoServer {

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		// 必须配置阻塞模式为False
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(7777));
		// 注册感兴趣的事件，这里注册的是接受事件
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		Iterator<SelectionKey> keys;
		SelectionKey key;
		// 阻塞调用线程，直到有某个Channel的某个感兴趣的Op准备好(单个Selector可以注册多个Channel)
		while (selector.select() >= 0) {
			keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				key = keys.next();
				keys.remove();
				if (key.isAcceptable()) {
					ServerSocketChannel ssc2 = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssc2.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
					System.out.println("Accept new connection: " + sc.socket().getPort());
				}
				if (key.isReadable()) {
					readDataFromSocket(key);
				}
				if (key.isWritable()) {
					writeDataThroughSocket(key);
				}
			}
		}
	}

	private static void readDataFromSocket(SelectionKey key) throws Exception {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len;
		while ((len = sc.read(buffer)) > 0) {
			baos.write(buffer.array(), 0, len);
		}
		if (len == -1) {
			sc.close();
			System.out.println("Connection closed: " + sc.socket().getPort());
			return;
		}
		System.out.println("Receive data from " + sc.socket().getPort() + ": " + baos.toString());
		key.attach(buffer);
		key.interestOps(SelectionKey.OP_WRITE);
	}

	private static void writeDataThroughSocket(SelectionKey key) throws Exception {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		buffer.flip();
		sc.write(buffer);
		key.interestOps(SelectionKey.OP_READ);
	}

}
