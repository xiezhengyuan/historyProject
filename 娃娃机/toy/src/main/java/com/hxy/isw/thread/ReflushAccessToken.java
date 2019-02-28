package com.hxy.isw.thread;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.HttpConnectionUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

public class ReflushAccessToken implements Runnable {
	public static String TICKET;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true)
		{
			
			try {
				
				String result = getHXToken();
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				if(json.get("expires_in")==null){
					Sys.out("get accesstoken error sleep one min ...");
					Thread.sleep(60*1000);
				}else{
					int expires_in = Integer.parseInt(json.get("expires_in").getAsString());
					//getJsapiTicket(json.get("access_token").getAsString());
					Thread.sleep((expires_in-60)*1000);//防止过期，提前一分钟刷新access_token
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getHXToken(){
		String result = "";
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConstantUtil.APPID+"&secret="+ConstantUtil.SECRET;
		result =  HttpConnectionUtil.get(url);
		Sys.out("HX_TOKEN ... get ACCESS_TOKEN result:"+result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		String access_token = json.get("access_token")==null?"":json.get("access_token").getAsString();
		Sys.out(" HX_TOKEN:"+access_token);
		ConstantUtil.HX_TOKEN = access_token;
		return result;
	}
	
	private  String getAccessToken(){
		String result = "";
		
		String url = "https://a1.easemob.com/"+ConstantUtil.org_name+"/"+ConstantUtil.app_name+"/token";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type","client_credentials");
		params.put("client_id",ConstantUtil.client_id);
		params.put("client_secret",ConstantUtil.client_secret);
		
		result =  HttpConnectionUtil.post(url, params);
		Sys.out("get ACCESS_TOKEN result:"+result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		String access_token = json.get("access_token")==null?"":json.get("access_token").getAsString();
		Sys.out(" ACCESS_TOKEN:"+access_token);
		ConstantUtil.ACCESS_TOKEN = access_token;
		return result;
		
	}
	
	public static void main(String[] args) {
		//getHXToken();
		String token = "HB7cxFdsvlCC-JDb1ghPmGhyadc4g58t0ktjQhN2t-qA-Q8vmZUAAd_7DBfdwikPNn7C2-xtOYNuSSmxxGGNTogxASskbhn86lB3-dl5C5VsCZwH7371fn3Z0FLy6em1BDPgACAGIU";
		
		String url = "https://a1.easemob.com/"+ConstantUtil.org_name+"/"+ConstantUtil.app_name+"/users";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username","hxtestnew2");
		params.put("password","123456");
		params.put("nickname","hxtestnew2");
		/*
		String result =  HttpConnectionUtil.post(url, params);
		Sys.out("register result:"+result);*/
		
		String result = ConstantUtil.PostRequest(url,JsonUtil.getGson().toJson(params),null);
		Sys.out(result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		if(json.get("entities")!=null){
			JsonArray array = json.get("entities").getAsJsonArray();
			for (JsonElement jsonElement : array) {
				JsonObject jObject = jsonElement.getAsJsonObject();
				Sys.out("username.."+jObject.get("username").getAsString()+"..."+"nickname.."+jObject.get("nickname").getAsString());
				
			}
		}
	}

	private void getJsapiTicket(String access_token) throws Exception{
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		String result =  HttpConnectionUtil.get(url);
		Sys.out("getJsapiTicket..."+result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		String errmsg = json.get("errmsg").getAsString();
		
		if(errmsg.equals("ok")){
			TICKET = json.get("ticket").getAsString();
		}
	}
	
	
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
//        return UUID.randomUUID().toString();
        return ConstantUtil.getRandomNum(10);
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
}
