package com.iteye.wwwcomy.web.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsClient2 {
	private static final Logger logger = LoggerFactory.getLogger(HttpsClient2.class);
	private SSLSocketFactory sslSocketFactory;
	private HostnameVerifier hostnameVerifier;

	private boolean isTrustAll = true;
	private File trustStoreFile = new File("PATH_TO_YOUR_TRUST_STORE");
	// key store type : https://blog.csdn.net/kgn28/article/details/4099167
	private String trustStoreType = "JKS";
	private char[] trustStorePassword = "".toCharArray();

	private File keyStoreFile = new File("PATH_TO_YOUR_KEYSTORE");
	private String keyStoreType = "JKS";
	private char[] keyStorePassword = "".toCharArray();

	public void init() {
		FileInputStream trustStoreFileInputStream = null;
		FileInputStream keyStoreFileInputStream = null;
		TrustManager[] tms = new TrustManager[1];
		KeyManager[] kms = null;
		try {
			if (isTrustAll) {
				logger.info("init the trust manager with AllowAll");
				hostnameVerifier = new AllowAllHostnameVerifier();
				tms[0] = new AllowAllTrustManager();
			} else {
				logger.info("init the trust manager from file: {}", trustStoreFile.getAbsolutePath());
				if (null != trustStoreFile) {
					trustStoreFileInputStream = FileUtils.openInputStream(trustStoreFile);
					KeyStore ks = KeyStore.getInstance(trustStoreType);
					ks.load(trustStoreFileInputStream, trustStorePassword);
					TrustManagerFactory tmf = TrustManagerFactory
							.getInstance(TrustManagerFactory.getDefaultAlgorithm());// SunX509
					tmf.init(ks);
					for (TrustManager tm : tmf.getTrustManagers()) {
						if (tm instanceof X509TrustManager) {
							tms[0] = (X509TrustManager) tm;
							break;
						}
					}
				} else {
					throw new RuntimeException("trustStoreFile must set");
				}
				hostnameVerifier = new StrictHostnameVerifier();
			}

			logger.info("init the key manager from file: {}", keyStoreFile.getAbsolutePath());
			if (null != keyStoreFile) {
				keyStoreFileInputStream = FileUtils.openInputStream(keyStoreFile);
				KeyStore ks = KeyStore.getInstance(keyStoreType);
				ks.load(keyStoreFileInputStream, keyStorePassword);
				KeyManagerFactory tmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); // SunX509
				tmf.init(ks, keyStorePassword);
				kms = tmf.getKeyManagers();
			}
			// can also be TLS, might not support 1.2
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(kms, tms, new java.security.SecureRandom());
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException("");
		} finally {
			IOUtils.closeQuietly(trustStoreFileInputStream);
			IOUtils.closeQuietly(keyStoreFileInputStream);
		}
	}

	protected HttpURLConnection getUrlConnection(URL url, boolean isHttps) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (isHttps && null != sslSocketFactory) {
			((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection) connection).setHostnameVerifier(hostnameVerifier);
		}
		return connection;
	}

}
