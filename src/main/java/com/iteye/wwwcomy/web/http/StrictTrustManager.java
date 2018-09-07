package com.iteye.wwwcomy.web.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * TrustManager will check strictly check if certificate is trustable
 */
public class StrictTrustManager implements X509TrustManager {
    private X509TrustManager trustManager;

    public StrictTrustManager(InputStream trustedStore, char[] password) throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, FileNotFoundException, NoSuchProviderException, IOException {
        this(trustedStore, password, KeyStore.getDefaultType());
    }
    
    public X509TrustManager getX509TrustManager(){
        return trustManager;
    }

    /**
     * Reads the keystore file and setup one trustManager.
     * 
     * @param trustedStore client trusted keystore file
     * @param password keystore password
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    public StrictTrustManager(InputStream trustedStore, char[] password, String keystoreType)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException,
            NoSuchProviderException {
        KeyStore ks = KeyStore.getInstance(keystoreType);
        ks.load(trustedStore, password);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        TrustManager tms[] = tmf.getTrustManagers();
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                trustManager = (X509TrustManager) tms[i];
                return;
            }
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] ax509certificate, String authType) throws CertificateException {
        trustManager.checkClientTrusted(ax509certificate, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] ax509certificate, String authType) throws CertificateException {
        trustManager.checkServerTrusted(ax509certificate, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return trustManager.getAcceptedIssuers();
    }
}
