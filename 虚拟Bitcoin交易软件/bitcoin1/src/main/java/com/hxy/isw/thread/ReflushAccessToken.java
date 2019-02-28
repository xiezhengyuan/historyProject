package com.hxy.isw.thread;

import java.util.Formatter;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.HttpConnectionUtil;
import com.hxy.isw.util.Sys;

public class ReflushAccessToken implements Runnable {
	public static String TICKET;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean b=false;
		while (b)
		{
			
			try {
				
				String result = getAccessToken();
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				if(json.get("expires_in")==null){
					Sys.out("get accesstoken error sleep one min ...");
					Thread.sleep(60*1000);
				}else{
					int expires_in = Integer.parseInt(json.get("expires_in").getAsString());
					getJsapiTicket(json.get("access_token").getAsString());
					Thread.sleep((expires_in-60)*1000);//防止过期，提前一分钟刷新access_token
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getAccessToken(){
		String result = "";
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConstantUtil.APPID+"&secret="+ConstantUtil.SECRET;
		result =  HttpConnectionUtil.get(url);
		Sys.out("get ACCESS_TOKEN result:"+result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		String access_token = json.get("access_token")==null?"":json.get("access_token").getAsString();
		Sys.out(" ACCESS_TOKEN:"+access_token);
		ConstantUtil.ACCESS_TOKEN = access_token;
		return result;
		
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
