package com.hxy.isw.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Element;

public class IswDataSourse extends BasicDataSource {
	private static boolean flag = false;
	
	// don't edit this string
	private final static String DB_PWD_KEY = "this_str_is_password_key___ensure_length_with_48";

	@Override
	public synchronized void setPassword(String password) {
		if(!IswDataSourse.flag){
			init();
		}
		
		password = ConstantUtil.PASSWORD;
		password = JM(password);
		super.setPassword(password);
	}
	
	@Override
	public synchronized void setUrl(String url) {
		if(!IswDataSourse.flag){
			init();
		}
		url = ConstantUtil.URL;
		super.setUrl(url);
	}
	
	@Override
	public void setUsername(String username) {
		if(!IswDataSourse.flag){
			init();
		}
		username = ConstantUtil.USER_NAME;
		super.setUsername(username);
	}
	
	@Override
	public synchronized void setDriverClassName(String driverClassName) {
		if(!IswDataSourse.flag){
			init();
		}
		driverClassName = ConstantUtil.DRIVER_CLASS_NAME;
		super.setDriverClassName(driverClassName);
	}
	
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	public void init(){
		String path = this.getClass().getResource("/").getPath();
		ConstantUtil.PROJECT_PATH = path;
		DocumentManagerUtil.initDom(path+"isw_config.xml");
		
		Element e = (Element)ConstantUtil.DOM.getElementsByTagName("test").item(0);
		String test_use = e.getElementsByTagName("use").item(0).getTextContent();
		String test_password = e.getElementsByTagName("password").item(0).getTextContent();
		String test_url = e.getElementsByTagName("url").item(0).getTextContent();
		String test_userName = e.getElementsByTagName("username").item(0).getTextContent();
		String test_driverClassName = e.getElementsByTagName("driverClassName").item(0).getTextContent();
		
		
		Element e_real = (Element)ConstantUtil.DOM.getElementsByTagName("real").item(0);
		String real_use = e_real.getElementsByTagName("use").item(0).getTextContent();
		String real_password = e_real.getElementsByTagName("password").item(0).getTextContent();
		String real_url = e_real.getElementsByTagName("url").item(0).getTextContent();
		String real_userName = e_real.getElementsByTagName("username").item(0).getTextContent();
		String real_driverClassName = e_real.getElementsByTagName("driverClassName").item(0).getTextContent();
		
		try {
			if( "true".equals(test_use) && "true".equals(real_use) ){
				throw new Exception("sms_config.xml:test,real are use");
			}else if( "false".equals(test_use) && "false".equals(real_use) ){
				throw new Exception("sms_config.xml:test,real are no use");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		
		if( test_use.equals("true") ){
			ConstantUtil.PASSWORD = test_password;
			ConstantUtil.URL = test_url;
			ConstantUtil.USER_NAME = test_userName;
			ConstantUtil.DRIVER_CLASS_NAME = test_driverClassName;
		}else if( real_use.equals("true") ){
			ConstantUtil.PASSWORD = real_password;
			ConstantUtil.URL = real_url;
			ConstantUtil.USER_NAME = real_userName;
			ConstantUtil.DRIVER_CLASS_NAME = real_driverClassName;
		}
		IswDataSourse.flag = true;
	}
	
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static void main(String[] args) throws Exception {
		//根据原始密码生成密文	密码不能包含下岗线
		//String password = "7Tio90)M!pRM!xi7%m";//原始密码
//		String password = "bellatrix7";//原始密码
		String password="123456";
		byte[] a = DES3.tripleEncrypt(password.getBytes(), DB_PWD_KEY);
		Sys.out("--复制以下密文到配置文件--");
		String sss = bytesToHexString(a);
		Sys.out(sss);
		
	}
	
	public static String JM(String mw){
		String pwd = null;
		try {
			byte[] tsb = hexStringToBytes(mw);
			pwd = new String(DES3.tripleDecrypt(tsb, DB_PWD_KEY));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pwd;
	}

}