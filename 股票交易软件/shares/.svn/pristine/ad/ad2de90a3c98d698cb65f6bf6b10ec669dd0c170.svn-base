package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.Company;
import com.hxy.isw.entity.FeedBack;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.FeedbackService;
import com.hxy.isw.util.DatabaseHelper;

@Repository
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public List<Map<String, Object>> queryfeedback(AccountInfo emp, String title, String content, Integer start,
			Integer limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select f,ui from FeedBack f,UserInfo ui  where ui.id = f.fuserinfoid and f.state = 0 ");
		hql = conditionfeedback(hql,emp, title,content);
		hql = hql.append(" order by f.createtime desc");
		List<Object[]> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object[] obj : lst) {
			FeedBack f = (FeedBack) obj[0];
			UserInfo u = (UserInfo) obj[1];
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",f.getId());
			map.put("title", f.getTitle());
			map.put("content", f.getContent());
			map.put("nickname",u.getNickname());
			map.put("time", f.getCreatetime());
			
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public int countfeedback(AccountInfo emp, String title, String content) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(f.id) from FeedBack f,UserInfo ui  where ui.id = f.fuserinfoid and f.state = 0 ");
		hql = conditionfeedback(hql, emp, title, content);
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private StringBuffer conditionfeedback(StringBuffer hql,AccountInfo emp,String title,String content){
		
		if(title!=null&&title.length()>0)
			hql = hql.append(" and f.title like '%").append(title).append("%'");
		
		if(content!=null&&content.length()>0)
			hql = hql.append(" and f.content like '%").append(content).append("%'");
		
		return hql;
	}

	@Override
	public void replyfeedback(String id, String replyinfo) throws Exception {
		// TODO Auto-generated method stub
		FeedBack f = (FeedBack) databaseHelper.getObjectById(FeedBack.class, Long.parseLong(id));
		f.setReplayto(replyinfo);
		databaseHelper.updateObject(f);
	}
	

}
