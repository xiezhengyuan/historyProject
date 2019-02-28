package com.hxy.isw.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Consumption;
import com.hxy.isw.entity.Designation;
import com.hxy.isw.entity.Gears;
import com.hxy.isw.entity.PayInfoByWx;
import com.hxy.isw.entity.PayinfoByAli;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AppServicePay;
import com.hxy.isw.util.AlipayConfig;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.MD5;
import com.hxy.isw.util.SignUtils;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年6月30日 上午10:58:58
* @describe
*/
@Repository
public class AppServicePayImpl implements AppServicePay {

	@Autowired
	DatabaseHelper databaseHelper;
	
	@Override
	public String gears(String userid) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		StringBuffer query = new StringBuffer("select g from Gears g where g.state = 0  order by g.golds asc");
		
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		for (Object object : lst) {
			Gears g = (Gears)object;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("gearsid", g.getId());
			map.put("golds", g.getGolds());
			map.put("price", g.getPrice());
			
			lstMap.add(map);
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",lstMap.size());
		obj.add("rows", arr);
		
		return obj.toString();
	} 
	
	
	@Override
	public String recharge(String userid, String gearsid, String paymentway,String ip) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		if(gearsid==null||gearsid.length()==0)throw new Exception("充值档位id错误");
		if(paymentway==null||paymentway.length()==0)throw new Exception("支付方式参数错误");
		
		//获取价格
		Gears g = (Gears)databaseHelper.getObjectById(Gears.class, Long.parseLong(gearsid));
		if(g==null)throw new Exception("充值档位信息错误");
		
		//用户信息
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		if(ui==null)throw new Exception("用户信息错误");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		
		//添加到消费记录表
		Consumption consumption = new Consumption();
		consumption.setBalance(ui.getBalance());
		consumption.setCreatetime(new Date());
		consumption.setFmachineid(null);
		consumption.setFuserinfoid(ui.getId());
		consumption.setGolds(g.getGolds());
		consumption.setOrderno(sdf.format(now));
		consumption.setPaymentway(Integer.parseInt(paymentway));
		consumption.setPaystate(0);
		consumption.setType(1);
		consumption.setFgearsid(g.getId());
		databaseHelper.persistObject(consumption);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("outtradno",consumption.getOrderno());
		obj.addProperty("info","下单成功");
		obj.addProperty("msg","success");
		obj.addProperty("paymentway",paymentway);
		
		String result = obj.toString();
		
		if(Integer.parseInt(paymentway)==2)result = alipay(obj, g.getPrice()).toString();
		else if(Integer.parseInt(paymentway)==1)result = unifiedorder(obj, ip, g.getPrice(), consumption.getOrderno()).toString();
		
		/*Sys.out("==========submitorder..result=========");
		Sys.out(result);
		Sys.out("======================================");*/
		return result;
		
	}

	
	private JsonObject alipay(JsonObject obj,Double totalfee){
		
		String orderInfo = getOrderInfo(obj.get("outtradno").getAsString(), totalfee.toString());
		
		String sign = sign(orderInfo);
		
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		
		//payInfo = orderInfo + "&sign=" + sign + "&"+ getSignTypeno();
		
		obj.addProperty("payInfo", payInfo);
		/*obj.addProperty("partner", AlipayConfig.PARTNER);
		obj.addProperty("seller_id", AlipayConfig.SELLER);
		obj.addProperty("out_trade_no", obj.get("outtradno").getAsString());
		obj.addProperty("subject", AlipayConfig.subject);
		obj.addProperty("body", AlipayConfig.body);
		obj.addProperty("total_fee",totalfee);
		obj.addProperty("notify_url",getNotifyUrld());
		obj.addProperty("service",AlipayConfig.service);
		obj.addProperty("payment_type",AlipayConfig.payment_type);
		obj.addProperty("input_charset",AlipayConfig.input_charset);
		obj.addProperty("it_b_pay",AlipayConfig.it_b_pay);
		obj.addProperty("return_url",AlipayConfig.return_url);
		obj.addProperty("sign",sign(getOrderInfo(obj.get("outtradno").getAsString(), totalfee.toString())));
		obj.addProperty("sign_type",AlipayConfig.sign_type);*/
		return obj;
	}


	/**
	 * create the order info. 创建订单信息
	 */
	private String getOrderInfo(String outtradeno, String price) {
	
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + AlipayConfig.PARTNER + "\"";
	
		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + AlipayConfig.SELLER + "\"";
	
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + outtradeno + "\"";
	
		// 商品名称
		orderInfo += "&subject=" + "\"" + AlipayConfig.subject + "\"";
	
		// 商品详情
		orderInfo += "&body=" + "\"" + AlipayConfig.body + "\"";
	
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
	
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + getNotifyUrld() + "\"";
	
		// 服务接口名称， 固定值
		orderInfo += "&service=\""+AlipayConfig.service+"\"";
	
		// 支付类型， 固定值
		orderInfo += "&payment_type=\""+AlipayConfig.payment_type+"\"";
	
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\""+AlipayConfig.input_charset+"\"";
	
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\""+AlipayConfig.it_b_pay+"\"";
	
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
	
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\""+AlipayConfig.return_url+"\"";
	
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
	
		return orderInfo;
	}
	
	private String sign(String content) {
		return SignUtils.sign(content, AlipayConfig.RSA_PRIVATE);
	}
	
	
	/**
	 * get the sign type we use. 获取签名方式
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	protected String getNotifyUrld() {
		return "http://zzl.runfkj.com/appServicePay/notifyurl.action";
	}
	
	
	//微信统一下单
	private JsonObject unifiedorder(JsonObject obj,String ip,Double totalprice,String outtradno) {
		
		
	    
		int fee = new BigDecimal(totalprice).multiply(new BigDecimal(100)).intValue();
		String nonce_str = ConstantUtil.create_nonce_str();
		String notify_url = ConstantUtil.notify_url;
		String out_trade_no= outtradno;
		String attach = AlipayConfig.subject;
		String body = AlipayConfig.body;
		int total_fee = fee;
		String spbill_create_ip = ip;
		String sign = sign(ConstantUtil.APPID, attach, body, nonce_str, notify_url, out_trade_no, spbill_create_ip, total_fee);
		String p = "<xml>"+
				"<appid><![CDATA["+ConstantUtil.APPID+"]]></appid>"+
				"<attach><![CDATA["+attach+"]]></attach>"+
				"<body><![CDATA["+body+"]]></body>"+
				"<mch_id><![CDATA["+ConstantUtil.mch_id+"]]></mch_id>"+
				"<nonce_str><![CDATA["+nonce_str+"]]></nonce_str>"+
				"<notify_url><![CDATA["+notify_url+"]]></notify_url>"+
				"<out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no>"+
				"<spbill_create_ip><![CDATA["+spbill_create_ip+"]]></spbill_create_ip>"+
				"<total_fee><![CDATA["+total_fee+"]]></total_fee>"+
				"<trade_type><![CDATA[APP]]></trade_type>"+
				"<sign><![CDATA["+sign+"]]></sign>"+
				"</xml>";
		
		p = "<xml>"+
				"<appid>"+ConstantUtil.APPID+"</appid>"+
				"<attach>"+attach+"</attach>"+
				"<body>"+body+"</body>"+
				"<mch_id>"+ConstantUtil.mch_id+"</mch_id>"+
				"<nonce_str>"+nonce_str+"</nonce_str>"+
				"<notify_url>"+notify_url+"</notify_url>"+
				"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
				"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
				"<total_fee>"+total_fee+"</total_fee>"+
				"<trade_type>APP</trade_type>"+
				"<sign>"+sign+"</sign>"+
				"</xml>";
		
		Sys.out("p:"+p);
		/*try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/unifiedorder");    
			httpost.setEntity(new StringEntity(p, "UTF-8"));    
			HttpResponse resp = httpclient.execute(httpost);    
			String result = EntityUtils.toString(resp.getEntity(), "utf-8"); 
			Sys.out("create:"+result);
			JsonUtil.success2page(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JsonUtil.noRight2page(response);
		} */
		String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String, String> result = new HashMap<String, String>();
		  try {
			  StringBuffer buffer = httpsRequest(requestUrl, "POST", p);
			  Sys.out("=========result========");
			  String strBuffer = buffer.toString();
			  Sys.out(strBuffer);
			  result = ConstantUtil.parse(strBuffer);
			  String return_code = result.get("return_code");
			  Sys.out("return_code:"+return_code);
			  if("SUCCESS".equals(return_code)){
				  String result_code = result.get("result_code");
				  Sys.out("result_code:"+result_code);
				  if("SUCCESS".equals(result_code)){
					  String prepay_id = result.get("prepay_id");
					  Sys.out("prepay_id:"+prepay_id);
					  
					  String packageValue = "Sign=WXPay";
					  String timestamp = ConstantUtil.create_timestamp();
					  
					  String sign4pay = sign4pay(ConstantUtil.APPID,timestamp , nonce_str, packageValue, ConstantUtil.mch_id, prepay_id);
					  
					  obj.addProperty("appid", ConstantUtil.APPID);
					  obj.addProperty("timestamp",timestamp);
					  obj.addProperty("noncestr", nonce_str);
					  obj.addProperty("package", packageValue);
					  obj.addProperty("partnerid", ConstantUtil.mch_id);
					  obj.addProperty("prepayid", prepay_id);
					  obj.addProperty("sign", sign4pay);
				  }else{
					  obj.addProperty("msg","fail");
					  obj.addProperty("info","微信支付参数错误");
				  }
			  }else{
				  obj.addProperty("msg","fail");
				  obj.addProperty("info","微信支付签名错误");
			  }
			  
			  
			
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		  
		  return obj;
	}
	
	//微信统一下单签名
	//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	private  String sign(String appid,String attach,String body,String nonce_str,String notify_url,String out_trade_no,String spbill_create_ip,int total_fee){
		String stringA = "appid="+appid+"&attach="+attach+"&body="+body+"&mch_id="+ConstantUtil.mch_id+"&nonce_str="+nonce_str+"&notify_url="+notify_url+"&out_trade_no="+out_trade_no+"&spbill_create_ip="+spbill_create_ip+"&total_fee="+total_fee+"&trade_type=APP";
		String stringSignTemp=stringA+"&key="+ ConstantUtil.key;
		Sys.out("sign.."+stringSignTemp);
		return MD5.JM(stringSignTemp).toUpperCase();
	}
	
	
	//微信支付签名
	//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	private  String sign4pay(String appId, String timeStamp, String nonceStr,String packageValue,String partnerid,String prepayid){
		String stringA = "appid="+appId+"&noncestr="+nonceStr+"&package="+packageValue+"&partnerid="+partnerid+"&prepayid="+prepayid+"&timestamp="+timeStamp;
		String stringSignTemp=stringA+"&key="+ ConstantUtil.key;
		Sys.out("sign4pay.."+stringSignTemp);
		return MD5.JM(stringSignTemp).toUpperCase();
	}
	
	private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
			  throws Exception {
		
			 // URL url = new URL(requestUrl);
		      URL url = new URL(null,requestUrl, new sun.net.www.protocol.https.Handler());
			  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			  connection.setDoOutput(true);
			  connection.setDoInput(true);
			  connection.setUseCaches(false);
			  connection.setRequestMethod(requestMethod);
			  if (null != output) {
			  OutputStream outputStream = connection.getOutputStream();
			  outputStream.write(output.getBytes("UTF-8"));
			  outputStream.close();
			  }
			  // 从输入流读取返回内容
			  InputStream inputStream = connection.getInputStream();
			  InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			  String str = null;
			  StringBuffer buffer = new StringBuffer();
			  while ((str = bufferedReader.readLine()) != null) {
			  buffer.append(str);
			  }
			  bufferedReader.close();
			  inputStreamReader.close();
			  inputStream.close();
			  inputStream = null;
			  connection.disconnect();
			  return buffer;
			 }


	@Override
	public boolean notifyurl(PayinfoByAli pali) throws Exception {
		// TODO Auto-generated method stub
		//检查金额是否相符
		StringBuffer checkhql = new StringBuffer("select c from Consumption c where c.paystate = 0 and c.type = 1 and c.orderno = '").append(pali.getOut_trade_no()).append("'");
        List<Object> checklst = databaseHelper.getResultListByHql(checkhql.toString());
        Double totalmoney = 0.0;
        for (Object object : checklst) {
        	Consumption c = (Consumption)object;
        	Gears g = (Gears)databaseHelper.getObjectById(Gears.class, c.getFgearsid());
        	totalmoney += g.getPrice();
//        	totalmoney += c.getGolds();
		}
		
        BigDecimal b1 = new BigDecimal(totalmoney);
		BigDecimal b2 = new BigDecimal(pali.getTotal_amount());
		Sys.out("order total money..."+b1+"   =====   alipay  total  amount..."+b2);
		Double diff = b1.subtract(b2).doubleValue();
		
		Sys.out("alipay...check....diff..."+Math.abs(diff));
		if(Math.abs(diff)>1){
			Sys.out("alipay...check....money...error");
			return false;//计算误差差值大于1，则认为此单数据错误
		}
		Sys.out("alipay...check....money...right");
        
		StringBuffer p = new StringBuffer("select pali from PayinfoByAli pali where pali.out_trade_no= '").append(pali.getOut_trade_no()).append("'");
		List<Object> pl = databaseHelper.getResultListByHql(p.toString());
		if(pl.size()>0)return false;
			
		databaseHelper.persistObject(pali);
		 for (Object object : checklst) {
	        Consumption c = (Consumption)object;
	        c.setPaystate(1);
	        c.setBalance(c.getBalance()+c.getGolds());
	        
	        databaseHelper.updateObject(c);
	        
	        UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, c.getFuserinfoid());
	        long number =ui.getExperiencenum()+c.getGolds();
	        ui.setBalance(ui.getBalance()+c.getGolds());
	        //添加用户经验值
	        ui.setExperiencenum(ui.getExperiencenum()+c.getGolds());
	        String sql1= "select d FROM Designation d where d.state=0 order by d.experience  asc ";
	       
	        List<Object> lst=databaseHelper.getResultListByHql(sql1);
	        for (int i = 1; i < lst.size(); i++) {
	        	//后一个称号
	        	Designation de= (Designation) lst.get(i);
	        	if(number<de.getExperience()){
	        		//前一个称号
	        		Designation d= (Designation) lst.get(i-1);
	        		ui.setDesignation(d.getDesignation());
	        		ui.setLevel(d.getLevel());
	        		break;
	        	}
	        	
			}
	        //最后一个
	        Designation des= (Designation) lst.get(lst.size()-1);
	        if(number>=des.getExperience()){
	        	ui.setDesignation(des.getDesignation());
	        	ui.setLevel(des.getLevel());
	        }
	        databaseHelper.updateObject(ui);
		}
		 
		 
		 return true;
	}


	@Override
	public boolean wxnotify(PayInfoByWx p) throws Exception {
		// TODO Auto-generated method stub
		//检查金额是否相符
		StringBuffer checkhql = new StringBuffer("select c from Consumption c where c.paystate = 0 and c.type = 1 and c.orderno = '").append(p.getOuttradeno()).append("'");
        List<Object> checklst = databaseHelper.getResultListByHql(checkhql.toString());
        Double totalmoney = 0.0;
        for (Object object : checklst) {
        	Consumption c = (Consumption)object;
        	totalmoney += c.getGolds();
		}
		
        BigDecimal b1 = new BigDecimal(totalmoney);
		BigDecimal b2 = new BigDecimal(p.getTotalfee()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
		Sys.out("order total money..."+b1+"   =====   wxpay  total  amount..."+b2);
		Double diff = b1.subtract(b2).doubleValue();
		
		Sys.out("wxpay...check....diff..."+Math.abs(diff));
		if(Math.abs(diff)>1){
			Sys.out("wxpay...check....money...error");
			return false;//计算误差差值大于1，则认为此单数据错误
		}
		Sys.out("wxpay...check....money...right");
        
		StringBuffer pay = new StringBuffer("select p from Payinfo p where p.outtradeno= '").append(p.getOuttradeno()).append("'");
		List<Object> pl = databaseHelper.getResultListByHql(pay.toString());
		if(pl.size()>0)return false;
			
		databaseHelper.persistObject(p);
		
		 for (Object object : checklst) {
	        Consumption c = (Consumption)object;
	        c.setPaystate(1);
	        c.setBalance(c.getBalance()+c.getGolds());
	        
	        databaseHelper.updateObject(c);
	        
	        UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, c.getFuserinfoid());
	        long number =ui.getExperiencenum()+c.getGolds();
	        ui.setBalance(ui.getBalance()+c.getGolds());
	      //添加用户经验值
	        ui.setExperiencenum(ui.getExperiencenum()+c.getGolds());
	        
	       
	        String sql1= "select d FROM Designation d where d.state=0 order by d.experience  asc ";
	       
	        List<Object> lst=databaseHelper.getResultListByHql(sql1);
	        for (int i = 1; i < lst.size(); i++) {
	        	//后一个称号
	        	Designation de= (Designation) lst.get(i);
	        	if(number<de.getExperience()){
	        		//前一个称号
	        		Designation d= (Designation) lst.get(i-1);
	        		ui.setDesignation(d.getDesignation());
	        		break;
	        	}
	        	
			}
	        //最后一个
	        Designation des= (Designation) lst.get(lst.size()-1);
	        if(number>=des.getExperience()){
	        	ui.setDesignation(des.getDesignation());
	        }
	        databaseHelper.updateObject(ui);
		}
		 
		 
		 return true;
	}


	
}
