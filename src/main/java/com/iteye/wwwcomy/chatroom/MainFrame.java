package com.iteye.wwwcomy.chatroom;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame {

	public static void main(String[] args) {
		MyChatFrame frame = new MyChatFrame("聊天窗口");
		frame.launchFrame();
		frame.connect();
		frame.beginRead();
	}

}

class MyChatFrame extends JFrame {
	MyChatFrame() {
		super();
	}

	MyChatFrame(String title) {
		super(title);
	}

	private static final long serialVersionUID = 1L;
	public JTextField jtf = new JTextField();
	public JTextArea jta = new JTextArea();
	Socket s;
	DataOutputStream dos;
	DataInputStream dis;

	public void launchFrame() {
		setBounds(300, 400, 300, 400);
		jtf.addKeyListener(new JtfMoniter());
		jta.setEditable(false);
		add(jta, BorderLayout.NORTH);
		add(jtf, BorderLayout.SOUTH);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					dos.close();
					s.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				System.exit(0);
			}
		});
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			System.out.println("Connected to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void beginRead() {
		new Thread(new InStreamDealer()).start();
	}

	private class JtfMoniter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 13 || e.getKeyCode() == 10) {
				String tmp = jtf.getText().trim();
				try {
					dos.writeUTF(tmp);
					dos.flush();
					if (tmp.equalsIgnoreCase("bye")) {
						setVisible(false);
						System.exit(0);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				// jta.append(tmp + "\n");
				jtf.setText("");
			}
		}
	}

	private class InStreamDealer implements Runnable {

		@Override
		public void run() {
			boolean status = true;
			while (status) {
				try {
					String tmp = dis.readUTF();
					jta.append(tmp + "\n");
				} catch (Exception e) {
					status = false;
					System.out.println("已经退出");
					e.printStackTrace();
				}
			}
		}
	}
}