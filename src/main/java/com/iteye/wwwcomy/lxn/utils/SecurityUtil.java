/*
 * Copyright (c) 2009 Wincor Nixdorf International GmbH,
 * Heinz-Nixdorf-Ring 1, 33106 Paderborn, Germany
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Wincor Nixdorf ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered
 * into with Wincor Nixdorf.
 */

package com.iteye.wwwcomy.lxn.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public final class SecurityUtil {

	private static final byte[] SALT = { 13, -118, 31, -63, 115, -60, 62, 50,
			-125, -85, 67, 49, 7, -95, 50, 74, 100, 2, -75, -89, -60, 82, 52,
			122 };
	
	private static Cipher cipher;
	
	static{
		try {
			cipher = Cipher.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			DebugUtil.debug("DESede Arithmetic Can Not Be Used Error!");
		} catch (NoSuchPaddingException e) {
			DebugUtil.debug("DESede Arithmetic Can Not Be Used Error!");
		} 
	}

	/**
     * private constructor
     */
	private SecurityUtil() {
	}

	/**
     * encrypt data by md5
     * 
     * @param strData
     *            original data.
     * @return String encrypted data.
     */
	public static String encryptString(String strData) {
		strData = strData.trim();
		StringBuffer digest = new StringBuffer();
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
			currentAlgorithm.reset();
			byte[] mess = strData.getBytes();
			byte[] hash = currentAlgorithm.digest(mess);
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i];
				if (v < 0) {
					v = 256 + v;
				}
				if (v < 16) {
					digest.append("0");
				}
				digest.append(Integer.toString(v, 16).toUpperCase());
			}
		} catch (NoSuchAlgorithmException e) {
			DebugUtil.debug("MD5 Arithmetic Can Not Be Used Error!");
		}
		return digest.toString();
	}

	public static String saltHash(String strData, byte[] salt) {
		String encoded = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			byte[] hashed = digest.digest(strData.getBytes("UTF-8"));
			encoded = Base64.encodeBytes(hashed);
		} catch (Exception e) {
			DebugUtil.debug("salt hash failed");
		}
		return encoded;
	}

	public static String defaultSaltHash(String strData) {
		String encoded = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(SALT);
			byte[] hashed = digest.digest(strData.getBytes("UTF-8"));
			encoded = Base64.encodeBytes(hashed);
		} catch (Exception e) {
			DebugUtil.debug("salt hash failed");
		}
		return encoded;
	}

	public static String encrypt(String strData, byte[] key) {
		byte[] encrypted = new byte[0];
		try {
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
			encrypted = cipher.doFinal(strData.getBytes("gb2312"));
		} catch (InvalidKeyException e) {
			DebugUtil.debug("Encrypt failed.");
		} catch (IllegalBlockSizeException e) {
			DebugUtil.debug("Encrypt failed.");
		} catch (BadPaddingException e) {
			DebugUtil.debug("Encrypt failed.");
		} catch (UnsupportedEncodingException e) {
			DebugUtil.debug("Encrypt failed.");
		}		
		String encryptedStr = Base64.encodeBytes(encrypted);		
		return encryptedStr;
	}

	public static String decrypt(String strData, byte[] key) {
		byte[] decrypted = new byte[0];
		try {
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
			decrypted = cipher.doFinal(Base64.decode(strData));
		} catch (InvalidKeyException e) {
			DebugUtil.debug("Decrypt failed.");
		} catch (IllegalBlockSizeException e) {
			DebugUtil.debug("Decrypt failed.");
		} catch (BadPaddingException e) {
			DebugUtil.debug("Decrypt failed.");
		}
		
		String decryptedStr = new String(decrypted);
		return decryptedStr;
	}
	
	private static SecretKey getSecretKey(byte[] key){
		SecretKey secretkey = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(key);
		try {
			ObjectInputStream ois = new ObjectInputStream(bais);
			secretkey = (SecretKey) ois.readObject();
		} catch (IOException e) {
			DebugUtil.debug("Get secret key failed.");
		} catch (ClassNotFoundException e) {
			DebugUtil.debug("Get secret key failed.");
		}
		return secretkey;
	}
	
	
}