package com.hxy.isw.control;

import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceToys;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/appServiceToys")
public class AppServiceToysControl {
	
	@Autowired
	AppServiceToys appServiceToys;
	
	//获取礼物盒列表
	@RequestMapping(method = RequestMethod.POST,value="/getgiftbox")
	public void getgiftbox(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String userid = request.getParameter("userid");
			if (StringUtils.isEmpty(userid)) {
				throw new Exception("用户不存在");
			}
			int start = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows") == null ? ConstantUtil.LIMIT: Integer.parseInt(request.getParameter("rows"));
			int state = request.getParameter("state")==null?0:Integer.parseInt(request.getParameter("state"));
			Map<String, Object> map =appServiceToys.queryGiftbox(userid, state, start, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	//物流详情
	@RequestMapping(method = RequestMethod.POST,value="/expressage")
	public void expressage(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String userid = request.getParameter("userid");
			if (StringUtils.isEmpty(userid)) {
				throw new Exception("用户不存在");
			}
			String giftboxid = request.getParameter("giftboxid");
			String result = appServiceToys.queryexpressage(giftboxid);
			
			JsonUtil.objToJson(result, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	//玩具分类列表
	
	@RequestMapping(method = RequestMethod.POST,value="/toystype")
	public void toystype(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
			int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
			
			String userid = request.getParameter("userid");
			Sys.out(userid);
			if(StringUtils.isEmpty(userid)){
				throw new Exception("该用户不存在");
			}
			Map<String, Object> map=appServiceToys.searchToystype(start, limit);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	//玩具详情
	
	@RequestMapping(method = RequestMethod.POST,value="/toysdetail")
	public void toysdetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String mechinesid= request.getParameter("machinesid");
			if(StringUtils.isEmpty(userid)){
				throw new NullPointerException("该用户不存在");
			}
			Map<String, Object> map=appServiceToys.searchToysdetail(mechinesid);
			JsonUtil.mapToJson(map, response);
			
		} 
		catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
		
	}
	//申请发货
	@RequestMapping(method = RequestMethod.POST,value="/applydelivery")
	public void applydelivery(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String userid = request.getParameter("userid");
			if(StringUtils.isEmpty(userid)){
			throw new NullPointerException("该用户不存在");
			}
			String giftboxid = request.getParameter("giftboxid");
			Map<String, Object> map= appServiceToys.searchApplydelivery(giftboxid,userid);
			JsonUtil.mapToJson(map, response);
		} catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	
	//抓娃娃
	@RequestMapping(method = RequestMethod.POST,value="/play")
	public synchronized void play(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		try {
			if (StringUtils.isEmpty(userid)) {
				throw new Exception("用户不存在");
			}
			String machineid = request.getParameter("machineid");
			String operation = request.getParameter("operation");  //操作  ：move--动   stop--停   play--抓
			String direction = request.getParameter("direction");//方向    ：former--向前    backward--向后    aleft-- 向左   aright-- 向右  
			String visualangle = request.getParameter("visualangle");//视角  ：1正面  2侧面
			String result = appServiceToys.play(userid,machineid,operation,direction,visualangle);
			
			JsonUtil.success2client(response, result);
		} catch (Exception e) {
			String mess = e.getMessage();
			if(mess.indexOf("Broken pipe")!=-1){
				/*Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMapOfApp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Long, SocketChannel> entry = it.next();
					if (entry.getKey().equals(Long.parseLong(userid))) {
						Sys.out("delete this key: " + userid + " = " + entry.getValue());
						it.remove(); // OK
					}
				}*/
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\"娃娃机网络异常，重连中。。\"}");
			}
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	//上传录制的视频
	@RequestMapping(method = RequestMethod.POST,value="/uploadvideo")
	public synchronized void uploadvideo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		try {
			String result = appServiceToys.uploadvideo(request);
			JsonUtil.success2client(response, result);
		}catch (Exception e) {
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
}
