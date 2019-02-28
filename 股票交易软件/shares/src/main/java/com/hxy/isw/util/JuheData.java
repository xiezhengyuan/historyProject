package com.hxy.isw.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
* @author lcc
* @date 2017年9月24日 上午11:13:47
* @describe
*/
public class JuheData {
	 public static final String DEF_CHATSET = "UTF-8";
	    public static final int DEF_CONN_TIMEOUT = 30000;
	    public static final int DEF_READ_TIMEOUT = 30000;
	    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	 
	    //配置您申请的KEY
	    public static final String APPKEY ="844913b37452e38deafa64f81d1c64a0";
	 
	    //1.沪深股市
	    public static void getRequest1(){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/hs";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("gid","");//股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009
	            params.put("key",APPKEY);//APP Key
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //2.香港股市
	    public static JsonObject getRequest2(String num){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/hk";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("num",num);//股票代码，如：00001 为“长江实业”股票代码
	            params.put("key",APPKEY);//APP Key
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	            	JsonObject jObject = object.get("result").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject();
	                System.out.println(jObject);
	                return jObject;
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    //3.美国股市
	    public static JsonObject getRequest3(String num){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/usa";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("gid",num);//股票代码，如：aapl 为“苹果公司”的股票代码
	            params.put("key",APPKEY);//APP Key
	 
	        try {
	            result =net(url, params, "GET");
	            
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	            	JsonObject jObject = object.get("result").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonObject();
	                System.out.println(jObject);
	                return jObject;
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return null;
	    }
	 
	    //4.香港股市列表
	    public static void getRequest4(){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/hkall";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("key",APPKEY);//您申请的APPKEY
	            params.put("page","");//第几页,每页20条数据,默认第1页
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //5.美国股市列表
	    public static void getRequest5(){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/usaall";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("key",APPKEY);//您申请的APPKEY
	            params.put("page","");//第几页,每页20条数据,默认第1页
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //6.深圳股市列表
	    public static void getRequest6(){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/szall";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("key",APPKEY);//您申请的APPKEY
	            params.put("page","");//第几页(每页20条数据),默认第1页
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    //7.沪股列表
	    public static void getRequest7(){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/shall";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("key",APPKEY);//您申请的APPKEY
	            params.put("page","");//第几页,每页20条数据,默认第1页
	 
	        try {
	            result =net(url, params, "GET");
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	                System.out.println(object.get("result"));
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 //获取最新价格
	    public static String getnowprice(String scur){
	    	try {
	    		
				String result = HttpRequest.sendGet("http://121.196.202.153:60123/","query=price&symbol="+scur+"&type=jsonret");
				result = result.replace("jsonret(", "").replace(")", "");
				JsonObject object = (JsonObject) new JsonParser().parse(result);
				
				System.out.println("getresult.."+result);
				String price = object.get("list").getAsJsonArray().size()==0?"0":object.get("list").getAsJsonArray().get(0).getAsJsonObject().get("price").getAsString();
				return price;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
	    
	    //美股K线
	    public static JsonObject getKlineUs(String num){
	        String result =null;
	        String url ="http://web.juhe.cn:8080/finance/stock/usa";//请求接口地址
	        Map params = new HashMap();//请求参数
	            params.put("gid",num);//股票代码，如：aapl 为“苹果公司”的股票代码
	            params.put("key",APPKEY);//APP Key
	 
	        try {
	            result =net(url, params, "GET");
	            
	            //JSONObject object = JSONObject.fromObject(result);
	            JsonObject object = (JsonObject) new JsonParser().parse(result);
	            if(object.get("error_code").getAsInt()==0){
	            	JsonObject jObject = object.get("result").getAsJsonArray().get(0).getAsJsonObject().get("gopicture").getAsJsonObject();
	                System.out.println(jObject);
	                return jObject;
	            }else{
	                System.out.println(object.get("error_code")+":"+object.get("reason"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return null;
	    }
	 
	    public static void main(String[] args) {
	    	//getRequest3("ndaq");
	    	//getRequest2("HSI");//国企
	    	//getRequest2("HSCEI");//国企
	    	//getRequest2("HSCCI");//红筹
	    	System.out.println(getnowprice("S-AABA"));
	    	
	    	/*try {
	    		Map params = new HashMap();//请求参数
	            params.put("query","price");
	            params.put("symbol","H-0002");
	            params.put("type","H-0002");
	    		
				String result = HttpRequest.sendGet("http://121.196.202.153:60123/","query=price&symbol=H-0002&type=json");
				System.out.println("getresult.."+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    }
	 
	    /**
	     *
	     * @param strUrl 请求地址
	     * @param params 请求参数
	     * @param method 请求方法
	     * @return  网络请求字符串
	     * @throws Exception
	     */
	    public static String net(String strUrl, Map params,String method) throws Exception {
	        HttpURLConnection conn = null;
	        BufferedReader reader = null;
	        String rs = null;
	        try {
	            StringBuffer sb = new StringBuffer();
	            if(method==null || method.equals("GET")){
	                strUrl = strUrl+"?"+urlencode(params);
	            }
	            URL url = new URL(strUrl);
	            conn = (HttpURLConnection) url.openConnection();
	            if(method==null || method.equals("GET")){
	                conn.setRequestMethod("GET");
	            }else{
	                conn.setRequestMethod("POST");
	                conn.setDoOutput(true);
	            }
	            conn.setRequestProperty("User-agent", userAgent);
	            conn.setUseCaches(false);
	            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
	            conn.setReadTimeout(DEF_READ_TIMEOUT);
	            conn.setInstanceFollowRedirects(false);
	            conn.connect();
	            if (params!= null && method.equals("POST")) {
	                try {
	                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	                        out.writeBytes(urlencode(params));
	                } catch (Exception e) {
	                    // TODO: handle exception
	                }
	            }
	            InputStream is = conn.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
	            String strRead = null;
	            while ((strRead = reader.readLine()) != null) {
	                sb.append(strRead);
	            }
	            rs = sb.toString();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                reader.close();
	            }
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
	        return rs;
	    }
	 
	    //将map型转为请求参数型
	    public static String urlencode(Map<String,Object>data) {
	        StringBuilder sb = new StringBuilder();
	        for (Map.Entry i : data.entrySet()) {
	            try {
	                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
}
