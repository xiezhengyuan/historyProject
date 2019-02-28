package com.hxy.isw.control;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AppServiceMachine;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Controller
@RequestMapping("/appServiceMachine")
public class AppServiceMachineControl {
	@Autowired
	AppServiceMachine appServiceMachine;
	
	/**
	 * 娃娃机列表
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/machines")
	public void machines(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String toystypeid = request.getParameter("toystypeid");
		String orderby = request.getParameter("orderby");
		String liftby = request.getParameter("liftby");
		int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
		int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
		
		try{
			String json = appServiceMachine.machines(userid,toystypeid,orderby,liftby,start,limit);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 娃娃机详情
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/machinedetail")
	public void machinedetail(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.machinedetail(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}

	/**
	 *进入娃娃机房间观看
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/views")
	public void views(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.views(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *退出娃娃机房间
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/exitroom")
	public void exitroom(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.exitroom(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *娃娃机预约
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/subscribe")
	public void subscribe(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.subscribe(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *娃娃机取消预约
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cancelsubscribe")
	public void cancelsubscribe(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.cancelsubscribe(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			String mess = e.getMessage();
			
			/*if(mess.indexOf("Broken pipe")!=-1){
				Iterator<Map.Entry<Long, SocketChannel>> it = NIOSServer.clientsLMapOfApp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Long, SocketChannel> entry = it.next();
					if (entry.getKey().equals(Long.parseLong(userid))) {
						Sys.out("delete this key: " + userid + " = " + entry.getValue());
						it.remove(); // OK
					}
				}
			}*/
			
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *娃娃机使用
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/use")
	public synchronized void use(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			appServiceMachine.use(userid,machinesid);
			
			Map<String,Object>map = new HashMap<String,Object>();
			map.put("msg", "success");
			map.put("info", "操作成功");
			map.put("userstate", 2);
			
			String json = JsonUtil.getGson().toJson(map);
			
			JsonUtil.success2client(response, json);
		}catch(Exception e){
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
				return;
			}
			
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 *娃娃机使用完毕
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/finish")
	public void finish(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.finish(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
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
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/collection")
	public void collection(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.collection(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+e.getMessage()+"\"}");
			e.printStackTrace();
		}
			
		}
	@RequestMapping(method = RequestMethod.POST, value = "/removecollection")
	public void removecollection(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String userid = request.getParameter("userid");
		String machinesid = request.getParameter("machinesid");
		
		try{
			String json = appServiceMachine.removecollection(userid,machinesid);
			JsonUtil.success2page(response, json);
		}catch(Exception e){
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+e.getMessage()+"\"}");
			e.printStackTrace();
		}
			
		}

	
}
