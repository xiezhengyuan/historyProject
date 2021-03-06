package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.BusinessInfo;
import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.Giftbox;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.UserService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年5月4日 下午3:31:15
* @describe
*/

@Repository
public class UserServiceImpl implements UserService{
	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public List<Map<String, Object>> queryuseinfo(Employee bi, String name, String mobile, Integer start,
			Integer limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select u from UserInfo u where u.role = 0 and  u.state = 0");
		hql=conditionuserinfo(hql,bi, name,mobile);
		hql.append(" order by u.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object obj : lst) {
			UserInfo u = (UserInfo) obj;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", u.getId());
			map.put("name", u.getNickname());
			map.put("mobile", u.getMobile());
			map.put("time", u.getCreatetime().toString());
			map.put("balance", u.getBalance());
			map.put("level", u.getLevel());
			
			//查询游戏次数
			StringBuffer querytotalgame = new StringBuffer("select count(mu.id) from MachineUserMark mu where mu.fuserinfoid = ")
					.append(u.getId()).append(" and mu.type = 2");
			List muList = databaseHelper.getResultListByHql(querytotalgame.toString());
			if(muList.size()>0){
			map.put("totalgame",  Integer.parseInt(muList.get(0).toString()));
			}else{
				map.put("totalgame", "0");
			}
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public int countuserinfo(Employee bi, String name, String mobile) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(u.id) from UserInfo u where u.role = 0 and  u.state = 0");
		hql = conditionuserinfo(hql, bi,name,mobile);
		
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private StringBuffer conditionuserinfo(StringBuffer hql,Employee bi,String name,String mobile){
	
			hql.append(" and u.nickname like '%").append(name).append("%' ");
	
			hql.append(" and u.mobile like '%").append(mobile).append("%' ");
		
		return hql;
	}

	@Override
	public int queryaddtoday(Employee bi) throws Exception {
		// TODO Auto-generated method stub
		String sql ="select count(u.id) from userinfo u where date(u.createtime) = curdate() and u.state=0 and u.role=1 and u.fbusinessid ="+bi.getId();
		int newadd = databaseHelper.getSqlCount(sql);
        return newadd;
	}

	@Override
	public void modifyuserinfo(UserInfo userinfo) throws Exception {
		// TODO Auto-generated method stub
		//检查用户名是否存在
		StringBuffer queryexit = new StringBuffer("select u from UserInfo u where u.state = 0 and u.username = '").append(userinfo.getUsername()).append("'");
		List<Object> lst = databaseHelper.getResultListByHql(queryexit.toString());
		if(lst.size()>0){
			UserInfo u = (UserInfo)lst.get(0);
			if(!u.getId().equals(userinfo.getId()))
				throw new Exception("用户名已存在");
		}
		
		//检查手机号是否存在
		queryexit = new StringBuffer("select u from UserInfo u where u.state = 0 and u.mobile = '").append(userinfo.getMobile()).append("'");
		lst = databaseHelper.getResultListByHql(queryexit.toString());
		
		if(lst.size()>0){
			UserInfo u = (UserInfo)lst.get(0);
			if(!u.getId().equals(userinfo.getId()))
				throw new Exception("手机号已存在");
		}
		
		/*//检查银行卡号是否存在
		//queryexit = new StringBuffer("select u from UserInfo u where u.state = 0 and u.cardno = '").append(userinfo.getCardno()).append("'");
		lst = databaseHelper.getResultListByHql(queryexit.toString());
		
		if(lst.size()>0){
			UserInfo u = (UserInfo)lst.get(0);
			if(!u.getId().equals(userinfo.getId()))
				throw new Exception("银行卡号已存在");
		}*/
		
		UserInfo oldu = (UserInfo)databaseHelper.getObjectById(UserInfo.class, userinfo.getId());
		
		oldu.setUsername(userinfo.getUsername());
		oldu.setSex(userinfo.getSex());
		oldu.setBirthday(userinfo.getBirthday());
		oldu.setLevel(userinfo.getLevel());
		oldu.setSelfinfo(userinfo.getSelfinfo());
		oldu.setBalance(userinfo.getBalance());
		oldu.setFreevoucher(userinfo.getFreevoucher());
		oldu.setMobile(userinfo.getMobile());
		databaseHelper.updateObject(oldu);
	}
	
	@Override
	public List<Map<String, Object>> querylower(Employee bi, String name, String mobile, String proxylevel,
			String userinfoid, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select u from UserInfo u where  u.state = 0");
		hql = conditionlower(hql,bi, name,mobile,proxylevel,userinfoid);
		hql = hql.append(" order by u.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object objects : lst) {
			UserInfo u = (UserInfo)objects;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", u.getId());
			map.put("name", u.getUsername());
			map.put("mobile", u.getMobile());
			map.put("level", Integer.parseInt(proxylevel)==1?"一级":Integer.parseInt(proxylevel)==2?"二级":"");
			map.put("time", u.getCreatetime().toString());
			
			
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public int countlower(Employee bi, String name, String mobile, String proxylevel, String userinfoid)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(u.id) from UserInfo u where  u.state = 0");
		hql = conditionlower(hql,bi, name,mobile,proxylevel,userinfoid);
		
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private StringBuffer conditionlower(StringBuffer hql,Employee bi,String name,String mobile,String proxylevel,String userinfoid){
		if(Integer.parseInt(proxylevel)==1)
			hql = hql.append(" and u.fparentid =").append(Long.parseLong(userinfoid));
		else if(Integer.parseInt(proxylevel)==2)
			hql = hql.append(" and u.fsuperiorid =").append(Long.parseLong(userinfoid));
		
		if(name!=null&&name.length()>0)
			hql = hql.append(" and u.name like '%").append(name).append("%'");
		
		if(mobile!=null&&mobile.length()>0)
			hql = hql.append(" and u.mobile like '%").append(mobile).append("%'");
		
		
		return hql;
	}

	@Override
	public void adduserinfo(UserInfo userinfo) throws Exception {
		// TODO Auto-generated method stub
		//检查用户名是否存在
				StringBuffer queryexit = new StringBuffer("select u from UserInfo u where u.state = 0 and u.username = '").append(userinfo.getUsername()).append("'");
				List<Object> lst = databaseHelper.getResultListByHql(queryexit.toString());
				
				if(lst.size()>0){
					
						throw new Exception("用户名已存在");
				}
				
				//检查手机号是否存在
				queryexit = new StringBuffer("select u from UserInfo u where u.state = 0 and u.mobile = '").append(userinfo.getMobile()).append("'");
				lst = databaseHelper.getResultListByHql(queryexit.toString());
				
				if(lst.size()>0){
					
						throw new Exception("手机号已存在");
				}
				
				 Map<String,Object> map = new HashMap<String,Object>();
					
	                //注册到环信
	                String url = "https://a1.easemob.com/"+ConstantUtil.org_name+"/"+ConstantUtil.app_name+"/users";
	        		
	        		Map<String, String> params = new HashMap<String, String>();
	        		params.put("username",userinfo.getUsername());
	        		params.put("password",ConstantUtil.TOKEN);
	        		params.put("nickname",userinfo.getNickname());
	        		
	        		
	        		String result = ConstantUtil.PostRequest(url,JsonUtil.getGson().toJson(params),null);
	        		Sys.out("hx..regist..result..."+result);
	        		JsonObject rjson = (JsonObject) new JsonParser().parse(result);
	        		if(rjson.get("entities")!=null){
	        			JsonArray array = rjson.get("entities").getAsJsonArray();
	        			for (JsonElement jsonElement : array) {
	        				JsonObject jObject = jsonElement.getAsJsonObject();
	        				Sys.out("username.."+jObject.get("username").getAsString()+"..."+"uuid.."+jObject.get("uuid").getAsString());
	        				String uuid = jObject.get("uuid").getAsString();
	        				
	        				userinfo.setUuid(uuid);
	        				
	        			}
	        			
	        		}else{
	        			throw new Exception("环信注册失败");
	        		}
	        		userinfo.setCreatetime(new Date());
				databaseHelper.persistObject(userinfo);
		
	}

	@Override
	public List<Map<String, Object>> queryrecord(Employee bi, String name, String userinfoid, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select gb,ti,mi,u from Giftbox gb,ToysInfo ti,MachineInfo mi,UserInfo u  where gb.fuserinfoid = u.id and gb.fmachineid = mi.id and gb.ftoysid = ti.id and gb.fuserinfoid = ")
				.append(Long.parseLong(userinfoid));
		hql = hql.append(" order by gb.createtime desc");
		List<Object[]> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object[] objects : lst) {
			Giftbox g = (Giftbox)objects[0];
			ToysInfo t =(ToysInfo) objects[1];
			MachineInfo m = (MachineInfo) objects[2];
			UserInfo u = (UserInfo) objects[3];
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", g.getId());
			map.put("username", u.getUsername());
			map.put("toyname", t.getName());
			map.put("machineno", m.getMachineno());
			map.put("photo", g.getPhoto());
			map.put("getphoto", g.getGetphoto());
			map.put("state", g.getState()==-1?"失效":g.getState()==0?"新获得":g.getState()==1?"配货中":"已发货");
			map.put("time", u.getCreatetime().toString());
			
			
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public int countrecord(Employee bi,String name, String userinfoid) throws Exception {
		StringBuffer hql = new StringBuffer("select count(gb.id) from Giftbox gb,ToysInfo ti,MachineInfo mi,UserInfo u  where gb.fuserinfoid = u.id and gb.fmachineid = mi.id and gb.ftoysid = ti.id and gb.fuserinfoid = ")
				.append(Long.parseLong(userinfoid));
		
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	
}
