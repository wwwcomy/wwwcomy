package com.iteye.wwwcomy.tcp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class server {
	public static void main(String[] args) throws IOException {

		try {
			ServerSocket server = new ServerSocket(2233);
			System.out.println("TCP socket server has been started.");
			while (true) {
				Socket client = server.accept();
				getTime(client);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getTime(Socket client) {
		Date now = new Date();
		SimpleDateFormat DateFormat = new SimpleDateFormat(
				"MMMM d, yyyy hh:mm:ss", Locale.US);
		String time = DateFormat.format(now);
		try {
			Thread t = new Thread();
			t.start();
			new BufferedReader(new InputStreamReader(client
					.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			System.out.println("Date Info. has been sent back.");
			out.println(time);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}