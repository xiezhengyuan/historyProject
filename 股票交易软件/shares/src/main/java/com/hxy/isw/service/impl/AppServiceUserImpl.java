package com.hxy.isw.service.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.Bank;
import com.hxy.isw.entity.CodeLog;
import com.hxy.isw.entity.Globepays;
import com.hxy.isw.entity.GoldsDetail;
import com.hxy.isw.entity.PayInfoByWX;
import com.hxy.isw.entity.ProportionInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserProfitStatistic;
import com.hxy.isw.entity.UserRecharge;
import com.hxy.isw.service.AppServiceUser;
import com.hxy.isw.util.AlipayConfig;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.HttpRequest;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.MD5;
import com.hxy.isw.util.SignatureUtil;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年7月20日 下午3:13:41
* @describe
*/

@Repository
public class AppServiceUserImpl implements AppServiceUser {

	@Autowired
	DatabaseHelper databaseHelper;

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String getcode(String type, String mobile) throws Exception {
		// TODO Auto-generated method stub
		if(type==null||type.length()==0)throw new Exception("参数type错误");
		
		if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号码");
		
		int int_type = Integer.parseInt(type);
		
		UserInfo ui = null;
		if(int_type == 2){//忘记密码 ，需要输入手机号检查用户是否存在
			
			
			StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.mobile = '").append(mobile).append("'");
			List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
			
			if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
			
			ui = (UserInfo)lst.get(0);
			
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
		
		String content = "【FX方程式】验证码为"+code+"。请勿泄露。";
		
//		boolean flag = ConstantUtil.yunpian(content,mobile);
		boolean flag = ConstantUtil.zz253(content, mobile);
		
		
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

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String login(String mobile, String password) throws Exception {
		// TODO Auto-generated method stub
		if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
		
		if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
		
		if(password==null||password.length()==0)throw new Exception("请输入密码");
		
		//检查用户名是否合法（防止sql注入）
		if(!ConstantUtil.checkUsername(mobile))throw new Exception("手机号码包含非法字符");
		
		StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.state = 0 and (ui.mobile = '").append(mobile).append("'").append(" or ui.username = '").append(mobile).append("')");
		List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
		
		if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
		
		UserInfo ui = (UserInfo)lst.get(0);
		
		if(ui.getState() < -1)throw new Exception("该手机号的用户已被禁用,请联系管理员o(︶︿︶)o");
		
		/*if(ui.getLoginstate() == 1){
			
			ui.setLoginstate(0);
			databaseHelper.updateObject(ui);
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("msg", "fail");
			map.put("info", "该用户已在线，请不要重复登录(︶︿︶)o");
			
			String json = JsonUtil.getGson().toJson(map);
			
			return json;
			
		}*/
		
		String md5Password_real = ConstantUtil.getMD5Str(ui.getPassword()+SignatureUtil.TOKEN,null);
		
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
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "登录成功");
		map.put("userid", ui.getId());
		map.put("isexample", ui.getIsexample());
		map.put("name",ui.getName());
		map.put("nickname",ui.getNickname());
		map.put("speech",ui.getSpeech());
		map.put("golds",ui.getGolds());
		map.put("virtualcapital",ui.getVirtualcapital());
		map.put("mobile",ui.getMobile());
		map.put("totalrecharge",ui.getTotalrecharge());
		map.put("username",ui.getUsername());
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void uploaduserphoto(String userid, String filename) throws Exception {
		// TODO Auto-generated method stub
		
		if(userid==null||userid.length()==0)throw new Exception("用户id错误");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		if(ui==null)throw new Exception("id为"+userid+"的用户不存在");
		
		ui.setHeadimg(ConstantUtil.SERVERURL+filename);
		
		databaseHelper.updateObject(ui);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String registe(String mobile, String password, String code,String faccountid,String finvateid,String openid,String nickname,String headimgurl,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
		
		if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
		
		if(password==null||password.length()==0)throw new Exception("请输入密码");
		
		if(password==null||password.length()==0)throw new Exception("请输入验证码");
		
		String hql = "select u from UserInfo u where u.mobile = '"+mobile+"'";
		List<Object> lst =  databaseHelper.getResultListByHql(hql);
		if(lst.size()>0)throw new Exception("该手机已注册");
		int type = 1;
		checkcode(mobile, type, code);
		
		UserInfo u = new UserInfo();
		u.setMobile(mobile);
		u.setPassword(password);
		u.setCreatetime(new Date());
		u.setUsername(mobile);
		u.setGolds(0d);
		u.setVirtualcapital(0d);
		u.setTotalrecharge(0d);
		Sys.out("finvateid..."+finvateid);
		if(finvateid!=null&&finvateid.length()>0&&!finvateid.equals("null")){
			u.setFinvateid(Long.parseLong(finvateid));
			
			//邀请人获得金币
			UserInfo invater = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(finvateid));
			invater.setGolds(invater.getGolds()+ConstantUtil.setting.getInvitefriends());
			databaseHelper.updateObject(invater);
			
			//添加到金币表
			GoldsDetail gd = new GoldsDetail();
			gd.setCreatetime(new Date());
			gd.setFuserinfoid(invater.getId());
			gd.setGolds(Double.parseDouble(ConstantUtil.setting.getInvitefriends().toString()));
			gd.setState(0);
			gd.setType(3);
			databaseHelper.persistObject(gd);
			
		}else u.setFinvateid(0l);
		
		Sys.out("faccountid..."+finvateid);
		if(faccountid!=null&&faccountid.length()>0&&!faccountid.equals("null"))u.setFaccountid(Long.parseLong(faccountid));
		else{
			//查找默认业务员
			StringBuffer query = new StringBuffer("select a from AccountInfo a where a.state = 0 and a.defaultaccount = 1 and a.role = ").append(ConstantUtil.ROLE_COMPANY_SALESMAN);
			List<Object> qlst = databaseHelper.getResultListByHql(query.toString());
			
			if(qlst.size()==0)u.setFaccountid(0l);
			else{
				AccountInfo a = (AccountInfo)qlst.get(0);
				u.setFaccountid(a.getId());
			}
		}
		u.setState(0);
		u.setSpeech(0);
		u.setIsexample(0);
		
		if(openid!=null&&openid.length()>0&&!openid.equals("null"))u.setWxid(openid);
		if(nickname!=null&&nickname.length()>0&&!nickname.equals("null"))u.setNickname(ConstantUtil.filterEmoji(nickname));
		if(headimgurl!=null&&headimgurl.length()>0&&!headimgurl.equals("null"))u.setHeadimg(headimgurl);
		
		databaseHelper.persistObject(u);
		
		if(finvateid!=null&&finvateid.length()>0&&!finvateid.equals("null")){
			//被邀请人获得金币
			u.setGolds(u.getGolds()+ConstantUtil.setting.getFriends());
			databaseHelper.updateObject(u);
			
			//添加到金币表
			GoldsDetail gdu = new GoldsDetail();
			gdu.setCreatetime(new Date());
			gdu.setFuserinfoid(u.getId());
			gdu.setGolds(Double.parseDouble(ConstantUtil.setting.getInvitefriends().toString()));
			gdu.setState(0);
			gdu.setType(3);
			databaseHelper.persistObject(gdu);
		}
		
		//TODO 生成推广链接和推广二维码
/*		String spreadurl= "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConstantUtil.APPID+"&redirect_uri="+ConstantUtil.MOBILEURL+"?finvateid="+u.getId()+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
*/		
		String spreadurl=ConstantUtil.MOBILEURL+"?finvateid="+u.getId();
		String filePath = request.getRealPath("/qrcode"); //获取文件需要上传到的路径;
		String ercode = ConstantUtil.genEncode(spreadurl,filePath);
		Sys.out("ercode..."+ercode);
		
		u.setSpreadurl(spreadurl);
		u.setSpreadurlimg(ercode);
		
		Sys.out("userinfo...id:"+u.getId()+"...Spreadurlimg.."+ercode);
		
		databaseHelper.updateObject(u);
		
		Sys.out("register...success");
		
        Map<String,Object> map = new HashMap<String,Object>();
        
        
		map.put("msg", "success");
		map.put("info", "注册成功");
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

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String forgotpassword(String mobile, String newpassword, String code) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if(mobile==null||mobile.length()==0)throw new Exception("请输入手机号");
		if(newpassword==null||newpassword.length()==0)throw new Exception("请输入新密码");
		if(newpassword.length()>16||newpassword.length()<6)throw new Exception("新密码必须为6到16位");
		
		if(!ConstantUtil.isPhoneLegal(mobile))throw new Exception("手机号码格式错误");
		
		StringBuffer querymobile = new StringBuffer("select ui from UserInfo ui where ui.state = 0 and ui.mobile = '").append(mobile).append("'");
		List<Object> lst = databaseHelper.getResultListByHql(querymobile.toString());
		
		if(lst.size()==0)throw new Exception("该手机号的用户不存在噢╮(╯_╰)╭");
		
		UserInfo ui = (UserInfo)lst.get(0);
		
		if(ui.getState() < -1)throw new Exception("该手机号的用户已被禁用,请联系管理员噢╮(╯_╰)╭");
		
		checkcode(mobile, 2, code);
		
		ui.setPassword(newpassword);
		
		databaseHelper.updateObject(ui);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "密码修改成功*^_^* ");
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String sevendayprofit(String userid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid错误");
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		
        StringBuffer query = null;
		
		for (int i = 0; i <= 3; i++) {
			Map<String,Object> map = timeindexofday(query, userid, now, i, sdf);
	        
	        lstMap.add(map);
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.add("rows", arr);
		
		return obj.toString();
	}

	private Map<String, Object> timeindexofday(StringBuffer query,String userid,Date now,int i,SimpleDateFormat sdf){
		Map<String,Object> map = new HashMap<String,Object>();
		
		query = new StringBuffer("select sum(upt.diffprofitoflasttime) 'a' from userprofit upt where upt.fuserinfoid = ").append(userid)
			.append(" and TO_DAYS(NOW()) - TO_DAYS(upt.time) = ").append(i); 
		
		List<Object> lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, -i);
        
        Date time = c.getTime();
        
        map.put("profit", profit);
        map.put("time", sdf.format(time));
        return map;
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String myprofit(String userid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid错误");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", ui.getName());
		map.put("nickname", ui.getNickname());
		map.put("headimg", ui.getHeadimg()==null?"":ui.getHeadimg());
		map.put("isexample", ui.getIsexample());
		map.put("totalamount", ConstantUtil.formatDouble(ui.getVirtualcapital()));
		map.put("golds", ui.getGolds());

		StringBuffer find = new StringBuffer("select sum(upt.diffprofitoflasttime) 'a', sum(upt.diffprofitoflasttimerate) 'b' from userprofit upt where upt.fuserinfoid = ").append(ui.getId())
				.append(" and upt.tags = 1 and TO_DAYS(NOW()) - TO_DAYS(upt.time) = 0"); 
		
		List<Object[]> xlst = databaseHelper.getResultListBySql(find.toString());
			
		Double todayreference = xlst==null?0d:xlst.size()==0?0d:xlst.get(0)[0]==null?0d:Double.parseDouble(xlst.get(0)[0].toString());
		Double todayreferencerate = xlst==null?0d:xlst.size()==0?0d:xlst.get(0)[1]==null?0d:Double.parseDouble(xlst.get(0)[1].toString());
		map.put("todayreference", ConstantUtil.formatDouble(todayreference));//今日盈亏参考
		map.put("todayreferencerate", ConstantUtil.formatDouble(todayreferencerate));//今日盈亏参考（率）
		
		find = new StringBuffer("select ups from UserProfitStatistic ups where ups.fuserinfoid = ").append(ui.getId());
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		if(lst.size()>0){
			UserProfitStatistic ups = (UserProfitStatistic)lst.get(0);
			map.put("profitloss", ConstantUtil.formatDouble(ups.getProfitloss()));//浮动盈亏
			map.put("marketvalue", ConstantUtil.formatDouble(ups.getTotalprofit()+ups.getTotalcost()));//持有市值
			map.put("marketvalueofhs", ConstantUtil.formatDouble(ups.getProfitofhs()+ups.getCostofhs()));
			map.put("totalprofitofhs", ConstantUtil.formatDouble(ups.getProfitofhs()));
			map.put("totalprofitrateofhs", ConstantUtil.formatDouble(ups.getRateofhs()));
			map.put("marketvalueofwh", ConstantUtil.formatDouble(ups.getProfitofwh()+ups.getCostofwh()));
			map.put("totalprofitofwh", ConstantUtil.formatDouble(ups.getProfitofwh()));
			map.put("totalprofitrateofwh", ConstantUtil.formatDouble(ups.getRateofwh()));
			map.put("marketvalueofus", ConstantUtil.formatDouble(ups.getProfitofus()+ups.getCostofus()));
			map.put("totalprofitofus", ConstantUtil.formatDouble(ups.getProfitofus()));
			map.put("totalprofitrateofus", ConstantUtil.formatDouble(ups.getRateofus()));
			map.put("profitlossofhs", ConstantUtil.formatDouble(ups.getProfitlossofhs()));
			map.put("profitlossrateofhs", ConstantUtil.formatDouble(ups.getProfitlossrateofhs()));
			map.put("profitlossofwh", ConstantUtil.formatDouble(ups.getProfitlossofwh()));
			map.put("profitlossrateofwh", ConstantUtil.formatDouble(ups.getProfitlossrateofwh()));
			map.put("profitlossofus", ConstantUtil.formatDouble(ups.getProfitlossofus()));
			map.put("profitlossrateofus", ConstantUtil.formatDouble(ups.getProfitlossrateofus()));
		}else{
			map.put("profitloss", 0);//浮动盈亏
			map.put("marketvalue", 0);
			map.put("marketvalueofhs", 0);
			map.put("totalprofitofhs", 0);
			map.put("totalprofitrateofhs", 0);
			map.put("marketvalueofwh", 0);
			map.put("totalprofitofwh", 0);
			map.put("totalprofitrateofwh", 0);
			map.put("marketvalueofus", 0);
			map.put("totalprofitofus", 0);
			map.put("totalprofitrateofus", 0);
			map.put("profitlossofhs", 0);
			map.put("profitlossrateofhs", 0);
			map.put("profitlossofwh", 0);
			map.put("profitlossrateofwh", 0);
			map.put("profitlossofus", 0);
			map.put("profitlossrateofus", 0);
		}
		
		//查看用户的冻结资金
		find = new StringBuffer("select sum(se.frozenamount) 'a' from sharesentrust se where se.entruststate = 0 and se.state = 0 and se.fuserinfoid = ").append(ui.getId());
		lst = databaseHelper.getResultListBySql(find.toString());
		Double sharesfrozenamount = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		find = new StringBuffer("select sum(fe.frozenamount) 'a' from foreignexchangeentrust fe where fe.entruststate = 0 and fe.state = 0 and fe.fuserinfoid = ").append(ui.getId());
		lst = databaseHelper.getResultListBySql(find.toString());
		Double fefrozenamount = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		map.put("coulduseamount", (ui.getVirtualcapital() - (sharesfrozenamount+fefrozenamount))<0?0:ConstantUtil.formatDouble((ui.getVirtualcapital() - (sharesfrozenamount+fefrozenamount))));
		map.put("frozenamount", ConstantUtil.formatDouble(sharesfrozenamount+fefrozenamount));
		
		//查看用户的跟单资产
		find = new StringBuffer("select sum(d.actualyamount) 'a' from documentary d where  d.state = 0 and d.fuserinfoid = ").append(ui.getId());
		lst = databaseHelper.getResultListBySql(find.toString());
		Double followamount = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		
		map.put("followamount", ConstantUtil.formatDouble(followamount));
		
		String json = JsonUtil.getGson().toJson(map);
		
		return json;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public String recharge(String userid, String amount, int paymentway,String ip) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid错误");
		if(amount==null||amount.length()==0)throw new Exception("参数amount错误");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String outtradno = sdf.format(now);
		
		UserRecharge ur = new UserRecharge();
		ur.setAmount(Double.parseDouble(amount));
		ur.setCreatetime(now);
		ur.setFuserinfoid(ui.getId());
		ur.setGolds(Double.parseDouble(amount));
		ur.setOuttradno(outtradno);
		ur.setPaystate(0);
		ur.setState(0);
		ur.setPaymentway("weixin");
		databaseHelper.persistObject(ur);
		
		
		/*Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "提交成功");
		
		String result = JsonUtil.getGson().toJson(map);*/
		JsonObject obj = new JsonObject();
		
		String result = obj.toString();
		
		//TODO  微信统一下单
		if(paymentway==1)result = unifiedorder(obj, ip, Double.parseDouble(amount), outtradno).toString();
		
		return result;
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
						  
						  String timestamp = ConstantUtil.create_timestamp();
						  
						 /* String packageValue = "Sign=WXPay";
						  String sign4pay = sign4pay(ConstantUtil.APPID,timestamp , nonce_str, packageValue, ConstantUtil.mch_id, prepay_id);
						  
						  obj.addProperty("appid", ConstantUtil.APPID);
						  obj.addProperty("timestamp",timestamp);
						  obj.addProperty("noncestr", nonce_str);
						  obj.addProperty("package", packageValue);
						  obj.addProperty("partnerid", ConstantUtil.mch_id);
						  obj.addProperty("prepayid", prepay_id);
						  obj.addProperty("sign", sign4pay);*/
						  
						  obj.addProperty("appId", ConstantUtil.APPID);
						  obj.addProperty("timestamp", timestamp);
						  obj.addProperty("nonceStr",nonce_str);
						  obj.addProperty("packMage", result.get("prepay_id"));
						  obj.addProperty("signType", "MD5");
						  obj.addProperty("paySign", sign4pay(ConstantUtil.APPID, timestamp, nonce_str, "prepay_id="+result.get("prepay_id"), "MD5"));
						  
						  obj.addProperty("msg","success");
						  obj.addProperty("info","提交成功，待支付。");
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
		
		
		//微信支付签名--APP
		//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
		private  String sign4pay(String appId, String timeStamp, String nonceStr,String packageValue,String partnerid,String prepayid){
			String stringA = "appid="+appId+"&noncestr="+nonceStr+"&package="+packageValue+"&partnerid="+partnerid+"&prepayid="+prepayid+"&timestamp="+timeStamp;
			String stringSignTemp=stringA+"&key="+ ConstantUtil.key;
			Sys.out("sign4pay.."+stringSignTemp);
			return MD5.JM(stringSignTemp).toUpperCase();
		}
		
		//微信支付签名--微信公众号
		//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
		private  String sign4pay(String appId, String timeStamp, String nonceStr,String ppackage,String signType){
			String stringA = "appId="+appId+"&nonceStr="+nonceStr+"&package="+ppackage+"&signType="+signType+"&timeStamp="+timeStamp;
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
		public boolean wxnotify(PayInfoByWX p) throws Exception {
			// TODO Auto-generated method stub
			Date now = new Date();
			
			//检查金额是否相符
			StringBuffer checkhql = new StringBuffer("select ur from UserRecharge ur where  ur.state = 0 and ur.outtradno = '").append(p.getOuttradeno()).append("'");
	        List<Object> checklst = databaseHelper.getResultListByHql(checkhql.toString());
	        Double totalmoney = 0.0;
	        if(checklst.size()==0)return false;
	        
	        if(((UserRecharge)checklst.get(0)).getPaystate()==1)return false;
	        
	        UserRecharge ur = (UserRecharge)checklst.get(0);
	        
	        totalmoney = ur.getAmount();
			
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
			
			StringBuffer pay = new StringBuffer("select p from PayInfoByWX p where p.outtradeno= '").append(p.getOuttradeno()).append("'");
			List<Object> pl = databaseHelper.getResultListByHql(pay.toString());
			if(pl.size()>0)return false;
			
			databaseHelper.persistObject(p);
			
			//将订单状态置为已支付
			ur.setPaystate(1);
		    databaseHelper.updateObject(ur);
		    
		    //支付成功后的金额分成
		    paysuccess(ur, now);
		    
		    return true;
		}

		//支付成功后的金额分成
		private void paysuccess(UserRecharge ur,Date now) throws Exception{
			 UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, ur.getFuserinfoid());
			    ui.setGolds(ui.getGolds()+ur.getGolds());
			    databaseHelper.updateObject(ui);
			    
			    GoldsDetail gd = new GoldsDetail();
			    gd.setCreatetime(new Date());
			    gd.setFuserinfoid(ui.getId());
			    gd.setGolds(ur.getGolds());
			    gd.setState(0);
			    gd.setType(1);// -7金币兑换虚拟资金  -6 管理员扣除  -5 打赏别人 -4观摩计划 -3抢购计划 -2提现 -1订阅   1充值 2被人打赏 3邀请好友 4管理员赠送
				
			    databaseHelper.persistObject(gd);
			    
			    
			    //找出用户的推广业务员、经理、公司、平台
			    AccountInfo ai_saler = (AccountInfo)databaseHelper.getObjectById(AccountInfo.class, ui.getFaccountid());//业务员
			    AccountInfo ai_manager = (AccountInfo)databaseHelper.getObjectById(AccountInfo.class, ai_saler.getFparentid());//经理
			    AccountInfo ai_company = (AccountInfo)databaseHelper.getObjectById(AccountInfo.class, ai_manager.getFparentid());//公司
			    
			    StringBuffer findadmin = new StringBuffer("select ai from AccountInfo ai where ai.role = 0 and ai.state = 0 ");
			    AccountInfo ai_admin = (AccountInfo)databaseHelper.getResultListByHql(findadmin.toString()).get(0); //管理员
			    
			    if(ai_company.getProportion()>1||ai_manager.getProportion()>1||ai_saler.getProportion()>1)throw new Exception("分成比例错误");
			    
			    Double amount_admin = ur.getAmount()*(1-ai_company.getProportion());//平台分成
			    Double amount_company = ur.getAmount()*ai_company.getProportion()*(1-ai_manager.getProportion());//公司分成
			    Double amount_manager = ur.getAmount()*ai_company.getProportion()*ai_manager.getProportion()*(1-ai_saler.getProportion());//经理分成
			    Double amount_saler = ur.getAmount()*ai_company.getProportion()*ai_manager.getProportion()*ai_saler.getProportion();//经理分成
			    
			    //添加到分成记录表--平台
			    ProportionInfo pi_admin = new ProportionInfo();
			    pi_admin.setAmount(amount_admin);
			    pi_admin.setFaccountinfoid(ai_admin.getId());
			    pi_admin.setFcompanyid(ai_company.getFcompanyid());
			    pi_admin.setFuserrechargeid(ur.getId());
			    pi_admin.setRole(ai_admin.getRole());
			    pi_admin.setTime(now);
			    databaseHelper.persistObject(pi_admin);
			    
			    //添加到分成记录表--公司
			    ProportionInfo pi_company = new ProportionInfo();
			    pi_admin.setAmount(amount_company);
			    pi_admin.setFaccountinfoid(ai_company.getId());
			    pi_admin.setFcompanyid(ai_company.getFcompanyid());
			    pi_admin.setFuserrechargeid(ur.getId());
			    pi_admin.setRole(ai_company.getRole());
			    pi_admin.setTime(now);
			    databaseHelper.persistObject(pi_company);
			    
			    //添加到分成记录表--经理
			    ProportionInfo pi_manager = new ProportionInfo();
			    pi_admin.setAmount(amount_manager);
			    pi_admin.setFaccountinfoid(ai_manager.getId());
			    pi_admin.setFcompanyid(ai_manager.getFcompanyid());
			    pi_admin.setFuserrechargeid(ur.getId());
			    pi_admin.setRole(ai_manager.getRole());
			    pi_admin.setTime(now);
			    databaseHelper.persistObject(pi_manager);
			    
			    //添加到分成记录表--业务员
			    ProportionInfo pi_saler = new ProportionInfo();
			    pi_admin.setAmount(amount_saler);
			    pi_admin.setFaccountinfoid(ai_saler.getId());
			    pi_admin.setFcompanyid(ai_saler.getFcompanyid());
			    pi_admin.setFuserrechargeid(ur.getId());
			    pi_admin.setRole(ai_saler.getRole());
			    pi_admin.setTime(now);
			    databaseHelper.persistObject(pi_saler);
		}
		
		
		public static void main(String[] args) {
			UserRecharge ur = new UserRecharge();
			ur.setAmount(100d);
			AccountInfo ai_saler = new AccountInfo();//业务员
			ai_saler.setProportion(0.3d);
			AccountInfo ai_manager = new AccountInfo();//经理
			ai_manager.setProportion(0.5d);
			AccountInfo ai_company = new AccountInfo();//公司
			ai_company.setProportion(0.7d);
			
			AccountInfo ai_admin = new AccountInfo();//
			
			Double amount_admin = ur.getAmount()*(1-ai_company.getProportion());//平台分成
		    Double amount_company = ur.getAmount()*ai_company.getProportion()*(1-ai_manager.getProportion());//公司分成
		    Double amount_manager = ur.getAmount()*ai_company.getProportion()*ai_manager.getProportion()*(1-ai_saler.getProportion());//经理分成
		    Double amount_saler = ur.getAmount()*ai_company.getProportion()*ai_manager.getProportion()*ai_saler.getProportion();//经理分成
		    
		    Sys.out("amount_admin:"+amount_admin);
		    Sys.out("amount_company:"+amount_company);
		    Sys.out("amount_manager:"+amount_manager);
		    Sys.out("amount_saler:"+amount_saler);
		    Sys.out("amount:"+(amount_saler+amount_admin+amount_company+amount_manager));
		}

		@Override
		public String rechargeOfPC(String userid, String amount, String paymentway) throws Exception {
			
			if(userid==null||userid.length()==0)throw new Exception("参数userid错误");
			if(amount==null||amount.length()==0)throw new Exception("参数amount错误");
			if(paymentway==null||paymentway.length()==0)throw new Exception("请填写银行代码");
			
			UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
			
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String outtradno = sdf.format(now);
			
			UserRecharge ur = new UserRecharge();
			ur.setAmount(Double.parseDouble(amount));
			ur.setCreatetime(now);
			ur.setFuserinfoid(ui.getId());
			ur.setGolds(Double.parseDouble(amount));
			ur.setOuttradno(outtradno);
			ur.setPaystate(0);
			ur.setState(0);
			ur.setPaymentway(paymentway);
			databaseHelper.persistObject(ur);
			
			String company_code = ConstantUtil.company_code;
			String order_number = outtradno;
			String order_amount = amount;
			String pay_id = paymentway;
			String return_url = "http://gpmobile.runfkj.com/";
			String notify_url = "http://gp.runfkj.com/appServiceUser/rechargeOfPCNotify.action";
			String timestamp = String.valueOf(new Date().getTime());
			String base64_memo = "";
			String sign_type = "SHA256";
			String version = "1.0";
			String card_type = "01";
			String sign = sign(company_code, order_number, order_amount, pay_id, return_url, notify_url, timestamp,base64_memo, sign_type, version, card_type);
			
			String url = "https://www.globepays.com/Api/Payin/";
			
	    	String p = "company_code="+company_code+"&order_number="+order_number+"&order_amount="+order_amount+"&pay_id="+pay_id+"&return_url="+return_url
	    			+"&notify_url="+notify_url+"&sign="+sign+"&timestamp="+timestamp+"&base64_memo="+base64_memo+"&sign_type="+sign_type+"&version="+version+"&card_type="+card_type;
	    	System.out.println("p......."+p);
			String result =  HttpRequest.sendPost(url, p);
			System.out.println("result..."+result);
			
			String filePath = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "pcrecharge/");
			String file = "pc_"+new Date().getTime()+".html";
			filePath += file;
			FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(result.getBytes());
            fos.close();
			
            Map<String,Object> map = new HashMap<String,Object>();
    		
    		map.put("msg", "success");
    		map.put("info", "提交成功");
    		map.put("url", ConstantUtil.SERVERURL+"/pcrecharge/"+file);
    		
    		String json = JsonUtil.getGson().toJson(map);
    		
    		return json;
		}
		
		private String sign(String company_code,String order_number,String order_amount,String pay_id,String return_url,String notify_url,String timestamp,String base64_memo,String sign_type,String version,String card_type){
			String sign = company_code+"&"+order_number+"&"+order_amount+"&"+pay_id+"&"+return_url+"&"+notify_url+"&"+timestamp+"&"+base64_memo+"&"+sign_type+"&"+version+"&"+card_type;
			sign += "&"+ConstantUtil.company_md5;
			return ConstantUtil.getSHA256StrJava(sign);
		}

		@Override
		public String getbank() throws Exception {
			// TODO Auto-generated method stub
			List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
			StringBuffer query = new StringBuffer("select b from Bank b where b.state = 0");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString());
			
			for (Object object : lst) {
				Bank b = (Bank)object;
				
				Map<String,Object> map = new HashMap<String, Object>();
		        
				map.put("bankname", b.getBankname());
				map.put("bankcode", b.getBankcode());
				
		        lstMap.add(map);
			}
			
			
			
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.add("rows", arr);
			
			return obj.toString();
		}

		@Override
		public boolean rechargeOfPCNotify(Map<String, String> map) throws Exception {
			// TODO Auto-generated method stub
			String company_code = map.get("company_code");
			String order_number = map.get("order_number");
			String order_amount = map.get("order_amount");
			String order_status = map.get("order_status");
			String sys_order_number = map.get("sys_order_number");
			String sett_date = map.get("sett_date");
			String order_fee = map.get("order_fee");
			String timestamp = map.get("timestamp");
			String sign = map.get("sign");
			String sign_type = map.get("sign_type");
			String version = map.get("version");
			
			boolean flag = checksign(company_code, order_number, order_amount, order_status, sys_order_number, sett_date, order_fee, timestamp, sign_type, version, sign);
			
			Date now = new Date();
			
			if(!flag){
				Sys.out("rechargeOfPCNotify..sign..error");
				return false;
			}
			
			Sys.out("rechargeOfPCNotify..order_status.."+order_status);
			
			if("2".equals(order_status)){//支付成功
				Sys.out("rechargeOfPCNotify..pay..success");
				
				//检查金额是否相符
				StringBuffer checkhql = new StringBuffer("select ur from UserRecharge ur where ur.state = 0 and ur.outtradno = '").append(order_number).append("'");
		        List<Object> checklst = databaseHelper.getResultListByHql(checkhql.toString());
		        Double totalmoney = 0.0;
		        if(checklst.size()==0)return false;
		        
		        if(((UserRecharge)checklst.get(0)).getPaystate()==1)return false;
		        
		        UserRecharge ur = (UserRecharge)checklst.get(0);
		        
		        totalmoney = ur.getAmount();
				
		        BigDecimal b1 = new BigDecimal(totalmoney);
				BigDecimal b2 = new BigDecimal(order_fee).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				Sys.out("order total money..."+b1+"   =====   rechargeOfPCNotify  total  amount..."+b2);
				Double diff = b1.subtract(b2).doubleValue();
				
				Sys.out("rechargeOfPCNotify...check....diff..."+Math.abs(diff));
				if(Math.abs(diff)>1){
					Sys.out("rechargeOfPCNotify...check....money...error");
					return false;//计算误差差值大于1，则认为此单数据错误
				}
				Sys.out("rechargeOfPCNotify...check....money...right");
				
				
				StringBuffer pay = new StringBuffer("select g from Globepays g where g.order_number= '").append(order_number).append("'");
				List<Object> pl = databaseHelper.getResultListByHql(pay.toString());
				if(pl.size()>0)return true;
				
				Globepays g = new Globepays();
				g.setCompany_code(company_code);
				g.setOrder_amount(order_amount);
				g.setOrder_fee(order_fee);
				g.setOrder_number(sys_order_number);
				g.setOrder_status(order_status);
				g.setSett_date(sett_date);
				g.setSign(sign_type);
				g.setSign_type(sign_type);
				g.setSys_order_number(sys_order_number);
				g.setTimestamp(timestamp);
				g.setVersion(version);
				
				databaseHelper.persistObject(g);
				
				//订单支付状态置为已支付
				ur.setPaystate(1);
			    databaseHelper.updateObject(ur);
			    
			    //支付成功后的金额分成
			    paysuccess(ur, now);
			    
			    return true;
			}
			
			return false;
			
		}
		
		
		private boolean checksign(String company_code,String order_number,String order_amount,String order_status,String sys_order_number,String sett_date,String order_fee,String timestamp,String sign_type,String version,String sign ){
			String mysign = company_code+"&"+order_number+"&"+order_amount+"&"+order_status+"&"+sys_order_number+"&"+sett_date+"&"+order_fee+"&"+timestamp+"&"+sign_type+"&"+version+"&"+ConstantUtil.company_md5;
			mysign = ConstantUtil.getSHA256StrJava(sign);
			return mysign.equals(sign);
		}
}
