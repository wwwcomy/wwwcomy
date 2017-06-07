//package com.iteye.wwwcomy.lxn.utils;
//
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.Security;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * AES算法
// * 
// * @author wwwcomy
// *
// */
//public class AESUtils {
//    private static final AESUtils instance = new AESUtils();
//    byte[] iv = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };
//
//    byte[] keybytes = { 0x70, 0x2F, 0x17, 0x7F, 0x6C, 0x3A, 0x22, 0x11, 0x3F, 0x44, 0x5A, 0x66, 0x77, 0x1A, 0x12,
//            0x1C };
//
//    private AESUtils() {
//
//    }
//
//    public static AESUtils getInstance() {
//        return instance;
//    }
//
//    private Key key;
//    private byte[] enc;
//
//    public String encrypt(String msg) {
//        String str;
//        try {
//            Security.addProvider(new BouncyCastleProvider());
//            key = new SecretKeySpec(keybytes, "AES");
//            Cipher in = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
//            in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
//            enc = in.doFinal(msg.getBytes());
//            str = new String(Hex.encode(enc));
//            return str;
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String decrypt(byte[] msg) {
//        String str;
//        try {
//            Security.addProvider(new BouncyCastleProvider());
//            key = new SecretKeySpec(keybytes, "AES");
//
//            Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
//            out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
//            byte[] dec = out.doFinal(msg);
//            str = new String(dec);
//            return str;
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
