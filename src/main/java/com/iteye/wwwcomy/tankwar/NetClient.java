package com.iteye.wwwcomy.tankwar;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class NetClient {

	private int UDPPortStart = 2223;
	private int UDPPort;
	WarFrame wf;

	DatagramSocket ds = null;

	public NetClient(WarFrame wf) {
		UDPPort = UDPPortStart + 1;
		this.wf = wf;
		try {
			ds = new DatagramSocket(UDPPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void send(TankNewMsg msg) {
		msg.send(ds, "127.0.0.1", 6666);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void connect(String IP, int port) {
		Socket s = null;
		DataOutputStream dos = null;
		try {
			s = new Socket(IP, port);
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(UDPPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			wf.myTank.id = id;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null) {
					dos.flush();
					dos.close();
				}
				if (s != null)
					s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		TankNewMsg tnewmsg = new TankNewMsg(wf.myTank);
		send(tnewmsg);
		new Thread(new udpRcvThread()).start();
	}

	private class udpRcvThread implements Runnable {
		byte[] b = new byte[1024];

		public void run() {
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(b, b.length);
				try {
					ds.receive(dp);
					System.out.println("package received from server");
					System.out.println("info:");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@SuppressWarnings("unused")
		public void parse(DatagramPacket dp) {
			ByteArrayInputStream bais = new ByteArrayInputStream(b, 0,
					dp.getLength());
		}
	}

}
