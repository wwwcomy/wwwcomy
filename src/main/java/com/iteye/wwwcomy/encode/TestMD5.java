package com.iteye.wwwcomy.encode;

import java.security.MessageDigest;

/**
 * MD5加解密,同时包括了SHA算法 BASE64的加密解密是双向的，可以求反解。
 * MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串
 * ，通常用来校验数据在传输过程中是否被修改。其中HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。
 * 单向加密的用途主要是为了校验数据在传输过程中是否被修改。
 * 
 * @author wwwcomy
 * 
 */
public class TestMD5 {

	public static void main(String[] args) throws Exception {
		String words = "Hello world!";
		String encryptWords = new String(encryptMD5(words.getBytes()));
		System.out.println(encryptWords);
	}

	/**
	 * MD5加密:通常我们不直接使用以下MD5加密,而是将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(data);
		return sha.digest();
	}

}
