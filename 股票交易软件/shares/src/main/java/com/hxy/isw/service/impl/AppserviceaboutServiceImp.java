package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxy.isw.entity.MessageInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.Postings;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.AppserviceaboutService;
import com.hxy.isw.util.DatabaseHelper;

@Repository
public class AppserviceaboutServiceImp implements AppserviceaboutService{

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public int countmyfans(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT count(f.fuserinfoid) from follow f where f.ffollowedid="+userid+"");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> myfansinfo(long userid,String nowuserid, int start, int limit) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT alltable.* from  ");
		sql.append("(SELECT t1.id,t1.nickname,t1.intro,t1.headimg,t1.createtime, t2.follows, t3.fans, '1' AS type from ");
		sql.append("(select u.id, u.nickname,u.intro, u.headimg , f.createtime from userinfo u LEFT JOIN follow f on u.id =f.fuserinfoid ");
		sql.append("where u.id in  (SELECT f1.fuserinfoid from follow f1 where f1.ffollowedid="+userid+" and f1.state=0) and f.ffollowedid="+userid+" " );
		sql.append("and u.id in ( select f2.ffollowedid from follow f2 where f2.fuserinfoid="+userid+" and f2.state=0)) t1 LEFT JOIN  ");
		sql.append("(select f3.fuserinfoid, COUNT(f3.fuserinfoid) follows from  follow f3  where f3.state=0 GROUP BY f3.fuserinfoid) t2 on t1.id = t2.fuserinfoid ");
		sql.append(" LEFT JOIN  ");
		sql.append("(select f3.ffollowedid, COUNT(f3.ffollowedid) fans from follow f3 where f3.state=0 GROUP BY f3.ffollowedid )t3 ON t1.id = t3.ffollowedid ");
		sql.append(" UNION ");
		sql.append("SELECT t1.id,t1.nickname,t1.intro, t1.headimg,t1.createtime, t2.follows, t3.fans ,'2' AS type from ");
		sql.append("(select u.id, u.nickname,u.intro, u.headimg, f.createtime from userinfo u LEFT JOIN follow f on u.id =f.fuserinfoid ");
		sql.append("where u.id in  (SELECT f1.fuserinfoid from follow f1 where f1.ffollowedid="+userid+" and f1.state=0) and f.ffollowedid="+userid+" ");
		sql.append("and u.id not  in ( select f2.ffollowedid from follow f2 where f2.fuserinfoid="+userid+" and f2.state=0)) t1 LEFT JOIN  ");
		sql.append("(select f3.fuserinfoid, COUNT(f3.fuserinfoid) follows from  follow f3  where f3.state=0 GROUP BY f3.fuserinfoid) t2 on t1.id = t2.fuserinfoid ");
		sql.append("LEFT JOIN  ");
		sql.append("(select f3.ffollowedid, COUNT(f3.ffollowedid) fans from follow f3 where f3.state=0 GROUP BY f3.ffollowedid )t3 ON t1.id = t3.ffollowedid) alltable");
	    List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString(), start, limit);
	    System.out.println(lst.size());
	    List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			String essql="select isexample from userinfo where id ="+object[0].toString()+"";
			List<Object> eslst=databaseHelper.getResultListBySql(essql);
			map.put("isisexample", eslst.get(0).toString());
			map.put("nickname", object[1]==null?"":object[1].toString());
			map.put("intro",object[2]==null?"":object[2].toString());
			map.put("headimg",object[3]==null?"":object[3].toString());
			map.put("createtime",object[4].toString());
			map.put("follows", object[5]==null?"0":object[5].toString());
			map.put("fans", object[6]==null?"0":object[6].toString());
			map.put("type",object[7].toString());
			String notifyidsql="select id from notifyinfo where  (fuserinfoid ="+userid+" and fsenduserid ="+object[0].toString()+") or(fuserinfoid ="+object[0].toString()+" and fsenduserid ="+userid+") and type =1 ";
			List<Object> notifyid=databaseHelper.getResultListBySql(notifyidsql);
			if(notifyid.size()>0&&notifyid.get(0)!=null){
				map.put("notifyid",notifyid.get(0).toString());
			}else{
				map.put("notifyid",0);
			}
			if(!nowuserid.equals("null")){
				String followdsql="select id from follow where fuserinfoid ="+nowuserid+" and ffollowedid = "+object[0].toString()+" and state = 0";
				List<Object> followd=databaseHelper.getResultListBySql(followdsql);
				if(followd.size()>0&&followd.get(0)!=null){
					map.put("followd",1);
				}else{
					map.put("followd",0);
				}
			}
		
			lstMap.add(map);
		}
	    return lstMap;
	    
	}

	@Override
	public int countmyfollow(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("select COUNT(f.id) from follow f where f.fuserinfoid="+userid+" AND f.state = 0");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> myfollowinfo(long userid,String nowuserid, int start, int limit) throws Exception {
		
		StringBuffer sql=new StringBuffer("SELECT t1.id,t1.nickname,t1.intro,t1.headimg,t1.createtime,t2.follows,t3.fans FROM ");
		sql.append("(SELECT u.id,u.nickname,u.intro,u.headimg,f.createtime	FROM userinfo u LEFT JOIN follow f ON u.id = f.ffollowedid ");
		sql.append("WHERE u.id IN (SELECT f1.ffollowedid FROM	follow f1	WHERE	f1.fuserinfoid = "+userid+"	AND f1.state = 0) AND f.fuserinfoid ="+userid+"	)t1 ");
		sql.append("LEFT JOIN (	SELECT	f3.fuserinfoid,	COUNT(f3.fuserinfoid) follows FROM follow f3 WHERE f3.state = 0	GROUP BY f3.fuserinfoid	) t2 ON t1.id = t2.fuserinfoid ");
		sql.append("LEFT JOIN (	SELECT	f3.ffollowedid,	COUNT(f3.ffollowedid) fans	FROM follow f3	WHERE f3.state = 0	GROUP BY f3.ffollowedid	) t3 ON t1.id = t3.ffollowedid ");
		System.out.println(sql.toString());
		List<Object[]> lst=databaseHelper.getResultListBySql(sql.toString(), start, limit);
	    List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for (Object[] object : lst) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			String essql="select isexample from userinfo where id ="+object[0].toString()+"";
			List<Object> eslst=databaseHelper.getResultListBySql(essql);
			map.put("isisexample", eslst.get(0).toString());
			map.put("nickname", object[1]==null?"":object[1].toString());
			map.put("intro",object[2]==null?"":object[2].toString());
			map.put("headimg",object[3]==null?"":object[3].toString());
			map.put("createtime",object[4].toString());
			map.put("follows", object[5]==null?"0":object[5].toString());
			map.put("fans", object[6]==null?"0":object[6].toString());
			String notifyidsql="select id from notifyinfo where  (fuserinfoid ="+userid+" and fsenduserid ="+object[0].toString()+") or(fuserinfoid ="+object[0].toString()+" and fsenduserid ="+userid+") and type =1 ";
			List<Object> notifyid=databaseHelper.getResultListBySql(notifyidsql);
			if(notifyid.size()>0&&notifyid!=null&&notifyid.get(0)!=null&&!notifyid.isEmpty()){
				map.put("notifyid",notifyid.get(0).toString());
			}else{
				map.put("notifyid",0);
			}
			if(!nowuserid.equals("null")){
				String followdsql="select id from follow where fuserinfoid ="+nowuserid+" and ffollowedid = "+object[0].toString()+" and state = 0";
				List<Object> followd=databaseHelper.getResultListBySql(followdsql);
				if(followd.size()>0&&followd!=null&&followd.get(0)!=null&&!followd.isEmpty()){
					map.put("followd",1);
				}else{
					map.put("followd",0);
				}
			}
			lstMap.add(map);
		}
	    return lstMap;
	    
	}

	@Override
	public int countcommentme(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(id) from postingscomments where  ");
		sql.append("(fpostingsid in ( select id from postings where fuserinfoid = "+userid+"  and state=0 ) and state =0 and fcommentid =0) or  ");
		sql.append("(fcommentid in (select id  from postingscomments where fuserinfoid ="+userid+"  ) and state=0) ");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> commentmeinfo(long userid, int start, int limit) throws Exception {
		StringBuffer sql1=new StringBuffer("select * from postingscomments where ");
		sql1.append(" (fpostingsid in ( select id from postings where fuserinfoid = "+userid+"  and state=0 ) and state =0 and fcommentid =0) or  ");
		sql1.append(" (fcommentid in (select id  from postingscomments where fuserinfoid ="+userid+"  ) and state=0) ");
		sql1.append(" order by createtime desc ");
		List<Object[]> lst=databaseHelper.getResultListBySql(sql1.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
			for (Object[] object : lst) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", object[0].toString());
				map.put("fpostingsid", object[1].toString());
				map.put("fuserinfoid",object[2].toString());
				UserInfo u=(UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(object[2].toString()));
				map.put("isexample", u.getIsexample().toString());
				map.put("comment",object[3].toString());
				map.put("commentcreatetime",object[5].toString());
				map.put("plorhf", Long.parseLong(object[6].toString())==0L?"0":"1");
				Postings p=(Postings) databaseHelper.getObjectById(Postings.class, Long.parseLong(object[1].toString()));
				map.put("postingstitle", p.getPostingstitle()==null?"":p.getPostingstitle());
				map.put("postingscontent", p.getPostingscontent()==null?"":p.getPostingscontent());
				map.put("postingscreatetime", p.getCreatetime().toString());	
				map.put("nickname", u.getNickname()==null?"":u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
				StringBuffer sqlimg=new StringBuffer("select p.id,p.photourl from postingsphotos p where p.fpostingsid= "+Long.parseLong(object[1].toString())+" and p.state=0 ");
				List<Object[]> lstimg=databaseHelper.getResultListBySql(sqlimg.toString());
				if(lstimg!=null&&lstimg.size()>0&&lstimg.get(0)!=null){
					List<Map<String,Object>> imglstMap = new ArrayList<Map<String,Object>>();
					for(Object[] obj : lstimg){
						Map<String,Object> imgmap = new HashMap<String,Object>();
						imgmap.put("imgid",obj[0].toString() );
						imgmap.put("imgurl",obj[1].toString() );
						imglstMap.add(imgmap);
					}
					map.put("postingimgs",imglstMap);
				}else{
					map.put("postingimgs","null");
				}
				String orderyou="select comment from postingscomments where fcommentid = "+object[0].toString()+" and fuserinfoid = "+userid+" and state= 0";
				List<Object> orderyoulst=databaseHelper.getResultListBySql(orderyou);
				if(orderyoulst!=null&&orderyoulst.size()>0&&orderyoulst.get(0)!=null){
					List<Map<String,Object>> orderyoulstMap = new ArrayList<Map<String,Object>>();
					for(Object obj : orderyoulst){
						Map<String,Object> orderyoumap = new HashMap<String,Object>();
						orderyoumap.put("ordercomment",obj.toString() );
						orderyoulstMap.add(orderyoumap);
					}
					map.put("orderyou",orderyoulstMap);
				}else{
					map.put("orderyou","null");
				}
				lstMap.add(map);
			}
		    return lstMap;
	}

	@Override
	public int countmycomment(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(pc.id) from postingscomments pc LEFT JOIN postings pt on pc.fpostingsid =pt.id  ");
		sql.append("where pc.fuserinfoid = "+userid+" and  pc.state =0 and pt.state=0 ");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> mycommentinfo(long userid, int start, int limit) throws Exception {
		StringBuffer sql1=new StringBuffer("select pc.* from postingscomments pc LEFT JOIN postings pt on pc.fpostingsid =pt.id   ");
		sql1.append(" where pc.fuserinfoid = "+userid+" and  pc.state =0 and pt.state=0  ");
		sql1.append(" order by pc.createtime desc ");
		List<Object[]> lst=databaseHelper.getResultListBySql(sql1.toString(), start,limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for(Object[] object : lst){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", object[0].toString());
			map.put("fpostingsid", object[1].toString());
			map.put("comment",object[3].toString());
			map.put("commentcreatetime",object[5].toString());
			map.put("plorhf", Long.parseLong(object[6].toString())==0L?"0":"1");
			Postings p=(Postings) databaseHelper.getObjectById(Postings.class, Long.parseLong(object[1].toString()));
			map.put("postingstitle", p.getPostingstitle()==null?"":p.getPostingstitle());
			map.put("postingscontent", p.getPostingscontent()==null?"":p.getPostingscontent());
			map.put("postingscreatetime", p.getCreatetime().toString());
			if( Long.parseLong(object[6].toString())!=0L){
				String Hql="select u from UserInfo u where u.id =(select p.fuserinfoid from PostingsComments p where p.id ="+object[6].toString()+")";
				UserInfo u=(UserInfo) databaseHelper.getResultListByHql(Hql.toString()).get(0);
				map.put("fuserinfoid",u.getId());
				map.put("nickname", u.getNickname()==null?"":u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
			}
			StringBuffer sqlimg=new StringBuffer("select p.id,p.photourl from postingsphotos p where p.fpostingsid= "+Long.parseLong(object[1].toString())+" and p.state=0 ");
			List<Object[]> lstimg=databaseHelper.getResultListBySql(sqlimg.toString());
			if(lstimg!=null&&lstimg.size()>0&&lstimg.get(0)!=null){
				List<Map<String,Object>> imglstMap = new ArrayList<Map<String,Object>>();
				for(Object[] obj : lstimg){
					Map<String,Object> imgmap = new HashMap<String,Object>();
					imgmap.put("imgid",obj[0].toString() );
					imgmap.put("imgurl",obj[1].toString() );
					imglstMap.add(imgmap);
				}
				map.put("postingimgs",imglstMap);
			}else{
				map.put("postingimgs","null");
			}
			lstMap.add(map);
		}
		return lstMap;
	}

	@Override
	public int countmyprivateletter(long userid) throws Exception {
		StringBuffer sql=new StringBuffer("select count(id) from usernotifystate where fuserinfoid="+userid+" and state=0 ");
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> myprivateletterinfo(long userid, int start, int limit) throws Exception {
		StringBuffer hql1 =new StringBuffer("select n from NotifyInfo n where n.id in  ");
		hql1.append("(select u.fnotifyinfoid from UserNotifyState u where u.fuserinfoid="+userid+" and u.state=0) ");
		hql1.append("order by n.createtime desc ");
		List<Object> lst1=databaseHelper.getResultListByHql(hql1.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for(Object o : lst1 ){
			NotifyInfo n =(NotifyInfo)o;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fnotifyid", n.getId().toString());
			//判断和谁聊天
			long chatuserid;
			if(n.getFuserinfoid()==userid){
				chatuserid=n.getFsenduserid();
			}else{
				chatuserid=n.getFuserinfoid();
			}
			map.put("chatuserid", chatuserid+"");
			StringBuffer hql2 =new StringBuffer("select u from UserInfo u where u.id="+chatuserid+"");
			List<Object> lst2=databaseHelper.getResultListByHql(hql2.toString());
			UserInfo u =(UserInfo) lst2.get(0);
			map.put("nickname", u.getNickname());
			map.put("headimg", u.getHeadimg());
			map.put("newcontent", n.getContent());
			map.put("createtime", n.getCreatetime().toString());
			StringBuffer sql=new StringBuffer("select count(m.id) from messageinfo m where m.fuserinfoid="+userid+" and m.state=0 and m.fnotifyid ="+n.getId()+"");
			List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
			map.put("inreadnum", lst==null?0:Integer.parseInt(lst.get(0).toString()));
			lstMap.add(map);
		}
		
		return lstMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int countmyprivateletterdetil(long userid, long fnotifyid) throws Exception {
		StringBuffer sql1=new StringBuffer("update messageinfo m set m.state=1 where m.fuserinfoid="+userid+" and m.fnotifyid="+fnotifyid+" and m.state=0; ");
		databaseHelper.executeNativeSql(sql1.toString());
		StringBuffer sql2=new StringBuffer("select count(id) from messageinfo  where fnotifyid="+fnotifyid+" ");
		List<Object> lst = databaseHelper.getResultListBySql(sql2.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> myprivateletterdetilinfo(long userid, long fnotifyid, int start, int limit)
			throws Exception {
		StringBuffer hql=new StringBuffer("select m,sendui,receiver from MessageInfo m,UserInfo sendui,UserInfo receiver where m.fsenduserid =sendui.id and m.fuserinfoid = receiver.id and m.fnotifyid="+fnotifyid+" order by m.createtime DESC");
		List<Object[]> lst=databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for(Object[] os :lst){
			MessageInfo m= (MessageInfo)os[0];
			UserInfo sendui= (UserInfo)os[1];
			UserInfo receiver= (UserInfo)os[2];
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("messageinfoid", m.getId().toString());
			map.put("getmessuserid", receiver.getId());
			map.put("getmessusername", receiver.getNickname());
			map.put("getmessuserheadimg", receiver.getHeadimg());
			map.put("sendmessuserid", sendui.getId());
			map.put("sendmessusername", sendui.getNickname());
			map.put("sendmessuserheadimg", sendui.getHeadimg());
			map.put("content", m.getContent());
			map.put("createtime",m.getCreatetime().toString());
			lstMap.add(map);
		}
		return lstMap;
	}

	@Override
	public int countmynews(long userid) throws Exception {
		StringBuffer sql2=new StringBuffer("select count(n.id) from notifyinfo n where n.fuserinfoid="+userid+" and n.type=0 and n.state=0 ");
		List<Object> lst = databaseHelper.getResultListBySql(sql2.toString());
		return lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> mynewsinfo(long userid, int start, int limit) throws Exception {
		StringBuffer hql=new StringBuffer("select n from NotifyInfo n where n.fuserinfoid="+userid+" and n.type=0 and n.state=0 order by n.createtime asc");
		List<Object> lst=databaseHelper.getResultListByHql(hql.toString(), start, limit);
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		for(Object o :lst){
			NotifyInfo m= (NotifyInfo)o;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fnotifyid", m.getId().toString());
			map.put("title", m.getTitle());
			map.put("content", m.getContent());
			map.put("createtime",m.getCreatetime().toString());
			map.put("State", m.getState().toString());
			lstMap.add(map);
		}
		return lstMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletemycomment(long id) throws Exception {
		String sql="update  postingscomments set state=-1 where id="+id+" ";
		int change =databaseHelper.executeNativeSql(sql);
		String sql1="select fpostingsid from postingscomments where  id = "+id+" ";
		List<Object> lst=databaseHelper.getResultListBySql(sql1);
		String sql2="update postings set comments= comments-1 where id ="+lst.get(0).toString()+" ";
		databaseHelper.executeNativeSql(sql2);
		if(change==0){
			throw new Exception("服务器忙");
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletemymessage(long fnotifyid,long userid) throws Exception {
		String sql="update  usernotifystate set state=-1 where fuserinfoid="+userid+" and fnotifyinfoid ="+fnotifyid+" ";
		int change =databaseHelper.executeNativeSql(sql);
		if(change==0){
			throw new Exception("服务器忙");
		}
		
	}

	@Override
	public void deletemysystemmessage(long fnotifyid) throws Exception {
		String sql="update  notifyinfo set state=-1 where  id = "+fnotifyid+" ";
		int change =databaseHelper.executeNativeSql(sql);
		if(change==0){
			throw new Exception("服务器忙");
		}
	}
}
