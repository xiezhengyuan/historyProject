package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.ForeignExchangeEntrust;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesEntrust;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.service.TradingService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;

@Repository
public class TradingServiceImpl implements TradingService {

	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public Map<String, Object> queryusertrading(Integer start, String keyword, Integer limit)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(*) from UserInfo n where n.state=0 and n.name like '%").append(keyword).append("%'");
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total = ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer2 = new StringBuffer("select n from UserInfo n where n.state=0 and n.name like '%").append(keyword).append("%'");
			List<Object> list =databaseHelper.getResultListByHql(buffer2.toString(), start, limit);
			for(Object obj :list){
				UserInfo userInfo = (UserInfo)obj;
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("id", userInfo.getId());
				map.put("name", userInfo.getName());
				map.put("mobile", userInfo.getMobile());
				map.put("golds",userInfo.getGolds());
				map.put("virtualcapital", userInfo.getVirtualcapital());
				rowList.add(map);
				
			}
			
			
		}
		
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public void modifytrading(UserInfo userInfo)throws Exception {
		UserInfo fo=(UserInfo)databaseHelper.getObjectById(UserInfo.class, userInfo.getId());
		fo.setName(userInfo.getName());
		
		fo.setGolds(userInfo.getGolds());
		fo.setVirtualcapital(userInfo.getVirtualcapital());
		databaseHelper.updateObject(fo);
	}
	@Override
	public Map<String, Object> queryposition(Integer start, String keyword, Integer limit,String tradingId,String type)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer=new StringBuffer("select count(s.id) from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and (s.state=0 or s.state=1) and s.sharesname like '%").append(keyword).append("%'")
				.append(" and s.fuserinfoid=").append(Long.parseLong(tradingId)).append(" and s.type=").append(Integer.parseInt(type));
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1=new StringBuffer("select s,h from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and (s.state=0 or s.state=1) and s.sharesname like '%").append(keyword).append("%'")
					.append(" and s.fuserinfoid=").append(Long.parseLong(tradingId)).append(" and s.type=").append(Integer.parseInt(type)).append(" order by s.time DESC");
			Sys.out(buffer1);
			List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object[] obj:list){
				SharesWareHouse sw=(SharesWareHouse)obj[0];
				Shares h=(Shares)obj[1];
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", sw.getId());
				map.put("sharesname", sw.getSharesname());
				map.put("marketvalue", sw.getMarketvalue());
				map.put("warehousenums",sw.getWarehousenums());
				map.put("couldusenums",sw.getCouldusenums());
				map.put("cost",sw.getCost());
				map.put("price",sw.getPrice());
				map.put("hprice",h.getPrice());
				map.put("profitloss",sw.getProfitloss());
				map.put("time", sw.getTime());
				rowList.add(map);
			}
			

		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public Map<String, Object> queryorders(Integer start, String keyword, Integer limit, String tradingId,String type)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer=new StringBuffer("select count(*) from SharesEntrust s,Shares h where s.fsharesid=h.id and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(tradingId))
				.append(" and s.sharesname like '%").append(keyword).append("%'").append(" and s.type=").append(Integer.parseInt(type));
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1=new StringBuffer("select s,h from SharesEntrust s,Shares h where s.fsharesid=h.id and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(tradingId))
					.append(" and s.sharesname like '%").append(keyword).append("%'").append(" and s.type=").append(Integer.parseInt(type))
					.append(" order by s.time DESC");
			List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object[] obj:list){
				SharesEntrust se =(SharesEntrust)obj[0];
				Shares h=(Shares)obj[1];
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",se.getId());
				map.put("sharesname",se.getSharesname());
				map.put("price",se.getPrice());
				map.put("hprice", h.getPrice());
				map.put("entrustprice",se.getEntrustprice());
				map.put("entrustnums",se.getEntrustnums());
				if(se.getFrozenamount()==null||se.getFrozenamount()==0){
					map.put("frozenamount", "委托卖出");
				}else {
					map.put("frozenamount", se.getFrozenamount());
				}
				
				map.put("time", se.getTime());
				rowList.add(map);
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public Map<String, Object> queryforex(Integer start, String keyword, Integer limit, String tradingId)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(f.id) from ForeignExchange f,Shares h where f.fsharesid=h.id and f.couldusenums>0 and (f.state=0 or f.state=1) and f.fuserinfoid=").append(Long.parseLong(tradingId))
				.append(" and f.foreignexchangename like '%").append(keyword).append("%'");
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1 = new StringBuffer("select f,h from ForeignExchange f,Shares h where f.fsharesid=h.id and f.couldusenums>0 and (f.state=0 or f.state=1) and f.fuserinfoid=").append(Long.parseLong(tradingId))
					.append(" and f.foreignexchangename like '%").append(keyword).append("%'").append(" order by f.time DESC");	
			List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object[] obj:list){
				ForeignExchange fe =(ForeignExchange)obj[0];
				Shares h=(Shares)obj[1];
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("id",fe.getId());
				map.put("foreignexchangename",fe.getForeignexchangename());
				map.put("price",fe.getPrice());
				map.put("hprice", h.getPrice());
				map.put("warehousenums",fe.getWarehousenums());
				map.put("purchase",fe.getPurchase());
				map.put("time",fe.getTime());
				rowList.add(map);
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public Map<String, Object> queryforex1(Integer start, String keyword, Integer limit, String tradingId)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(*) from ForeignExchangeEntrust f,Shares h where f.fsharesid=h.id and (f.state=0 or f.state=-1) and f.fuserinfoid=").append(Long.parseLong(tradingId))
				.append(" and f.foreignexchangename like '%").append(keyword).append("%'");
		
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1 = new StringBuffer("select f,h from ForeignExchangeEntrust f,Shares h where f.fsharesid=h.id and (f.state=0 or f.state=-1) and f.fuserinfoid=").append(Long.parseLong(tradingId))
					.append(" and f.foreignexchangename like '%").append(keyword).append("%'").append(" order by f.time DESC");
			List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object[] obj :list){
				ForeignExchangeEntrust fe =(ForeignExchangeEntrust)obj[0];
				Shares h=(Shares)obj[1];
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("id",fe.getId());
				map.put("foreignexchangename",fe.getForeignexchangename());
				map.put("price",fe.getPrice());
				map.put("hprice", h.getPrice());
				map.put("entrustprice",fe.getEntrustprice());
				map.put("entrustnums",fe.getEntrustnums());
				if(fe.getFrozenamount()==null||fe.getFrozenamount()==0){
					map.put("frozenamount", "委托卖出");
				}else {
					map.put("frozenamount", fe.getFrozenamount());
				}
				
				map.put("time",fe.getTime());
				rowList.add(map);
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public Map<String, Object> queryhistorytrading(Integer start, String keyword, Integer limit, String tradingId,String type)throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer=new StringBuffer("select count(*) from SharesWareHouse s where s.state=-1 and s.sharesname like '%").append(keyword).append("%'")
				.append(" and s.fuserinfoid=").append(Long.parseLong(tradingId)).append(" and s.type=").append(Integer.parseInt(type));
		
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1=new StringBuffer("select s from SharesWareHouse s where  s.state=-1 and s.sharesname like '%").append(keyword).append("%'")
					.append(" and s.fuserinfoid=").append(Long.parseLong(tradingId)).append(" and s.type=").append(Integer.parseInt(type))
					.append(" order by s.time DESC");
			List<Object> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object obj:list){
				SharesWareHouse sh  =(SharesWareHouse)obj;
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", sh.getId());
				map.put("sharesname", sh.getSharesname());
				map.put("marketvalue",sh.getMarketvalue());
				map.put("warehousenums",sh.getWarehousenums());
				map.put("couldusenums",sh.getCouldusenums());
				map.put("cost",sh.getCost());
				map.put("price",sh.getPrice());
				map.put("profitloss", sh.getProfitloss());
				map.put("time", sh.getTime());
				rowList.add(map);
				
			}
			
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}
	@Override
	public Map<String, Object> queryhistoryforex(Integer start, String keyword, Integer limit, String tradingId)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		StringBuffer buffer = new StringBuffer("select count(f.id) from ForeignExchange f where f.state=-1 and f.fuserinfoid=").append(Long.parseLong(tradingId))
				.append(" and f.foreignexchangename like '%").append(keyword).append("%'");
		int records = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		int total=ConstantUtil.pages(records, limit);
		if(records>0){
			StringBuffer buffer1 = new StringBuffer("select f from ForeignExchange f where f.state=-1 and f.fuserinfoid=").append(Long.parseLong(tradingId))
					.append(" and f.foreignexchangename like '%").append(keyword).append("%'").append(" order by f.time DESC");	
			List<Object> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
			for(Object obj:list){
				ForeignExchange fe =(ForeignExchange)obj;
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("id",fe.getId());
				map.put("foreignexchangename",fe.getForeignexchangename());
				map.put("amount",fe.getPrice());
				map.put("warehousenums",fe.getWarehousenums());
				map.put("purchase",fe.getPurchase());
				map.put("sellout", fe.getSellout());
				map.put("profitloss", fe.getProfitloss());
				
				map.put("time",fe.getTime());
				rowList.add(map);
			}
		}
		resultMap.put("total", total);
		resultMap.put("records", records);
		resultMap.put("rows", rowList);
		
		return resultMap;
	}

}
