package com.iteye.wwwcomy.web.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Trust manager that blindly accepts all certificates
 */
public class AllowAllTrustManager implements X509TrustManager {
    /**
     * Given the partial or complete certificate chain provided by the peer, build a certificate path to a trusted root
     * and return whether it can be validated and is trusted for client SSL authentication based on the authentication
     * type.
     *
     * @param x509Certificates The peer certificate chain
     * @param authType The authentication type based on the client certificate
     *
     * @throws CertificateException The certificate chain is not trusted by this {@link TrustManager}
     */
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
        // Do nothing: should implicitly trust all
    }

    /**
     * Given the partial or complete certificate chain provided by the peer, build a certificate path to a trusted root
     * and return whether it can be validated and is trusted for client SSL authentication based on the authentication
     * type.
     *
     * @param x509Certificates The peer certificate chain
     * @param authType The key exchange algorithm used
     *
     * @throws CertificateException if the certificate chain is not trusted by this {@link TrustManager}
     */
    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
        // Do nothing; should implicitly trust all
    }

    /**
     * Return an array of certificate authority certificates which are trusted for authenticating peers.
     *
     * @return a non-{@code null} (possibly empty) array of acceptable CA issuer certificates
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}