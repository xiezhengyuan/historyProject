package com.hxy.isw.control;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.entity.PayInfoByWx;
import com.hxy.isw.entity.PayinfoByAli;
import com.hxy.isw.service.AppServicePay;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年6月30日 上午10:51:36
* @describe
*/

@Controller
@RequestMapping("/appServicePay")
public class AppServicePayControl {
	
	@Autowired
	AppServicePay appServicePay;

	/**
	 * 获取充值档位
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/gears")
	public void gears(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			
			
			String json = appServicePay.gears(userid);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
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
		try {
			
			String userid = request.getParameter("userid");
			String gearsid = request.getParameter("gearsid");
			String paymentway = request.getParameter("paymentway");
			
			String ip = ConstantUtil.getIp(request);
			
			String json = appServicePay.recharge(userid, gearsid,paymentway,ip);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	//支付宝异步通知
	@RequestMapping(value = "/notifyurl")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized void notifyurl(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		Sys.out("==================notifyurl by  ali=======================");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				Sys.out("name:"+name+",valueStr:"+valueStr);
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			String notify_type = new String(request.getParameter("notify_type").getBytes("ISO-8859-1"),"UTF-8");
			String notify_id = new String(request.getParameter("notify_id").getBytes("ISO-8859-1"),"UTF-8");
			String sign_type = new String(request.getParameter("sign_type").getBytes("ISO-8859-1"),"UTF-8");
			String sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"),"UTF-8");
			String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),"UTF-8");
			String total_amount  = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
			
			
			Sys.out("out_trade_no:"+out_trade_no);
			Sys.out("trade_no:"+trade_no);
			Sys.out("trade_status:"+trade_status);

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			//if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				
				Sys.out("verify:success");
				
				boolean flag = false;
				if(trade_status.equals("TRADE_SUCCESS")){
					Sys.out("TRADE_SUCCESS");
					PayinfoByAli pali = new PayinfoByAli();
					pali.setBuyer_email(buyer_email);
					pali.setNotify_id(notify_id);
					pali.setNotify_type(notify_type);
					pali.setOut_trade_no(out_trade_no);
					pali.setSign(sign);
					pali.setSign_type(sign_type);
					pali.setTime(new Date());
					pali.setTotal_amount(total_amount);
					pali.setTrade_no(trade_no);
					pali.setTrade_status(trade_status);
					
					flag = appServicePay.notifyurl(pali);
					
					/*databaseHelper.persistObject(pali);
					
					
					String hql = "select oit from OrderInfoTemp oit where oit.orderno = '"+out_trade_no+"'";
			        List<Object> lst = databaseHelper.getResultListByHql(hql);
			        for (Object object : lst) {
			        	OrderInfoTemp oit = (OrderInfoTemp)object;
			        	oit.setPaystate(1);
			        	databaseHelper.updateObject(oit);
					}
			        
			        hql = "select oi from OrderInfo oi where oi.orderno = '"+out_trade_no+"'";
			        lst = databaseHelper.getResultListByHql(hql);
			        for (Object object : lst) {
			        	OrderInfo oi = (OrderInfo)object;
			        	oi.setPaystate(1);
			        	oi.setFpayinfoid(pali.getId());
			        	databaseHelper.updateObject(oi);
					}*/
					
				}
				
				
				

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				//判断是否在商户网站中已经做过了这次通知返回的处理
					//如果没有做过处理，那么执行商户的业务程序
					//如果有做过处理，那么不执行商户的业务程序
				if(flag)	
					out.println("success");	//请不要修改或删除
				else
					out.println("fail");
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				//////////////////////////////////////////////////////////////////////////////////////////
			//}else{//验证失败
			//	out.println("fail");
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			out.close();
		}
		
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
        	
        	 PayInfoByWx p = new PayInfoByWx();
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
        	
             boolean flag = appServicePay.wxnotify(p);
             
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
}
