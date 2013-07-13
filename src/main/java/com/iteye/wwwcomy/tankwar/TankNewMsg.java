package com.iteye.wwwcomy.tankwar;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg {
	Tank t;

	public TankNewMsg(Tank t) {
		this.t = t;
	}
	
	public TankNewMsg() {
	}

	public void send(DatagramSocket ds, String ip, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(t.x);
			dos.writeInt(t.id);
			dos.writeInt(t.y);
			dos.writeInt(t.d.ordinal());
			dos.writeBoolean(t.isEvil());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b = baos.toByteArray();
		DatagramPacket dp;
		try {
			dp = new DatagramPacket(b, b.length, new InetSocketAddress(ip,
					udpPort));
			ds.send(dp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
