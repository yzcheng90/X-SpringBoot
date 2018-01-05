package com.suke.czx.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class CDESCrypt {

	    public CDESCrypt() {
	    }

	    public static String encryptString(String message,String key) throws Exception {
	        return new String(Base64.encodeBase64(encrypt(message, key)));
	    }

	    public static String encryptAsHexString(String message,String key) throws Exception {
	        return toHexString(encrypt(message, key));
	    }

	    public static byte[] encrypt(String message, String key) throws Exception {
	        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
	        cipher.init(1, secretKey, iv);
	        byte[] bytes = message.getBytes("UTF-8");
	        return cipher.doFinal(bytes);
	    }

	    public static String decryptString(String message,String key) throws Exception {
	  
            byte[] bytes = Base64.decodeBase64(message.getBytes("UTF-8"));
            return decrypt(bytes, key);
	        
	    }

	    public static String decryptAsHexString(String message,String key) throws Exception {
	    	byte[] bytes = convertHexString(message);
            return decrypt(bytes, key);
	    }

	    public static String decrypt(byte[] bytes, String key) throws Exception {
	        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
	        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
	        cipher.init(2, secretKey, iv);
	        byte[] retBytes = cipher.doFinal(bytes);
	        return new String(retBytes);
	    }

	    public static byte[] convertHexString(String ss) {
	        byte[] digest = new byte[ss.length() / 2];

	        for(int i = 0; i < digest.length; ++i) {
	            String byteString = ss.substring(2 * i, 2 * i + 2);
	            int byteValue = Integer.parseInt(byteString, 16);
	            digest[i] = (byte)byteValue;
	        }

	        return digest;
	    }

	    public static String toHexString(byte[] b) {
	        StringBuffer hexString = new StringBuffer();

	        for(int i = 0; i < b.length; ++i) {
	            String plainText = Integer.toHexString(255 & b[i]);
	            if(plainText.length() < 2) {
	                plainText = "0" + plainText;
	            }

	            hexString.append(plainText);
	        }

	        return hexString.toString();
	    }
}
