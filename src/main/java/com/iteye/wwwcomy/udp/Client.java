package com.iteye.wwwcomy.udp;

import java.io.IOException;
import java.net.*;

public class Client {
	static DatagramSocket client;
	static DatagramPacket packet;
	static byte[] data = new byte[128];

	public static void main(String[] args) throws Exception {
		try {
			client = new DatagramSocket();
			packet = new DatagramPacket(data, data.length, InetAddress
					.getByName("127.0.0.1"), 23);
			client.send(packet);
			packet = new DatagramPacket(data, data.length);

			client.receive(packet);
			String output = new String(packet.getData(),0,packet.getLength());
			client.close();
			System.out.println(output);
		} catch (IOException e) {
			System.out.println("The 'server' may not be running!");
		}

	}
}
