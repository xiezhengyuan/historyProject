package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxy.isw.entity.Allege;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.UserAllege;
import com.hxy.isw.service.UserAppealService;
import com.hxy.isw.util.DatabaseHelper;

@Service
public class UserAppealServiceImp implements UserAppealService {

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public int countsystemappeal() throws Exception {
		String sql=" select count(id) from allege where state=0 ";
		return databaseHelper.getSqlCount(sql);
	}

	@Override
	public List<Map<String, Object>> systemappeal(int start, int limit) throws Exception {
		String hql=" select a from Allege a where a.state=0 ";
		List<Object> lst=databaseHelper.getResultListByHql(hql, start, limit);
		List<Map<String, Object>> lstmap=new ArrayList<Map<String,Object>>();
		for (Object obj : lst) {
			Allege a=(Allege) obj;
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("id", a.getId());
			map.put("content", a.getContent());
			map.put("createtime", a.getCreatetime());
			lstmap.add(map);
		}
		return lstmap;
	}

	@Override
	public void addappeal(String content) throws Exception {
		if(content.trim().length()==0)throw new Exception("没有内容");
			
		Allege al=new Allege();
		al.setContent(content);
		al.setCreatetime(new Date());
		al.setState(0);
		databaseHelper.persistObject(al);
		
	}

	@Override
	public void deleteappeal(String id) throws Exception {
		   String  sql="update allege set state=-1 where id= "+id+"";
           int num=databaseHelper.executeNativeSql(sql);
           if(num==0){
        	   throw new Exception("id异常");
           }
	}

	@Override
	public Map<String, Object> deleteappeal(String id, String type, String newcontent) throws Exception {
		Map<String, Object> map =new  HashMap<String, Object>();
		if(type.equals("kan")){
			Allege al=(Allege) databaseHelper.getObjectById(Allege.class, Long.parseLong(id));
			map.put("content", al.getContent());
		}else{
			String sql="update allege set content ='"+newcontent+"' where id="+id+"";
			int num=databaseHelper.executeNativeSql(sql);
			map.put("change", num);
		}
		
		return map;
	}

	@Override
	public int countuserappeal(String username, String moblie, String appealtype,String neworold) throws Exception {
		System.out.println(moblie+"123");
		StringBuffer countsql=new StringBuffer(" select count(ua.id) from userallege ua JOIN userinfo ui on ua.fuserid = ui.id ");
		countsql.append(sqlapp(username, moblie, appealtype, neworold).toString());
		int count =databaseHelper.getSqlCount(countsql.toString());
		return count;
	}

	@Override
	public List<Map<String, Object>> queryuserappeal(String username, String moblie, String appealtype,String neworold, int start,
			int limit) throws Exception {
		StringBuffer sql=new StringBuffer(" select ua.id,ua.fallegeid,ua.substance,ua.createtime ,ui.nickname,ui.mobile ,vi.toyname,vi.toyphoto,vi.successed ");
		sql.append(" from userallege ua JOIN userinfo ui on ua.fuserid = ui.id ");
		sql.append(" join videoinfo vi  on ua.videoinfoid =vi.id   ");
		sql.append(sqlapp(username, moblie, appealtype, neworold).toString());
		sql.append(" order by ua.createtime desc ");
		
		List<Object[]> lst =databaseHelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap =new ArrayList<Map<String,Object>>();
		for (Object[]  o : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("id", o[0].toString());
			if(o[1].toString().equals("0")){
				map.put("substance", o[2].toString());
			}else{
				Allege al= (Allege) databaseHelper.getObjectById(Allege.class, Long.parseLong(o[1].toString()));
			    map.put("substance", al.getContent());
			}
			map.put("createtime", o[3].toString());
			map.put("nickname", o[4].toString());
			map.put("mobile", o[5].toString());
			map.put("toyname", o[6].toString());
			map.put("toyphoto", o[7].toString());
			map.put("issuccess", o[8].toString().equals("0")?"失败":"成功");
			lstmap.add(map);
			
			
		}
		return lstmap;
	}
	
	public StringBuffer sqlapp(String username, String moblie, String appealtype,String neworold){
		StringBuffer sql=new StringBuffer("");
		System.out.println(moblie+"asfdasd");
		if(neworold.equals("new")){
			sql.append(" where ua.state=0 ");
		}
		if(neworold.equals("old")){
			sql.append(" where ua.state=1 ");
		}
		if(username!=null&&username.length()>0){
			sql.append(" and ui.nickname like '%"+username+"%' ");
		}
		if(moblie!=null&&moblie.length()>0){
			System.out.println(moblie);
			sql.append(" and ui.mobile like '%"+moblie+"%'  ");
		}
		if(!appealtype.equals("-1")){
			sql.append(" and ua.fallegeid ="+appealtype+" ");
		}
		sql.append("  and  ui.state=0  ");
		
		return sql;
	}

	@Override
	public void deleteuserappeal(String id,String type) throws Exception {
		String sql ="update userallege set state="+type+" where id="+id+" ";
		int count=databaseHelper.executeNativeSql(sql);
		if(count==0){
			throw new Exception("id错误");
		}
		
	}

	@Override
	public Map<String, Object> appealtetail(String appealid) throws Exception {
		StringBuffer sql=new StringBuffer("select ul.fallegeid,ul.substance,ui.nickname,ui.mobile,vi.fmachineid,vi.videourl ");
		sql.append(" from userallege ul join userinfo ui on ul.fuserid=ui.id  ");
		sql.append(" join videoinfo  vi on  ul.videoinfoid = vi.id where  ul.id="+appealid+" ");
		List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString());
		Map<String, Object> map=new HashMap<String, Object>();
		Object[] o=lst.get(0);
		if(o[0].toString().equals("0")){
			map.put("substance", o[1].toString());
		}else{
			Allege al= (Allege) databaseHelper.getObjectById(Allege.class, Long.parseLong(o[0].toString()));
		    map.put("substance", al.getContent());
		}
		map.put("nickname", o[2].toString());
		map.put("mobile", o[3].toString());
		
		MachineInfo m=(MachineInfo) databaseHelper.getObjectById(MachineInfo.class, Long.parseLong(o[4].toString()));
		map.put("machieno", m.getMachineno());
		map.put("videourl", o[5].toString());

		return map ;
	}

	@Override
	public void sendcontent(String id, String content) throws Exception {
		UserAllege ua=(UserAllege) databaseHelper.getObjectById(UserAllege.class,Long.parseLong(id));
		long userid=ua.getFuserid();
		NotifyInfo ni=new NotifyInfo();
		ni.setType(0);
		ni.setFuserinfoid(userid);
		ni.setTitle("回复您的申诉");
		ni.setContent(content);
		ni.setCreatetime(new Date());
		ni.setState(0);
		databaseHelper.persistObject(ni);
		
	}
}
