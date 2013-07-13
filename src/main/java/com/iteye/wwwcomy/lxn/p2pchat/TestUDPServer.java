package com.iteye.wwwcomy.lxn.p2pchat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class TestUDPServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		DatagramSocket ds;
		try {
			ds = new DatagramSocket(8888);
			ds.receive(dp);
			System.out.println(new String(buf, 0, dp.getLength()));
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
