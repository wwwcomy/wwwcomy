package com.iteye.wwwcomy.udp;

import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws Exception {
		try {
			DatagramSocket server = new DatagramSocket(23);
			System.out.println("UDP socket 'server' has been started.");
			while (true) {
				getTime(server);
			}			
		} catch (Exception e) {
			System.out.println("Fail to start the server...");
		}
	}

	public static void getTime(DatagramSocket server) {
		Date now = new Date();
		SimpleDateFormat DateFormat = new SimpleDateFormat(
				"MMMM d, yyyy hh:mm:ss", Locale.US);
		String time = DateFormat.format(now);
		byte[] timeout = time.getBytes();
		try {
			byte[] data = new byte[128];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			server.receive(packet);
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(timeout, timeout.length, address, port);
			server.send(packet);
			System.out.println("Date Info. has been sent!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
