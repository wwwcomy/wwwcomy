package com.iteye.wwwcomy.tcp;

import java.io.*;
import java.net.*;

public class client {
	static Socket server;

	public static void main(String[] args) throws Exception {
		try {
			server = new Socket("127.0.0.1", 2233);
			BufferedReader in = new BufferedReader(new InputStreamReader(server
					.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream());

			String str = "Socket Message";
			out.println(str);
			out.flush();
			System.out.println(in.readLine());
			server.close();
		} catch (IOException e) {
			System.out.println("The server may not be running!");
		}
	}

}