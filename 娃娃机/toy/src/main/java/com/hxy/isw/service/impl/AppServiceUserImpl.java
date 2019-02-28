package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Allege;
import com.hxy.isw.entity.Appeal;
import com.hxy.isw.entity.AppealPhotos;
import com.hxy.isw.entity.Banner;
import com.hxy.isw.entity.CodeLog;
import com.hxy.isw.entity.Consumption;
import com.hxy.isw.entity.Edition;
import com.hxy.isw.entity.FeedBack;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.MessageInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.ProxyApply;
import com.hxy.isw.entity.ShippingAddress;
import com.hxy.isw.entity.SignInfo;
import com.hxy.isw.entity.UserAllege;
import com.hxy.isw.entity.UserFollow;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.VideoInfo;
import com.hxy.isw.service.AppServiceUser;
import com.hxy.isw.socket.NIOSServer;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;
@Repository
public class AppServiceUserImpl implements AppServiceUser{
	@Autowired
  DatabaseHelper databaseHelper;
	@Override
	public String getcode(String userid, String mobile, String type) throws Exception {
		// TODO Auto-generated method stub
				if(type==null||type.length()==0)throw new Exception("参数type错误");
				
				int int_type = Integer.parseInt(type);
				
				UserInfo ui = null;
				if(int_type == 2){//忘记密码 ，需要输入手机号
					
					if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号码");
					
					StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.mobile = '").append(mobile).append("'");
					List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
					
					if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
					
					ui = (UserInfo)lst.get(0);
					
				}else {//其它情况需要用户id
					
					/*if(userid==null||userid.length()==0)throw new Exception("用户id错误");
					
					ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
					
					if(ui!=null)throw new Exception("id为"+userid+"的用户已存在");
					*/
					
					/*if(int_type==2||int_type==3){//修改登录密码或支付密码时，手机号码从用户信息中取
						
						mobile = ui.getMobile();
						
					}else{//邀请下级或修改手机号   需要新的手机号
						
						if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号码噢╮(╯_╰)╭");
						
					}*/
				}
				
				
				//检查1分钟之内是否已经发送过
				StringBuffer check = new StringBuffer("select cl from CodeLog cl where cl.mobile ='")
						.append(mobile).append("' and cl.type = ").append(int_type).append(" order by cl.createtime desc");
				
				List<Object> lst = databaseHelper.getResultListByHql(check.toString());
				
				if(lst.size()>0){
					CodeLog cl = (CodeLog)lst.get(0);
					Date last = cl.getCreatetime();
					Date now = new Date();
					long diff = now.getTime()-last.getTime();
					//如果小于1分钟
					if(diff<1000l*60)throw new Exception("1分钟内请不要重复发送验证码噢╮(╯_╰)╭");
				}
				
				//检查今天该手机号一共接收了多少次短信
				check = new StringBuffer("select cl from CodeLog cl where ")
						.append(" cl.mobile ='").append(mobile).append("' and date(cl.createtime) = curdate()");
				
				lst = databaseHelper.getResultListByHql(check.toString());
				
				if(lst.size()>10)throw new Exception("同一个手机号一天最多只能接收10次短信噢╮(╯_╰)╭");
				
				String code = ConstantUtil.gencode();
				
				String content = "【抟腾爱抓】验证码为"+code+"。请勿泄露。";
				
				boolean flag = ConstantUtil.yunpian(content,mobile);
				
				
				if(!flag)throw new Exception("短信服务器异常`(*>﹏<*)′");
				
				CodeLog cl = new CodeLog();
				cl.setCode(code);
				//cl.setFuserid(ui.getId());
				cl.setMobile(mobile);
				cl.setCreatetime(new Date());
				cl.setType(Integer.parseInt(type));
				
				databaseHelper.persistObject(cl);
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "验证码发送成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String login(String mobile, String password) throws Exception {
		// TODO Auto-generated method stub
		if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
		
		if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
		
		if(password==null||password.length()==0)throw new Exception("请输入密码");
		
		//检查用户名是否合法（防止sql注入）
		if(!ConstantUtil.checkUsername(mobile))throw new Exception("手机号码包含非法字符");
		
		StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.state = 0 and ui.mobile = '").append(mobile).append("'");
		List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
		
		if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
		
		UserInfo ui = (UserInfo)lst.get(0);
		
		if(ui.getState() < 0)throw new Exception("该手机号的用户已被禁用,请联系管理员o(︶︿︶)o");
		
		/*if(ui.getLoginstate() == 1){
			
			ui.setLoginstate(0);
			databaseHelper.updateObject(ui);
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("msg", "fail");
			map.put("info", "该用户已在线，请不要重复登录(︶︿︶)o");
			
			String json = JsonUtil.getGson().toJson(map);
			
			return json;
			
		}*/
		
		String md5Password_real = ConstantUtil.getMD5Str(ui.getPassword()+ConstantUtil.TOKEN,null);
		
		if(!md5Password_real.equals(password))throw new Exception("密码不正确噢╮(╯_╰)╭");
		
		/*//登录成功，将用户在线状态置为1
		if(ui.getLoginstate()!=1){
			ui.setLoginstate(1);
			databaseHelper.updateObject(ui);
		}*/
		
		return getuserinfo(ui);
	}
	
	//获取用户信息
			private String getuserinfo(UserInfo ui){
				StringBuffer querysign = new StringBuffer("select si from SignInfo si where si.fuserinfoid = ").append(ui.getId())
						.append("  order by si.createtime desc");
				List<Object>sList = databaseHelper.getResultListByHql(querysign.toString());
				
				Integer signcount = 0;
				if(sList.size()>0){
					SignInfo signInfo = (SignInfo) sList.get(0);
					if(new Date().getTime()-signInfo.getCreatetime().getTime()<=24*60*60*1000l){
						signcount = signInfo.getSigncount();
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "操作成功");
				map.put("userid", ui.getId());
				map.put("nickname", ui.getNickname());
			    map.put("headimg", ui.getHeadimg());
			    map.put("sex", ui.getSex());
			    map.put("mobile", ui.getMobile());
			    map.put("birthday", ui.getBirthday());
			    map.put("selfinfo", ui.getSelfinfo());
			    map.put("balance", ui.getBalance());
			    map.put("freevoucher", ui.getFreevoucher());
				map.put("level", ui.getLevel());
				map.put("uuid", ui.getUuid());
				map.put("signcount", signcount);
				map.put("username", ui.getUsername());
			
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}


			@Override
			@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
			public String registe(String mobile, String password, String code) throws Exception {
				// TODO Auto-generated method stub
				if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
				
				if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
				
				if(password==null||password.length()==0)throw new Exception("请输入密码");
				
				if(password==null||password.length()==0)throw new Exception("请输入验证码");
				
				
				String hql = "select u from UserInfo u where (u.mobile = '"+mobile+"' or u.username = '"+mobile+"')";
				List<Object> lst =  databaseHelper.getResultListByHql(hql);
				if(lst.size()>0)throw new Exception("该手机已注册");
				int type = 1;
				checkcode(mobile, type, code);
				
				UserInfo u = new UserInfo();
				u.setMobile(mobile);
				u.setPassword(password);
				u.setCreatetime(new Date());
				u.setUsername(mobile);
				u.setInviter(Long.parseLong("0"));
				u.setNickname(mobile);
				u.setLevel(0);
				u.setState(0);
				u.setSelfinfo("");
				u.setIsplay(0);
				u.setDesignation("无惧黑铁");
				u.setExperiencenum(0l);
				u.setIsdraw(0);
				databaseHelper.persistObject(u);
                Map<String,Object> map = new HashMap<String,Object>();
				
                //注册到环信
                String url = "https://a1.easemob.com/"+ConstantUtil.org_name+"/"+ConstantUtil.app_name+"/users";
        		
        		Map<String, String> params = new HashMap<String, String>();
        		params.put("username",u.getUsername());
        		params.put("password",ConstantUtil.TOKEN);
        		params.put("nickname",u.getNickname());
        		
        		
        		String result = ConstantUtil.PostRequest(url,JsonUtil.getGson().toJson(params),null);
        		Sys.out("hx..regist..result..."+result);
        		JsonObject rjson = (JsonObject) new JsonParser().parse(result);
        		if(rjson.get("entities")!=null){
        			JsonArray array = rjson.get("entities").getAsJsonArray();
        			for (JsonElement jsonElement : array) {
        				JsonObject jObject = jsonElement.getAsJsonObject();
        				Sys.out("username.."+jObject.get("username").getAsString()+"..."+"uuid.."+jObject.get("uuid").getAsString());
        				String uuid = jObject.get("uuid").getAsString();
        				
        				u.setUuid(uuid);
        				databaseHelper.updateObject(u);
        			}
        			map.put("msg", "success");
    				map.put("info", "注册成功");
        		}else{
        			map.put("msg", "fail");
    				map.put("info", "环信服务帐户注册失败");
        		}
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}

			//检查验证码
			private void checkcode(String mobile,int type,String code) throws Exception {
				
				String msg1 = "",msg2 = "";
				if(type==1){
					msg1 = "注册的手机验证码未成功发送噢╮(╯_╰)╭";
					msg2 = "注册的手机验证码不正确噢╮(╯_╰)╭";
				}else if(type==2){
					msg1 = "忘记密码的验证码未成功发送噢╮(╯_╰)╭";
					msg2 = "忘记密码的验证码不正确噢╮(╯_╰)╭";
				}
				
				StringBuffer queryloginpwdcode = new StringBuffer("select cl from CodeLog cl where cl.mobile =")
						.append(Long.parseLong(mobile)).append(" and cl.type = ").append(type).append(" order by cl.createtime desc");
				
				List<Object> lst = databaseHelper.getResultListByHql(queryloginpwdcode.toString());
				
				if(lst.size()==0)throw new Exception(msg1);
				
				CodeLog cl = (CodeLog)lst.get(0);
				
				if(!cl.getCode().equals(code))throw new Exception(msg2);
			}


			@Override
			public String forgotpassword(String mobile, String newpassword, String code) throws Exception {
				// TODO Auto-generated method stub
				if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
				if(newpassword==null||newpassword.length()==0)throw new Exception("请输入新密码");
				if(newpassword.length()>16||newpassword.length()<6)throw new Exception("新密码必须为6到16位");
				
				if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
				
				StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.state = 0 and ui.mobile = '").append(mobile).append("'");
				List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
				
				if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
				
				UserInfo ui = (UserInfo)lst.get(0);
				
				if(ui.getState() < 0)throw new Exception("该手机号的用户已被禁用,请联系管理员噢╮(╯_╰)╭");
				
				checkcode(mobile, 2, code);
				
				ui.setPassword(newpassword);
				
				databaseHelper.updateObject(ui);
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "密码修改成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				
				return json;

			}

			@Override
			public String uploaduserphoto(String userid, String filename) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				ui.setHeadimg(filename);
				
				databaseHelper.updateObject(ui);
				
				 Map<String,Object> map = new HashMap<String,Object>();
					
				 map.put("msg", "success");
				 map.put("info", "上传成功");
				 map.put("url", filename);
				 
				 String json = JsonUtil.getGson().toJson(map);
				return json;
			}
			
			@Override
			public String userinfo(String userid) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				return getuserinfo(ui);
			}


			@Override
			public String modifyuserinfo(String userid, String nickname, String sex, String birthday, String selfinfo)
					throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				
				if(nickname!=null&&nickname.length()>0)ui.setNickname(ConstantUtil.filterEmoji(nickname));
				
				if(sex!=null&&sex.length()>0)ui.setSex(sex);
				
				if(birthday!=null&&birthday.length()>0)ui.setBirthday(birthday);

				if(selfinfo!=null&&selfinfo.length()>0)ui.setSelfinfo(ConstantUtil.filterEmoji(selfinfo));
				
				databaseHelper.updateObject(ui);
				 Map<String,Object> map = new HashMap<String,Object>();
				map.put("msg", "success");
				map.put("info", "修改成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				
				return json;
			}

			@Override
			public String getshippingaddress(String userid) throws Exception {
				// TODO Auto-generated method stub
                if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				int count = countorder(userid);
				StringBuffer query = new StringBuffer("select sa,ui from ShippingAddress sa,UserInfo ui where sa.state = 0 and sa.fuserinfoid = ui.id and sa.fuserinfoid = ")
						.append(Long.parseLong(userid)).append(" order by sa.common DESC");
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				
				List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
				for (Object[] object : lst) {
					ShippingAddress s = (ShippingAddress)object[0];
					UserInfo u = (UserInfo)object[1];
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("addressid", s.getId());
					map.put("consigneename", s.getConsigneename());
					map.put("consigneemobile",s.getConsigneemobile());
					map.put("province", s.getProvince());
					map.put("city",s.getCity());
					map.put("area", s.getArea());
					map.put("address", s.getAddress());
					map.put("common", s.getCommon());
				
					lstMap.add(map);
				}
				
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				
				return obj.toString();
			}
			
			private int countorder(String userid){
				StringBuffer count = new StringBuffer("select count(sa.id) from ShippingAddress sa,UserInfo ui where sa.state = 0 and sa.fuserinfoid = ui.id and sa.fuserinfoid = ")
						.append(Long.parseLong(userid));
				List lst = databaseHelper.getResultListByHql(count.toString());
				return Integer.parseInt(lst.get(0).toString());
			}


			@Override
			public String addnewaddress(String userid, String consigneename, String consigneemobile, String province,
					String city, String area, String address) throws Exception {
				// TODO Auto-generated method stub
				 if(userid==null||userid.length()==0)throw new Exception("用户id错误");
					
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
					
					if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
					
					StringBuffer queryexist = new StringBuffer("select sa from ShippingAddress sa where sa.state = 0 and sa.fuserinfoid = ")
							.append(Long.parseLong(userid));
					List<Object> lst = databaseHelper.getResultListByHql(queryexist.toString());
					if(lst.size()>0){
						for (Object object : lst) {
							ShippingAddress s = (ShippingAddress) object;
							if(s.getProvince().equals(province)&&s.getCity().equals(city)&&s.getArea().equals(area)&&s.getAddress().equals(address))throw new 

Exception("此地址已存在");
						}
					}
					ShippingAddress sa = new ShippingAddress();
					if(userid!=null&&userid.length()>0)sa.setFuserinfoid(Long.parseLong(userid));
					if(consigneename!=null&&consigneename.length()>0)sa.setConsigneename(consigneename);
					if(consigneemobile!=null&&consigneemobile.length()>0)sa.setConsigneemobile(consigneemobile);
					if(province!=null&&province.length()>0)sa.setProvince(province);
					if(city!=null&&city.length()>0)sa.setCity(city);
					if(area!=null&&area.length()>0)sa.setArea(area);
					if(address!=null&&address.length()>0)sa.setAddress(address);
					sa.setCommon(0);
					sa.setCreatetime(new Date());
					sa.setState(0);
					databaseHelper.persistObject(sa);
					 Map<String,Object> map = new HashMap<String,Object>();
					map.put("msg", "success");
					map.put("info", "添加成功*^_^* ");
					
					String json = JsonUtil.getGson().toJson(map);
					
					return json;
			}

			@Override
			public String setdefault(String userid, String addressid) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				StringBuffer queryexciest = new StringBuffer("select sa from ShippingAddress sa where sa.state = 0 and sa.common = 1 and sa.fuserinfoid = ")
							.append(Long.parseLong(userid));
				List<Object>saList = databaseHelper.getResultListByHql(queryexciest.toString());
				if(saList.size()>0){
					ShippingAddress sa  = (ShippingAddress) saList.get(0);
					sa.setCommon(0);
					databaseHelper.updateObject(sa);
				}
				ShippingAddress sa = (ShippingAddress) databaseHelper.getObjectById(ShippingAddress.class, Long.parseLong(addressid));
				sa.setCommon(1);
				databaseHelper.updateObject(sa);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("msg", "success");
				map.put("info", "设置成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}


			@Override
			public String deladdress(String userid, String addressid) throws Exception {
				// TODO Auto-generated method stub
                if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				ShippingAddress sa = (ShippingAddress) databaseHelper.getObjectById(ShippingAddress.class, Long.parseLong(addressid));
				sa.setState(-1);
				databaseHelper.updateObject(sa);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("msg", "success");
				map.put("info", "删除成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}


			@Override
			public String consumption(String userid, Integer start, Integer limit)throws Exception {
				// TODO Auto-generated method stub
                if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				int count = countconsumption(userid);
				int pages = ConstantUtil.pages(count, limit);
				
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				StringBuffer queryconsumption = new StringBuffer("select cp from Consumption cp where cp.paystate>=0 and cp.fuserinfoid = ")
						.append(Long.parseLong(userid)).append(" order by cp.createtime desc");
				List<Object> lst = databaseHelper.getResultListByHql(queryconsumption.toString(), start, limit);
				for (Object objects : lst) {
					Consumption consumption = (Consumption) objects;
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("consumptionid", consumption.getId());
					map.put("type", consumption.getType());
					map.put("paystate", consumption.getPaystate());
					map.put("golds", consumption.getGolds());
					map.put("time", consumption.getCreatetime().toString());
					lstMap.add(map);
				}
				
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.addProperty("pages",pages);
				obj.add("rows", arr);
				
				return obj.toString();
			}


			private int countconsumption(String userid){
				StringBuffer count = new StringBuffer("select count(cp.id) from Consumption cp where cp.paystate>=0 and cp.fuserinfoid = ")
						.append(Long.parseLong(userid)).append(" order by cp.createtime desc");
				List lst = databaseHelper.getResultListByHql(count.toString());
				return Integer.parseInt(lst.get(0).toString());
			}
			
			@Override
			public String applyproxy(String userid, String name, String mobile, String province, String city,
					String area) throws Exception {
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				
				ProxyApply pa = new ProxyApply();
				if(userid!=null&&userid.length()>0)pa.setFuserinfoid(Long.parseLong(userid));
				if(name!=null&&name.length()>0)pa.setName(name);
				if(mobile!=null&&mobile.length()>0)pa.setMobile(mobile);
				if(province!=null&&province.length()>0)pa.setProvince(province);
				if(city!=null&&city.length()>0)pa.setCity(city);
				if(area!=null&&area.length()>0)pa.setArea(area);
				pa.setCreatetime(new Date());
				pa.setState(0);
				databaseHelper.persistObject(pa);
				 Map<String,Object> map = new HashMap<String,Object>();
				map.put("msg", "success");
				map.put("info", "申请成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				
				return json;
		}
			
			@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
			@Override
			public String sign(String userid) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				//检查今天是否已签到
				StringBuffer hql = new StringBuffer("select count(si.id) from SignInfo si where si.fuserinfoid = ").append(Long.parseLong(userid)).append(" and date(si.createtime) = curdate()");
				List lst = databaseHelper.getResultListByHql(hql.toString());
				if(Integer.parseInt(lst.get(0).toString())>0)throw new Exception("你今天已签到噢╮(╯_╰)╭");
				
				int signcount = 1;
				int awardpoint = 0;//连续签到奖励

				if(Integer.parseInt(lst.get(0).toString())==0){
					StringBuffer queryold = new StringBuffer("select si from SignInfo si where si.fuserinfoid = ").append(Long.parseLong(userid))
							.append(" and TO_DAYS(NOW())-TO_DAYS(si.createtime) = 1 ").append(" order by si.createtime desc");
					
					List<Object> yesterdaylst = databaseHelper.getResultListByHql(queryold.toString());
					
					if(yesterdaylst.size()==0){
						signcount = 1;//昨天没有签到，则认为断签，连续签到数重置
						awardpoint=1;//签到第一天 奖励1喵币
					}else{
						SignInfo si = (SignInfo)yesterdaylst.get(0);
						int yesterdaycount = si.getSigncount();
						
						//if(yesterdaycount>=7)signcount = 1;//超过7天 ，连续签到数重置
						
							signcount = yesterdaycount+1;
							
							if(signcount==2)awardpoint=2;//连续2天，奖励2喵币
							else if(signcount==3)awardpoint=3;//连续3天，奖励3喵币
							else if(signcount==4)awardpoint=5;//连续4天，奖励5喵币
							else if(signcount==5)awardpoint=7;//连续5天，奖励7喵币
							else if(signcount==6)awardpoint=8;//连续6天，奖励8喵币
							else if(signcount==7)awardpoint=12;//连续7天，奖励12喵币
							else awardpoint=1;
						
						
					}
					
					SignInfo si = new SignInfo();
					si.setCreatetime(new Date());
					si.setFuserinfoid(Long.parseLong(userid));
					si.setGolds(awardpoint);
					si.setSigncount(signcount);
					databaseHelper.persistObject(si);
					
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
					ui.setBalance(ui.getBalance()+awardpoint);
					databaseHelper.updateObject(ui);
				}
				
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "签到成功噢╮(╯_╰)╭");
				map.put("signcount", signcount);
				
				String json = JsonUtil.getGson().toJson(map);
				return json;
			}


			@Override
			public String appeal(String userid, String content, String photos) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				
				if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				StringBuffer queryappealexcist = new StringBuffer("select ap from Appeal ap where ap.state = 0 and ap.fuserinfoid = ")
						.append(userid);
				List<Object>appeallist = databaseHelper.getResultListByHql(queryappealexcist.toString());
				if(appeallist.size()>0)throw new Exception("请等待上次申诉处理后再发起申诉");
				
		       JsonArray arr	 = (JsonArray) JsonUtil.getJsonParser().parse(photos);
		       Appeal appeal = new Appeal();
			    if(content!=null&&content.length()>0)appeal.setContent(content);
			    appeal.setFuserinfoid(Long.parseLong(userid));
			    appeal.setState(0);
			    appeal.setCreatetime(new Date());
			    databaseHelper.persistObject(appeal);
		       
				for (JsonElement jsonElement : arr) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					String url = jsonObject.get("url").getAsString();
				    
				    AppealPhotos appealPhotos = new AppealPhotos();
				    appealPhotos.setCreatetime(new Date());
				    appealPhotos.setFappealid(appeal.getId());
				    appealPhotos.setPhotourl(url);
				    appealPhotos.setState(0);
				    databaseHelper.persistObject(appealPhotos);
				}
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "申诉成功");
				
				String json = JsonUtil.getGson().toJson(map);
				
				return json;
			}


			@Override
			public String leavemessage(String userid, String content, String title) throws Exception {
				// TODO Auto-generated method stub
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				if(content==null||content.length()==0)throw new Exception("请填写意见内容噢╮(╯_╰)╭");
				if(content.length()>=128)throw new Exception("意见内容不能超过64个字噢╮(╯_╰)╭");
				if(title==null||title.length()==0)throw new Exception("请填写意见标题噢╮(╯_╰)╭");
				if(title.length()>=8)throw new Exception("意见内容不能超过8个字噢╮(╯_╰)╭");
				
				//UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				//if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
				
				StringBuffer queryappealexcist = new StringBuffer("select fb from FeedBack fb where fb.state = 0 and fb.fuserinfoid = ")
						.append(userid);
				List<Object>appeallist = databaseHelper.getResultListByHql(queryappealexcist.toString());
				if(appeallist.size()>0)throw new Exception("请等待上次反馈回复后再发起反馈");
				
				FeedBack feedBack = new FeedBack();
				feedBack.setContent(content);
				feedBack.setCreatetime(new Date());
				feedBack.setFuserinfoid(Long.parseLong(userid));
				feedBack.setState(0);
				feedBack.setTitle(title);
				
				databaseHelper.persistObject(feedBack);
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("msg", "success");
				map.put("info", "意见反馈成功*^_^* ");
				
				String json = JsonUtil.getGson().toJson(map);
				
				return json;
			}


			@Override
			@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
			public String wxlogin(String openid, String nickname, String headimg) throws Exception {
				// TODO Auto-generated method stub
				StringBuffer find = new StringBuffer("select userinfo from UserInfo userinfo where userinfo.state = 0 and userinfo.openid = '").append(openid).append("'");
				List<Object> lst = databaseHelper.getResultListByHql(find.toString());
				
				if(lst.size()==0){
					UserInfo u = new UserInfo();
					
					u.setMobile("");
					u.setPassword("");
					u.setCreatetime(new Date());
					u.setUsername(openid);
					u.setInviter(Long.parseLong("0"));
					u.setNickname(ConstantUtil.filterEmoji(nickname));
					u.setOpenid(openid);
					databaseHelper.persistObject(u);
	                Map<String,Object> map = new HashMap<String,Object>();
					
	                //注册到环信
	                String url = "https://a1.easemob.com/"+ConstantUtil.org_name+"/"+ConstantUtil.app_name+"/users";
	        		
	        		Map<String, String> params = new HashMap<String, String>();
	        		params.put("username",u.getOpenid());
	        		params.put("password",ConstantUtil.TOKEN);
	        		params.put("nickname",u.getNickname());
	        		
	        		
	        		String result = ConstantUtil.PostRequest(url,JsonUtil.getGson().toJson(params),null);
	        		Sys.out("hx..regist..result..."+result);
	        		JsonObject rjson = (JsonObject) new JsonParser().parse(result);
	        		if(rjson.get("entities")!=null){
	        			JsonArray array = rjson.get("entities").getAsJsonArray();
	        			for (JsonElement jsonElement : array) {
	        				JsonObject jObject = jsonElement.getAsJsonObject();
	        				Sys.out("username.."+jObject.get("username").getAsString()+"..."+"uuid.."+jObject.get("uuid").getAsString());
	        				String uuid = jObject.get("uuid").getAsString();
	        				
	        				u.setUuid(uuid);
	        				databaseHelper.updateObject(u);
	        			}
	        			return getuserinfo(u);
	        		}else{
	        			map.put("msg", "fail");
	    				map.put("info", "环信服务帐户注册失败");
	    				String json = JsonUtil.getGson().toJson(map);
	    				return json;
	        		}
					
				}else{
					UserInfo ui = (UserInfo)lst.get(0);
					
					return getuserinfo(ui);
				}
			}


			@Override
			public String userlevellist(String userid, Integer start, Integer limit) throws Exception {
				if(userid==null||userid.length()==0)throw new Exception("用户id错误");
				String sql1="select count(id) from userinfo where state=0 ";
				int count = databaseHelper.getSqlCount(sql1);
				int pages = ConstantUtil.pages(count, limit);
				
				StringBuffer sql2=new StringBuffer("select u.id,u.nickname,u.headimg ,u.selfinfo,u.`level`,u.username, ");
				sql2.append("(select id from userfollow where fuserid="+userid+" and followuserid= u.id and state= 0) isfollow ");
				sql2.append("from userinfo u where u.state=0 ORDER BY u.`level` desc  ");
				List<Object[]> lst=databaseHelper.getResultListBySql(sql2.toString(), start, limit);
                List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				for (Object[] o : lst) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userid", o[0].toString());
					map.put("nickname", o[1]==null?"":o[1].toString());
					map.put("headurl", o[2]==null?"":o[2].toString());
					map.put("selfinfo",o[3]==null?"":o[3].toString());
					map.put("level",o[4]==null?"1":o[4].toString() );
					map.put("username",o[5]==null?"1":o[5].toString() );
					map.put("isfollow",o[6]==null?"0":"1");
					lstMap.add(map);
				}
				
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public void dofollow(String myid, String userid) throws Exception {
				String sql="select count(id) from userfollow where fuserid ="+myid+" and followuserid ="+userid+" and state =0 ";
				int count=databaseHelper.getSqlCount(sql);
				if(count>0)throw new Exception("你已关注过她了");
				UserFollow uf=new UserFollow();
				uf.setFuserid(Long.parseLong(myid));
				uf.setFollowuserid(Long.parseLong(userid));
				uf.setCreatetime(new Date());
				uf.setState(0);
				databaseHelper.persistObject(uf);
				
			}


			@Override
			public void removefollow(String myid, String userid) throws Exception {
				String sql="select count(id) from userfollow where fuserid ="+myid+" and followuserid ="+userid+" and state =0 ";
				int count=databaseHelper.getSqlCount(sql);
				if(count==0)throw new Exception("你还未关注过他");
				String sql1="update userfollow set state=-1 where fuserid ="+myid+" and followuserid ="+userid+" and state =0";
				databaseHelper.executeNativeSql(sql1);
			}


			@Override
			public Map<String, Object> userstate(String myid, String userid) throws Exception {
				UserInfo u= (UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("userid", u.getId().toString());
				map.put("createtime", u.getCreatetime().toString());
				map.put("level", u.getLevel());
				map.put("selfinfo", u.getSelfinfo()==null?"":u.getSelfinfo().toString());
				map.put("nickname", u.getNickname());
				map.put("headurl", u.getHeadimg()==null?"":u.getHeadimg());
				map.put("designation", u.getDesignation());
				if(u.getIsplay()==null||u.getIsplay()==0){
					if(NIOSServer.clientsLMapOfApp.get("userid")==null){
						map.put("state", "0");
					}else{
						map.put("state", "1");
					}
				}else{
					map.put("state", "2");
					map.put("machineid", u.getUsingmachineid());
					MachineInfo m=(MachineInfo) databaseHelper.getObjectById(MachineInfo.class,u.getUsingmachineid());
				    map.put("machineno", m.getMachineno());
				}
				//查用户粉丝数
				String sql1="select count(id) from userfollow where followuserid ="+u.getId()+" and state =0 ";
				map.put("fansnumber", databaseHelper.getSqlCount(sql1));
				//查用户关注数
				
				String sql2 ="select count(id) from userfollow where fuserid ="+u.getId()+" and state =0 ";
				map.put("follownumber",databaseHelper.getSqlCount(sql2) );
				
				
				//我有没有关注过
				String sql3="select count(id) from userfollow where fuserid ="+myid+"  and followuserid = "+u.getId()+"  and state =0 ";
				if(databaseHelper.getSqlCount(sql3)==0){
					map.put("isfollow", "0");
				}else{
					map.put("isfollow", "1");
				}
				return map;
			}


			@Override
			public String myfans(String userid, Integer start, Integer limit) throws Exception {
				String sql1="select count(ui.id) from userinfo ui join userfollow uf  on ui.id=uf.fuserid where uf.state=0  and  uf.followuserid ="+userid+"";
				int count =databaseHelper.getSqlCount(sql1);
				int pages = ConstantUtil.pages(count, limit);
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				if (count>0){
					StringBuffer sql2 =new StringBuffer("select ui.id,ui.nickname,ui.`level` ,ui.selfinfo, ui.headimg ,ui.username, ");
			        sql2.append("(select count(id) from userfollow where fuserid ="+userid+"  and  followuserid =ui.id and state =0) isfollow ");
			        sql2.append("from userinfo ui join userfollow uf  on ui.id=uf.fuserid  ");
			        sql2.append("where uf.state=0  and  uf.followuserid ="+userid+"");
			        List<Object[]> lst=databaseHelper.getResultListBySql(sql2.toString(), start, limit);
			        for (Object[] o : lst) {
						Map<String, Object> map =new HashMap<String, Object>();
						map.put("userid", o[0].toString());
						map.put("nickname", o[1]==null?"":o[1].toString());
						map.put("level", o[2].toString());
						map.put("selfinfo", o[3]==null?"":o[3].toString());
						map.put("headimg", o[4]==null?"":o[4].toString());
						map.put("username", o[5]==null?"":o[5].toString());
						map.put("isfollow", o[6].toString());
						lstMap.add(map);
					}
				}
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public String myfollow(String userid, Integer start, Integer limit) throws Exception {
				String sql1="select count(ui.id) from userinfo ui join userfollow uf  on ui.id=uf.followuserid where uf.state=0  and  uf.fuserid ="+userid+"";
				int count =databaseHelper.getSqlCount(sql1);
				int pages = ConstantUtil.pages(count, limit);
				 List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				if (count>0){
					StringBuffer sql2 =new StringBuffer("select ui.id,ui.nickname,ui.`level` ,ui.selfinfo, ui.headimg,ui.username ");
			        sql2.append("from userinfo ui join userfollow uf  on ui.id=uf.followuserid  ");
			        sql2.append("where uf.state=0  and  uf.fuserid ="+userid+"");
			        List<Object[]> lst=databaseHelper.getResultListBySql(sql2.toString(), start, limit);
			        for (Object[] o : lst) {
						Map<String, Object> map =new HashMap<String, Object>();
						map.put("userid", o[0].toString());
						map.put("nickname", o[1]==null?"":o[1].toString());
						map.put("level", o[2].toString());
						map.put("selfinfo", o[3]==null?"":o[3].toString());
						map.put("headimg", o[4]==null?"":o[4].toString());
						map.put("username", o[5]==null?"":o[5].toString());
						lstMap.add(map);
					}
				}
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public String usersussesstoys(String userid, Integer start, Integer limit) throws Exception {
				String sql1="select count(id) from giftbox where fuserinfoid ="+userid+" and state !=-1 ";
				int count =databaseHelper.getSqlCount(sql1);
				int pages = ConstantUtil.pages(count, limit);
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				if(count>0){
					String sql2="select toysname,photo,createtime from giftbox where fuserinfoid ="+userid+" and state !=-1 ";
					List<Object[]> lst=databaseHelper.getResultListBySql(sql2.toString(), start, limit);
			        for (Object[] o : lst) {
						Map<String, Object> map =new HashMap<String, Object>();
						map.put("toysname", o[0].toString());
						map.put("photo", o[1]==null?"":o[1].toString());
						map.put("createtime", o[2].toString());
						lstMap.add(map);
					}
				}
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public String myuseremember(String userid, Integer start, Integer limit) throws Exception {
				String sql1="select count(id) from videoinfo where fuserid ="+userid+"";
				int count =databaseHelper.getSqlCount(sql1);
				int pages = ConstantUtil.pages(count, limit);
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				if(count>0){
					String Hql2="select vi from VideoInfo  vi where vi.fuserid ="+userid+" ORDER BY vi.time DESC ";
					List<Object> lst=databaseHelper.getResultListByHql(Hql2.toString(), start, limit);
			        for (Object o : lst) {
			        	VideoInfo vi=(VideoInfo) o;
						Map<String, Object> map =new HashMap<String, Object>();
						map.put("vid",vi.getFmachineid().toString());
						map.put("machineid", vi.getFmachineid().toString());
						map.put("toyname",vi.getToyname().toString());
						map.put("toyphoto",vi.getToyphoto().toString());
						map.put("time",vi.getTime().toString());
						map.put("videourl",vi.getVideourl().toString());
						map.put("issuccessed", vi.getSuccessed()==1?"成功":"失败");
						lstMap.add(map);
					}
				}
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public 	String allege() throws Exception {
				String hql= "select a from Allege a where a.state=0 ";
				List<Object> lst=databaseHelper.getResultListByHql(hql);
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
	
				for (Object o : lst) {
					Allege a=(Allege) o;
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("allegeid", a.getId());
					map.put("content", a.getContent());
					lstMap.add(map);
					
				}
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("allegeid", 0);
				map.put("content", "其它");
				lstMap.add(map);
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.add("rows", arr);
				
				return obj.toString();
			}


			@Override
			public void userallege(String userid, String allegeid, String substance, String videoinfoid)
					throws Exception {
				UserAllege ua=new UserAllege();
				ua.setFuserid(Long.parseLong(userid));
				ua.setFallegeid(Long.parseLong(allegeid));
				ua.setSubstance(substance);
				ua.setVideoinfoid(Long.parseLong(videoinfoid));
				ua.setCreatetime(new Date());
				ua.setState(0);
				databaseHelper.persistObject(ua);	
			}


			@Override
			public Map<String, Object> my(String userid) throws Exception {
				Map<String, Object> map =new HashMap<String, Object>();
				UserInfo ui=(UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				map.put("userid", ui.getId());
				map.put("nickname", ui.getNickname()==null?"":ui.getNickname());
				map.put("headurl", ui.getHeadimg()==null?"":ui.getHeadimg());
				map.put("level", ui.getLevel());
				map.put("designation", ui.getDesignation());
				
				//查用户粉丝数
				String sql1="select count(id) from userfollow where followuserid ="+userid+" and state =0 ";
				map.put("fansnumber", databaseHelper.getSqlCount(sql1));
				//查用户关注数
				
				String sql2 ="select count(id) from userfollow where fuserid ="+userid+" and state =0 ";
				map.put("follownumber",databaseHelper.getSqlCount(sql2) );
				
				return map;
			}


			@Override
			public String nearsuccess(String machineid, int start, int limit) throws Exception {
				String countsql=" select count(id) from videoinfo where fmachineid ="+machineid+"  and state=0 and successed =1  ";
				int count =databaseHelper.getSqlCount(countsql);
				int pages = ConstantUtil.pages(count, limit);
				List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
				if(count>0){
					StringBuffer querysql= new StringBuffer(" select ui.nickname, ui.headimg,ui.`level`, vi.time,vi.videourl ");
					querysql.append(" from videoinfo vi join userinfo ui on vi.fuserid =ui.id ");
					querysql.append(" where vi.fmachineid ="+machineid+"  and vi.state=0 and vi.successed =1  ");
					querysql.append("  order by  vi.time desc ");
					List<Object[]> lst=databaseHelper.getResultListBySql(querysql.toString(), start, limit);
					for (Object[] obj : lst) {
						Map<String,Object> map =new HashMap<String, Object>();
						map.put("nickname", obj[0].toString());
						map.put("headurl",obj[1]==null?"": obj[1].toString());
						map.put("level", obj[2].toString());
						map.put("createtime", obj[3].toString());
						map.put("videourl", obj[4].toString());
						lstMap.add(map);
					}
				
				}
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.addProperty("total",count);
				obj.add("rows", arr);
				obj.addProperty("pages", pages);
				return obj.toString();
			}


			@Override
			public Map<String, Object> usershare(String userid) throws Exception {
				Map<String, Object> map =new HashMap<String, Object>();

				String shareno=setshareno(userid);
				map.put("html", ConstantUtil.server_url+"/down/down.html");
				map.put("title", "爱抓 — 抓出你想要" );
				map.put("description", "我在用手机在线抓娃娃,"
						+ "下载填邀请码"+shareno+"得免玩劵免费玩~");
				map.put("thumbimage", ConstantUtil.server_url+"/image/lovezhua.png");
				map.put("shareno", shareno);
				return map;
			}


			@Override
			@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
			public void invitema(String userid, String shareno) throws Exception {
				
				if(shareno.length()!=7)throw new Exception("邀请码不正确！");
				
				
				
				String hql="  select u from UserInfo u where u.id='"+getid(shareno)+"' ";
				List<Object> lst= databaseHelper.getResultListByHql(hql);
				if(lst.size()==0||lst.get(0)==null)throw new Exception("邀请码不正确！");
				UserInfo mu=(UserInfo) lst.get(0);
				if(mu.getId()==Long.parseLong(userid))throw new Exception("不能使用自己的邀请码！");
				mu.setFreevoucher(mu.getFreevoucher()+1);
				databaseHelper.updateObject(mu);
				
				//当签名用户
				UserInfo ui =(UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
				if(ui.getIsdraw()==1) throw new Exception("您已经领取过免玩券！");
				ui.setFreevoucher(ui.getFreevoucher()+1);
				ui.setIsdraw(1);
				databaseHelper.updateObject(ui);
				NotifyInfo ni=new NotifyInfo();
				ni.setType(0);
				ni.setFuserinfoid(mu.getId());
				ni.setTitle("邀请用户提醒");
				ni.setContent("成功邀请用户"+ui.getMobile()+"，系统已为您增加1个免玩券");
				ni.setCreatetime(new Date());
				ni.setState(0);
				databaseHelper.persistObject(ni);
				
				//邀请用户
				
				
			}


			@Override
			public Map<String, Object> testedition(String editionno, String system) throws Exception {
				
				String hql =" select e from Edition e where e.state=0  ";
				List<Object> lst=databaseHelper.getResultListByHql(hql);
				Edition e1=(Edition) lst.get(0);
				Edition e2=(Edition) lst.get(1);
			    Map<String, Object> map =new HashMap<String, Object>();
				if(system.equals("android")){
					if(e1.getType()==1){
						if(!e1.getEditionno().equals(editionno)){
							map.put("msg", "fail");
							map.put("info", e1.getUrl());
							map.put("editionno", e1.getEditionno());
							map.put("Description", e1.getDescription());
						}
					}else{
						if(!e2.getEditionno().equals(editionno)){
							map.put("msg", "fail");
							map.put("info", e2.getUrl());
							map.put("editionno", e2.getEditionno());
							map.put("Description", e2.getDescription());
						}
					}
				}else if(system.equals("apple")){
					if(e1.getType()==2){
						if(!e1.getEditionno().equals(editionno)){
							map.put("msg", "fail");
							map.put("info", e1.getUrl());
							map.put("editionno", e1.getEditionno());
							map.put("Description", e1.getDescription());
						}
					}else{
						if(!e2.getEditionno().equals(editionno)){
							map.put("msg", "fail");
							map.put("info", e1.getUrl());
							map.put("editionno", e1.getEditionno());
							map.put("Description", e1.getDescription());
						}
					}
				}else{
					throw new Exception("参数错误");
				}
				if(map.get("msg")==null){
					map.put("msg", "success");
				}
				return map;
				
				
			}


			@Override
			public String banner() throws Exception {
				
				String Hql=" select b from Banner b where b.state=0 ";
				List<Object> lst=databaseHelper.getResultListByHql(Hql);
				List<Map<String, Object> > lstMap=new ArrayList<Map<String,Object>>();
				for (Object obj : lst) {
					Banner ban= (Banner) obj; 
					Map<String, Object> map =new HashMap<String, Object>();
					map.put("imgurl", ban.getUrl());
					map.put("type", ban.getType());
					map.put("url", ban.getChangeurl());
					lstMap.add(map);
				}
				
				
				String json = new Gson().toJson(lstMap);
				JsonArray arr = (JsonArray) new JsonParser().parse(json);
				JsonObject obj = new JsonObject();
				obj.add("rows", arr);
				return obj.toString();
			}
			
			
		//根据userid生成邀请码的加密算法
			public  String setshareno (String userid){
				String []arr={"1","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n",
						
						
						"o","p","q","r","s","t","u","v","w","x","y"};
				
				
				
				int length=userid.length();
				if(length<8){
					for(int i=7;i>=length;i--){
						userid+=arr[i]+"";
					}
				}
				String ma="";
			
				for(int i=0;i<6;i++){
					ma+=arr[(int) (Long.parseLong(userid)%(arr.length-i))];
				}
				ma+=length+"";
				System.out.println("加密码："+ma);
				return ma;
			}
       //解密算法
			
		public  String getid(String shareno ){
			String []arr={"1","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n",
					
					
					"o","p","q","r","s","t","u","v","w","x","y"};
			int length=Integer.parseInt(shareno.substring(6, 7));
			String []farr=new String[6];
			for(int i=0;i<shareno.substring(0, 6).length();i++){
				farr[i]=shareno.substring(0, 6).substring(i, i+1);
			}
			
			int []anum= new int[6];
			for(int i=0;i<arr.length;i++){
				if(arr[i].equals(farr[0])){
				   anum[0]=i;
				}
				if(arr[i].equals(farr[1])){
					   anum[1]=i;
					}
				if(arr[i].equals(farr[2])){
					   anum[2]=i;
					}
				if(arr[i].equals(farr[3])){
					   anum[3]=i;
					}
				if(arr[i].equals(farr[4])){
					   anum[4]=i;
					}
				if(arr[i].equals(farr[5])){
					   anum[5]=i;
					}
				
			}
		
			String userid="";
			for(int i=10000000;i<=99999999;i++){
				if(i%33==anum[0]&&i%32==anum[1]&&i%31==anum[2]&&i%30==anum[3]&&i%29==anum[4]&&i%28==anum[5]){
					String getma=i+"";
					
					String lastma="";
					for(int j=7;j>=length;j--){
						lastma+=arr[j]+"";
					}
					if(getma.substring(length, getma.length()).equals(lastma)){
						userid= getma.substring(0,length);
					}
				}
				
			}
			return userid;
		}
		
}
