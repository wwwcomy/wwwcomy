package com.iteye.wwwcomy.encode;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加解密
 * 
 * @author wwwcomy
 * 
 */
@SuppressWarnings("restriction")
public class TestBase64 {

	public static void main(String[] args) throws Exception {
		String words = "Hello world!";
		String encryptWords = encryptBASE64(words.getBytes());
		System.out.println(encryptWords);
		System.out.println(new String(decryptBASE64(encryptWords)));
	}

	/**
	 * BASE64加密,对相同byte[]生成的加密结果是相同的
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

}
