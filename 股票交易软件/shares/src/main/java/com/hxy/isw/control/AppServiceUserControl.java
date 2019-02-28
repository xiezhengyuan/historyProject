package com.hxy.isw.control;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.PayInfoByWX;
import com.hxy.isw.service.AppServiceUser;
import com.hxy.isw.thread.ReflushAccessToken;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.HttpConnectionUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年7月20日 下午3:11:55
* @describe
*/


@Controller
@RequestMapping("/appServiceUser")
public class AppServiceUserControl {

	@Autowired
	AppServiceUser appServiceUser;
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getcode")
	public void getcode(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String type = request.getParameter("type");
		String mobile = request.getParameter("mobile");
		
		try{
			String json = appServiceUser.getcode(type,mobile);
			JsonUtil.success2client(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	 /**
	 * 登录
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public void queryemployee(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			
			String json = appServiceUser.login(mobile, password);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	 /**
	 * 注册
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/registe")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void register(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		String faccountid = request.getParameter("faccountid");//推广业务员id
		String finvateid = request.getParameter("finvateid");//邀请人id（用户）
		String openid = request.getParameter("openid");//
		String nickname = request.getParameter("nickname");
		String headimgurl = request.getParameter("headimgurl");
		try {
			String json = appServiceUser.registe(mobile, password,code,faccountid,finvateid,openid,nickname,headimgurl,request);
			JsonUtil.success2client(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			Sys.out("registe..exception..mess"+mess);
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @param session
	 */
    @RequestMapping(method = RequestMethod.POST, value = "/forgotpassword")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public void forgotpassword(HttpServletRequest request,HttpSession session,HttpServletResponse response){
    	String mobile = request.getParameter("mobile");
    	String newpassword = request.getParameter("newpassword");
    	String code = request.getParameter("code");
    	try {
    		String json = appServiceUser.forgotpassword(mobile, newpassword,code);
			JsonUtil.success2client(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
    }
    
    
    /**
     * 上传图片
     * @param request 请求       
     * @param response 响应
     * @param session 会话
	 * @RequestMapping 地址映射
	 * @Transactional 异常回滚
     */
	 @RequestMapping(method = RequestMethod.POST, value = "/uploadimg")
	 @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 public void uploadimg(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		try {
		 String stringbase64 = request.getParameter("base64");
		 String userid = request.getParameter("userid");
		 String type = request.getParameter("type");
		 String filename = ConstantUtil.GenerateImage(stringbase64);
		 if(filename==null)throw new Exception("图片参数错误");
		 
		 
		 boolean flag = type!=null&&type.length()>0&&Integer.parseInt(type)==1;
		 //如果type为1   则是用户头像
		 if(flag)appServiceUser.uploaduserphoto(userid,filename);
		 
		 Map<String,Object> map = new HashMap<String,Object>();
			
		 map.put("msg", "success");
		 map.put("info", "上传成功");
		 map.put("url", filename);
		 
		 String result = JsonUtil.getGson().toJson(map);
		 
		 JsonUtil.success2client(response, result);
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
	 }
    
    /**
	 * 近7日收益走势统计图
	 * @param request
	 * @param response
	 * @param session
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/sevendayprofit")
	public void sevendayprofit(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		
		try{
			String json = appServiceUser.sevendayprofit(userid);
			JsonUtil.success2client(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 我的收益数据
	 * @param request
	 * @param response
	 * @param session
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@RequestMapping(method = RequestMethod.POST, value = "/myprofit")
	public void myprofit(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		
		try{
			String json = appServiceUser.myprofit(userid);
			JsonUtil.success2client(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 充值
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/recharge")
	public void recharge(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String amount = request.getParameter("amount");
		int paymentway = request.getParameter("paymentway")==null?1:Integer.parseInt(request.getParameter("paymentway"));
		String ip = ConstantUtil.getIp(request);
		try{
			String json = appServiceUser.recharge(userid,amount,paymentway,ip);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取银行列表
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getbank")
	public void getbank(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try{
			String json = appServiceUser.getbank();
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 充值(PC)
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/rechargeOfPC")
	public void rechargeOfPC(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String amount = request.getParameter("amount");
		String paymentway = request.getParameter("paymentway");
		try{
			String json = appServiceUser.rechargeOfPC(userid,amount,paymentway);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	//PC第三方异步通知
	@RequestMapping(method = RequestMethod.POST, value = "/rechargeOfPCNotify")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void rechargeOfPCNotify(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		Sys.out("===============rechargeOfPC===notifyurl=======================");
		/*InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");
        Sys.out(result);
        result = result.replace("\n", "");
        Map<String, String> map = ConstantUtil.parse(result);*/
		Map<String, String> _map = new HashMap<String, String>();
		Map map=request.getParameterMap();  
	    Set keSet=map.entrySet();  
	    for(Iterator itr=keSet.iterator();itr.hasNext();){  
	        Map.Entry me=(Map.Entry)itr.next();  
	        Object ok=me.getKey();  
	        Object ov=me.getValue();  
	        String[] value=new String[1];  
	        if(ov instanceof String[]){  
	            value=(String[])ov;  
	        }else{  
	            value[0]=ov.toString();  
	        }  
	  
	        for(int k=0;k<value.length;k++){  
	            _map.put(ok.toString(), value[k]);  
	        }  
	      }  
        
        boolean flag = appServiceUser.rechargeOfPCNotify(_map);
        Sys.out(flag?"pc recharge success":"pc recharge fail");
        if(flag)JsonUtil.success2page(response, "success");
        else JsonUtil.success2page(response, "fail");
	}
	
	//微信支付异步通知
	@RequestMapping(method = RequestMethod.POST, value = "/wxnotify")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void wxnotify(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		Sys.out("===============wx===notifyurl=======================");
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");
        Sys.out(result);
        result = result.replace("\n", "");
        Map<String, String> map = ConstantUtil.parse(result);
       /* for(Object keyValue : map.keySet()){
            Sys.out(keyValue+"="+map.get(keyValue));
        }*/
        
        String return_code = map.get("return_code");
        String result_code = map.get("result_code");
        Sys.out("wx..pay..return_code:"+return_code);
        Sys.out("wx..pay..result_code:"+result_code);
        if("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code)){
        	
        	Sys.out("~~~~~~~~~~wx~~~~~~pay success~~~~~~~~~");
        	
        	PayInfoByWX p = new PayInfoByWX();
             p.setAppid(map.get("appid"));
             p.setAttach(map.get("attach"));
             p.setBanktype(map.get("bank_type"));
             p.setCashfee(Integer.parseInt(map.get("cash_fee")));
             p.setFeetype(map.get("fee_type"));
             p.setIssubscribe(map.get("is_subscribe"));
             p.setMchid(map.get("mch_id"));
             p.setNoncestr(map.get("nonce_str"));
             p.setOpenid(map.get("openid"));
             p.setOuttradeno(map.get("out_trade_no"));
             p.setResultcode(map.get("result_code"));
             p.setReturncode(map.get("return_code"));
             p.setSign(map.get("sign"));
             p.setTime(new Date());
             p.setTimeend(map.get("time_end"));
             p.setTotalfee(Integer.parseInt(map.get("total_fee")));
             p.setTradetype(map.get("trade_type"));
             p.setTransactionid(map.get("transaction_id"));
        	
             boolean flag = appServiceUser.wxnotify(p);
             
             if(flag)
            	 response.getWriter().write(setXML("SUCCESS", ""));
             else
            	 response.getWriter().write(setXML("FAIL", ""));
        }else{
        	response.getWriter().write(setXML("FAIL", ""));
        }
        
	}
	
	public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
	}
	
	//微信信息
	@RequestMapping(method = RequestMethod.POST, value = "/wxinfo")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public  void wxinfo(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		Map<String, String> map = new HashMap<String, String>();
		if(code!=null&&state!=null){
    	 	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ConstantUtil.APPID+"&secret="+ConstantUtil.SECRET+"&code="+code+"&grant_type=authorization_code";
    	 	String result =  HttpConnectionUtil.get(url);
    	 	Sys.out(result);
    	 	JsonObject json = (JsonObject) new JsonParser().parse(result);
    	 	if(json.get("errcode")==null){
    	 		String openid = json.get("openid").getAsString();
    	 		Sys.out("openid:"+openid);
    	 		url = "https://api.weixin.qq.com/sns/userinfo?access_token="+json.get("access_token").getAsString()+"&openid="+openid+"&lang=zh_CN";
    	 		String msg = HttpConnectionUtil.get(url);
    	 		Sys.out(msg);
    	 		JsonObject jsonmsg = (JsonObject) new JsonParser().parse(msg);
    	 		if(jsonmsg.get("errcode")==null){
    	 			 openid = jsonmsg.get("openid").getAsString();
   	 				 String nickname =   jsonmsg.get("nickname").getAsString();
   	 				 int sex = Integer.parseInt(jsonmsg.get("sex").getAsString());
   	 				 String city =   jsonmsg.get("city").getAsString();
   	 				 String province =   jsonmsg.get("province").getAsString();
   	 				 String country =   jsonmsg.get("country").getAsString();
   	 				 String headimgurl =   jsonmsg.get("headimgurl").getAsString();
   	 				 
   	 				 map.put("nickname",nickname);
   	 				 map.put("sex",jsonmsg.get("sex").getAsString()); 
   	 				 map.put("city",city);
   	 				 map.put("province",province);
   	 				 map.put("country",country);
   	 				 map.put("headimgurl",headimgurl);
    	 				 
   	 				 map.put("openid", openid);
   	 				 map.put("msg", "success");
    	 		}else{
    	 			 map.put("msg", "fail");
    	 		}
    	 		
    	 	}else{
    	 		 map.put("msg", "fail");
    	 	}
    	 }else{
    		 map.put("msg", "fail");
    	 }
		
		String json = JsonUtil.getGson().toJson(map);
		
		JsonUtil.success2client(response, json);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/getwechatJsData")
	public void getwechatJsData(HttpSession session,HttpServletRequest request,HttpServletResponse response){
		try {
			String my_url = "http://mobilegp.runfkj.com";
			//my_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx44f90f8d53c3c9be&redirect_uri=http%3A%2F%2Flcc.so%2Fmp%2Fx&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
			//my_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx44f90f8d53c3c9be&redirect_uri=http%3A%2F%2Flcc.so%2Fmp%2Fx&response_type=code&scope=snsapi_base&state=STATE";
			my_url = request.getParameter("url");
			Sys.out("my_url:"+my_url);
			Map<String,String> map = new HashMap<String,String>();
			
			ConstantUtil.NONCE_STR = ReflushAccessToken.create_nonce_str();
			ConstantUtil.TIMESTAMP = ReflushAccessToken.create_timestamp();
			
			String string1 = "jsapi_ticket=" + ReflushAccessToken.TICKET +
            "&noncestr=" + ConstantUtil.NONCE_STR +
            "&timestamp=" + ConstantUtil.TIMESTAMP +
            "&url=" + my_url;
			Sys.out("string1:"+string1);
			
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			
			
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            ConstantUtil.SIGNATURE = ReflushAccessToken.byteToHex(crypt.digest());
			
           /* byte[] digest = crypt.digest(string1.toString().getBytes());
            ConstantUtil.SIGNATURE = ConstantUtil.byteToStr(digest);*/
			//ConstantUtil.SIGNATURE = SHA1Utils.hex_sha1(string1);
            Sys.out("ConstantUtil.SIGNATURE:"+ConstantUtil.SIGNATURE);
			
			map.put("appId", ConstantUtil.APPID);
			map.put("timestamp", ConstantUtil.TIMESTAMP);
			map.put("nonceStr",ConstantUtil.NONCE_STR);
			map.put("signature", ConstantUtil.SIGNATURE);
			map.put("string1", string1);
			
			String json = new Gson().toJson(map);
			Sys.out("getwechatJsData.."+json);
			response.setContentType("text/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/test")
	public void test(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String spreadurl= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConstantUtil.APPID+"&redirect_uri="+ConstantUtil.MOBILEURL+"?finvateid=1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			String filePath = request.getRealPath("/qrcode"); //获取文件需要上传到的路径;
			String ercode = ConstantUtil.genEncode(spreadurl,filePath);
			Sys.out("ercode..."+ercode);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/exit")
	public void exit(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String userid = request.getParameter("userid");
			String url= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConstantUtil.APPID+"&redirect_uri="+ConstantUtil.MOBILEURL+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"已成功退出\",\"url\":\""+url+"\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
