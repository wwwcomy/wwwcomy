package com.iteye.wwwcomy.lxn.p2pchat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TestUDPClient {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		byte[] buf = "Hello World".getBytes();
		DatagramPacket dp = new DatagramPacket(buf, buf.length,
				new InetSocketAddress("127.0.0.1", 8888));
		DatagramSocket ds = new DatagramSocket(9999);
		ds.send(dp);
		ds.close();
	}

}
