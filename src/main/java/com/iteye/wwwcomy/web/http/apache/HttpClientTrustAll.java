package com.iteye.wwwcomy.web.http.apache;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

public class HttpClientTrustAll {
	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		HttpUriRequest request = new HttpGet("https://www.baidu.com");
		CloseableHttpResponse response = httpClient.execute(request);
		System.out.println(IOUtils.toString(response.getEntity().getContent()));
	}

	private static CloseableHttpClient getHttpClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
					.build();
			CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			return httpClient;
		} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}

	public static CloseableHttpClient getHttpClientTls12() {
		try {
			SSLContext sslContext = SSLContexts.createDefault();

			SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1.2" }, null,
					new DefaultHostnameVerifier());

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(f).build();
			return httpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
