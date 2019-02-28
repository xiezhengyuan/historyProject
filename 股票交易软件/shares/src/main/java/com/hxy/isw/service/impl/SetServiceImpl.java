package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.RewardSetting;
import com.hxy.isw.entity.Setting;
import com.hxy.isw.service.SetService;
import com.hxy.isw.util.DatabaseHelper;
@Repository
public class SetServiceImpl implements SetService{
	
	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public void modifyset(AccountInfo emp, Setting setting) throws Exception {
		// TODO Auto-generated method stub
		databaseHelper.updateObject(setting);
	}

	@Override
	public void addreward(String position) throws Exception {
		// TODO Auto-generated method stub
		if(position==null||position.length()==0)throw new Exception("参数position为空");
		StringBuffer queryexcist = new StringBuffer("select r from RewardSetting r where r.state = 0 and r.position = ")
				.append(Integer.parseInt(position));
		List<Object>rList = databaseHelper.getResultListByHql(queryexcist.toString());
		if(rList.size()>0)throw new Exception("当前档位已存在！");
		
		RewardSetting rewardSetting = new RewardSetting();
		rewardSetting.setPosition(Integer.parseInt(position));
		rewardSetting.setState(0);
		rewardSetting.setCreatetime(new Date());
		databaseHelper.persistObject(rewardSetting);
		
	}

	@Override
	public List<Map<String, Object>> queryreward(Integer start, Integer limit) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select r from RewardSetting r where r.state = 0 ");
		hql = hql.append(" order by r.createtime desc");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		for (Object obj : lst) {
			RewardSetting r = (RewardSetting) obj;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", r.getId());
			map.put("position", r.getPosition());
			
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	public Integer countreward() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(r.id) from RewardSetting r where r.state = 0 ");
		List lst = databaseHelper.getResultListByHql(hql.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public void delreward(String rewardid) throws Exception {
		// TODO Auto-generated method stub
		RewardSetting rewardSetting = (RewardSetting) databaseHelper.getObjectById(RewardSetting.class, Long.parseLong(rewardid));
	    rewardSetting.setState(-1);
	    databaseHelper.updateObject(rewardSetting);
	}

	
}
