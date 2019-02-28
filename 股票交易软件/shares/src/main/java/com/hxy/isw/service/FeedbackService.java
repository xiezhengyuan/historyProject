package com.hxy.isw.service;

import java.util.List;
import java.util.Map;

import com.hxy.isw.entity.AccountInfo;

public interface FeedbackService {
	public List<Map<String, Object>> queryfeedback(AccountInfo emp,String title,String content,Integer start,Integer limit)throws Exception;
	public int countfeedback(AccountInfo emp,String title,String content)throws Exception;
	public void replyfeedback(String id,String replyinfo)  throws Exception;
}
