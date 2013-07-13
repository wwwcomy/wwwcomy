package com.iteye.wwwcomy.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {

	List<SocketDealer> list = new LinkedList<SocketDealer>();

	public void begin() {
		try {
			ServerSocket sSocket = new ServerSocket(8888);
			System.out.println("Server Started...");
			while (true) {
				Socket s = sSocket.accept();
				System.out.println("Client accept:" + s.getInetAddress());
				try {
					SocketDealer socketDealer = new SocketDealer(s);
					list.add(socketDealer);
					System.out.println("现在有" + list.size() + "个客户端存在!");
					new Thread(socketDealer).start();
				} catch (Exception e) {
					System.out.println("客户端强制退出");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatServer().begin();
	}

	class SocketDealer implements Runnable {
		private Socket s;

		public SocketDealer(Socket s) {
			this.s = s;
		}

		private void sendMsg(String msg) throws IOException {
			for (SocketDealer sDealer : list) {
				DataOutputStream dos = new DataOutputStream(
						sDealer.s.getOutputStream());
				dos.writeUTF(msg);
				dos.flush();
			}
		}

		@Override
		public void run() {
			DataInputStream dis = null;
			try {
				boolean status = true;
				while (status) {
					dis = new DataInputStream(s.getInputStream());

					String msg = dis.readUTF();// 这里是阻塞式的
					System.out.println("Client said:" + msg);
					sendMsg(msg);
					if (msg.equalsIgnoreCase("bye"))
						status = false;
				}
				System.out.println("Client已经退出");
				System.out.println("现在有" + list.size() + "个客户端存在!");
			} catch (Exception e) {
				System.out.println("现在有" + list.size() + "个客户端存在!");
				if (dis != null)
					try {
						dis.close();
						s.close();
						s = null;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				System.out.println("Client粗暴退出");
			} finally {
				list.remove(this);
			}
		}
	}
}
