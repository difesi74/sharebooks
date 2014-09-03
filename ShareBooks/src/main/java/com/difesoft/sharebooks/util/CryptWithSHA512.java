package com.difesoft.sharebooks.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptWithSHA512 {
	
	public static String getSecurePassword(String passwordToHash) {
		
		StringBuilder sb = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordToHash.getBytes());
			byte[] bytes = md.digest();
			sb = new StringBuilder();
			for (int i = 0; i < bytes.length ; i++) {
				sb.append(Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1));
			}
		} 
		catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
		}
	   return sb.toString();	
	}
}
