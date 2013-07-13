package com.iteye.wwwcomy.servlet.smack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jivesoftware.smack.packet.Message;

import com.iteye.wwwcomy.lxn.utils.StringUtil;

public class RcvMsg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final long WAIT_TIME = 100000;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String usrName = req.getParameter("username");
		if (StringUtil.isBlankOrNull(usrName))
			return;
		Object o = WebClient.getMap().get(usrName);
		if (o == null)
			return;
		synchronized (o) {
			try {
				o.wait(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ConcurrentLinkedQueue<Message> queue = WebClient.getMsgMap().remove(usrName);
		// ((Message) WebClient.getMsgMap().get(usrName));
		if (queue == null)
			return;
		Iterator<Message> i = queue.iterator();
		JSONArray array = new JSONArray();
		while (i.hasNext()) {
			Message msg = i.next();
			String sFrom = msg.getFrom();
			String sMsg = msg.getBody();
			JSONObject tmp = new JSONObject();
			tmp.put("from", sFrom);
			tmp.put("msg", sMsg);
			array.add(tmp);
		}
		try {
			resp.setCharacterEncoding("UTF-8");
			PrintWriter pw = resp.getWriter();
			pw.print(array);
			pw.flush();
			pw.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
