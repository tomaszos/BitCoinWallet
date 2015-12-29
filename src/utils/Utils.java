package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

public class Utils {

	Utils(){
		// utils
	}

	public static String getEncrypted(String str) throws NoSuchAlgorithmException{

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(str.getBytes());

		return new sun.misc.BASE64Encoder().encode(md.digest());
	}

	public static String getKeyFromHeaders(HttpHeaders headers){
		List<String> restKey = headers.getRequestHeader("REST_KEY");

		if (restKey != null && restKey.size() > 0)
		{
			return restKey.get(0);
		}

		return null;
	}
}
