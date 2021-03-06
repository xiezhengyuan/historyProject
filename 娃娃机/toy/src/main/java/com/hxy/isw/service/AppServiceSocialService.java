package com.hxy.isw.service;

import java.util.Map;

public interface AppServiceSocialService {

	/**
	 * @Description: 帖子分类
	 * @author: lixq
	 * @date 2017年7月4日 下午2:57:41
	 */
	Map<String, Object> postingstyle();

	/**
	 * @Description: 帖子列表
	 * @author: lixq
	 * @date 2017年7月5日 上午9:48:49
	 */
	Map<String, Object> postings(String userid, String postingstyleid, String myself, int start, int limit);

	/**
	 * @Description: 帖子评论列表
	 * @author: lixq
	 * @date 2017年7月5日 上午9:48:59
	 */
	Map<String, Object> postingcomments(String userid, String postingid, int start, int limit);

	/**
	 * @Description: 发布帖子
	 * @author: lixq
	 * @date 2017年7月5日 上午10:39:57
	 */
	void sendpostings(String userid, String postingsname, String postingscontent, String postingstyleid, String photos);

	/**
	 * @Description: 评论帖子
	 * @author: lixq
	 * @date 2017年7月5日 上午10:53:13
	 */
	void sendcomment(String userid, String postingid, String comment, String commentid,String isanonymous);

	/**
	 * @Description: 浏览帖子
	 * @author: lixq
	 * @date 2017年7月5日 上午11:18:56
	 */
	Map<String, Object> views(String userid, String postingid);

	/**
	 * @Description: 点赞/取消点赞
	 * @author: lixq
	 * @date 2017年7月5日 上午11:29:40
	 */
	String praise(String userid, String postingid);

	/**
	 * @Description: 我回复的帖子列表
	 * @author: lixq
	 * @date 2017年7月5日 下午2:32:24
	 */
	Map<String, Object> myreplaypostings(String userid, int start, int limit);
}
