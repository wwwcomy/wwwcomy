package com.iteye.wwwcomy.servlet.smack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import com.iteye.wwwcomy.lxn.utils.StringUtil;

public class SendMsg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static XMPPConnection con;
	public static Chat newChat;
	public static ChatManager chatmanager;

	static {
		try {
			// Create a connection to server
			ConnectionConfiguration config = new ConnectionConfiguration("1.1.7.81", 5222);
			con = new XMPPConnection(config);

			// connect and login with the username and pwd on server
			con.connect();
			con.login("liuxn", "111111");
			System.out.println("Authenticated = " + con.isAuthenticated());
			// add a listener to receive all messages
			addListener();
		} catch (XMPPException e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = req.getParameter("msg");
		String toUsr = req.getParameter("tousr");
		if (StringUtil.isBlankOrNull(msg) || StringUtil.isBlankOrNull(toUsr))
			return;
		chatmanager = con.getChatManager();
		newChat = chatmanager.createChat("admin@1.1.7.81", new MessageListener() {
			public void processMessage(Chat chat, Message message) {
				System.out.println("I'm sending: " + message.getBody());
			}

		});
		try {
			newChat.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("success", "true");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
	}

	private static void addListener() {
		PacketFilter filterMessage = new PacketTypeFilter(Message.class);

		PacketListener myListener = new PacketListener() {
			public void processPacket(Packet packet) {
				System.out.println("From: " + packet.getFrom() + "\n");
				ConcurrentLinkedQueue<Message> queue = WebClient.getMsgMap().get("a");
				if (queue == null)
					queue = new ConcurrentLinkedQueue<Message>();
				queue.add((Message) packet);
				WebClient.getMsgMap().put("a", queue);
				Object lock = WebClient.getMap().get("a");
				synchronized (lock) {
					try {
						lock.notifyAll();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				System.out.println("Body: " + ((Message) packet).getBody());
			}
		};
		// register the listener to the connection
		con.addPacketListener(myListener, filterMessage);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
