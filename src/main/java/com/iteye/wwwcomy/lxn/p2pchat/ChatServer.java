package com.iteye.wwwcomy.lxn.p2pchat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ServerSocket sc = new ServerSocket(8888);
			Socket s = sc.accept();
			new Thread(new SocketDealer(s)).start();
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			// DataInputStream dis = new DataInputStream(s.getInputStream());
			// System.out.println("Client" + s.getInetAddress() + ": "
			// + dis.readUTF());

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String msg;
			while ((msg = br.readLine()) != null) {
				dos.writeUTF(msg);
				dos.flush();
				if (msg.equalsIgnoreCase("exit")) {
					br.close();
					s.close();
					// dis.close();
					sc.close();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class SocketDealer implements Runnable {
	private Socket s;

	public SocketDealer(Socket socket) {
		this.s = socket;
	}

	public void run() {
		try {
			DataInputStream dis;
			dis = new DataInputStream(s.getInputStream());
			while (true) {
				String msg = dis.readUTF();
				if (dis != null)
					System.out.println("Client said: " + msg);
				if (msg.equalsIgnoreCase("bye"))
					dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
