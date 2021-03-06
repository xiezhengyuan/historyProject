package com.hxy.isw.control;

import java.util.HashMap;
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

import com.hxy.isw.service.AppServiceUser;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.JsonUtil;

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
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    public void getcode(HttpServletRequest request,HttpSession session,HttpServletResponse response){
    	String userid = request.getParameter("userid");
    	String mobile = request.getParameter("mobile");
    	String type = request.getParameter("type");
    	try {
    		String json = appServiceUser.getcode(userid,mobile,type);
			JsonUtil.success2page(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
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
	public void login(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			
			String json = appServiceUser.login(mobile, password);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信第三方登录
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/wxlogin")
	public void wxlogin(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String openid = request.getParameter("openid");
			String nickname = request.getParameter("nickname");
			String headimg = request.getParameter("headimg");
			
			String json = appServiceUser.wxlogin(openid, nickname,headimg);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
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
    	try {
    		String json = appServiceUser.registe(mobile, password,code);
			JsonUtil.success2page(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
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
			JsonUtil.success2page(response, json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
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
		 String json ="";
		 if(flag){ 
			 json = appServiceUser.uploaduserphoto(userid,filename);
		}else{
			 Map<String,Object> map = new HashMap<String,Object>();
				
			 map.put("msg", "success");
			 map.put("info", "上传成功");
			 map.put("url", filename);
			 
			 json = JsonUtil.getGson().toJson(map);
		}
		 
			 
		 JsonUtil.success2page(response, json);
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			e.printStackTrace();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
		}
	 }
	 
	 /**
		 * 查看用户个人信息
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/userinfo")
		public void userinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.userinfo(userid);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 修改用户个人信息
		 * @param request
		 * @param response
		 * @param session
		 */
	@RequestMapping(method = RequestMethod.POST, value = "/modifyuserinfo")
	public void modifyuserinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String birthday = request.getParameter("birthday");
			String selfinfo = request.getParameter("selfinfo");
			
			
			String json = appServiceUser.modifyuserinfo(userid,nickname,sex,birthday,selfinfo);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
    
	 /**
	 * 获取用户地址
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getshippingaddress")
	public void getshippingaddress(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String userid = request.getParameter("userid");
			
			String json = appServiceUser.getshippingaddress(userid);
			JsonUtil.success2page(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	 /**
		 * 添加用户地址
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/addnewaddress")
		public void addnewaddress(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String consigneename = request.getParameter("consigneename");
			String consigneemobile = request.getParameter("consigneemobile");
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			String address = request.getParameter("address");
			try {
			
				String json = appServiceUser.addnewaddress(userid,consigneename,consigneemobile,province,city,area,address);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
	
		 /**
		 * 设为默认地址
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/setdefault")
		public void setdefault(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String addressid = request.getParameter("addressid");
			
			try {
			
				String json = appServiceUser.setdefault(userid,addressid);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 删除收货地址
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/deladdress")
		public void deladdress(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String addressid = request.getParameter("addressid");
			
			try {
			
				String json = appServiceUser.deladdress(userid,addressid);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 消费明细
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/consumption")
		public void consumption(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.consumption(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		 /**
		 * 添加用户地址
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/applyproxy")
		public void applyproxy(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String name = request.getParameter("name");
			String mobile = request.getParameter("mobile");
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			try {
			
				String json = appServiceUser.applyproxy(userid,name,mobile,province,city,area);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 签到
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/sign")
		public void sign(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			
			try{
				String json = appServiceUser.sign(userid);
				JsonUtil.success2client(response, json);
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 申诉
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/appeal")
		public void appeal(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String content = request.getParameter("content");
			String photos = request.getParameter("photos");
			
			try{
				String json = appServiceUser.appeal(userid,content,photos);
				JsonUtil.success2client(response, json);
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		/**
		 * 意见反馈
		 * @param request
		 * @param response
		 * @param session
		 */
		
		@RequestMapping(method = RequestMethod.POST, value = "/leavemessage")
		public void leavemessage(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			String userid = request.getParameter("userid");
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			
			try{
				String json = appServiceUser.leavemessage(userid,content,title);
				JsonUtil.success2page(response, json);
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 等级列表
		 * @param request
		 * @param response
		 * @param session
		 */
		@RequestMapping(method = RequestMethod.POST, value = "/userlevellist")
		public void userlevellist(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.userlevellist(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		
		
		@RequestMapping(method = RequestMethod.POST, value = "/dofollow")
		public void dofollow(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			
			String myid=request.getParameter("myid");
			String userid = request.getParameter("userid");
			
			try{
				appServiceUser.dofollow(myid,userid);
				JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"\"}");
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/removefollow")
		public void removefollow(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			
			String myid=request.getParameter("myid");
			String userid = request.getParameter("userid");
			
			try{
				appServiceUser.removefollow(myid,userid);
				JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"\"}");
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/userstate")
		public void userstate(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			
			String myid=request.getParameter("myid");
			String userid = request.getParameter("userid");
			
			try{
				Map<String, Object> map=appServiceUser.userstate(myid,userid);
				JsonUtil.mapToJson(map, response);
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
	
		@RequestMapping(method = RequestMethod.POST, value = "/my")
		public void my(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			
			String userid = request.getParameter("userid");
			
			try{
				Map<String, Object> map=appServiceUser.my(userid);
				JsonUtil.mapToJson(map, response);
			}catch(Exception e){
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		
		
		@RequestMapping(method = RequestMethod.POST, value = "/myfans")
		public void myfans(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.myfans(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/myfollow")
		public void myfollow(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.myfollow(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		
		@RequestMapping(method = RequestMethod.POST, value = "/usersussesstoys")
		public void usersussesstoys(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.usersussesstoys(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/myuseremember")
		public void myuseremember(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String userid = request.getParameter("userid");
				
				String json = appServiceUser.myuseremember(userid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/nearsuccess")
		public void nearsuccess(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				int start = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
				int limit = request.getParameter("rows")==null?ConstantUtil.LIMIT:Integer.parseInt(request.getParameter("rows"));
				String machineid = request.getParameter("machineid");
				
				String json = appServiceUser.nearsuccess(machineid,start,limit);
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/allege")
		public void allege(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String json= appServiceUser.allege();
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/userallege")
		public void userallege(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String userid=request.getParameter("userid");
				String allegeid=request.getParameter("allegeid");
				String substance=request.getParameter("substance");
				String videoinfoid=request.getParameter("videoinfoid");
				appServiceUser.userallege(userid,allegeid,substance,videoinfoid);
				JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"\"}");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				
			}
		}
		
		
	//上的功夫大师发噶都会及时发给	
		
		@RequestMapping(method = RequestMethod.POST, value = "/usershare")
		public void usershare(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				String userid=request.getParameter("userid");
				Map<String,Object>  map = appServiceUser.usershare(userid);
				JsonUtil.mapToJson(map, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/invitema")
		public void invitema(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String userid=request.getParameter("userid");
				String shareno=request.getParameter("shareno");
				appServiceUser.invitema(userid,shareno);
				JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"您已成功领取1个免玩券\"}");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/testedition")
		public void testedition(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String editionno=request.getParameter("editionno");
				String system=request.getParameter("system");
				Map<String, Object> map=appServiceUser.testedition(editionno,system);
				if(map.get("msg").toString().equals("fail")){
					JsonUtil.mapToJson(map, response);
				}else{
					JsonUtil.success2page(response,"{\"msg\":\"success\",\"info\":\"版本正确\"}");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		
		@RequestMapping(method = RequestMethod.POST, value = "/banner")
		public void banner(HttpServletRequest request,HttpServletResponse response,HttpSession session){
			try {
				
				String json=appServiceUser.banner();
				JsonUtil.success2page(response, json);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				String mess = e.getMessage();
				JsonUtil.success2page(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
				e.printStackTrace();
			}
		}
		 
}
