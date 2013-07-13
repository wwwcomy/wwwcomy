package com.iteye.wwwcomy.lxn.p2pchat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("127.0.0.1", 8888);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String msg;
			while ((msg = br.readLine()) != null) {
				// System.out.println(msg);
				dos.writeUTF(msg);
				dos.flush();
//				if (dis != null) {
//					System.out.println("Server:" + dis.readUTF());
//				}
				if (msg.equalsIgnoreCase("exit")) {
					dis.close();
					br.close();
					s.close();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
