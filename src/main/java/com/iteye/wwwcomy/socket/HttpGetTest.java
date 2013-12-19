package com.iteye.wwwcomy.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpGetTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String host = "localhost";
		int port = 8080;
		
		// connecting......
		Socket socket = new Socket(host, port);
		socket.setTcpNoDelay(true);
		socket.setSoTimeout(0);
		
		// send GET request
		InputStream input = new BufferedInputStream(socket.getInputStream(),2048);
		OutputStream output = new BufferedOutputStream(socket.getOutputStream(),2048);
		StringBuffer req = new StringBuffer();
		req.append("GET / HTTP/1.1\r\n");
//		req.append("User-Agent: Jakarta Commons-HttpClient/3.0.1\r\n");
		req.append("Host: " + host+ "\r\n");
		req.append("\r\n");
		
		output.write(req.toString().getBytes());
		output.flush();
		
		// read response
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while((len = input.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
 		System.out.println(baos.toString());
 		// connection closing
		socket.close();
	}

}
