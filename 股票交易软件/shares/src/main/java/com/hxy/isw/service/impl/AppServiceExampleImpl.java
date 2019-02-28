package com.hxy.isw.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.Documentary;
import com.hxy.isw.entity.ExamplePlan;
import com.hxy.isw.entity.Follow;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.GoldsDetail;
import com.hxy.isw.entity.MessageInfo;
import com.hxy.isw.entity.NotifyInfo;
import com.hxy.isw.entity.PanicBuying;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesCollect;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.Subscription;
import com.hxy.isw.entity.TradeInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserNotifyState;
import com.hxy.isw.entity.UserProfit;
import com.hxy.isw.entity.UserProfitStatistic;
import com.hxy.isw.service.AppServiceExample;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;

/**
* @author lcc
* @date 2017年7月20日 下午3:13:41
* @describe
*/

@Repository
public class AppServiceExampleImpl implements AppServiceExample {

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public String quotation4hs(String userid,String type, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		
		int int_type = Integer.parseInt(type);
		
		String upanddown = int_type==1?"涨":"跌";
		
		int count = countquotation4hs(userid,upanddown);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select s from Shares s where s.type = 0 and s.state = 0 and s.upanddown = '").append(upanddown).append("'")
					.append(" order by s.diffmoney desc");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				Shares s = (Shares)object;
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("shareid", s.getId());
				map.put("sharename", s.getSharesname());
				map.put("code", s.getCode());
				map.put("market", s.getMarket());
				map.put("price", s.getPrice());
				map.put("diffmoney", s.getDiffmoney());
				map.put("diffrate", s.getDiffrate());
				map.put("lastupdate", s.getLastupdate().toString().split(" ")[1]);
				map.put("type",s.getType());//0沪深 1美股 2外汇
				map.put("ico", s.getImg()==null?"":(ConstantUtil.SERVERURL+s.getImg()));
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	public int countquotation4hs(String userid,String upanddown){
		StringBuffer count = new StringBuffer("select count(s.id) from Shares s where s.type = 0 and s.state = 0 and s.upanddown = '").append(upanddown).append("'");
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	@Override
	public String quotation4wh(String userid, int start, int limit) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO Auto-generated method stub
		int count = countquotation4wh(userid);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select s from Shares s where s.type = 2 and s.state = 0 ")
					.append(" order by s.diffmoney desc");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				Shares s = (Shares)object;
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("shareid", s.getId());
				map.put("sharename", s.getSharesname());
				map.put("code", s.getCode());
				map.put("market", s.getMarket());
				map.put("price", s.getPrice());
				map.put("diffmoney", s.getDiffmoney()==null?0:s.getDiffmoney());
				map.put("diffrate", s.getDiffrate()==null?0:s.getDiffrate());
				map.put("lastupdate", sdf.format(new Date()).toString().split(" ")[1]);
				map.put("type",s.getType());//0沪深 1美股 2外汇
				map.put("ico", s.getImg()!=null&&s.getImg().length()>0?(ConstantUtil.SERVERURL+s.getImg()):"");
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}
	
	public int countquotation4wh(String userid){
		StringBuffer count = new StringBuffer("select count(s.id) from Shares s where s.type = 2 and s.state = 0 ");
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	@Override
	public String quotation4us(String userid, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO Auto-generated method stub
		int count = countquotation4wh(userid);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select s from Shares s where s.type = 1 and s.state = 0 ")
					.append(" order by s.diffmoney desc");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				Shares s = (Shares)object;
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("shareid", s.getId());
				map.put("sharename", s.getSharesname());
				map.put("code", s.getCode());
				map.put("market", s.getMarket());
				map.put("price", s.getPrice());
				map.put("diffmoney", s.getDiffmoney()==null?0:s.getDiffmoney());
				map.put("diffrate", s.getDiffrate()==null?0:s.getDiffrate());
				map.put("lastupdate", sdf.format(new Date()).toString().split(" ")[1]);
				map.put("type",s.getType());//0沪深 1美股 2外汇
				map.put("ico", s.getImg()!=null&&s.getImg().length()>0?(ConstantUtil.SERVERURL+s.getImg()):"");
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}
	
	public int countquotation4us(String userid){
		StringBuffer count = new StringBuffer("select count(s.id) from Shares s where s.type = 1 and s.state = 0 ");
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	@Override
	public String index4shares(String userid,String type) throws Exception {
		// TODO Auto-generated method stub
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		
		String json = "";
		
		if("hs".equals(type)) json = new Gson().toJson(ConstantUtil.lm_index_hs);
		else if("us".equals(type))json = new Gson().toJson(ConstantUtil.lm_index_us);
		else if("wh".equals(type))json = new Gson().toJson(ConstantUtil.lm_index_wh);
		else json = new Gson().toJson(ConstantUtil.lm_index_hs);
		
		
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",arr.size());
		obj.add("rows", arr);
		return obj.toString();
	}

	@Override
	public String queryshareslist(String userid, String condition, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("用户id不能为空");
		
		int count = countshares(userid,condition);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select s from Shares s where s.state = 0 ");
			
			if(condition!=null&&condition.length()>0){
				query = query.append("and (s.sharesname like '%").append(condition).append("%'")
					.append(" or s.code like '%").append(condition).append("%')");
			}
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				Shares s = (Shares)object;
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("shareid", s.getId());
				map.put("sharename", s.getSharesname());
				map.put("code", s.getCode());
				map.put("market", s.getMarket());
				map.put("price", s.getPrice());
				map.put("diffmoney", s.getDiffmoney());
				map.put("diffrate", s.getDiffrate());
				map.put("lastupdate", new Date().toString());
				map.put("type",s.getType());//0沪深 1美股 2外汇
				//检查此股票是否被此用户添加为自选股（收藏）
				query = new StringBuffer("select sc from SharesCollect sc where sc.fuserinfoid = ").append(Long.parseLong(userid)).append(" and sc.code = '")
						.append(s.getCode()).append("'");
				
				List<Object> clst = databaseHelper.getResultListByHql(query.toString());
				
				if(clst.size()==0)map.put("collected", 0);
				else{
					SharesCollect sc = (SharesCollect)clst.get(0);
					
					if(sc.getState()==0){
						map.put("collected", 1);
						map.put("fcollectid", sc.getId());
					}else{
						map.put("collected", 0);
					}
				}
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	
	public int countshares(String userid,String condition){
		StringBuffer count = new StringBuffer("select count(s.id) from Shares s where s.state = 0 and (s.sharesname like '%").append(condition).append("%'")
				.append(" or s.code like '%").append(condition).append("%')");
		
		if(condition!=null&&condition.length()>0){
			count = count.append("and (s.sharesname like '%").append(condition).append("%'")
				.append(" or s.code like '%").append(condition).append("%')");
		}
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String sharesdetail(String userid, String shareid, String code, String type) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("userid不能为空");
		
		if(shareid==null||shareid.length()==0)throw new Exception("shareid不能为空");
		
		Shares s = (Shares)databaseHelper.getObjectById(Shares.class, Long.parseLong(shareid));
		
		if(!"wh".equals(s.getType())){
		
			JsonObject json = null;
			if("hs".equals(type)){
				String quotation = s.getQuotation();
				
				json = (JsonObject) new JsonParser().parse(quotation);
				
				
			}else{
				/*json = new JsonObject();
				json.addProperty("sharesname", s.getSharesname());
				json.addProperty("code", s.getCode());
				json.addProperty("price", s.getPrice());
				json.addProperty("diffmoney", s.getDiffmoney());
				json.addProperty("diffrate", s.getDiffrate());
				json.addProperty("lastupdate", sdf.format(new Date()).toString().split(" ")[1]);*/
				
				String quotation = s.getQuotation();
				
				json = (JsonObject) new JsonParser().parse(quotation);
			}
			
			//检查此用户是否对此股票有持仓
			StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 0 and swh.couldusenums > 0")
					.append(" and swh.fuserinfoid = ").append(Long.parseLong(userid)).append(" and swh.fsharesid = ").append(Long.parseLong(shareid))
					.append(" and swh.isplan = 0");
			
			List<Object> lst = databaseHelper.getResultListByHql(check.toString());
			
			if(lst.size()==0)json.addProperty("hasthisshare", 0);
			else {
				json.addProperty("hasthisshare", 1);
				SharesWareHouse swh = (SharesWareHouse)lst.get(0);
				json.addProperty("sharenums", swh.getCouldusenums());
			}
			
			return json.toString();
			
		}else{
			JsonObject json = new JsonObject();
			json.addProperty("sharesname", s.getSharesname());
			json.addProperty("code", s.getCode());
			json.addProperty("price", s.getPrice());
			json.addProperty("diffmoney", s.getDiffmoney());
			json.addProperty("diffrate", s.getDiffrate());
			json.addProperty("lastupdate", sdf.format(new Date()).toString().split(" ")[1]);
			
			//检查此用户是否对此外汇有持仓
			StringBuffer check = new StringBuffer("select fe from ForeignExchange swh where fe.state = 0 and fe.couldusenums > 0")
					.append(" and fe.fuserinfoid = ").append(Long.parseLong(userid)).append(" and fe.fsharesid = ").append(Long.parseLong(shareid))
					.append(" and fe.isplan = 0");
			
			List<Object> lst = databaseHelper.getResultListByHql(check.toString());
			
			if(lst.size()==0)json.addProperty("hasthisshare", 0);
			else {
				json.addProperty("hasthisshare", 1);
				SharesWareHouse swh = (SharesWareHouse)lst.get(0);
				json.addProperty("sharenums", swh.getCouldusenums());
			}
			
			return json.toString();
		}
	}

	@Override
	public String sharesKline(String userid, String shareid, String code, String type) throws Exception {
		// TODO Auto-generated method stub
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		
		String result = "";
		
		if("mh".equals(type)){
			//分时线
			ApiResponse response = sd.股票实时分时线数据(code, "1");//1表示当天
			
			result = new String(response.getBody(), "utf-8");
			
			
		}else{
			ApiResponse response = sd.股票K线数据(code, type);
			
			result = new String(response.getBody(), "utf-8");
		}
		
		return result;
	}
	
	@Override
	public String sharesKlineUs(String userid, String shareid, String code, String type) throws Exception {
		// TODO Auto-generated method stub
		if(code==null||code.length()==0)throw new Exception("参数code不能为空");
		
		JsonObject jObject = JuheData.getKlineUs(code);
		
		return jObject.toString();
	}


	@Override
	public String queryexamplelist(String userid, String condition, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		int count = countexample(userid,condition);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select u from UserInfo u where u.state = 0 and u.isexample = 1 ");
			
			if(condition!=null&&condition.length()>0){
				query = query.append(" and  (u.name like '%").append(condition).append("%'")
						.append(" or u.nickname like '%").append(condition).append("%')");
			}
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(),start,limit);
			
			for (Object object : lst) {
				UserInfo u = (UserInfo)object;
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("name", u.getName());
				map.put("nickname", u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
				map.put("exampleid", u.getId());

				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}
	
	
	public int countexample(String userid,String condition){
		StringBuffer count = new StringBuffer("select count(u.id) from UserInfo u where u.state = 0 and u.isexample = 1 ");
		
		if(condition!=null&&condition.length()>0){
			count = count.append(" and  (u.name like '%").append(condition).append("%'")
					.append(" or u.nickname like '%").append(condition).append("%')");
		}
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String profitrankinglist(String userid, String type, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		if(userid==null||userid.length()==0)throw new Exception("userid不能为空");
		
		int int_type = Integer.parseInt(type);
		
		int count = countprofitranking(userid,int_type);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		StringBuffer check = null;
		if(count>0){
			StringBuffer query = new StringBuffer("select u,ups from UserInfo u,UserProfitStatistic ups where u.state = 0 and u.isexample = 1 and ups.fuserinfoid = u.id ");
			
				
				
				if(int_type==-1)query = query.append(" order by ups.totalrate desc");
				else if(int_type==0)query = query.append(" order by ups.rateofhs desc");
				else if(int_type==1)query = query.append(" order by ups.rateofus desc");
				else if(int_type==2)query = query.append(" order by ups.rateofwh desc");
			
			List<Object[]> lst = databaseHelper.getResultListByHql(query.toString(),start,limit);
			
			for (Object[] objects : lst) {
				UserInfo u = (UserInfo)objects[0];
				UserProfitStatistic ups = (UserProfitStatistic)objects[1];
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("name", u.getName());
				map.put("nickname", u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
				map.put("exampleid", u.getId());
				
				if(int_type==-1)map.put("profitrate", ConstantUtil.formatDouble(ups.getTotalprofit()));
				else if(int_type==0)map.put("profitrate", ConstantUtil.formatDouble(ups.getProfitofhs()));
				else if(int_type==1)map.put("profitrate", ConstantUtil.formatDouble(ups.getProfitofus()));
				else if(int_type==2)map.put("profitrate", ConstantUtil.formatDouble(ups.getProfitofwh()));
				
				map.put("fans", ups.getFans());
				
				//检查当前用户是否关注此人
				check = new StringBuffer("select f from Follow f where f.state = 0 and f.fuserinfoid = ").append(Long.parseLong(userid)).append(" and f.ffollowedid =").append(u.getId());
				List<Object> clst = databaseHelper.getResultListByHql(check.toString());
				if(clst.size()==0){
					map.put("followed", 0);
				}else{
					Follow f = (Follow)clst.get(0);
					map.put("followed", 1);
					map.put("followid", f.getId());
				}
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}
	
	
	public int countprofitranking(String userid,int int_type){
		StringBuffer count = new StringBuffer("select count(ups.id) from UserInfo u,UserProfitStatistic ups where u.state = 0 and u.isexample = 1 and ups.fuserinfoid = u.id ");
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String popularityrankinglist(String userid, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("userid不能为空");
		
		
		int count = countprofitranking(userid,-1);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		StringBuffer check = null;
		if(count>0){
			StringBuffer query = new StringBuffer("select u,ups from UserInfo u,UserProfitStatistic ups where u.state = 0 and u.isexample = 1 and ups.fuserinfoid = u.id ")
					.append(" order by ups.fans desc");
			
			
			
			List<Object[]> lst = databaseHelper.getResultListByHql(query.toString(),start,limit);
			
			for (Object[] objects : lst) {
				UserInfo u = (UserInfo)objects[0];
				UserProfitStatistic ups = (UserProfitStatistic)objects[1];
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("name", u.getName());
				map.put("nickname", u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
				map.put("exampleid", u.getId());
		
				
				map.put("fans", ups.getFans());
				map.put("rewordnums", ups.getRewordnums());
				
				//检查当前用户是否关注此人
				check = new StringBuffer("select f from Follow f where f.state = 0 and f.fuserinfoid = ").append(Long.parseLong(userid)).append(" and f.ffollowedid =").append(u.getId());
				List<Object> clst = databaseHelper.getResultListByHql(check.toString());
				if(clst.size()==0){
					map.put("followed", 0);
				}else{
					Follow f = (Follow)clst.get(0);
					map.put("followed", 1);
					map.put("followid", f.getId());
				}
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	@Override
	public String planrankinglist(String userid, String type, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		if(userid==null||userid.length()==0)throw new Exception("userid不能为空");
		
		int int_type = Integer.parseInt(type);
		
		int count = countprofitranking(userid,int_type);
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		StringBuffer check = null;
		if(count>0){
			StringBuffer query = new StringBuffer("select u,ups from UserInfo u,UserProfitStatistic ups where u.state = 0 and u.isexample = 1 and ups.fuserinfoid = u.id ");
			
				
				
				if(int_type==-1)query = query.append(" order by ups.totalplansuccess desc,ups.totalplanfrofit desc");
				else if(int_type==0)query = query.append(" order by ups.hsplansuccess desc,ups.hsplanfrofit desc");
				else if(int_type==1)query = query.append(" order by ups.usplansuccess desc,ups.usplanfrofit desc");
				else if(int_type==2)query = query.append(" order by ups.whplansuccess desc,ups.whplanfrofit desc");
			
			List<Object[]> lst = databaseHelper.getResultListByHql(query.toString(),start,limit);
			
			for (Object[] objects : lst) {
				UserInfo u = (UserInfo)objects[0];
				UserProfitStatistic ups = (UserProfitStatistic)objects[1];
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("name", u.getName());
				map.put("nickname", u.getNickname());
				map.put("headimg", u.getHeadimg()==null?"":u.getHeadimg());
				map.put("exampleid", u.getId());
				
				if(int_type==-1){
					map.put("planfrofit", ups.getTotalplanfrofit());
					map.put("successrate", ups.getTotalplansuccess());
				}else if(int_type==0){
					map.put("planfrofit", ups.getHsplanfrofit());
					map.put("successrate", ups.getHsplansuccess());
				}else if(int_type==1){
					map.put("planfrofit", ups.getUsplanfrofit());
					map.put("successrate", ups.getUsplansuccess());
				}else if(int_type==2){
					map.put("planfrofit", ups.getWhplanfrofit());
					map.put("successrate", ups.getWhplansuccess());
				}
				
				
				//检查当前用户是否关注此人
				check = new StringBuffer("select f from Follow f where f.state = 0 and f.fuserinfoid = ").append(Long.parseLong(userid)).append(" and f.ffollowedid =").append(u.getId());
				List<Object> clst = databaseHelper.getResultListByHql(check.toString());
				if(clst.size()==0){
					map.put("followed", 0);
				}else{
					Follow f = (Follow)clst.get(0);
					map.put("followed", 1);
					map.put("followid", f.getId());
				}
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	@Override
	public String exampledetail(String userid, String exampleid) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		int year = Integer.parseInt(sdf.format(now).split("-")[0]);
		int month = Integer.parseInt(sdf.format(now).split("-")[1]);
		
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(exampleid==null||exampleid.length()==0)throw new Exception("参数exampleid不能为空");
		
		UserInfo example = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(exampleid));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name",example.getName());
		map.put("nickname",example.getNickname());
		map.put("headimg",example.getHeadimg());
		
		//查看牛人的粉丝数
		StringBuffer query = new StringBuffer("select count(f.id) from Follow f where f.state = 0 and f.ffollowedid = ").append(example.getId());
		List lst = databaseHelper.getResultListByHql(query.toString());
		int fans = Integer.parseInt(lst.get(0).toString());
		map.put("fans",fans);
		
		//查看牛人的关注数
		query = new StringBuffer("select count(f.id) from Follow f where f.state = 0 and f.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListByHql(query.toString());
		int follows = Integer.parseInt(lst.get(0).toString());
		map.put("follows",follows);
		
		//累计收益--持仓
		query = new StringBuffer("select sum(userp.diffprofitoflasttime) 'a' from userprofit userp where userp.tags = 1 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit0 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		//累计收益--卖出
		query = new StringBuffer("select sum(userp.profit) 'a' from userprofit userp where userp.tags = 0 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit1 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		map.put("totalprofit",ConstantUtil.formatDouble((profit0+profit1)));
		
		
		//年收益--持仓
		query = new StringBuffer("select sum(userp.diffprofitoflasttime) 'a' from userprofit userp where year(userp.time)=").append(year).append(" and userp.tags = 1 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double year_profit0 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		//年收益--卖出
		query = new StringBuffer("select sum(userp.profit) 'a' from userprofit userp where year(userp.time)=").append(year).append(" and userp.tags = 0 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double year_profit1 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		map.put("yearprofit",ConstantUtil.formatDouble(year_profit0+year_profit1));
		
		
		//月收益--持仓
		query = new StringBuffer("select sum(userp.diffprofitoflasttime) 'a' from userprofit userp where year(userp.time)=").append(year).append(" and month(userp.time) = ").append(month).append(" and userp.tags = 1 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double month_profit0 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		//月收益--卖出
		query = new StringBuffer("select sum(userp.profit) 'a' from userprofit userp where year(userp.time)=").append(year).append(" and month(userp.time) = ").append(month).append(" and userp.tags = 0 and userp.fuserinfoid = ").append(example.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		Double month_profit1 = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:Double.parseDouble(lst.get(0).toString());
		map.put("monthprofit",ConstantUtil.formatDouble(month_profit0+month_profit1));
		
		
		//检查用户是否关注此牛人
		StringBuffer check = new StringBuffer("select f from Follow f where f.state = 0 and f.fuserinfoid = ").append(Long.parseLong(userid)).append(" and f.ffollowedid =").append(example.getId());
		List<Object> clst = databaseHelper.getResultListByHql(check.toString());
		if(clst.size()==0){
			map.put("followed", 0);
		}else{
			Follow f = (Follow)clst.get(0);
			map.put("followed", 1);
			map.put("followid", f.getId());
		}
		
		
		//检查用户是否订阅此牛人
		check = new StringBuffer("select s from Subscription s where s.state = 0 and s.fuserinfoid = ").append(Long.parseLong(userid)).append(" and s.fsubscribedid =").append(example.getId());
		clst = databaseHelper.getResultListByHql(check.toString());
		if(clst.size()==0){
			map.put("subscribed", 0);
		}else{
			Follow f = (Follow)clst.get(0);
			map.put("subscribed", 1);
			map.put("subscribid", f.getId());
		}
		
		
		//检查用户是否跟随此牛人
		check = new StringBuffer("select d from Documentary d where d.state = 0 and d.fuserinfoid = ").append(Long.parseLong(userid)).append(" and d.ffollowuserinfoid =").append(example.getId());
		clst = databaseHelper.getResultListByHql(check.toString());
		if(clst.size()==0){
			map.put("aftered", 0);
		}else{
			Documentary d = (Documentary)clst.get(0);
			map.put("aftered", 1);
			map.put("afterid", d.getId());
		}
		
		map.put("subscriptiongold",ConstantUtil.setting.getSubscriptiongold());//订阅牛人所需金币（单月）
		map.put("purchasegold",ConstantUtil.setting.getPurchasegold());//抢购计划所需金币
		map.put("viewgold",ConstantUtil.setting.getViewgold());//观摩计划所需金币
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}


	@Override
	public String planlist(String userid, String exampleid, String self, String type, String tag, int start, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		String result = "";
		
		if(exampleid!=null&&exampleid.length()>0){
			//查看牛人计划列表
			result = queryexampleplanlst(userid, exampleid,type, start, limit);
		}else{
			if(self!=null&&Integer.parseInt(self)==1){
				//查看我的计划列表
				result = queryselfplanlst(userid, tag, start, limit);
			}else{
				//查看所有计划列表
				result = queryallplanlst(userid, type, tag, start, limit);
			}
			
		}
		
		return result;
	}
	
	private Map<String, Object> staticofplan(Map<String, Object> map,ExamplePlan ep){
		map.put("startime", ep.getStarttime().toString());//计划开始时间
		map.put("endtime", ep.getEndtime().toString());//计划结束时间
		map.put("maxtime", ConstantUtil.differentDays(ep.getStarttime(), ep.getEndtime()));
		if(ep.getState()==0){//抢购中
			map.put("buyingendtime", ep.getStarttime().toString());
			//查看计划抢购人数
			StringBuffer check = new StringBuffer("select count(panicbuying.id) from PanicBuying panicbuying where panicbuying.fexampleplanid = ").append(ep.getId()).append(" and panicbuying.state = 0");
			List checklst = databaseHelper.getResultListByHql(check.toString());
			int buyingmembers =  Integer.parseInt(checklst.get(0).toString());
			map.put("buyingmembers", buyingmembers);
			
			map.put("distancetime", ConstantUtil.getDistanceTime(new Date(), ep.getStarttime()));//计划开始倒计时
			
		}else if(ep.getState()==1){//运行中
			int diffday = ConstantUtil.differentDays(ep.getStarttime(), new Date());
			map.put("runday", diffday<0?0:diffday);
			
			//查看计划中最新的持仓记录
			int type = ep.getType();
			if(type<2){
				//股票
				StringBuffer find = new StringBuffer("select swh from SharesWareHouse swh where swh.fexampleplanid = ").append(ep.getId()).append(" order by time desc");
				
				List<Object> findlst = databaseHelper.getResultListByHql(find.toString());
				
				if(findlst.size()==0){
					map.put("newplaytime","");
					map.put("warehouse","");
				}else{
					SharesWareHouse swh = (SharesWareHouse)findlst.get(0);
					int couldusenums = 0;
					for (Object object2 : findlst) {
						SharesWareHouse s = (SharesWareHouse)object2;
						couldusenums += s.getCouldusenums();
					}
					
					map.put("newplaytime",swh.getTime());
					
					double warehouse = ConstantUtil.intdevice2(swh.getCouldusenums(), couldusenums, true);//new BigDecimal((float)swh.getCouldusenums()/couldusenums*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
					
					map.put("warehouse",warehouse+"%");
				}
			}else{
				//外汇
				StringBuffer find = new StringBuffer("select fe from ForeignExchange fe where fe.fexampleplanid = ").append(ep.getId()).append(" order by time desc");
				
				List<Object> findlst = databaseHelper.getResultListByHql(find.toString());
				
				if(findlst.size()==0){
					map.put("newplaytime","");
					map.put("warehouse","");
				}else{
					ForeignExchange fe = (ForeignExchange)findlst.get(0);
					int couldusenums = 0;
					for (Object object2 : findlst) {
						ForeignExchange f = (ForeignExchange)object2;
						couldusenums += f.getCouldusenums();
					}
					
					map.put("newplaytime",fe.getTime());
					
					double warehouse = new BigDecimal((float)fe.getCouldusenums()/couldusenums*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
					
					map.put("warehouse",warehouse+"%");
				}
			}
			
			map.put("maxtime", diffday<0?0:diffday);//最长时限
			
			//TODO  大盘同期
			map.put("stockindex", "0%");//大盘同期
		}else if(ep.getState()==2){//已结束
			int diffday = ConstantUtil.differentDays(ep.getStarttime(), ep.getEndtime());
			map.put("runday", diffday<0?0:diffday);
			
			map.put("successed", ep.getSuccessed());
		}
		
		return map;
	}
	
	//查看牛人计划列表
	private String queryexampleplanlst(String userid, String exampleid,String retype , int start, int limit){
		
		int count = countexampleplan(userid, exampleid,retype);
		Map<String, Object> m = statisticexampleplan(userid, exampleid);
		
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		if(count>0){
			StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(exampleid));
			
			if(retype!=null&&Integer.parseInt(retype)>-1)query.append(" and ep.type = ").append(Integer.parseInt(retype));
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				ExamplePlan ep = (ExamplePlan)object;
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("planid",ep.getId());
				map.put("planname",ep.getPlanname());
				map.put("plandesc",ep.getPlandesc());
				map.put("targetprofit",ep.getTargetprofit());
				map.put("stopline",ep.getStopline());
				map.put("actualprofit",ep.getActualprofit()==null?0d:ep.getActualprofit());
				
				
				map.put("state", ep.getState());
				map.put("type", ConstantUtil.type[ep.getType()]);
				map.put("planstate", ConstantUtil.planstate[ep.getState()]);//0抢购 1运行中 2已结束
				
				//检查当前用户对计划是否抢购或观摩
				StringBuffer checkb = new StringBuffer("select panicbuying from PanicBuying panicbuying where panicbuying.fexampleplanid = ").append(ep.getId())
						.append(" and panicbuying.fuserinfoid = ").append(Long.parseLong(userid));
				List<Object> checked = databaseHelper.getResultListByHql(checkb.toString());
				if(checked.size()==0){
					map.put("panicbuying", -1);
				}else{
					PanicBuying panicbuying = (PanicBuying)checked.get(0);
					map.put("panicbuying", panicbuying.getState());
				}
				
				map = staticofplan(map, ep);
				
				
				lstMap.add(map);
			}
			
		}
		
		//牛人信息
		UserInfo example = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(exampleid));
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.addProperty("name",example.getNickname()==null?example.getName():example.getNickname());
		obj.addProperty("headimg",example.getHeadimg());
		obj.addProperty("profitrate",m.get("profitrate").toString());
		obj.addProperty("successnums",m.get("successnums").toString());
		obj.add("rows", arr);
		
		return obj.toString();
		
	}
	
	public int countexampleplan(String userid, String exampleid,String retype){
		StringBuffer count = new StringBuffer("select count(ep.id) from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(exampleid));
		if(retype!=null&&Integer.parseInt(retype)>-1)count.append(" and ep.type = ").append(Integer.parseInt(retype));
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	
	public Map<String, Object> statisticexampleplan(String userid, String exampleid){
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(exampleid));
		
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		Double total = 0d;
		int success = 0;
		for (Object object : lst) {
			ExamplePlan ep = (ExamplePlan)object;
			total += (ep.getActualprofit()==null?0d:ep.getActualprofit());
			if(ep.getSuccessed()==1)success++;
		}
		
		double profitrate = lst.size()==0?0: new BigDecimal(total/lst.size()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		map.put("profitrate", profitrate+"%");
		map.put("successnums", success);
		
		return map;
	}
	
	
	
	//查看个人计划列表
	private String queryselfplanlst(String userid, String tag,  int start, int limit){
		
		int count = countselfplan(userid, tag);
		Map<String, Object> m = statisticselfplan(userid, tag);
		
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		if(count>0){
			StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(userid)).append(" and ep.state = ").append(Integer.parseInt(tag));
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				ExamplePlan ep = (ExamplePlan)object;
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("planid",ep.getId());
				map.put("planname",ep.getPlanname());
				map.put("plandesc",ep.getPlandesc());
				map.put("targetprofit",ep.getTargetprofit());
				map.put("stopline",ep.getStopline());
				map.put("actualprofit",ep.getActualprofit()==null?0d:ep.getActualprofit());
				
				map.put("state", ep.getState());
				map.put("type", ConstantUtil.type[ep.getType()]);
				map.put("planstate", ConstantUtil.planstate[ep.getState()]);//0抢购 1运行中 2已结束
				
				map = staticofplan(map, ep);
				map.put("tag",tag);
				
				lstMap.add(map);
			}
			
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.addProperty("profitrate",m.get("profitrate").toString());
		obj.addProperty("successnums",m.get("successnums").toString());
		obj.add("rows", arr);
		
		return obj.toString();
		
	}
	
	public int countselfplan(String userid, String tag){
		StringBuffer count = new StringBuffer("select count(ep.id) from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(userid)).append(" and ep.state = ").append(Integer.parseInt(tag));
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	
	public Map<String, Object> statisticselfplan(String userid, String tag){
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(userid)).append(" and ep.state = ").append(Integer.parseInt(tag));
		
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		Double total = 0d;
		int success = 0;
		for (Object object : lst) {
			ExamplePlan ep = (ExamplePlan)object;
			total += (ep.getActualprofit()==null?0d:ep.getActualprofit());
			if(ep.getSuccessed()==1)success++;
		}
		
		double profitrate =lst.size()==0?0d: new BigDecimal(total/lst.size()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		map.put("profitrate", profitrate+"%");
		map.put("successnums", success);
		
		return map;
	}
	
	
	//查看所有计划列表
	private String queryallplanlst(String userid, String retype,String tag,  int start, int limit){
		
		int count = countallplan(userid, retype, tag);
		
		
		int pages = ConstantUtil.pages(count, limit);
		
		List<Map<String,Object>> lstMap = new ArrayList<Map<String,Object>>();
		
		if(count>0){
			StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where 1 = 1");
			//-1所有  0沪深  1美股  2外汇
			if(retype!=null&&Integer.parseInt(retype)>-1)query.append(" and ep.type = ").append(Integer.parseInt(retype));
			
			//3精选计划  0抢购中  1运行中  2往期成功
			if(tag!=null){
				int int_tag = Integer.parseInt(tag);
				if(int_tag==3){//精选计划
					query.append(" and ep.ischoiceness = 1 ");
				}else{
					query.append(" and ep.ischoiceness = 0 ");
					if(int_tag==2){//往期成功
						query.append(" and ep.state = 2 and ep.successed = 1 ");
					}else{
						query.append(" and ep.state = ").append(int_tag);
					}
				}
			}
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(), start, limit);
			
			for (Object object : lst) {
				ExamplePlan ep = (ExamplePlan)object;
				
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("planid",ep.getId());
				map.put("planname",ep.getPlanname());
				map.put("plandesc",ep.getPlandesc());
				map.put("targetprofit",ep.getTargetprofit());
				map.put("stopline",ep.getStopline());
				map.put("actualprofit",ep.getActualprofit()==null?0d:ep.getActualprofit());
				
				map.put("state", ep.getState());
				map.put("type", ConstantUtil.type[ep.getType()]);
				map.put("planstate", ConstantUtil.planstate[ep.getState()]);//0抢购 1运行中 2已结束
				map.put("tag",tag);
				//检查当前用户对计划是否抢购或观摩
				StringBuffer checkb = new StringBuffer("select panicbuying from PanicBuying panicbuying where panicbuying.fexampleplanid = ").append(ep.getId())
						.append(" and panicbuying.fuserinfoid = ").append(Long.parseLong(userid));
				List<Object> checked = databaseHelper.getResultListByHql(checkb.toString());
				if(checked.size()==0){
					map.put("panicbuying", -1);
				}else{
					PanicBuying panicbuying = (PanicBuying)checked.get(0);
					map.put("panicbuying", panicbuying.getState());
				}
				
				map = staticofplan(map, ep);
				
				Map<String, Object> m = statisticallplan(userid, ep.getFuserinfoid().toString());
				map.put("profitrate",m.get("profitrate").toString());
				map.put("successnums",m.get("successnums").toString());
				
				//牛人信息
				UserInfo example = (UserInfo)databaseHelper.getObjectById(UserInfo.class, ep.getFuserinfoid());
				map.put("name",example.getNickname()==null?example.getName():example.getNickname());
				map.put("headimg",example.getHeadimg());
				map.put("exampleid",example.getId());
				
				lstMap.add(map);
			}
			
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
		
	}
	
	public int countallplan(String userid, String retype, String tag){
		StringBuffer count = new StringBuffer("select count(ep.id) from ExamplePlan ep where 1 = 1 ");
		
		//-1所有  0沪深  1美股  2外汇
		if(retype!=null&&Integer.parseInt(retype)>-1)count.append(" and ep.type = ").append(Integer.parseInt(retype));
		
		//3精选计划  0抢购中  1运行中  2往期成功
		if(tag!=null){
			int int_tag = Integer.parseInt(tag);
			if(int_tag==3){//精选计划
				count.append(" and ep.ischoiceness = 1 ");
			}else{
				count.append(" and ep.ischoiceness = 0 ");
				if(int_tag==2){//往期成功
					count.append(" and ep.state = 2 and ep.successed = 1 ");
				}else{
					count.append(" and ep.state = ").append(int_tag);
				}
			}
		}
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	
	public Map<String, Object> statisticallplan(String userid, String exampleid){
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer query = new StringBuffer("select ep from ExamplePlan ep where ep.fuserinfoid = ").append(Long.parseLong(exampleid));
		
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		Double total = 0d;
		int success = 0;
		for (Object object : lst) {
			ExamplePlan ep = (ExamplePlan)object;
			total += (ep.getActualprofit()==null?0d:ep.getActualprofit());
			if(ep.getSuccessed()==1)success++;
		}
		
		double profitrate = new BigDecimal(total/lst.size()*100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		map.put("profitrate", profitrate);
		map.put("successnums", success);
		
		return map;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String follow(String userid, String exampleid) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(exampleid==null||exampleid.length()==0)throw new Exception("参数exampleid不能为空");
		
		if(userid.equals(exampleid))throw new Exception("不要自己关注自己o(︶︿︶)o");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		StringBuffer query = new StringBuffer("select follow from Follow follow where follow.ffollowedid = ").append(exampleid).append(" and follow.fuserinfoid = ").append(userid);
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		int followed = 0;
		if(lst.size() ==0){
			Follow follow = new Follow();
			follow.setCreatetime(new Date());
			follow.setFfollowedid(Long.parseLong(exampleid));
			follow.setFuserinfoid(Long.parseLong(userid));
			follow.setState(0);
			databaseHelper.persistObject(follow);
			followed = 1;
		}else{
			Follow follow = (Follow)lst.get(0);
			int state = follow.getState()==0?-1:0;
			follow.setState(state);
			
			databaseHelper.updateObject(follow);
			followed = state==0?1:0;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "操作成功");
		map.put("followed", followed);
		
		//TODO 给关注人发送一条私信
		if(followed==1){
			Date now = new Date();
			String content = "感谢您的关注";
			
			//检查notifyinfo表是否存在两个人的消息记录
			query = new StringBuffer("select n from NotifyInfo n where  (n.fuserinfoid = ").append(userid)
					.append(" and n.fsenduserid = ").append(exampleid).append(") or (n.fuserinfoid = ").append(exampleid).append(" and n.fsenduserid = ").append(userid).append(")");
			
			List<Object> qlst = databaseHelper.getResultListByHql(query.toString());
			NotifyInfo n = null;
			if(qlst.size()==0){
				//没有  则新增
				n = new NotifyInfo();
				n.setContent(ConstantUtil.filterEmoji(content));
				n.setTitle(ui.getNickname());
				n.setCreatetime(now);
				n.setFsenduserid(Long.parseLong(exampleid));
				n.setFuserinfoid(ui.getId());
				n.setState(0);
				n.setType(1);
				
				databaseHelper.persistObject(n);
				
			}else{
				//有   则更新notifyinfo的content和title为发送人的昵称和内容
				n = (NotifyInfo)qlst.get(0);
				n.setContent(ConstantUtil.filterEmoji(content));
				//n.setTitle(ui.getNickname());
				n.setCreatetime(now);
				databaseHelper.updateObject(n);
			}	
			
			//检查双方对此回话消息的状态
			StringBuffer check = new StringBuffer("select uns from UserNotifyState uns where uns.fuserinfoid = ").append(ui.getId()).append(" and uns.fnotifyinfoid = ").append(n.getId());
			List<Object> clst = databaseHelper.getResultListByHql(check.toString());
			if(clst.size()==0){
				UserNotifyState uns1 = new UserNotifyState();
				uns1.setFnotifyinfoid(n.getId());
				uns1.setFuserinfoid(ui.getId());
				uns1.setState(0);
				uns1.setTime(now);
				databaseHelper.persistObject(uns1);
			}else{
				UserNotifyState uns1 = (UserNotifyState)clst.get(0);
				uns1.setState(0);
				uns1.setTime(now);
				databaseHelper.updateObject(uns1);
			}
			
			
			check = new StringBuffer("select uns from UserNotifyState uns where uns.fuserinfoid = ").append(exampleid).append(" and uns.fnotifyinfoid = ").append(n.getId());
			clst = databaseHelper.getResultListByHql(check.toString());
			if(clst.size()==0){
				UserNotifyState uns2 = new UserNotifyState();
				uns2.setFnotifyinfoid(n.getId());
				uns2.setFuserinfoid(Long.parseLong(exampleid));
				uns2.setState(0);
				uns2.setTime(now);
				databaseHelper.persistObject(uns2);
			}else{
				UserNotifyState uns2 = (UserNotifyState)clst.get(0);
				uns2.setState(0);
				uns2.setTime(now);
				databaseHelper.updateObject(uns2);
			}
			
			/*NotifyInfo ni = new NotifyInfo();
			ni.setContent(content);
			ni.setCreatetime(now);
			ni.setFsenduserid(Long.parseLong(exampleid));
			ni.setFuserinfoid(Long.parseLong(userid));
			ni.setState(0);
			ni.setTitle("");
			ni.setType(1);
			databaseHelper.persistObject(ni);
			
			UserNotifyState uns1 = new UserNotifyState();
			uns1.setFnotifyinfoid(ni.getId());
			uns1.setFuserinfoid(Long.parseLong(userid));
			uns1.setState(0);
			uns1.setTime(now);
			databaseHelper.persistObject(uns1);
			
			UserNotifyState uns2 = new UserNotifyState();
			uns2.setFnotifyinfoid(ni.getId());
			uns2.setFuserinfoid(Long.parseLong(userid));
			uns2.setState(0);
			uns2.setTime(now);
			databaseHelper.persistObject(uns2);*/
			
			MessageInfo mi = new MessageInfo();
			mi.setContent(content);
			mi.setCreatetime(now);
			mi.setFnotifyid(n.getId());
			mi.setFsenduserid(Long.parseLong(exampleid));
			mi.setFuserinfoid(Long.parseLong(userid));
			mi.setState(0);
			databaseHelper.persistObject(mi);
			
			//TODO websocket 消息推送   --- 参数   接收人的用户 username 接收人的用户id 消息内容
			ConstantUtil.broadcast(ui.getUsername(), ui.getId(), content);
		}
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String subscription(String userid, String exampleid, String month, String amount) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(exampleid==null||exampleid.length()==0)throw new Exception("参数exampleid不能为空");
		
		if(userid.equals(exampleid))throw new Exception("不能自己订阅自己o(︶︿︶)o");
		
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class,Long.parseLong(userid));
		
		StringBuffer query = new StringBuffer("select s from Subscription s where s.fsubscribedid = ").append(exampleid).append(" and s.fuserinfoid = ").append(userid);
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		
		int subscribed = 0;
		if(lst.size() ==0){
			//检查是否有足够的金币
			if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
			if(month==null||month.length()==0)throw new Exception("参数month不能为空");
			if(u.getGolds()<Double.parseDouble(amount))throw new Exception("很抱歉，你的金币不足，无法订阅o(︶︿︶)o");
			
			Subscription s = new Subscription();
			s.setCreatetime(new Date());
			s.setFsubscribedid(Long.parseLong(exampleid));
			s.setFuserinfoid(Long.parseLong(userid));
			s.setState(0);
			s.setAmount(Double.parseDouble(amount));
			s.setStarttime(s.getCreatetime());
			
			Calendar cl = Calendar.getInstance();  
	        cl.setTime(s.getCreatetime());  
	        cl.add(Calendar.MONTH, Integer.parseInt(month));  
			
			s.setEndtime(cl.getTime());
			databaseHelper.persistObject(s);
			subscribed = 1;
		}else{
			Subscription s = (Subscription)lst.get(0);
			int state = s.getState()==1?0:1;
			
			if(state==1){
				//检查是否有足够的金币
				if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
				if(month==null||month.length()==0)throw new Exception("参数month不能为空");
				if(u.getGolds()<Double.parseDouble(amount))throw new Exception("很抱歉，你的金币不足，无法订阅o(︶︿︶)o");
			}
			
			s.setState(state);
			
			s.setStarttime(new Date());
			
			Calendar cl = Calendar.getInstance();  
	        cl.setTime(s.getStarttime());  
	        cl.add(Calendar.MONTH, Integer.parseInt(month));  
			
			s.setEndtime(cl.getTime());
			
			databaseHelper.updateObject(s);
			subscribed = state;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "操作成功");
		map.put("subscribed", subscribed);
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
		
	}

	/*@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String documentary(String userid, String exampleid, String amount) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(exampleid==null||exampleid.length()==0)throw new Exception("参数exampleid不能为空");
		//if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
		if(userid.equals(exampleid))throw new Exception("不能自己跟随自己o(︶︿︶)o");
		
		//检查用户是否已跟随此牛人
		StringBuffer query = new StringBuffer("select d from Documentary d where d.fuserinfoid = ").append(userid).append(" and d.ffollowuserinfoid = ").append(exampleid);
		List<Object> querylst = databaseHelper.getResultListByHql(query.toString());
		
		if(querylst.size()>0&&((Documentary)querylst.get(0)).getState()==1)throw new Exception("你已跟随此人，请不要重复操作o(︶︿︶)o");
		
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		Double ta = 0d;
		//找出用户的与此牛人的跟单股票，全部卖出
		ta = checkuserwarehouse(exampleid, userid, sd, ta);
		
		Double d_amount =amount==null?ta: Double.parseDouble(amount);
		
		//更新用户的虚拟资金
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		ui.setVirtualcapital(ui.getVirtualcapital()+ta);
		databaseHelper.updateObject(ui);
		
		//检查用户的虚拟资金是否足够
		if(ui.getVirtualcapital()<d_amount)throw new Exception("你的虚拟资金不足o(︶︿︶)o");
		
		
		//跟随牛人买入相同比例的股票和外汇
		Double diffamount = getexamplewarehouse(exampleid, userid, sd, d_amount);
		
		
		//添加到跟单表
		if(querylst.size()==0){
			Documentary d = new Documentary();
			d.setActualyamount(d_amount-diffamount);
			d.setCreatetime(new Date());
			d.setFfollowuserinfoid(Long.parseLong(exampleid));
			d.setFuserinfoid(Long.parseLong(userid));
			d.setDiffamount(diffamount);
			d.setMoney(Integer.parseInt(amount));
			d.setState(0);
			databaseHelper.persistObject(d);
		}else{
			Documentary d = (Documentary)querylst.get(0);
			d.setActualyamount(d_amount-diffamount);
			d.setDiffamount(diffamount);
			d.setMoney(Integer.parseInt(amount));
			d.setState(0);
			databaseHelper.updateObject(d);
		}
		
		//更新用户的虚拟资金
		ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		ui.setVirtualcapital(ui.getVirtualcapital()-d_amount);
		databaseHelper.updateObject(ui);
		
		if(amount!=null){
			//添加到资金明细表
			CapitalDetail cd = new CapitalDetail();
			cd.setFuserinfoid(Long.parseLong(userid));
			cd.setType(-5);
			cd.setCapital(d_amount);
			cd.setCreatetime(new Date());
			cd.setState(0);
			databaseHelper.persistObject(cd);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "操作成功");
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}*/
	
	@Override
	public String documentary(String userid, String exampleid, String type, String value) throws Exception {
		// TODO Auto-generated method stub
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(exampleid==null||exampleid.length()==0)throw new Exception("参数exampleid不能为空");
		//if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
		if(userid.equals(exampleid))throw new Exception("不能自己跟随自己");
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		if(value==null||value.length()==0)throw new Exception("参数value不能为空");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		//查看用户与此牛人是否有跟随关系
		StringBuffer query = new StringBuffer("select d from Documentary d where d.state = 0 and d.fuserinfoid = ").append(userid).append(" and d.ffollowuserinfoid = ").append(exampleid);
		List<Object> querylst = databaseHelper.getResultListByHql(query.toString());
		
		if(querylst.size()>0)throw new Exception("你已跟随此人，请不要重复操作");
		
		
		int int_type = Integer.parseInt(type);//1金额 2手数 3比例
		
		if(int_type==0&&ui.getVirtualcapital()<Double.parseDouble(value))throw new Exception("你的虚拟资金不足");
		
		//跟随牛人买入相同比例的股票和外汇
		getexamplewarehouseofnew(exampleid, ui, sd, int_type,Double.parseDouble(value));
			
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "操作成功");
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}
	
	
	//新增跟单时 跟随牛人买入相同比例的股票和外汇
	private void getexamplewarehouseofnew(String exampleid,UserInfo ui,SyncDemo_股票行情 sd,int int_type,Double dv) throws Exception{
		UserInfo example = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(exampleid));
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		Double total = 0d;//跟随的牛人所有持仓的总金额
		//判断牛人的类型
		if(example.getTag()<2){
			//找出牛人持仓的股票
			StringBuffer find = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 0 and swh.fuserinfoid = ").append(Long.parseLong(exampleid));
			List<Object> lst = databaseHelper.getResultListByHql(find.toString());
			
			Double totalshars = 0d;
			for (Object object : lst) {
				SharesWareHouse swh = (SharesWareHouse)object;
				
				Double nowPrice = getpriceofshares(swh, sd);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", swh.getCode());
				map.put("sharesname", swh.getSharesname());
				map.put("shareid", swh.getFsharesid());
				map.put("type", swh.getType());
				map.put("nowPrice", nowPrice);
				map.put("marketvalue", nowPrice*swh.getCouldusenums());
				map.put("couldusenums", swh.getCouldusenums());
				
				totalshars += nowPrice*swh.getCouldusenums();
				
				swh.setPrice(nowPrice);
				swh.setMarketvalue(nowPrice*swh.getCouldusenums());
				databaseHelper.updateObject(swh);
				
				lstMap.add(map);
			}
			
			total = totalshars;
		}else{
			//找出牛人持仓的外汇
			StringBuffer find = new StringBuffer("select fe from ForeignExchange fe where fe.state = 0 and fe.fuserinfoid = ").append(Long.parseLong(exampleid));
			List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
			Double totalwh = 0d;
			for (Object object : lst) {
				ForeignExchange fe = (ForeignExchange)object;
				
				Double nowPrice = getpriceofwh(fe);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", fe.getCode());
				map.put("sharesname", fe.getForeignexchangename());
				map.put("shareid", fe.getFsharesid());
				map.put("type", 2);
				map.put("nowPrice", nowPrice);
				map.put("marketvalue", nowPrice*fe.getCouldusenums());
				map.put("couldusenums", fe.getCouldusenums());
				
				totalwh += nowPrice*fe.getCouldusenums();
				
				fe.setPrice(nowPrice);
				databaseHelper.updateObject(fe);
				
				lstMap.add(map);
			}
			total = totalwh;
		}
		
		
		Double rate = 0d;
		if(int_type==1){
			//计算用户与牛人的资金比例
			rate = ConstantUtil.formatDouble(dv/total);
		}else if(int_type==3){
			rate = dv;
		}
		
		Double needamount = 0d;//此次跟单用户所需金额
		
		List<Map<String, Object>> newListMap = new ArrayList<Map<String,Object>>(); 
		
		for (Map<String, Object> map : lstMap) {
			Double marketvalue = Double.parseDouble(map.get("marketvalue").toString());
			Double nowPrice = Double.parseDouble(map.get("nowPrice").toString());//当前价
			int couldusenums = Integer.parseInt(map.get("couldusenums").toString());//数量
			
			int userscouldusernums =rate==0d?(int)Double.parseDouble(dv.toString()):(int)(couldusenums*rate);//用户手数
			
			Double cost = nowPrice*userscouldusernums;//成本
			
			int usenums = (int)(cost/nowPrice);
			
			//diffamount += ((cost/nowPrice) - (int)(cost/nowPrice))*nowPrice;
			needamount += cost;
			
			map.put("cost",cost);
			map.put("usenums",userscouldusernums);
			map.put("nowPrice",nowPrice);
			
			newListMap.add(map);
		}
		
		//检查用户资金是否足够
		if(ui.getVirtualcapital()<needamount)throw new Exception("你的虚拟资金不足");
		
		//添加到跟单表
		Documentary d = new Documentary();
		d.setActualyamount(needamount);
		d.setCreatetime(new Date());
		d.setFfollowuserinfoid(Long.parseLong(exampleid));
		d.setFuserinfoid(ui.getId());
		d.setDiffamount(int_type==1?(dv-needamount):0d);
		d.setMoney(int_type==1?(int)Double.parseDouble(dv.toString()):(int)Double.parseDouble(needamount.toString()));
		d.setState(0);
		d.setTag(example.getTag());
		d.setUservalues(rate==0d?Double.parseDouble(dv.toString()):rate);
		d.setType(int_type);
		databaseHelper.persistObject(d);
		
		
		//更新用户的虚拟资金
		ui.setVirtualcapital(ui.getVirtualcapital()-needamount);
		databaseHelper.updateObject(ui);
		
		for (Map<String, Object> map : newListMap) {
			int type = Integer.parseInt(map.get("type").toString());
			Double cost = Double.parseDouble(map.get("cost").toString());
			int usenums = Integer.parseInt(map.get("usenums").toString());
			Double nowPrice = Double.parseDouble(map.get("nowPrice").toString());
			
			if(type<2){
				//添加到用户股票表
				SharesWareHouse swh = new SharesWareHouse();
				swh.setCode(map.get("code").toString());
				swh.setCost(cost);
				swh.setCouldusenums(usenums);
				swh.setFexampleid(Long.parseLong(exampleid));
				swh.setFsharesid(Long.parseLong(map.get("shareid").toString()));
				swh.setFshareswarehouseid(0l);
				swh.setFuserinfoid(ui.getId());
				swh.setIsplan(0);
				swh.setMarketvalue(nowPrice*usenums);
				swh.setPrice(nowPrice);
				swh.setProfitloss(0d);
				swh.setSharesname(map.get("sharesname").toString());
				swh.setState(1);
				swh.setTime(new Date());
				swh.setType(Integer.parseInt(map.get("type").toString()));
				swh.setWarehousenums(usenums);
				swh.setFdocumentaryid(d.getId());
				databaseHelper.persistObject(swh);
				
				//添加到资金明细表
				CapitalDetail cd = new CapitalDetail();
				cd.setFuserinfoid(ui.getId());
				cd.setType(swh.getType()==0?-1:-3);
				cd.setCapital(swh.getMarketvalue());
				cd.setCreatetime(new Date());
				cd.setState(0);
				databaseHelper.persistObject(cd);
				
			}else{
				//添加到外汇表
				ForeignExchange fe = new ForeignExchange();
				fe.setCode(map.get("code").toString());
				fe.setCouldusenums(usenums);
				fe.setFexampleid(Long.parseLong(exampleid));
				fe.setFsharesid(Long.parseLong(map.get("shareid").toString()));
				fe.setFforeignexchangeid(0l);
				fe.setFuserinfoid(ui.getId());
				fe.setIsplan(0);
				fe.setForeignexchangename(map.get("sharesname").toString());
				fe.setPrice(nowPrice);
				fe.setProfitloss(0d);
				fe.setState(1);
				fe.setTime(new Date());
				fe.setSellout(0d);
				fe.setPurchase(0d);
				fe.setWarehousenums(usenums);
				fe.setFexampleplanid(0l);
				fe.setFdocumentaryid(d.getId());
				databaseHelper.persistObject(fe);
				
				//添加到资金明细表
				CapitalDetail cd = new CapitalDetail();
				cd.setFuserinfoid(ui.getId());
				cd.setType(-2);
				cd.setCapital(fe.getSellout());
				cd.setCreatetime(new Date());
				cd.setState(0);
				databaseHelper.persistObject(cd);
			}
		}
		
		if(needamount>0d){
			//添加到资金明细表
			CapitalDetail cd = new CapitalDetail();
			cd.setFuserinfoid(ui.getId());
			cd.setType(-5);
			cd.setCapital(needamount);
			cd.setCreatetime(new Date());
			cd.setState(0);
			databaseHelper.persistObject(cd);
		}
	}
	
	
	//找出用户的与此牛人的跟单股票和外汇
	private Double checkuserwarehouse(String exampleid,String userid,SyncDemo_股票行情 sd,Double ta) throws Exception{
		//找出用户的与此牛人的跟单股票，全部卖出
		StringBuffer finddocumentary = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 1 and swh.fuserinfoid = ").append(userid)
				.append(" and swh.fexampleid =").append(exampleid).append(" and swh.couldusenums > 0");
		List<Object> doclst = databaseHelper.getResultListByHql(finddocumentary.toString());
		for (Object object : doclst) {
			SharesWareHouse swh = (SharesWareHouse)object;
			
			Double nowPrice = getpriceofshares(swh, sd);
			//将此股票的持仓平掉
			swh.setCouldusenums(0);
			databaseHelper.updateObject(swh);
			
			//新增一条卖出记录
			SharesWareHouse newswh = swh;
			newswh.setId(null);
			newswh.setCouldusenums(swh.getWarehousenums());
			newswh.setWarehousenums(swh.getWarehousenums());
			newswh.setFshareswarehouseid(swh.getId());
			newswh.setMarketvalue(nowPrice*swh.getWarehousenums());
			newswh.setPrice(nowPrice);
			newswh.setProfitloss(newswh.getMarketvalue()-swh.getCost());
			newswh.setState(-1);
			newswh.setTime(new Date());
			databaseHelper.persistObject(newswh);
			
			//添加到资金明细表
			CapitalDetail cd = new CapitalDetail();
			cd.setFuserinfoid(Long.parseLong(userid));
			cd.setType(swh.getType()==0?1:3);
			cd.setCapital(newswh.getMarketvalue());
			cd.setCreatetime(new Date());
			cd.setState(0);
			databaseHelper.persistObject(cd);
			
			//累加市值
			ta+=newswh.getMarketvalue();
		}
		
		
		//找出用户的与此牛人的跟单外汇，全部卖出
		finddocumentary = new StringBuffer("select fe from ForeignExchange fe where fe.state = 1 and swh.fuserinfoid = ").append(userid)
				.append(" and swh.fexampleid =").append(exampleid).append(" and swh.couldusenums > 0");
		doclst = databaseHelper.getResultListByHql(finddocumentary.toString());
		for (Object object : doclst) {
			ForeignExchange fe = (ForeignExchange)object;
			
			Double nowPrice = getpriceofwh(fe);
			//将此外汇的持仓平掉
			fe.setCouldusenums(0);
			databaseHelper.updateObject(fe);
			
			//新增一条卖出记录
			ForeignExchange newfe = fe;
			newfe.setId(null);
			newfe.setCouldusenums(fe.getWarehousenums());
			newfe.setWarehousenums(fe.getWarehousenums());
			newfe.setFforeignexchangeid(fe.getId());
			//newfe.setMarketvalue(nowPrice*fe.getWarehousenums());
			newfe.setPrice(nowPrice);
			newfe.setProfitloss(nowPrice*fe.getWarehousenums()-fe.getPurchase());
			newfe.setSellout(nowPrice*fe.getWarehousenums());
			newfe.setState(-1);
			newfe.setTime(new Date());
			databaseHelper.persistObject(newfe);
			
			//添加到资金明细表
			CapitalDetail cd = new CapitalDetail();
			cd.setFuserinfoid(Long.parseLong(userid));
			cd.setType(2);
			cd.setCapital(newfe.getSellout());
			cd.setCreatetime(new Date());
			cd.setState(0);
			databaseHelper.persistObject(cd);
			
			//累加市值
			ta+=newfe.getSellout();
		}
				
		return ta;
	}
	
	
	private Double getexamplewarehouse(String exampleid,String userid,SyncDemo_股票行情 sd,Double d_amount) throws Exception{
		//找出牛人持仓的股票
		StringBuffer find = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 0 and swh.fuserinfoid = ").append(Long.parseLong(exampleid));
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		Double totalshars = 0d;
		for (Object object : lst) {
			SharesWareHouse swh = (SharesWareHouse)object;
			
			Double nowPrice = getpriceofshares(swh, sd);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", swh.getCode());
			map.put("sharesname", swh.getSharesname());
			map.put("shareid", swh.getFsharesid());
			map.put("type", swh.getType());
			map.put("nowPrice", nowPrice);
			map.put("marketvalue", nowPrice*swh.getCouldusenums());
			
			totalshars += nowPrice*swh.getCouldusenums();
			
			swh.setPrice(nowPrice);
			swh.setMarketvalue(nowPrice*swh.getCouldusenums());
			databaseHelper.updateObject(swh);
			
			lstMap.add(map);
		}
		
		//找出牛人持仓的外汇
		find = new StringBuffer("select fe from ForeignExchange fe where fe.state = 0 and fe.fuserinfoid = ").append(Long.parseLong(exampleid));
		lst = databaseHelper.getResultListByHql(find.toString());
		
		Double totalwh = 0d;
		for (Object object : lst) {
			ForeignExchange fe = (ForeignExchange)object;
			
			Double nowPrice = getpriceofwh(fe);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", fe.getCode());
			map.put("sharesname", fe.getForeignexchangename());
			map.put("shareid", fe.getFsharesid());
			map.put("type", 2);
			map.put("nowPrice", nowPrice);
			map.put("marketvalue", nowPrice*fe.getCouldusenums());
			
			totalwh += nowPrice*fe.getCouldusenums();
			
			fe.setPrice(nowPrice);
			databaseHelper.updateObject(fe);
			
			lstMap.add(map);
		}


		Double total = totalshars+totalwh;//跟随的牛人所有持仓的总金额
		Double diffamount = 0d;//多余的金额
		for (Map<String, Object> map : lstMap) {
			Double marketvalue = Double.parseDouble(map.get("marketvalue").toString());
			Double nowPrice = Double.parseDouble(map.get("nowPrice").toString());//当前价
			Double cost = marketvalue/total*d_amount;//成本
			
			int usenums = (int)(cost/nowPrice);
			
			diffamount += ((cost/nowPrice) - (int)(cost/nowPrice))*nowPrice;
			
			int type = Integer.parseInt(map.get("type").toString());
			
			if(type<2){
				//添加到用户股票表
				SharesWareHouse swh = new SharesWareHouse();
				swh.setCode(map.get("code").toString());
				swh.setCost(cost);
				swh.setCouldusenums(usenums);
				swh.setFexampleid(Long.parseLong(exampleid));
				swh.setFsharesid(Long.parseLong(map.get("shareid").toString()));
				swh.setFshareswarehouseid(0l);
				swh.setFuserinfoid(Long.parseLong(userid));
				swh.setIsplan(0);
				swh.setMarketvalue(nowPrice*usenums);
				swh.setPrice(nowPrice);
				swh.setProfitloss(0d);
				swh.setSharesname(map.get("sharesname").toString());
				swh.setState(1);
				swh.setTime(new Date());
				swh.setType(Integer.parseInt(map.get("type").toString()));
				swh.setWarehousenums(usenums);
				databaseHelper.persistObject(swh);
				
				//添加到资金明细表
				CapitalDetail cd = new CapitalDetail();
				cd.setFuserinfoid(Long.parseLong(userid));
				cd.setType(swh.getType()==0?-1:-3);
				cd.setCapital(swh.getMarketvalue());
				cd.setCreatetime(new Date());
				cd.setState(0);
				databaseHelper.persistObject(cd);
				
			}else{
				//添加到外汇表
				ForeignExchange fe = new ForeignExchange();
				fe.setCode(map.get("code").toString());
				fe.setCouldusenums(usenums);
				fe.setFexampleid(Long.parseLong(exampleid));
				fe.setFsharesid(Long.parseLong(map.get("shareid").toString()));
				fe.setFforeignexchangeid(0l);
				fe.setFuserinfoid(Long.parseLong(userid));
				fe.setIsplan(0);
				fe.setForeignexchangename(map.get("sharesname").toString());
				fe.setPrice(nowPrice);
				fe.setProfitloss(0d);
				fe.setState(1);
				fe.setTime(new Date());
				fe.setSellout(0d);
				fe.setPurchase(0d);
				fe.setWarehousenums(usenums);
				fe.setFexampleplanid(0l);
				databaseHelper.persistObject(fe);
				
				//添加到资金明细表
				CapitalDetail cd = new CapitalDetail();
				cd.setFuserinfoid(Long.parseLong(userid));
				cd.setType(-2);
				cd.setCapital(fe.getSellout());
				cd.setCreatetime(new Date());
				cd.setState(0);
				databaseHelper.persistObject(cd);
			}
		}
		
		return diffamount;
	}
	
	//获取股票当前市价
	private Double getpriceofshares(SharesWareHouse swh,SyncDemo_股票行情 sd) throws Exception{
		Double nowPrice = 0d;
		if(swh.getType()==0){//沪深
			
			ApiResponse response = sd.股票行情(swh.getCode(), "0", "0");
			
			String result = new String(response.getBody(), "utf-8");
			
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			if(json.get("showapi_res_code").getAsInt()==0){
			
				JsonObject jo = json.get("showapi_res_body").getAsJsonObject().get("stockMarket").getAsJsonObject();
			
				nowPrice = jo.get("nowPrice").getAsDouble();
			}
			
		}else{//美股
			
			/*String result = NowApiUtil.getShareIndexOfUS(swh.getCode());
			
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			if(json.get("success").getAsInt()==1){
			
				JsonObject jo = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(swh.getCode()).getAsJsonObject();
			
				nowPrice = jo.get("last_price").getAsDouble();
			}*/
			
			Shares s = (Shares)databaseHelper.getObjectById(Shares.class, swh.getFsharesid());
			
			String price = JuheData.getnowprice(s.getPinyin());
			
			nowPrice = Double.parseDouble(price);
		}
		
		return nowPrice;
	}
	
	//获取外汇当前汇率
	private Double getpriceofwh(ForeignExchange fe) throws Exception{
		Double nowPrice = 0d;
		
		/*String result = NowApiUtil.getWHIndex(fe.getCode());
		
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		if(json.get("success").getAsInt()==1){
		
			JsonObject jo = json.get("result").getAsJsonObject();
		
			nowPrice = jo.get("rate").getAsDouble();
		}*/
		
		/*String result = NowApiUtil.getWHIndexByAli(fe.getCode());
		
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		if("ok".equals(json.get("msg").getAsString())){
		
			JsonObject jo = json.get("result").getAsJsonObject();
		
			nowPrice = jo.get("rate").getAsDouble();
		}*/
		nowPrice = Double.parseDouble(JuheData.getnowprice(fe.getCode()));
		return nowPrice;
	}
	
	public static void main(String[] args) {
		/*Double d = 32103d;
		Double s = 32.19d;
		Sys.out(d/s);
		Sys.out((int)(d/s));
		Sys.out(d/s-(int)(d/s));*/
		/*Long a = 128l;
		Long b = 128l;
		Sys.out(a==b);*/
			String d = "5800.0";
			System.out.println((int)Double.parseDouble(d));
	}

	@Override
	public String plandetail(String userid, String exampleid, String planid) throws Exception {
		// TODO Auto-generated method stub
		ExamplePlan ep = (ExamplePlan)databaseHelper.getObjectById(ExamplePlan.class, Long.parseLong(planid));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("planid",ep.getId());
		map.put("planname",ep.getPlanname());
		map.put("plandesc",ep.getPlandesc());
		map.put("targetprofit",ep.getTargetprofit());
		map.put("stopline",ep.getStopline());
		map.put("actualprofit",ep.getActualprofit()==null?0d:ep.getActualprofit());
		
		
		map.put("state", ep.getState());
		map.put("type", ConstantUtil.type[ep.getType()]);
		map.put("planstate", ConstantUtil.planstate[ep.getState()]);//0抢购 1运行中 2已结束
		
		map = staticofplan(map, ep);
		
		Map<String, Object> m = statisticexampleplan(userid, exampleid);
		
		map.put("profitrate",m.get("profitrate").toString());
		map.put("successnums",m.get("successnums").toString());
		
		map.put("subscriptiongold",ConstantUtil.setting.getSubscriptiongold());//订阅牛人所需金币（单月）
		map.put("purchasegold",ConstantUtil.setting.getPurchasegold());//抢购计划所需金币
		map.put("viewgold",ConstantUtil.setting.getViewgold());//观摩计划所需金币
		
		StringBuffer query = new StringBuffer("select pb from PanicBuying pb where pb.fexampleplanid =").append(planid);
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		int purchase=0,view=0;
		for (Object object : lst) {
			PanicBuying pb = (PanicBuying)object;
			if(pb.getState()==0)purchase++;
			else view++;
		}
		
		map.put("purchase",purchase);//抢购计划人数
		map.put("getpurchasegolds",purchase*ConstantUtil.setting.getPurchasegold());//抢购计划所得金币
		
		//按计划抢购数量排序
		StringBuffer orderby = new StringBuffer("select fexampleplanid,count(fexampleplanid) as c from panicbuying where state = 0 Group by fexampleplanid order by c desc");
		List<Object[]> orderbylst = databaseHelper.getResultListBySql(orderby.toString());
		int i= 0;
		for (Object[] objects : orderbylst) {
			i++;
			if(ep.getId().equals(Long.parseLong(objects[0].toString())))break;
		}
		map.put("purchaseranking",i);
		
		map.put("view",view);//观摩计划人数
		map.put("getviewgolds",view*ConstantUtil.setting.getViewgold());//观摩计划所得金币
		
		//按计划数量排序
		orderby = new StringBuffer("select fexampleplanid,count(fexampleplanid) as c from panicbuying where state = 1 Group by fexampleplanid order by c desc");
		orderbylst = databaseHelper.getResultListBySql(orderby.toString());
		int j= 0;
		for (Object[] objects : orderbylst) {
			j++;
			if(ep.getId().equals(Long.parseLong(objects[0].toString())))break;
		}
		map.put("viewranking",j);
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Override
	public String planwarehouse(String userid, String exampleid, String planid,int rows) throws Exception {
		// TODO Auto-generated method stub
		ExamplePlan ep = (ExamplePlan)databaseHelper.getObjectById(ExamplePlan.class, Long.parseLong(planid));
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		
		if(ep.getType()<2){
			//股票
			StringBuffer get = new StringBuffer("select sum(swh.couldusenums) from SharesWareHouse swh where swh.isplan = 1 and swh.fexampleplanid = ")
					.append(ep.getId()).append(" and swh.state = 0 and swh.couldusenums > 0 ");
			List<Object> getlst = databaseHelper.getResultListByHql(get.toString());
			
			int totalwarehouse = getlst==null?0:getlst.size()==0?0:getlst.get(0)==null?0:Integer.parseInt(getlst.get(0).toString());
			
			StringBuffer query = new StringBuffer("select swh from SharesWareHouse swh where swh.isplan = 1 and swh.fexampleplanid = ")
					.append(ep.getId()).append(" and swh.state = 0 and swh.couldusenums > 0 ");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(),1,rows);
			
			for (Object object : lst) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				SharesWareHouse swh = (SharesWareHouse)object;
				
				map.put("sharename", swh.getSharesname());
				map.put("code", swh.getCode());
				map.put("market", swh.getMarket());
				map.put("cost", swh.getCost());
				
				map.put("warehouse", ConstantUtil.intdevice2(swh.getCouldusenums(), totalwarehouse, true)+"%");
				
				map.put("profitloss", swh.getProfitloss());
				map.put("profitlossrate", ConstantUtil.formatDouble(swh.getProfitloss()/swh.getCost())+"%");
				
				map.put("type", swh.getType()==0?"沪深":"美股");
				
				lstMap.add(map);
			}
			
		}else{
			//外汇
			StringBuffer get = new StringBuffer("select sum(fe.couldusenums) from ForeignExchange fe where fe.isplan = 1 and fe.fexampleplanid = ")
					.append(ep.getId()).append(" and fe.state = 0 and fe.couldusenums > 0 ");
			List<Object> getlst = databaseHelper.getResultListByHql(get.toString());
			
			int totalwarehouse = getlst==null?0:getlst.size()==0?0:getlst.get(0)==null?0:Integer.parseInt(getlst.get(0).toString());
			
			StringBuffer query = new StringBuffer("select fe from ForeignExchange fe where fe.isplan = 1 and fe.fexampleplanid = ")
					.append(ep.getId()).append(" and fe.state = 0 and fe.couldusenums > 0 ");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(),1,rows);
			
			for (Object object : lst) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				ForeignExchange fe = (ForeignExchange)object;
				
				map.put("sharename", fe.getForeignexchangename());
				map.put("code", fe.getCode());
				map.put("market", "");
				map.put("cost", fe.getPurchase());
				
				map.put("warehouse", ConstantUtil.intdevice2(fe.getCouldusenums(), totalwarehouse, true)+"%");
				
				map.put("profitloss", fe.getProfitloss());
				map.put("profitlossrate", ConstantUtil.formatDouble(fe.getProfitloss()/fe.getPurchase())+"%");
				
				map.put("type", "外汇");
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",arr.size());
		obj.add("rows", arr);
		
		return obj.toString();
	}

	@Override
	public String planofnew(String userid, String exampleid, String planid,String tag,int rows) throws Exception {
		// TODO Auto-generated method stub
		ExamplePlan ep = (ExamplePlan)databaseHelper.getObjectById(ExamplePlan.class, Long.parseLong(planid));
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		
		if(ep.getType()<2){
			//股票
			StringBuffer get = new StringBuffer("select sum(swh.couldusenums) from SharesWareHouse swh where swh.isplan = 1 and swh.fexampleplanid = ")
					.append(ep.getId()).append(" and swh.couldusenums > 0 ");
			if(tag!=null){
				get.append(" and swh.state = ").append(tag);
			}
			List<Object> getlst = databaseHelper.getResultListByHql(get.toString());
			
			int totalwarehouse = getlst==null?0:getlst.size()==0?0:getlst.get(0)==null?0:Integer.parseInt(getlst.get(0).toString());
			
			StringBuffer query = new StringBuffer("select swh from SharesWareHouse swh where swh.isplan = 1 and swh.fexampleplanid = ")
					.append(ep.getId()).append(" and swh.couldusenums > 0 order by swh.time desc");
			if(tag!=null){
				get.append(" and swh.state = ").append(tag);
			}
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(),1,rows);
			
			for (Object object : lst) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				SharesWareHouse swh = (SharesWareHouse)object;
				
				map.put("sharename", swh.getSharesname());
				map.put("code", swh.getCode());
				map.put("market", swh.getMarket());
				map.put("price", swh.getPrice());
				
				map.put("warehouse", ConstantUtil.intdevice2(swh.getCouldusenums(), totalwarehouse, true)+"%");
				
				map.put("warehousenums", swh.getCouldusenums());
				map.put("usenums", swh.getWarehousenums());
				map.put("time", swh.getTime().toString());
				
				map.put("type", swh.getType()==0?"沪深":"美股");
				map.put("tag", swh.getState());
				
				lstMap.add(map);
			}
			
		}else{
			//外汇
			StringBuffer get = new StringBuffer("select sum(fe.couldusenums) from ForeignExchange fe where fe.isplan = 1 and fe.fexampleplanid = ")
					.append(ep.getId()).append(" and fe.couldusenums > 0 ");
			if(tag!=null){
				get.append(" and fe.state = ").append(tag);
			}
			
			List<Object> getlst = databaseHelper.getResultListByHql(get.toString());
			
			int totalwarehouse = getlst==null?0:getlst.size()==0?0:getlst.get(0)==null?0:Integer.parseInt(getlst.get(0).toString());
			
			StringBuffer query = new StringBuffer("select fe from ForeignExchange fe where fe.isplan = 1 and fe.fexampleplanid = ")
					.append(ep.getId()).append(" and fe.couldusenums > 0 order by fe.time desc ");
			if(tag!=null){
				get.append(" and fe.state = ").append(tag);
			}
			
			List<Object> lst = databaseHelper.getResultListByHql(query.toString(),1,rows);
			
			for (Object object : lst) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				ForeignExchange fe = (ForeignExchange)object;
				
				map.put("sharename", fe.getForeignexchangename());
				map.put("code", fe.getCode());
				map.put("market", "");
				map.put("price", fe.getPrice());
				
				map.put("warehouse", ConstantUtil.intdevice2(fe.getCouldusenums(), totalwarehouse, true)+"%");
				map.put("warehousenums", fe.getCouldusenums());
				map.put("usenums", fe.getWarehousenums());
				map.put("time", fe.getTime().toString());
				
				map.put("type", "外汇");
				map.put("tag", fe.getState());
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",arr.size());
		obj.add("rows", arr);
		
		return obj.toString();
	}

	@Override
	public String watchplan(String userid, String exampleid, String planid, String amount) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(planid==null||planid.length()==0)throw new Exception("参数planid不能为空");
		if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
		
		Date now = new Date();
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		ExamplePlan ep = (ExamplePlan)databaseHelper.getObjectById(ExamplePlan.class, Long.parseLong(planid));
		
		//检查计划是否开始运行
		if(ep.getState()==0)throw new Exception("计划还没开始运行，不能观摩");
		
		//检查用户是否观摩过此计划
		StringBuffer check = new StringBuffer("select pb from PanicBuying pb where pb.fuserinfoid = ").append(userid).append(" and pb.fexampleplanid =").append(planid);
		List<Object> lst = databaseHelper.getResultListByHql(check.toString());
		
		if(lst.size()>0){
			for (Object object : lst) {
				PanicBuying pb =  (PanicBuying)object;
				if(pb.getState()==0)throw new Exception("你已抢购了此计划，不需要再观摩");
				else throw new Exception("你已抢观摩了此计划，不要重复观摩");
			}
		}
		
		//检查用户金币是否足够
		if(ui.getGolds()<ConstantUtil.setting.getViewgold())throw new Exception("你的金币不足，请先充值");
		
		//添加到金币明细表
		GoldsDetail gd = new GoldsDetail();
		gd.setCreatetime(now);
		gd.setFuserinfoid(ui.getId());
		gd.setGolds(Double.parseDouble(amount));
		gd.setState(0);
		gd.setType(-4);
		databaseHelper.persistObject(gd);
		
		//添加到抢购表
		PanicBuying pb = new PanicBuying();
		pb.setAmount(Double.parseDouble(amount));
		pb.setCreatetime(new Date());
		pb.setFexampleplanid(ep.getId());
		pb.setFgoldsdetailid(gd.getId());
		pb.setFuserinfoid(ui.getId());
		pb.setState(1);
		databaseHelper.persistObject(pb);
		
		//扣除用户金币
		ui.setGolds(ui.getGolds()-Double.parseDouble(amount));
		databaseHelper.updateObject(ui);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "观摩成功");
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Override
	public String panicbuying(String userid, String exampleid, String planid, String amount) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(planid==null||planid.length()==0)throw new Exception("参数planid不能为空");
		if(amount==null||amount.length()==0)throw new Exception("参数amount不能为空");
		
		Date now = new Date();
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		ExamplePlan ep = (ExamplePlan)databaseHelper.getObjectById(ExamplePlan.class, Long.parseLong(planid));
		
		//检查计划是否开始运行
		if(ep.getState()>0)throw new Exception("计划已运行，不能抢购");
		
		//检查用户是否抢购过此计划
		StringBuffer check = new StringBuffer("select pb from PanicBuying pb where pb.fuserinfoid = ").append(userid).append(" and pb.fexampleplanid =").append(planid);
		List<Object> lst = databaseHelper.getResultListByHql(check.toString());
		
		if(lst.size()>0){
			for (Object object : lst) {
				PanicBuying pb =  (PanicBuying)object;
				if(pb.getState()==0)throw new Exception("你已抢购了此计划，不要重复抢购");
			}
		}
		
		//检查用户金币是否足够
		if(ui.getGolds()<ConstantUtil.setting.getPurchasegold())throw new Exception("你的金币不足，请先充值");
		
		//添加到金币明细表
		GoldsDetail gd = new GoldsDetail();
		gd.setCreatetime(now);
		gd.setFuserinfoid(ui.getId());
		gd.setGolds(Double.parseDouble(amount));
		gd.setState(0);
		gd.setType(-3);
		databaseHelper.persistObject(gd);
		
		//添加到抢购表
		PanicBuying pb = new PanicBuying();
		pb.setAmount(Double.parseDouble(amount));
		pb.setCreatetime(new Date());
		pb.setFexampleplanid(ep.getId());
		pb.setFgoldsdetailid(gd.getId());
		pb.setFuserinfoid(ui.getId());
		pb.setState(0);
		databaseHelper.persistObject(pb);
		
		//扣除用户金币
		ui.setGolds(ui.getGolds()-Double.parseDouble(amount));
		databaseHelper.updateObject(ui);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "抢购成功");
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Override
	public String publishplan(ExamplePlan ep) throws Exception {
		// TODO Auto-generated method stub
		if(ep.getFuserinfoid()==null)throw new Exception("参数userid不能为空");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, ep.getFuserinfoid());
		
		if(ui.getIsexample()==0)throw new Exception("你不是牛人，不能发布计划");
		
		//检查用户是否有计划
		StringBuffer check = new StringBuffer("select ep from ExamplePlan ep where ep.state < 2 and ep.fuserinfoid = ").append(ui.getIntro()).append(" order by ep.endtime");
		List<Object> lst = databaseHelper.getResultListByHql(check.toString());
		
		if(lst.size()>0){
			ExamplePlan eplan = (ExamplePlan)lst.get(0);
			
			//检查计划开始时间是否在往期最新计划结束时间的三天后
			
			if(ConstantUtil.differentDays(eplan.getEndtime(), ep.getStarttime())<=3)throw new Exception("计划开始时间必须在你最新计划结束时间的三天后");
			
		}
		
		ep.setActualprofit(0d);
		ep.setCreatetime(new Date());
		ep.setIschoiceness(0);
		ep.setState(0);
		ep.setSuccessed(0);
		
		databaseHelper.persistObject(ep);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("msg", "success");
		map.put("info", "计划发布成功");
		
		String json = JsonUtil.getGson().toJson(map);
		return json;
	}

	@Override
	public String profitstatistic(String userid, String exampleid, int tag) throws Exception {
		// TODO Auto-generated method stub
		
    	StringBuffer find = new StringBuffer("select upt from UserProfit upt where upt.fuserinfoid = ").append(userid).append(" order by upt.time desc");
    	List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		userid = exampleid==null?userid:exampleid;
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		
        StringBuffer query = null;
        if(tag==1){
        	for (int i = 0; i <= 30; i++) {
        		if(i==0||i==30||i%8==0){
        			
        			Map<String,Object> map = timeindexofday(query, userid, now, i, sdf);
        	        
        	        lstMap.add(map);
        		}
			}
        }else if(tag==2){
        	for (int i = 0; i <= 5; i++) {
        		if(i==0||i==5||i%2==0){
        			
        			Map<String,Object> map = timeindexofmonth(query, userid, now, i, sdf);
        	        
        	        lstMap.add(map);
        		}
			}
        }else if(tag==3){
        	for (int i = 0; i <= 11; i++) {
        		if(i==0||i==11||i%4==0){
        			
        			Map<String,Object> map = timeindexofmonth(query, userid, now, i, sdf);
        	        
        	        lstMap.add(map);
        		}
			}
        }else{
        	
        	
        	if(lst.size()>0){
        		
        		UserProfit lastupt = (UserProfit)lst.get(0);
        		UserProfit firstupt = (UserProfit)lst.get(lst.size()-1);
        		
        		//检查两条数据的时间差
        		int diff = ConstantUtil.differentDays(firstupt.getTime(), lastupt.getTime());
        		if(diff<=3){
        			for (int i = 0; i <= 3; i++) {
        				Map<String,Object> map = timeindexofday(query, userid, now, i, sdf);
            	        
            	        lstMap.add(map);
					}
        			
        		}else if(diff<=180){
        			
        		}
        	}
        }
        
        String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.add("rows", arr);
		obj.addProperty("userprofit", lst.size()==0?0:ConstantUtil.formatDouble(((UserProfit)lst.get(0)).getProfitrate()));//用户收益
		obj.addProperty("stockindexprofit", lst.size()==0?0:ConstantUtil.formatDouble(((UserProfit)lst.get(0)).getProfitrate()));//大盘收益  
		return obj.toString();
	}
	
	private Map<String, Object> timeindexofday(StringBuffer query,String userid,Date now,int i,SimpleDateFormat sdf){
		Map<String,Object> map = new HashMap<String,Object>();
		
		query = new StringBuffer("select sum(upt.diffprofitoflasttimerate) 'a' from userprofit upt where upt.fuserinfoid = ").append(userid)
			.append(" and TO_DAYS(NOW()) - TO_DAYS(upt.time) = ").append(i); 
		
		List<Object> lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:ConstantUtil.formatDouble(Double.parseDouble(lst.get(0).toString()));
		
		Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, -i);
        
        Date time = c.getTime();
        
        map.put("profit", profit);
        map.put("stockindex", i);
        map.put("time", sdf.format(time));
        return map;
	}
	
	private Map<String, Object> timeindexofmonth(StringBuffer query,String userid,Date now,int i,SimpleDateFormat sdf){
		Map<String,Object> map = new HashMap<String,Object>();
		
		query = new StringBuffer("select sum(upt.diffprofitoflasttimerate) 'a' from userprofit upt where upt.fuserinfoid = ").append(userid)
			.append(" and PERIOD_DIFF( date_format( now() , '%Y%m' ) , date_format(upt.time, '%Y%m' ) ) = ").append(i); 
		
		List<Object> lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:ConstantUtil.formatDouble(Double.parseDouble(lst.get(0).toString()));
		
		Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -i);
        
        Date time = c.getTime();
        
        map.put("profit", profit);
        map.put("stockindex", i);
        map.put("time", sdf.format(time));
        
        return map;
	}

	
	private Map<String, Object> timeindexofhour(StringBuffer query,String userid,Date now,int i,SimpleDateFormat sdf){
		Map<String,Object> map = new HashMap<String,Object>();
		
		query = new StringBuffer("select sum(upt.diffprofitoflasttimerate) 'a' from userprofit upt where upt.fuserinfoid = ").append(userid)
			.append(" and upt.time between date_sub(date_format(now(),'%Y-%m-%d %H:00:00'),interval ").append(i).append(" hour) and date_format(now(),'%Y-%m-%d %H:00:00')"); 
		
		List<Object> lst = databaseHelper.getResultListBySql(query.toString());
		
		Double profit = lst==null?0d:lst.size()==0?0d:lst.get(0)==null?0d:ConstantUtil.formatDouble(Double.parseDouble(lst.get(0).toString()));
		
		Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.HOUR_OF_DAY, -i);
        
        Date time = c.getTime();
        
        map.put("profit", profit);
        map.put("stockindex", i);
        map.put("time", sdf.format(time));
        
        return map;
	}
	
	@Override
	public String tradestatistic(String userid, String exampleid, int tag, int type) throws Exception {
		// TODO Auto-generated method stub
		userid = exampleid==null?userid:exampleid;
		
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		

    	StringBuffer find = new StringBuffer("select upt from UserProfit upt where upt.fuserinfoid = ").append(userid).append(" order by upt.time desc");
    	List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		userid = exampleid==null?userid:exampleid;
		
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>(); 
		
        StringBuffer query = null;
        if(tag==1){//日
        	for (int i = 0; i <= 23; i++) {
        		if(i==0||i==23||i%7==0){
        			
        			Map<String,Object> map = timeindexofhour(query, userid, now, i, sdf);
        	        
        	        lstMap.add(map);
        		}
			}
        }else if(tag==2){//周
        	for (int i = 0; i <= 6; i++) {
        		if(i==0||i==6||i%2==0){
        			
        			Map<String,Object> map = timeindexofday(query, userid, now, i, sdf);
        	        
        	        lstMap.add(map);
        		}
			}
        }else{
        	
        	
        	if(lst.size()>0){
        		
        		UserProfit lastupt = (UserProfit)lst.get(0);
        		UserProfit firstupt = (UserProfit)lst.get(lst.size()-1);
        		
        		//检查两条数据的时间差
        		int diff = ConstantUtil.differentDays(firstupt.getTime(), lastupt.getTime());
        		if(diff<=3){
        			for (int i = 0; i <= 3; i++) {
        				Map<String,Object> map = timeindexofday(query, userid, now, i, sdf);
            	        
            	        lstMap.add(map);
					}
        			
        		}else if(diff<=180){
        			
        		}
        	}
        }
        
        String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.add("rows", arr);
		obj.addProperty("profit", lst.size()==0?0:ConstantUtil.formatDouble(((UserProfit)lst.get(0)).getProfitrate()));//用户收益
		obj.addProperty("hsindex", lst.size()==0?0:ConstantUtil.formatDouble(((UserProfit)lst.get(0)).getProfitrate()));//沪深300收益  
		find = new StringBuffer("select ups from UserProfitStatistic ups where ups.fuserinfoid = ").append(u.getId());
		lst = databaseHelper.getResultListByHql(find.toString());
		if(lst.size()>0){
			UserProfitStatistic ups = (UserProfitStatistic)lst.get(0);
			obj.addProperty("userprofit",ups.getTotalprofit());	//个人收益
		}else{
			obj.addProperty("userprofit",0);	//个人收益
		}
		obj.addProperty("hsindexprofit",0);	//沪深300
		
		obj.addProperty("totalamount",u.getVirtualcapital());	//总资产
		
		StringBuffer xfind = new StringBuffer("select sum(upt.diffprofitoflasttime) 'a', sum(upt.diffprofitoflasttimerate) 'b' from userprofit upt where upt.fuserinfoid = ").append(u.getId())
				.append(" and TO_DAYS(NOW()) - TO_DAYS(upt.time) = 0"); 
		
		List<Object[]> xlst = databaseHelper.getResultListBySql(xfind.toString());
			
		Double todayreference = xlst==null?0d:xlst.size()==0?0d:xlst.get(0)[0]==null?0d:Double.parseDouble(xlst.get(0)[0].toString());
		Double todayreferencerate = xlst==null?0d:xlst.size()==0?0d:xlst.get(0)[1]==null?0d:Double.parseDouble(xlst.get(0)[1].toString());
		
		obj.addProperty("todayprofit",todayreference);	//今日收益
		
		//持仓--股票
		query = new StringBuffer("select sum(swh.couldusenums) 'a' from shareswarehouse swh where swh.state = 0 and swh.fuserinfoid = ").append(u.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		int shares = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
		
		query = new StringBuffer("select sum(fe.couldusenums) 'a' from foreignexchange fe where fe.state = 0 and fe.fuserinfoid = ").append(u.getId());
		lst = databaseHelper.getResultListBySql(query.toString());
		
		int foreign = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
		obj.addProperty("warehouse",shares+foreign);	//持仓仓位
		
		xfind = new StringBuffer("select sum(upt.diffprofitoflasttime) 'a', sum(upt.diffprofitoflasttimerate) 'b' from userprofit upt where upt.fuserinfoid = ").append(u.getId())
					.append(" and upt.tags = 1 and TO_DAYS(NOW()) - TO_DAYS(upt.time) = 0"); 
			
		xlst = databaseHelper.getResultListBySql(xfind.toString());
				
		todayreference = xlst==null?0d:xlst.size()==0?0d:xlst.get(0)[0]==null?0d:Double.parseDouble(xlst.get(0)[0].toString());
		obj.addProperty("profitloss",todayreference);	//持仓盈亏
		
		StringBuffer count = new StringBuffer("select count(cd.id) from CapitalDetail cd where (cd.type between -3 and 3 ) and cd.fuserinfoid = ").append(u.getId());
		
		List clst = databaseHelper.getResultListByHql(count.toString());
		int tradtimes =  Integer.parseInt(clst.get(0).toString());
		
		obj.addProperty("tradtimes",tradtimes);	//交易次数
		
		count = new StringBuffer("select count(cd.id) from CapitalDetail cd where (cd.type between -3 and 3 ) and cd.fuserinfoid = ").append(u.getId()).append(" and TO_DAYS(NOW()) - TO_DAYS(cd.createtime) <= 30");
		
		clst = databaseHelper.getResultListByHql(count.toString());
		int tradtimesofmonth =  Integer.parseInt(clst.get(0).toString());
		obj.addProperty("tradtimesofmonth",tradtimesofmonth);	//月均交易数
		
		count = new StringBuffer("select count(cd.id) from CapitalDetail cd where (cd.type between 1 and 3 ) and cd.capital > 0 and cd.fuserinfoid = ").append(u.getId());
		
		List sclst = databaseHelper.getResultListByHql(count.toString());
		int sctradtimes =  Integer.parseInt(clst.get(0).toString());
		obj.addProperty("tradsuccessrate",ConstantUtil.intdevice2(sctradtimes, tradtimes, false));	//交易胜率
		
		return obj.toString();
		
	}

	@Override
	public String historylog(String userid, String exampleid, String type, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		
		int int_type = Integer.parseInt(type);
		
		Long uid =  exampleid==null?Long.parseLong(userid):Long.parseLong(exampleid);
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(int_type<3){
			int count = counthistoryofshare(uid,int_type);
			int pages = ConstantUtil.pages(count, limit);
			
			if(count>0){
				StringBuffer query = new StringBuffer("select swh,s from SharesWareHouse swh,Shares s where swh.fsharesid = s.id and swh.fuserinfoid =").append(uid)
						.append(" and swh.state = -1 and swh.couldusenums > 0 ");
				
				if(int_type==-1)query.append(" and swh.type = ").append(int_type);
				
				List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
				
				for (Object[] objects : lst) {
					SharesWareHouse swh = (SharesWareHouse)objects[0];
					Shares s = (Shares)objects[1];
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("shareid",s.getId());	//股票id
					map.put("sharename",swh.getSharesname());	//股票名称
					map.put("totalprofitloss",swh.getProfitloss());	//累计盈亏
					map.put("profitrate",ConstantUtil.formatDouble(swh.getProfitloss()/swh.getCost()*100)+"%");//	收益率
					map.put("time",swh.getTime().toString());//	清算时间
					
					lstMap.add(map);
				}
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",count);
			obj.addProperty("pages",pages);
			obj.add("rows", arr);
			
			return obj.toString();
			
		}else{
			int count = counthistoryoffe(uid,int_type);
			int pages = ConstantUtil.pages(count, limit);
			
			if(count>0){
				StringBuffer query = new StringBuffer("select fe,s from ForeignExchange fe,Shares s where fe.fsharesid = s.id and fe.fuserinfoid =").append(uid)
						.append(" and fe.state = -1 and fe.couldusenums > 0 ");
				
				
				List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
				
				for (Object[] objects : lst) {
					ForeignExchange fe = (ForeignExchange)objects[0];
					Shares s = (Shares)objects[1];
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("shareid",s.getId());	//股票id
					map.put("sharename",s.getSharesname());	//股票名称
					map.put("totalprofitloss",fe.getProfitloss());	//累计盈亏
					map.put("profitrate",ConstantUtil.formatDouble(fe.getProfitloss()/fe.getPurchase()*100)+"%");//	收益率
					map.put("time",fe.getTime());//	清算时间
					
					lstMap.add(map);
				}
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",count);
			obj.addProperty("pages",pages);
			obj.add("rows", arr);
			
			return obj.toString();
		}
		
	}

	
	private int counthistoryofshare(Long uid,int int_type){
		StringBuffer count = new StringBuffer("select count(swh.id) from SharesWareHouse swh where swh.fuserinfoid =").append(uid)
				.append(" and swh.state = -1 and swh.couldusenums > 0 ");
		if(int_type==-1)count.append(" and swh.type = ").append(int_type);
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private int counthistoryoffe(Long uid,int int_type){
		StringBuffer count = new StringBuffer("select count(fe.id) from ForeignExchange fe where fe.fuserinfoid =").append(uid)
				.append(" and fe.state = -1 and fe.couldusenums > 0 ");
		
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String houselog(String userid, String exampleid, String type, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		
		int int_type = Integer.parseInt(type);
		
		Long uid =  exampleid==null?Long.parseLong(userid):Long.parseLong(exampleid);
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(int_type<3){
			int count = counthousrlogofshare(uid,int_type);
			int pages = ConstantUtil.pages(count, limit);
			
			if(count>0){
				StringBuffer query = new StringBuffer("select swh,s from SharesWareHouse swh,Shares s where swh.fsharesid = s.id and swh.fuserinfoid =").append(uid)
						.append(" and swh.state = 0 and swh.couldusenums > 0 ");
				
				if(int_type==-1)query.append(" and swh.type = ").append(int_type);
				
				List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
				
				for (Object[] objects : lst) {
					SharesWareHouse swh = (SharesWareHouse)objects[0];
					Shares s = (Shares)objects[1];
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("shareid",s.getId());	//股票id
					map.put("sharename",swh.getSharesname());	//股票名称
					map.put("marketvalue",swh.getCouldusenums()*s.getPrice());	//市值
					map.put("housenums",swh.getWarehousenums());//	持仓手数
					map.put("couldusenums",swh.getCouldusenums());//	可用手数
					map.put("cost",swh.getCost());//	成本
					map.put("nowprice",s.getPrice());	//现价
					map.put("profitloss",s.getDiffrate());	//盈亏
					map.put("updatetime",s.getLastupdate().toString());//	更新时间
					
					lstMap.add(map);
				}
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",count);
			obj.addProperty("pages",pages);
			obj.add("rows", arr);
			
			return obj.toString();
			
		}else{
			int count = counthousrlogoffe(uid,int_type);
			int pages = ConstantUtil.pages(count, limit);
			
			if(count>0){
				StringBuffer query = new StringBuffer("select fe,s from ForeignExchange fe,Shares s where fe.fsharesid = s.id and fe.fuserinfoid =").append(uid)
						.append(" and fe.state = 0 and fe.couldusenums > 0 ");
				
				
				List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
				
				for (Object[] objects : lst) {
					ForeignExchange fe = (ForeignExchange)objects[0];
					Shares s = (Shares)objects[1];
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("shareid",s.getId());	//股票id
					map.put("sharename",s.getSharesname());	//股票名称
					map.put("marketvalue",fe.getCouldusenums()*s.getPrice());	//市值
					map.put("housenums",fe.getWarehousenums());//	持仓手数
					map.put("couldusenums",fe.getCouldusenums());//	可用手数
					map.put("cost",fe.getPurchase());//	成本
					map.put("nowprice",s.getPrice());	//现价
					map.put("profitloss",s.getDiffrate());	//盈亏
					map.put("updatetime",s.getLastupdate().toString());//	更新时间
					
					lstMap.add(map);
				}
			}
			
			String json = new Gson().toJson(lstMap);
			JsonArray arr = (JsonArray) new JsonParser().parse(json);
			JsonObject obj = new JsonObject();
			obj.addProperty("total",count);
			obj.addProperty("pages",pages);
			obj.add("rows", arr);
			
			return obj.toString();
		}
		
	}

	
	private int counthousrlogofshare(Long uid,int int_type){
		StringBuffer count = new StringBuffer("select count(swh.id) from SharesWareHouse swh where swh.fuserinfoid =").append(uid)
				.append(" and swh.state = 0 and swh.couldusenums > 0 ");
		if(int_type==-1)count.append(" and swh.type = ").append(int_type);
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private int counthousrlogoffe(Long uid,int int_type){
		StringBuffer count = new StringBuffer("select count(fe.id) from ForeignExchange fe where fe.fuserinfoid =").append(uid)
				.append(" and fe.state = 0 and fe.couldusenums > 0 ");
		
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String followlog(String userid, String type, String tag, int start, int limit) throws Exception {
		// TODO Auto-generated method stub
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		if(type==null||type.length()==0)throw new Exception("参数type不能为空");
		if(tag==null||tag.length()==0)throw new Exception("参数tag不能为空");
		
		int int_type = Integer.parseInt(type);// 0沪深  1美股  2外汇
		int int_tag = Integer.parseInt(tag);// 0正在跟随  -1历史跟随
		
		int count = countfollowlog(userid,int_type,int_tag);
		int pages = ConstantUtil.pages(count, limit);
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
			StringBuffer query = new StringBuffer("select d,example from Documentary d,UserInfo example where d.fuserinfoid = ").append(userid).append(" and d.ffollowuserinfoid = example.id ")
					.append(" and d.state = ").append(int_tag).append(" and d.tag =  ").append(int_type);
			
			List<Object[]> lst = databaseHelper.getResultListByHql(query.toString());
			
			for (Object[] objects : lst) {
				Documentary d = (Documentary)objects[0];
				UserInfo example = (UserInfo)objects[1];
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("documentaryid",d.getId());	//跟单id
				map.put("exampleid",example.getId());	//牛人id
				map.put("nickname",example.getNickname());	//牛人名称
				map.put("headimg",example.getHeadimg());	//牛人
				map.put("actualyamount",d.getActualyamount());	//跟单金额
				map.put("state",d.getState());
				StringBuffer find = new StringBuffer("select upt from UserProfit upt where upt.tags = 2 and upt.fuserinfoid = ").append(d.getFuserinfoid())
						.append(" order by time desc ");
				
				List<Object> uplst = databaseHelper.getResultListByHql(find.toString());
				if(uplst.size()==0){
					map.put("marketvalue",0);	//市值
					map.put("totalprofitofus", 0);//累计盈亏
					map.put("totalprofitrateofus", 0);//盈亏比例
				}else{
					UserProfit upt = (UserProfit)uplst.get(0);
					map.put("marketvalue",ConstantUtil.formatDouble(upt.getPrice()*upt.getNums()));	//市值
					map.put("totalprofitofus", ConstantUtil.formatDouble(upt.getProfit()));//累计盈亏
					map.put("totalprofitrateofus", ConstantUtil.formatDouble(upt.getProfitrate()));//盈亏比例
				}
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}

	private int countfollowlog(String userid,int int_type,int int_tag){
		StringBuffer count = new StringBuffer("select count(d.id) from Documentary d,UserInfo ui where d.fuserinfoid = ").append(userid).append(" and d.ffollowuserinfoid = ui.id ")
				.append(" and d.state = ").append(int_tag).append(" and d.tag =  ").append(int_type);
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public String tradeoverview(String userid, String exampleid) throws Exception {
		if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		
		Long uid = exampleid==null?Long.parseLong(userid):Long.parseLong(exampleid);
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, uid);
		
		int totalnums = 0;//总手数
		Double followamount = 0d;//交易（跟随）总金额
		//查看最近一次股票交易
		StringBuffer query_share = new StringBuffer("select swh from SharesWareHouse swh where swh.fuserinfoid = ").append(ui.getId()).append(" order by swh.time desc");
		List<Object> lst_share = databaseHelper.getResultListByHql(query_share.toString());
		
		for (Object object : lst_share) {
			SharesWareHouse swh = (SharesWareHouse)object;
			totalnums += swh.getCouldusenums();
			followamount += swh.getCost();
		}
		
		SharesWareHouse swh = lst_share.size()==0?null:(SharesWareHouse)lst_share.get(0);
		
		//查看最近一次外汇交易
		StringBuffer query_wh = new StringBuffer("select fe from ForeignExchange fe where fe.fuserinfoid = ").append(ui.getId()).append(" order by fe.time desc");
		List<Object> lst_wh = databaseHelper.getResultListByHql(query_wh.toString());
		
		for (Object object : lst_wh) {
			ForeignExchange fe = (ForeignExchange)object;
			totalnums += fe.getCouldusenums();
			followamount += fe.getPurchase();
		}
		
		ForeignExchange fe = lst_wh.size()==0?null:(ForeignExchange)lst_wh.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(swh!=null&&fe!=null){
			if(swh.getTime().getTime()-fe.getTime().getTime()<0){
				map.put("lasttrade",ConstantUtil.getDistanceTime(swh.getTime(),new Date())+"前");
			}else{
				map.put("lasttrade",ConstantUtil.getDistanceTime(fe.getTime(),new Date())+"前");
			}
			
		}else if(swh!=null){
			map.put("lasttrade",ConstantUtil.getDistanceTime(swh.getTime(),new Date())+"前");
		}else if(fe!=null){
			map.put("lasttrade",ConstantUtil.getDistanceTime(fe.getTime(),new Date())+"前");
		}else{
			map.put("lasttrade","无");
		}
			

		map.put("totalnums",totalnums);
		map.put("tradecycle",ConstantUtil.showTime(ui.getCreatetime().getTime()));
		map.put("followamount",followamount);	
		
		String result = JsonUtil.getGson().toJson(map);
		return result;
	}

	@Override
	public String tradeinfo(String userid, String type, int start, int limit) throws Exception {
		//if(userid==null||userid.length()==0)throw new Exception("参数userid不能为空");
		
		int count = counttradeinfo(userid);
		int pages = ConstantUtil.pages(count, limit);
		List<Map<String, Object>> lstMap = new ArrayList<Map<String,Object>>();
		if(count>0){
		
			StringBuffer sql = new StringBuffer("select ti,ui from TradeInfo ti,UserInfo ui where ti.fuserid = ui.id order by time desc");
			List<Object[]> lst = databaseHelper.getResultListByHql(sql.toString(),start,limit);
			
			for (Object[] objects : lst) {
				TradeInfo ti = (TradeInfo)objects[0];
				UserInfo ui = (UserInfo)objects[1];
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", ui.getNickname()==null?ui.getName():ui.getNickname());
				map.put("headimg", ui.getHeadimg());
				map.put("sharename", ti.getSharename());
				if(ti.getFexampleid()==null){
					map.put("trade", "自主下单");
				}else{
					UserInfo ex = (UserInfo)databaseHelper.getObjectById(UserInfo.class, ti.getFexampleid());
					map.put("trade", "跟随"+(ex.getNickname()==null?ex.getName():ex.getNickname()));
				}
				map.put("tradetype", ti.getTradetype()==1?"买入":"卖出");
				map.put("nums", ti.getNums());
				map.put("amount", ti.getAmount());
				map.put("profit", ti.getProfit());
				map.put("time", ti.getTime());
				
				lstMap.add(map);
			}
		}
		
		String json = new Gson().toJson(lstMap);
		JsonArray arr = (JsonArray) new JsonParser().parse(json);
		JsonObject obj = new JsonObject();
		obj.addProperty("total",count);
		obj.addProperty("pages",pages);
		obj.add("rows", arr);
		
		return obj.toString();
	}
	
	private int counttradeinfo(String userid){
		StringBuffer count = new StringBuffer("select count(ti.id) from TradeInfo ti,UserInfo ui where ti.fuserid = ui.id");
		
		List lst = databaseHelper.getResultListByHql(count.toString());
		return Integer.parseInt(lst.get(0).toString());
	}

	
	
}
