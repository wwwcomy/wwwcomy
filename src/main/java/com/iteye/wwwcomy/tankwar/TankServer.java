package com.iteye.wwwcomy.tankwar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class TankServer {

	public final static int port = 8888;
	public final static int udpport = 6666;

	private static int id = 100;

	List<Client> clients = new ArrayList<Client>();

	public void start() {
		new Thread(new udpThread()).start();
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				Socket s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String ip = s.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				clients.add(new Client(ip, udpPort));
				System.out.println("A client has connected");
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(id++);
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TankServer ts = new TankServer();
		ts.start();
	}

	private class Client {
		String IP;
		int UDPPort;

		public Client(String ip, int udpPort) {
			this.IP = ip;
			this.UDPPort = udpPort;

		}
	}

	private class udpThread implements Runnable {
		DatagramSocket ds = null;
		byte[] b = new byte[1024];

		@Override
		public void run() {
			try {
				ds = new DatagramSocket(udpport);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(b, b.length);
				try {
					ds.receive(dp);
					for (int i = 0; i < clients.size(); i++) {
						Client c = clients.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.IP,
								c.UDPPort));
						ds.send(dp);
					}
					System.out.println("package receive");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
