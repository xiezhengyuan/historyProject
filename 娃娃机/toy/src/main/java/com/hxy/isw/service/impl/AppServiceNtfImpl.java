package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.Select;
import org.jcp.xml.dsig.internal.MacOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.MessageInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AppServiceNtf;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
@Repository
public class AppServiceNtfImpl implements AppServiceNtf {
	
	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public Map<String, Object> queryNotifyinfo(String userid, Integer limit, Integer start) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(1) from notifyinfo n where n.fuserinfoid =").append(Long.parseLong(userid)).append(" or n.fsenduserid=").append(Long.parseLong(userid));
		int count =databaseHelper.getSqlCount(buffer.toString());
		
		int pages = ConstantUtil.pages(count, limit);
		if(count>0){
			StringBuffer hql =new StringBuffer("select n from NotifyInfo n where n.fuserinfoid =").append(Long.parseLong(userid))
					.append(" or n.fsenduserid=").append(Long.parseLong(userid))
					.append(" order by n.createtime desc");
			List<Object> lst = databaseHelper.getResultListByHql(hql.toString(), start, limit);
			for(Object object:lst){
				NotifyInfo notifyInfo =(NotifyInfo) object;
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("notifyid", notifyInfo.getId());
				map.put("type", notifyInfo.getType());
				//map.put("title", notifyInfo.getTitle());
				map.put("content", notifyInfo.getContent());
				map.put("time", notifyInfo.getCreatetime().toString());
				if(notifyInfo.getType()==0){
					map.put("title", "系统消息");
				}else if(notifyInfo.getType()==1){
				
				if(notifyInfo.getFuserinfoid().equals(Long.parseLong(userid))){
					//当前用户为接收者，则title为发送者昵称
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, notifyInfo.getFsenduserid());
					map.put("title", ui.getNickname());
					map.put("username", ui.getUsername());
					map.put("otheruserid", ui.getId());
					map.put("level", ui.getLevel());
				}else{
					//当前用户为发送者，则title为接收者昵称
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, notifyInfo.getFuserinfoid());
					map.put("title", ui.getNickname());
					map.put("username", ui.getUsername());
					map.put("otheruserid", ui.getId());
					map.put("level", ui.getLevel());
				}
			}
				rowList.add(map);
			}
			
			
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", rowList);
		return resultMap;
		
		
	}
	
	//私信
	@Override
	public Map<String, Object> queryMessage(String userid, Integer limit, Integer start,String notifyid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(m.id) from messageinfo m where m.fnotifyid=").append(Long.parseLong(notifyid));
	/*	StringBuffer buffer = new StringBuffer("select count(m.id) from messageinfo m where m.fuserinfoid=").append(Long.parseLong(userid))
				.append(" or m.fsenduserid=").append(Long.parseLong(userid));*/
		int count =databaseHelper.getSqlCount(buffer.toString());
		
		int pages = ConstantUtil.pages(count, limit);
		if(count>0){
			//根据userid获取到所有与此相关的私信记录
			StringBuffer hql = new StringBuffer("select m from MessageInfo m where m.fnotifyid=").append(Long.parseLong(notifyid))
					.append(" order by m.createtime ");
			List<Object> lst = databaseHelper.getResultListByHql(hql.toString(),start,limit);
			for(Object object :lst){
				MessageInfo messageInfo = (MessageInfo) object;

				
				//根据发送者，接受者id获取到相关的用户信息
				StringBuffer hql1 = new StringBuffer("select u from UserInfo u where u.id=").append(messageInfo.getFuserinfoid());
				List<Object> list1=databaseHelper.getResultListByHql(hql1.toString());
				UserInfo juser =(UserInfo) list1.get(0);
				StringBuffer hql2 = new StringBuffer("select u from UserInfo u where u.id=").append(messageInfo.getFsenduserid());
				List<Object> list2=databaseHelper.getResultListByHql(hql1.toString());
				UserInfo fuser =(UserInfo) list1.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("messageid", messageInfo.getId());
				map.put("fsenduserid", fuser.getId());
				map.put("sender", fuser.getNickname());
				map.put("senderheadimg", fuser.getHeadimg());
				map.put("freceiverid", juser.getId());
				map.put("receiver", juser.getNickname());
				map.put("receiverheadimg", juser.getHeadimg());
				map.put("content", messageInfo.getContent());
				map.put("time", messageInfo.getCreatetime());
				rowList.add(map);
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			return resultMap;
		}
		return null;
	}
	@Override//发私信
	public Map<String, Object> querySendmessage(String userid, String receiveid, String content) {
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));//发送人
		
		NotifyInfo n = null;
		
		Date now = new Date();
		
		//检查notifyinfo表是否存在两个人的消息记录
		StringBuffer query = new StringBuffer("select n from NotifyInfo n where n.state = 0 and (n.fuserinfoid = ").append(userid)
				.append(" and n.fsenduserid = ").append(receiveid).append(") or (n.fuserinfoid = ").append(receiveid).append(" and n.fsenduserid = ").append(userid)
				.append(")");
		
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		if(lst.size()==0){
			//没有  则新增
			n = new NotifyInfo();
			n.setContent(ConstantUtil.filterEmoji(content));
			n.setTitle(ui.getNickname());
			n.setCreatetime(now);
			n.setFsenduserid(ui.getId());
			n.setFuserinfoid(Long.parseLong(receiveid));
			n.setState(0);
			n.setType(1);
			
			databaseHelper.persistObject(n);
		}else{
			//有   则更新notifyinfo的content和title为发送人的昵称和内容
			n = (NotifyInfo)lst.get(0);
			n.setContent(ConstantUtil.filterEmoji(content));
			//n.setTitle(ui.getNickname());
			n.setCreatetime(now);
			databaseHelper.updateObject(n);
			
		}
		
		//私信加入messageinfo表
		MessageInfo mi = new MessageInfo();
		mi.setContent(ConstantUtil.filterEmoji(content));
		mi.setCreatetime(now);
		mi.setFnotifyid(n.getId());
		mi.setFsenduserid(ui.getId());
		mi.setFuserinfoid(Long.parseLong(receiveid));
		mi.setState(0);
		databaseHelper.persistObject(mi);
		
		
		/*StringBuffer hql = new StringBuffer("select m from No  m where m.fuserinfoid=").append(Long.parseLong(userid))
				.append("and m.fsenduserid=").append(Long.parseLong(receiveid))
				.append(" or m.fuserinfoid=").append(Long.parseLong(receiveid))
				.append("and m.fsenduserid=").append(Long.parseLong(userid))
				.append(" order by createtime DESC limit 1");
		List<Object> lst = databaseHelper.getResultListByHql(hql.toString());
		
			
			
			MessageInfo info =(MessageInfo) lst.get(0);
			//根据最新的私信数据判断 fnotifyid是否存在，从而判断消息列表是否有无数据，有数据则跟新，无数据则插入一条
			if(info.getFnotifyid()==null){
				NotifyInfo notifyInfo = new NotifyInfo();
				
				notifyInfo.setType(1);
				notifyInfo.setFuserinfoid(Long.parseLong(receiveid));
				notifyInfo.setFsenduserid(Long.parseLong(userid));
				notifyInfo.setTitle(content);
				notifyInfo.setContent(content);
				notifyInfo.setCreatetime(info.getCreatetime());
				//notifyInfo.setState(info.getState());
				databaseHelper.persistObject(notifyInfo);
				
				MessageInfo info1 = new MessageInfo();
				info1.setFuserinfoid(Long.parseLong(receiveid));
				info1.setFsenduserid(Long.parseLong(userid));
				info1.setContent(content);
				info1.setCreatetime(new Date());
				info1.setFnotifyid(notifyInfo.getId());
				databaseHelper.persistObject(info1);
				
				
			}else{
				MessageInfo info1 = new MessageInfo();
				info1.setFuserinfoid(Long.parseLong(receiveid));
				info1.setFsenduserid(Long.parseLong(userid));
				info1.setContent(content);
				info1.setCreatetime(new Date());
				info1.setFnotifyid(info.getFnotifyid());
				databaseHelper.persistObject(info1);
				
				databaseHelper.getObjectById(NotifyInfo.class, Id)
				
				StringBuffer buffer4 = new StringBuffer("select n from NotifyInfo n where n.id=").append(info.getFnotifyid());
				List<Object> list1 = databaseHelper.getResultListByHql(buffer4.toString());
				NotifyInfo notifyInfo = (NotifyInfo)list1.get(0);
				notifyInfo.setFuserinfoid(Long.parseLong(receiveid));
				notifyInfo.setFsenduserid(Long.parseLong(userid));
				notifyInfo.setTitle(info1.getContent());
				notifyInfo.setContent(info1.getContent());
				notifyInfo.setCreatetime(info1.getCreatetime());
				notifyInfo.setState(info1.getState());
				databaseHelper.updateObject(notifyInfo);
				
				
			}*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		map.put("info", "发送私信成功");
		return map;
	}

	@Override
	public Map<String, Object> nearnotifyinfo(String userid, Integer limit, Integer start) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(1) from notifyinfo n where (n.fuserinfoid =").append(Long.parseLong(userid)).append(" or n.fsenduserid=").append(Long.parseLong(userid))
				.append(") and n.type=1 ").append(" and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= n.createtime ");
		int count =databaseHelper.getSqlCount(buffer.toString());
		
		int pages = ConstantUtil.pages(count, limit);
		if(count>0){
			StringBuffer Sql =new StringBuffer("select n.* from notifyinfo n where (n.fuserinfoid =").append(Long.parseLong(userid))
					.append(" or n.fsenduserid=").append(Long.parseLong(userid)).append("  ) and n.type=1")
					.append(" and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= n.createtime ")
					.append(" order by n.createtime desc");

			List<Object[]> lst = databaseHelper.getResultListBySql(Sql.toString(), start, limit);
			for(Object[] obj:lst){
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("notifyid", obj[0].toString());
				map.put("type", obj[1].toString());
				//map.put("title", notifyInfo.getTitle());
				map.put("content", obj[5].toString());
				map.put("time", obj[6].toString());
				if(obj[2].toString().equals(Long.parseLong(userid))){
					//当前用户为接收者，则title为发送者昵称
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(obj[3].toString()));
					map.put("title", ui.getNickname());
					map.put("username", ui.getUsername());
					map.put("otheruserid", ui.getId());
					map.put("level", ui.getLevel());
				}else{
					//当前用户为发送者，则title为接收者昵称
					UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(obj[2].toString()));
					map.put("title", ui.getNickname());
					map.put("username", ui.getUsername());
					map.put("otheruserid", ui.getId());
					map.put("level", ui.getLevel());
				}
				rowList.add(map);
			}
				
			
			
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", rowList);
		return resultMap;
	}
	

}
