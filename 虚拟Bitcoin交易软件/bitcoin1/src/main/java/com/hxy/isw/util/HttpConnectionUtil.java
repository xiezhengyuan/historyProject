package com.hxy.isw.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpConnectionUtil {
	private static Logger log = Logger.getLogger(HttpConnectionUtil.class);  
    
    public static String post(String url, Map<String, String> params) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
        log.info("create httppost:" + url);  
        HttpPost post = postForm(url, params);  
          
        body = invoke(httpclient, post);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
      
    public static String get(String url) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
        log.info("create httppost:" + url);  
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }  
          
      
    private static String invoke(DefaultHttpClient httpclient,  
            HttpUriRequest httpost) {  
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
          
        return body;  
    }  
  
    private static String paseResponse(HttpResponse response) {  
        log.info("get response from http server..");  
        HttpEntity entity = response.getEntity();  
        log.info("response status: " + response.getStatusLine());  
        String charset = EntityUtils.getContentCharSet(entity);  
        log.info(charset);  
        log.info("contentencodeing:"+entity.getContentEncoding()); 
        String body = "";  
        try {  
           body = EntityUtils.toString(entity,"UTF-8");  
        	/*InputStream is = entity.getContent();
        	 if (is == null) {
        		 body= null;
             }
        	 int i = (int)entity.getContentLength();
             if (i < 0) {
                 i = 4096;
             }
        	 Reader reader = new InputStreamReader(is, HTTP.UTF_8);
             CharArrayBuffer buffer = new CharArrayBuffer(i);
             char[] tmp = new char[1024];
             int l;
             while((l = reader.read(tmp)) != -1) {
                 buffer.append(tmp, 0, l);
             }
             body=buffer.toString();*/
            log.info(body);  
          //  is.close();
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    }  
  
    private static HttpResponse sendRequest(DefaultHttpClient httpclient,  
            HttpUriRequest httpost) {  
        log.info("execute post...");  
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
          
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
        	Sys.out(key+"..."+params.get(key));
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
            log.info("set utf-8 form entity to httppost");  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return httpost;  
    }  
    public static void main(String[] args){
    	Map<String, String> params = new HashMap<String, String>();  
    	params.put("item", "usermgr");  
    	params.put("method", "queryphone");  
    	params.put("phone", "13088888888");  
    	//String a = HttpConnectionUtil.get("http://127.0.0.1:6688/isw/mgr/dispatch.action?item=test");
    	
    	String xml = HttpConnectionUtil.post("http://127.0.0.1:6688/isw/mgr/dispatch.action", params);  
    	Sys.out("");
    } 
}
