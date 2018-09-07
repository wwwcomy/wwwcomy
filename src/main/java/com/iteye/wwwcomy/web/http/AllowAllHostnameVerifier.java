package com.iteye.wwwcomy.web.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllowAllHostnameVerifier implements HostnameVerifier{

    @Override
    public boolean verify(String arg0, SSLSession arg1) {
        return true;
    }
}
