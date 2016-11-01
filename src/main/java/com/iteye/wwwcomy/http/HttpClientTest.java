package com.iteye.wwwcomy.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientTest {
	static String adfs1 = "https://www.findmima.com/ajax.php?act=select";

	public static void main(String[] args) throws Exception {
		String url = "http://www.findmima.com";
		new HttpClientTest().testGet(url);
		new HttpClientTest().testPost(adfs1);
	}

	private void testGet(String url) {
		HttpClient client = getClient();
		HttpGet get = new HttpGet(url);
		get.addHeader("Authorization", "Basic ZmluZG1pbWE6ZmluZG1pbWE=");
		ResponseHandler responseHandler = new BasicResponseHandler();
		String responseBody;
		try {
			responseBody = client.execute(get, responseHandler);
			System.out.println(responseBody);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testPost(String url) {
		HttpClient client = getClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Authorization", "Basic ZmluZG1pbWE6ZmluZG1pbWE=");

		List<NameValuePair> params = new ArrayList<NameValuePair>(4);
		// select_act=4&match_act=1&key=hello.lq&table=mydb3
		params.add(new BasicNameValuePair("select_act", "4"));
		params.add(new BasicNameValuePair("match_act", "1"));
		params.add(new BasicNameValuePair("table", "mydb3"));
		params.add(new BasicNameValuePair("key", "123456"));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			ResponseHandler responseHandler = new BasicResponseHandler();
			String responseBody;
			responseBody = client.execute(httpPost, responseHandler);
			System.out.println(responseBody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HttpClient getClient() {
		HttpClient httpclient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			return httpclient;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
