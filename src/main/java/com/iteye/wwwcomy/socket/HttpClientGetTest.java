package com.iteye.wwwcomy.socket;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientGetTest {

	public static void main(String[] args) throws HttpException, IOException {
		GetMethod method = new GetMethod("http://localhost:8080");
		HttpClient client = new HttpClient();
		int status = client.executeMethod(method);
		String content = method.getResponseBodyAsString();
		System.out.println("Status code:" + status);
		System.out.println(content);
	}

}
