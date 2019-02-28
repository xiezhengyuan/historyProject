package com.hxy.isw.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
* @author lcc
* @date 2017年7月27日 下午6:15:49
* @describe   nowapi   美股  外汇工具表
*/
public class NowApiUtil {

	
	//获取美股指数
	public static String getShareIndexOfUS(String symbol) throws Exception{
		
		URL u=new URL("http://api.k780.com/?app=finance.stock_realtime&symbol="+symbol+"&appkey="+ConstantUtil.appkey+"&sign="+ConstantUtil.sign+"&format=json");
		 InputStream in=u.openStream();
		 ByteArrayOutputStream out=new ByteArrayOutputStream();
		 try {
		     byte buf[]=new byte[1024];
		     int read = 0;
		     while ((read = in.read(buf)) > 0) {
		         out.write(buf, 0, read);
		     }
		 }  finally {
		     if (in != null) {
		         in.close();
		     }
		 }
		 byte b[]=out.toByteArray( );
		 String result = new String(b,"utf-8");
		 
		 
		 return result;
	}
	
	
	//获取外汇指数--nowapi
	/*public static String getWHIndex(String scur) throws Exception{
		
		URL u=new URL("http://api.k780.com/?app=finance.rate&scur="+scur+"&tcur=CNY&appkey="+ConstantUtil.appkey+"&sign="+ConstantUtil.sign+"&format=json");
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        String result = new String(b,"utf-8");
        System.out.println(new String(b,"utf-8"));
		 
		 return result;
	}*/
	
	public static void main(String[] args) {
		try {
			//getWHIndexByAli("CNH");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*String host = "http://jisuhuilv.market.alicloudapi.com";
	    String path = "/exchange/currency";
	    String method = "GET";
	    String appcode = "272e6eb1e31946b18cfbf844b60bf75b";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();


	    try {
	    	*//**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*//*
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }*/
	}
	//获取外汇指数--aliyun
	/*public static String getWHIndexByAli(String scur) throws Exception{
		String host = "http://jisuhuilv.market.alicloudapi.com";
	    String path = "/exchange/convert";
	    String method = "GET";
	    String appcode = "272e6eb1e31946b18cfbf844b60bf75b";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("amount", "1");
	    querys.put("from", scur);
	    querys.put("to", "CNY");
	    String body = "";  
	    try {
	    	*//**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*//*
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	//Sys.out(response.toString());
	    	HttpEntity entity = response.getEntity(); 
	    	body = EntityUtils.toString(entity,"UTF-8");  
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    Sys.out("getWHIndexByAli."+scur+".result.."+body);
	    return body;
	}*/
}
