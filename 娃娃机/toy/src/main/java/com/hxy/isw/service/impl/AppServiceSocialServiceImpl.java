package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hxy.isw.entity.PostingStyle;
import com.hxy.isw.entity.Postings;
import com.hxy.isw.entity.PostingsComments;
import com.hxy.isw.entity.PostingsPhotos;
import com.hxy.isw.entity.PostingsPraise;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AppServiceSocialService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;

@Repository
public class AppServiceSocialServiceImpl implements AppServiceSocialService {

	@Autowired
	private DatabaseHelper databaseHelper;

	@Override
	public Map<String, Object> postingstyle() {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM postingstyle ps WHERE ps.state=0");

		Integer count = databaseHelper.getSqlCount(sql.toString());
		resultMap.put("total", count);

		StringBuffer hql = new StringBuffer("SELECT ps FROM PostingStyle ps WHERE ps.state=0");
		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString());

		for (Object object : resultList) {
			PostingStyle postingStyle = (PostingStyle) object;
			Map<String, Object> map = JsonUtil.obj2map(postingStyle);
			list.add(map);
		}
		resultMap.put("rows", list);
		resultMap.put("msg", "success");
		resultMap.put("info", "");
		return resultMap;
	}

	@Override
	public Map<String, Object> postings(String userid, String postingstyleid, String myself, int start, int limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM postings p WHERE p.state=0 ");
		StringBuffer hql = new StringBuffer("SELECT p FROM Postings p WHERE p.state=0 ");

		if (!StringUtils.isEmpty(postingstyleid)) {
			sql.append(" AND p.fpostingstyleid=").append(postingstyleid);
			hql.append(" AND p.fpostingstyleid=").append(postingstyleid);
		}
		if (!StringUtils.isEmpty(myself)) {
			sql.append(" AND p.fuserinfoid=").append(userid);
			hql.append(" AND p.fuserinfoid=").append(userid);
		}

		sql.append(" ORDER BY p.createtime DESC");
		hql.append(" ORDER BY p.createtime DESC");

		Integer count = databaseHelper.getSqlCount(sql.toString());
		int pages = ConstantUtil.pages(count, limit);

		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		for (Object object : resultList) {
			Postings postings = (Postings) object;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("postingid", postings.getId());// 帖子id
			map.put("postingsname", postings.getPostingsname());// 标题
			map.put("postingscontent", postings.getPostingscontent());// 内容
			map.put("views", postings.getViews());// 浏览量
			map.put("comments", postings.getComments());// 评论数
			map.put("praise", postings.getPraise());// 点赞数
			map.put("time", postings.getCreatetime().toString());// 时间

			UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, postings.getFuserinfoid());
			map.put("headimg", StringUtils.isEmpty(userInfo.getHeadimg()) ? "" : userInfo.getHeadimg());// 头像
			map.put("nickname", userInfo.getNickname());// 昵称
			map.put("userid", userInfo.getId());// 用户id
			map.put("uuid", userInfo.getUuid());// 用户注册环信成功后得到的uuid
			map.put("username", userInfo.getUsername());// 用户注册环信成功的账户名

			// 图片
			StringBuffer hqlphoto = new StringBuffer("SELECT pp FROM PostingsPhotos pp WHERE pp.state=0 ");
			hqlphoto = hqlphoto.append("AND pp.fpostingsid=").append(postings.getId());
			List<Object> photos = databaseHelper.getResultListByHql(hqlphoto.toString());
			List<Map<String, Object>> photolist = new ArrayList<Map<String, Object>>();
			for (Object photo : photos) {
				PostingsPhotos postingsPhotos = (PostingsPhotos) photo;
				Map<String, Object> photomap = new HashMap<String, Object>();
				photomap.put("photo", postingsPhotos.getPhotourl());
				photolist.add(photomap);
			}
			map.put("photo", photolist);// 图片

			// 是否点赞
			StringBuffer haspraisesql = new StringBuffer(
					"SELECT COUNT(1) FROM postingspraise pp WHERE pp.state=0 AND pp.fpostingsid=")
							.append(postings.getId());

			int haspraise = databaseHelper
					.getSqlCount(haspraisesql.append(" AND pp.fuserinfoid=").append(userid).toString());
			if (haspraise > 0) {
				map.put("haspraise", 1);// 是否已点过赞 0否 1是
			} else {
				map.put("haspraise", 0);
			}
			list.add(map);
		}
		resultMap.put("msg", "success");
		resultMap.put("info", "");
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", list);
		return resultMap;
	}

	@Override
	public Map<String, Object> postingcomments(String userid, String postingid, int start, int limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 浏览量+1
		StringBuffer sql1 = new StringBuffer("UPDATE postings p SET p.views=p.views+1 WHERE p.id=").append(postingid);
		databaseHelper.executeNativeSql(sql1.toString());

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM postingscomments pc WHERE pc.state=0 ")
				.append(" AND pc.fpostingsid=").append(postingid);
		StringBuffer hql = new StringBuffer(
				"SELECT pc,ui FROM PostingsComments pc,UserInfo ui WHERE pc.fuserinfoid=ui.id AND pc.state=0 ")
						.append(" AND pc.fpostingsid=").append(postingid).append(" ORDER BY pc.createtime DESC");

		Integer count = databaseHelper.getSqlCount(sql.toString());
		int pages = ConstantUtil.pages(count, limit);

		List<Object[]> resultList = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		for (Object[] object : resultList) {
			PostingsComments pc = (PostingsComments) object[0];
			UserInfo ui = (UserInfo) object[1];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pc.getId());// 评论id
			map.put("postingid", pc.getFpostingsid());// 帖子id
			map.put("comment", pc.getComment());// 内容
			map.put("userid", ui.getId());// 评论人id
			map.put("nickname", ui.getNickname());// 评论者昵称
			map.put("headimg", ui.getHeadimg());// 评论者头像
			map.put("fuserid", pc.getFuserinfoid());// 评论者id
			map.put("time", pc.getCreatetime().toString());// 时间
			
			map.put("isanonymous", pc.getIsanonymous().toString());// 是否匿名 0否1是

			if (pc.getFcommentid() == null || pc.getFcommentid().equals(0L)) {
				map.put("commentid", "");// 评论id
				map.put("superaddition", 0);// 是否为追加评论 0不是 1是
				map.put("replyto", "");// 被回复人的昵称（当superaddition值为1时会有此字段）
			} else {
				map.put("commentid", pc.getFcommentid());// 评论id
				map.put("superaddition", 1);// 是否为追加评论 0不是 1是
				PostingsComments comments = (PostingsComments) databaseHelper.getObjectById(PostingsComments.class,
						pc.getFcommentid());
				if(comments.getIsanonymous()==1){
					map.put("replyto", "匿名"+pc.getFcommentid());
				}else{
					UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, comments.getFuserinfoid());
					map.put("replyto", userInfo.getNickname());// 被回复人的昵称（当superaddition值为1时会有此字段）
				}
			}
			list.add(map);
		}
		resultMap.put("msg", "success");
		resultMap.put("info", "");
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", list);
		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendpostings(String userid, String postingsname, String postingscontent, String postingstyleid,
			String photos) {
		Postings postings = new Postings();

		postings.setFpostingstyleid(Long.parseLong(postingstyleid));
		postings.setFuserinfoid(Long.parseLong(userid));
		postings.setPostingsname(ConstantUtil.filterEmoji(postingsname));
		postings.setPostingscontent(ConstantUtil.filterEmoji(postingscontent));
		postings.setState(0);
		postings.setCreatetime(new Date());

		databaseHelper.persistObject(postings);
		// 贴子图片
		if (!StringUtils.isEmpty(photos)) {
			JsonArray arr = (JsonArray) JsonUtil.getJsonParser().parse(photos);
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String url = jsonObject.get("url").getAsString();
				PostingsPhotos postingsPhotos = new PostingsPhotos();
				postingsPhotos.setFpostingsid(postings.getId());
				postingsPhotos.setPhotourl(url);
				postingsPhotos.setState(0);
				postingsPhotos.setCreatetime(new Date());
				databaseHelper.persistObject(postingsPhotos);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendcomment(String userid, String postingid, String comment, String commentid,String isanonymous) {
		PostingsComments comments = new PostingsComments();
		comments.setFuserinfoid(Long.parseLong(userid));
		comments.setFpostingsid(Long.parseLong(postingid));
		comments.setComment(ConstantUtil.filterEmoji(comment));
		comments.setState(0);
		comments.setCreatetime(new Date());
		comments.setIsanonymous(Integer.parseInt(isanonymous));//是否匿名 0 否 1是
		if (StringUtils.isEmpty(commentid) || commentid.equals("0")) {
			comments.setFcommentid(null);
		} else {
			comments.setFcommentid(Long.parseLong(commentid));
		}
		databaseHelper.persistObject(comments);

		// 评论数+1
		StringBuffer sql = new StringBuffer("UPDATE postings p SET p.comments=p.comments+1 WHERE p.id=")
				.append(postingid);
		databaseHelper.executeNativeSql(sql.toString());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> views(String userid, String postingid) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		// 浏览量+1
		StringBuffer sql = new StringBuffer("UPDATE postings p SET p.views=p.views+1 WHERE p.id=").append(postingid);
		databaseHelper.executeNativeSql(sql.toString());

		StringBuffer hql = new StringBuffer("SELECT p FROM Postings p WHERE p.state=0 AND p.id=").append(postingid);
		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString());
		Postings postings = (Postings) resultList.get(0);

		resultmap.put("postingid", postings.getId());
		resultmap.put("postingsname", postings.getPostingsname());
		resultmap.put("postingscontent", postings.getPostingscontent());
		resultmap.put("views", postings.getViews());
		resultmap.put("comments", postings.getComments());
		resultmap.put("praise", postings.getPraise());
		resultmap.put("time", postings.getCreatetime().toString());

		// 是否已点赞
		StringBuffer praisesql = new StringBuffer("SELECT COUNT(1) FROM postingspraise pp WHERE pp.fpostingsid=")
				.append(postingid).append(" AND pp.fuserinfoid=").append(userid);
		StringBuffer praisehql = new StringBuffer("SELECT pp FROM PostingsPraise pp WHERE pp.fpostingsid=")
				.append(postingid).append(" AND pp.fuserinfoid=").append(userid);

		Integer count = databaseHelper.getSqlCount(praisesql.toString());
		// 点赞
		if (count > 0) {
			List<Object> praise = databaseHelper.getResultListByHql(praisehql.toString());
			PostingsPraise postingsPraise = (PostingsPraise) praise.get(0);
			if (postingsPraise.getState() == 0) {
				resultmap.put("haspraise", "1");// haspraise 点赞状态 0未赞 1已赞
			} else {
				resultmap.put("haspraise", "0");// haspraise 点赞状态 0未赞 1已赞
			}
		} else {
			resultmap.put("haspraise", "0");// haspraise 点赞状态 0未赞 1已赞
		}

		// 图片
		StringBuffer photohql = new StringBuffer("SELECT pp FROM PostingsPhotos pp WHERE pp.fpostingsid=")
				.append(postingid);
		List<Object> photos = databaseHelper.getResultListByHql(photohql.toString());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Object object : photos) {
			PostingsPhotos postingsPhotos = (PostingsPhotos) object;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("photo", postingsPhotos.getPhotourl());
			list.add(map);
		}
		resultmap.put("photos", list);

		resultmap.put("msg", "success");
		resultmap.put("info", "");
		return resultmap;
	}

	@Override
	public String praise(String userid, String postingid) {
		String haspraise = "";

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM postingspraise pp WHERE pp.fpostingsid=")
				.append(postingid).append(" AND pp.fuserinfoid=").append(userid);

		StringBuffer hql = new StringBuffer("SELECT pp FROM PostingsPraise pp WHERE pp.fpostingsid=").append(postingid)
				.append(" AND pp.fuserinfoid=").append(userid);

		Integer count = databaseHelper.getSqlCount(sql.toString());
		// 未点赞
		if (count.equals(0)) {
			PostingsPraise praise = new PostingsPraise();
			praise.setFuserinfoid(Long.parseLong(userid));
			praise.setFpostingsid(Long.parseLong(postingid));
			praise.setState(0);
			praise.setCreatetime(new Date());
			databaseHelper.persistObject(praise);
			haspraise = "1";// haspraise 点赞状态 0未赞 1已赞
			// 点赞数+1
			StringBuffer praisecount = new StringBuffer("UPDATE postings p SET p.praise=p.praise+1 WHERE p.id=")
					.append(postingid);
			databaseHelper.executeNativeSql(praisecount.toString());
		} else {
			List resultList = databaseHelper.getResultListByHql(hql.toString());
			PostingsPraise postingsPraise = (PostingsPraise) resultList.get(0);
			// 等于0 表示是取消点赞操作
			if (postingsPraise.getState() == 0) {
				postingsPraise.setState(1);
				databaseHelper.updateObject(postingsPraise);
				haspraise = "0";// haspraise 点赞状态 0未赞 1已赞

				// 点赞数-1
				StringBuffer praisecount = new StringBuffer("UPDATE postings p SET p.praise=p.praise-1 WHERE p.id=")
						.append(postingid);
				databaseHelper.executeNativeSql(praisecount.toString());
			} else {
				postingsPraise.setState(0);
				databaseHelper.updateObject(postingsPraise);
				haspraise = "1";// haspraise 点赞状态 0未赞 1已赞

				// 点赞数+1
				StringBuffer praisecount = new StringBuffer("UPDATE postings p SET p.praise=p.praise+1 WHERE p.id=")
						.append(postingid);
				databaseHelper.executeNativeSql(praisecount.toString());
			}
		}

		return haspraise;
	}

	/*
	 * @Override public Map<String, Object> myreplaypostings(String userid, int
	 * start, int limit) { Map<String, Object> resultmap = new HashMap<String,
	 * Object>(); List<Map<String, Object>> list = new ArrayList<Map<String,
	 * Object>>(); StringBuffer sql = new StringBuffer(
	 * "SELECT COUNT(1) FROM postingscomments pc WHERE pc.state=0"); //
	 * StringBuffer hql=new StringBuffer("SELECT pc FROM PostingsComments pc //
	 * WHERE pc.state=0 AND fuserinfoid=").append(userid); StringBuffer hql =
	 * new StringBuffer(
	 * "SELECT pc,ui FROM PostingsComments pc,UserInfo ui WHERE pc.fuserinfoid=ui.id AND pc.state=0 "
	 * ) .append(" AND pc.fuserinfoid=").append(userid).append(
	 * " ORDER BY pc.createtime DESC"); Integer count =
	 * databaseHelper.getSqlCount(sql.toString()); int pages =
	 * ConstantUtil.pages(count, limit);
	 * 
	 * List<Object[]> resultList =
	 * databaseHelper.getResultListByHql(hql.toString(), start, limit); for
	 * (Object[] object : resultList) { PostingsComments pc = (PostingsComments)
	 * object[0]; UserInfo ui = (UserInfo) object[1]; Map<String, Object> map =
	 * new HashMap<String, Object>();
	 * 
	 * map.put("postingid", pc.getFpostingsid());// 帖子id map.put("comment",
	 * pc.getComment());// 内容 map.put("nickname", ui.getNickname());// 评论者昵称
	 * map.put("headimg", ui.getHeadimg());// 评论者头像 map.put("fuserid",
	 * pc.getFuserinfoid());// 评论者id map.put("time",
	 * pc.getCreatetime().toString());// 时间
	 * 
	 * if (pc.getFcommentid() == null||pc.getFcommentid()==0) {
	 * map.put("commentid", null);// 评论id map.put("superaddition", 0);// 是否为追加评论
	 * 0不是 1是 map.put("replyto", "");// 被回复人的昵称（当superaddition值为1时会有此字段） } else
	 * { map.put("commentid", pc.getFcommentid());// 评论id
	 * map.put("superaddition", 1);// 是否为追加评论 0不是 1是 PostingsComments comments =
	 * (PostingsComments) databaseHelper.getObjectById(PostingsComments.class,
	 * pc.getFcommentid()); UserInfo userInfo = (UserInfo)
	 * databaseHelper.getObjectById(UserInfo.class, comments.getFuserinfoid());
	 * map.put("replyto", userInfo.getNickname());//
	 * 被回复人的昵称（当superaddition值为1时会有此字段） } list.add(map); } resultmap.put("msg",
	 * "success"); resultmap.put("info", ""); resultmap.put("total", count);
	 * resultmap.put("pages", pages); resultmap.put("rows", list); return
	 * resultmap; }
	 */

	@Override
	public Map<String, Object> myreplaypostings(String userid, int start, int limit) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer(
				"SELECT p.id, p.postingsname, p.postingscontent, p.views, p.comments, p.praise, p.createtime, ui.id, ui.headimg, ui.nickname, ui.uuid, ui.username "
						+ "FROM postingscomments pm LEFT JOIN postings p ON pm.fpostingsid = p.id LEFT JOIN userinfo ui ON p.fuserinfoid = ui.id WHERE pm.fuserinfoid = ")
								.append(userid).append(" GROUP BY pm.fpostingsid ORDER BY p.createtime DESC");
		
		StringBuffer sqlCount = new StringBuffer(
				"SELECT COUNT(*) FROM (SELECT p.id FROM postingscomments pm LEFT JOIN postings p ON pm.fpostingsid = p.id LEFT JOIN userinfo ui ON p.fuserinfoid = ui.id WHERE pm.fuserinfoid = ")
								.append(userid).append(" GROUP BY pm.fpostingsid) t");

		
		
		Integer count = databaseHelper.getSqlCount(sqlCount.toString());
		int pages = ConstantUtil.pages(count, limit);
		
		List<Object[]> resultList = databaseHelper.getResultListBySql(sql.toString(), start, limit);
		for (Object object[] : resultList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("postingid", object[0]==null?"": object[0].toString());// 帖子id
			map.put("postingsname", object[1]==null?"": object[1].toString());// 标题
			map.put("postingscontent", object[2]==null?"": object[2].toString());// 内容
			map.put("views", object[3]==null?"": object[3].toString());// 浏览量
			map.put("comments", object[4]==null?"": object[4].toString());// 评论数
			map.put("praise", object[5]==null?"": object[5].toString());// 点赞数
			map.put("time",object[6]==null?"": object[6].toString());// 时间

			map.put("userid", object[7]==null?"": object[7].toString());// 用户id
			map.put("headimg", object[8]==null?"": object[8].toString());// 头像
			map.put("nickname", object[9]==null?"": object[9].toString());// 昵称
			map.put("uuid", object[10]==null?"": object[10].toString());// 用户注册环信成功后得到的uuid
			map.put("username", object[11]==null?"": object[11].toString());// 用户注册环信成功的账户名

			// 图片
			StringBuffer hqlphoto = new StringBuffer("SELECT pp FROM PostingsPhotos pp WHERE pp.state=0 ");
			hqlphoto = hqlphoto.append("AND pp.fpostingsid=").append(object[0].toString());
			List<Object> photos = databaseHelper.getResultListByHql(hqlphoto.toString());
			List<Map<String, Object>> photolist = new ArrayList<Map<String, Object>>();
			for (Object photo : photos) {
				PostingsPhotos postingsPhotos = (PostingsPhotos) photo;
				Map<String, Object> photomap = new HashMap<String, Object>();
				photomap.put("photo", postingsPhotos.getPhotourl());
				photolist.add(photomap);
			}
			map.put("photo", photolist);// 图片

			// 是否点赞
			StringBuffer haspraisesql = new StringBuffer(
					"SELECT COUNT(1) FROM postingspraise pp WHERE pp.state=0 AND pp.fpostingsid=")
							.append(object[0].toString());

			int haspraise = databaseHelper
					.getSqlCount(haspraisesql.append(" AND pp.fuserinfoid=").append(userid).toString());
			if (haspraise > 0) {
				map.put("haspraise", 1);// 是否已点过赞 0否 1是
			} else {
				map.put("haspraise", 0);
			}
			list.add(map);

		}
		resultmap.put("msg", "success");
		resultmap.put("info", "");
		resultmap.put("total", count);
		resultmap.put("pages", pages);
		resultmap.put("rows", list);

		return resultmap;
	}
}
