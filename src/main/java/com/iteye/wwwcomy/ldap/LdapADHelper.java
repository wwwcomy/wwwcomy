package com.iteye.wwwcomy.ldap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * Windows Active Directory is not the same when connecting to openLDAP, root is
 * not needed, the user name should add the suffix of the domain. not needed,
 * 
 * @author liuxingn
 *
 */
@SuppressWarnings("restriction")
public class LdapADHelper {

	// private static String ldapURL = "ldap://16.187.134.34:10389/";
	private static String ldapURL = "ldap://16.187.188.127:389/";

	private static LdapContext ctx;

	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public static LdapContext getCtx() {
		if (ctx == null) {
			synchronized (LdapHelper.class) {
				if (ctx == null) {
					String account = "cn=person2,o=organization1"; // binddn
					String password = "1"; // bindpwd

					account = "testl@kserver1.com"; // binddn
					password = "1qaz@WSX"; // bindpwd

					Hashtable env = new Hashtable();
					env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
					env.put(Context.PROVIDER_URL, ldapURL);
					env.put(Context.SECURITY_AUTHENTICATION, "simple");
					// This account should be followed by root DN
					// + "," + root
					env.put(Context.SECURITY_PRINCIPAL, account);
					env.put(Context.SECURITY_CREDENTIALS, password);
					try {
						// 链接ldap
						ctx = new InitialLdapContext(env, null);
						System.out.println("认证成功");
					} catch (javax.naming.AuthenticationException e) {
						System.out.println("认证失败,密码输入错误");
						e.printStackTrace();
					} catch (Exception e) {
						System.out.println("认证出错：");
						e.printStackTrace();
					}
				}
			}
		}
		return ctx;
	}

	public static void closeCtx() {
		try {
			ctx.close();
		} catch (NamingException ex) {
			Logger.getLogger(LdapHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {

		// MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// 取出加密字符
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// 解码BASE64
		byte[] ldappwbyte = Base64.decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// 前20位是SHA-1加密段，20位后是最初加密时的随机明文
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}

		// 把用户输入的密码添加到摘要计算信息
		md.update(inputpw.getBytes());
		// 把随机明文添加到摘要计算信息
		md.update(salt);

		// 按SSHA把当前用户密码进行计算
		byte[] inputpwbyte = md.digest();

		// 返回校验结果
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	public static void main(String[] args) throws Exception {
		getCtx();
		// LdapContext instance = ctx.newInstance(null);
	}

}
