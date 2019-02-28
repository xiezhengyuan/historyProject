package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.hxy.isw.entity.PostingStyle;
import com.hxy.isw.entity.Postings;
import com.hxy.isw.entity.PostingsComments;
import com.hxy.isw.entity.ProxyApply;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.PostingService;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

@Repository
public class PostingServiceImpl implements PostingService{
	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public JsonArray querypostingstyle() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer querypostingstyle = new StringBuffer("select ps from PostingStyle ps where ps.state = 0 ");
		List<Object> lst = databaseHelper.getResultListByHql(querypostingstyle.toString());
		
		return JsonUtil.castLst2Arr4SingleTime(lst);
	}
	
	@Override
	public PostingStyle addpostingstyle(String name) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryexit = new StringBuffer("select ps from PostingStyle ps where ps.state = 0 and ps.name = '").append(name).append("'");
		List<Object> lst = databaseHelper.getResultListByHql(queryexit.toString());
		
		//if(lst.size()>0)throw new Exception("分类名已存在");
		if(lst.size()>0){
			PostingStyle postingStyle = (PostingStyle) lst.get(0);
			if(postingStyle.getState()==0)throw new Exception("分类名已存在");
			postingStyle.setState(0);
			databaseHelper.updateObject(postingStyle);
			return postingStyle;
		}else{
			
			PostingStyle postingStyle  = new PostingStyle();
		postingStyle.setName(name);
		postingStyle.setCreatetime(new Date());
		postingStyle.setState(0);
		databaseHelper.persistObject(postingStyle);
		return postingStyle;}
	}

	@Override
	public List<Map<String, Object>> queryposting(String name, String fpostingstyleid, String fuser, int start,
			int limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select p,u,ps from Postings p,UserInfo u,PostingStyle ps where p.fpostingstyleid = ps.id and p.state = 0 and u.id = p.fuserinfoid ");
		hql = conditionposting(hql,name,fpostingstyleid,fuser);
		hql = hql.append(" order by p.createtime desc");
		List<Object[]> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		
         for (Object[] objects : lst) {
			Postings p = (Postings) objects[0];
			UserInfo u = (UserInfo) objects[1];
			PostingStyle ps = (PostingStyle) objects[2];
			
			Map<String,Object> map = new HashMap<String,Object>();
			String postingscontent = "";
			if(p.getPostingscontent().length()>20){
				postingscontent = p.getPostingscontent().substring(0, 20)+"...";
			}else{postingscontent = p.getPostingscontent();}
			map.put("id", p.getId());
			map.put("fpostingstyle", ps.getName());
			map.put("postingsname", p.getPostingsname());
			map.put("postingscontent", postingscontent);
			map.put("fuser", u.getNickname()==null?u.getUsername():u.getNickname());
			map.put("state", p.getState()==0?"新帖":"失效");
			map.put("createtime", p.getCreatetime().toString());
			
			lstMap.add(map);
         }
		
		return lstMap;
	}
	
	private StringBuffer conditionposting(StringBuffer hql,String name,String fpostingstyleid, String fuser){
		if(name!=null&&name.length()>0)
			hql = hql.append(" and p.postingsname like '%").append(name).append("%'");
		
		if(fpostingstyleid!=null&&fpostingstyleid.length()>0)
			hql = hql.append(" and p.fpostingstyleid =").append(Long.parseLong(fpostingstyleid));
		
		if(fuser!=null&&fuser.length()>0)
			hql = hql.append(" and u.username like '%").append(fuser).append("%'");
		
		return hql;
	}

	@Override
	public int countposting(String name, String fpostingstyleid, String fuser) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(p.id) from Postings p,UserInfo u where  p.state = 0 and u.id = p.fuserinfoid ");
		hql = conditionposting(hql,name,fpostingstyleid,fuser);
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public JsonArray querypostingphotosbyid(Long id) {
		// TODO Auto-generated method stub
		StringBuffer querypostingsphotos = new StringBuffer("select pp from PostingsPhotos pp where pp.fpostingsid =")
				.append(id).append("and pp.state = 0");
		List<Object>ppflist = databaseHelper.getResultListByHql(querypostingsphotos.toString());
		
		return JsonUtil.castLst2Arr4Single(ppflist);
	}

	@Override
	public List<Map<String, Object>> querypostingcomments(String postingid, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select pc,u from PostingsComments pc,UserInfo u where pc.state = 0 and u.id = pc.fuserinfoid and pc.fpostingsid = ")
				.append(Long.parseLong(postingid));
		hql = hql.append(" order by pc.createtime desc");
		List<Object[]> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		Sys.out(lst.size());
         for (Object[] objects : lst) {
        	 PostingsComments pc = (PostingsComments) objects[0];
			UserInfo u = (UserInfo) objects[1];
			
			Map<String,Object> map = new HashMap<String,Object>();
	
			map.put("id",pc.getId());
			map.put("comment", pc.getComment());
			map.put("username", u.getNickname()==null?u.getUsername():u.getNickname());
			map.put("createtime", pc.getCreatetime());
			
			if(pc.getFcommentid()!=null&&pc.getFcommentid()>0){
				map.put("superaddition", 1);
				PostingsComments pcomment = (PostingsComments)databaseHelper.getObjectById(PostingsComments.class, pc.getFcommentid());
				UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, pcomment.getFuserinfoid());
				
				map.put("replyto", ui.getNickname()==null?ui.getUsername():ui.getNickname());
				
			}else{
				map.put("superaddition", 0);
			}
			lstMap.add(map);
         }
		
		return lstMap;
	}

	@Override
	public int countpostingcomments(String postingid) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(pc.id) from PostingsComments pc,UserInfo u where pc.state = 0 and u.id = pc.fuserinfoid and pc.fpostingsid = ")
				.append(Long.parseLong(postingid));
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public void passposting(String id) throws Exception {
		// TODO Auto-generated method stub
		Postings postings = (Postings) databaseHelper.getObjectById(Postings.class, Long.parseLong(id));
		
		postings.setState(0);
		databaseHelper.updateObject(postings);
	}

	@Override
	public void nopassposting(String id) throws Exception {
		// TODO Auto-generated method stub
		Postings postings = (Postings) databaseHelper.getObjectById(Postings.class, Long.parseLong(id));
		
		postings.setState(-1);
		databaseHelper.updateObject(postings);
	}
	

}
