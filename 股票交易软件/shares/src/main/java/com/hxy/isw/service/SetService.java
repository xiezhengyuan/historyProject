package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.entity.Setting;

public interface SetService {

	public void modifyset(AccountInfo emp,Setting setting)throws Exception;
	public void addreward(String position)throws Exception;
	public void delreward(String rewardid)throws Exception;
	public List<Map<String, Object>>queryreward(Integer start,Integer limit)throws Exception;
	public Integer countreward()throws Exception;
}
