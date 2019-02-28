package com.hxy.isw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
* @author lcc
* @date 2017年7月20日 下午4:06:18
* @describe
*/
public class SignatureUtil {

	public static final String TOKEN = "RVnfEngkj2017sHAresParad1se12l";
	
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		 
	    String[] arr = { TOKEN, timestamp, nonce };

	    Arrays.sort(arr);
	    StringBuilder content = new StringBuilder();
	    for (int i = 0; i < arr.length; i++) {
	      content.append(arr[i]);
	    }
	    //Sys.out(content.toString());
	    MessageDigest md = null;
	    String tmpStr = null;
	    try
	    {
	      md = MessageDigest.getInstance("SHA-1");

	      byte[] digest = md.digest(content.toString().getBytes());
	      tmpStr = byteToStr(digest);
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }

	    content = null;
	    //Sys.out(tmpStr);
	    return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	  }
		public static String byteToStr(byte[] byteArray) {
		    String strDigest = "";
		    for (int i = 0; i < byteArray.length; i++) {
		      strDigest = strDigest + byteToHexStr(byteArray[i]);
		    }
		    return strDigest;
		  }

		  public static String byteToHexStr(byte mByte) {
		    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		    char[] tempArr = new char[2];
		    tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
		    tempArr[1] = Digit[(mByte & 0xF)];

		    String s = new String(tempArr);
		    return s;
		  }
		  
		  
		  public static String getSignature(String token,String timestamp, String nonce) {
				String[] arr = new String[] { token, timestamp, nonce };
				// 将token、timestamp、nonce三个参数进行字典序排序
				Arrays.sort(arr);
				StringBuilder content = new StringBuilder();
				for (int i = 0; i < arr.length; i++) {
					content.append(arr[i]);
				}
				MessageDigest md = null;
				String tmpStr = null;

				try {
					md = MessageDigest.getInstance("SHA-1");
					// 将三个参数字符串拼接成一个字符串进行sha1加密
					byte[] digest = md.digest(content.toString().getBytes());
					tmpStr = byteToStr(digest);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}

				content = null;
				return tmpStr;
			}
	
}
