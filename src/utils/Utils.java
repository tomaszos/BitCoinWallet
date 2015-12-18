package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	Utils(){
		// utils
	}

	public static String getEncrypted(String str) throws NoSuchAlgorithmException{

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(str.getBytes());

		return new sun.misc.BASE64Encoder().encode(md.digest());
	}
}
