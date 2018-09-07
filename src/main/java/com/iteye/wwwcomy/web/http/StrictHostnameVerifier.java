package com.iteye.wwwcomy.web.http;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InvalidNameException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrictHostnameVerifier implements HostnameVerifier {
	private static final Logger logger = LoggerFactory.getLogger(StrictHostnameVerifier.class);

	List<String> extractSubjectAlternativeNames(X509Certificate cert, int subjectType) {
		Collection<List<?>> c = null;
		try {
			c = cert.getSubjectAlternativeNames();
		} catch (CertificateParsingException ignore) {
		}
		List<String> subjectAltList = new ArrayList<String>();
		if (null != c) {
			for (List<?> subjectAlt : c) {
				if (((Integer) (subjectAlt.get(0))).intValue() == subjectType) {
					subjectAltList.add((String) subjectAlt.get(1));
				}
			}
		}
		return subjectAltList;
	}

	@Override
	public boolean verify(String host, SSLSession session) {
		java.security.cert.Certificate certs[] = null;
		try {
			certs = session.getPeerCertificates();
		} catch (SSLPeerUnverifiedException e) {
			logger.error("Failed to getPeerCertificates", e);
		}
		if (null == certs || 0 == certs.length) {
			return false;
		}
		X509Certificate x509 = (X509Certificate) certs[0];
		X500Principal subjectPrincipal = x509.getSubjectX500Principal();
		String cn = "";
		try {
			cn = extractCN(subjectPrincipal.getName("RFC2253"));
		} catch (Exception e) {
			logger.error("Failed to extractCN", e);
		}
		return host.equalsIgnoreCase(cn);
	}

	String extractCN(String subjectPrincipal) throws InvalidNameException, SSLException {
		if (subjectPrincipal == null)
			return null;
		LdapName subjectDN = new LdapName(subjectPrincipal);
		List<Rdn> rdns = subjectDN.getRdns();
		for (Rdn rds : rdns) {
			Attribute cn;
			Attributes attributes = rds.toAttributes();
			cn = attributes.get("cn");
			if (cn != null) {
				try {
					Object value = cn.get();
					if (value != null) {
						return value.toString();
					}
				} catch (Exception ignore) {
					throw new SSLException((new StringBuilder()).append(subjectPrincipal)
							.append(" is not a valid X500 distinguished name").toString());
				}
			}
		}
		return null;
	}
}
