package com.hxy.isw.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.Documentary;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.ForeignExchangeEntrust;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesEntrust;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserSharesSetting;
import com.hxy.isw.service.AppServiceSet;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;
import com.mysql.jdbc.Buffer;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.sun.xml.internal.bind.v2.model.core.ID;

import javassist.compiler.ast.NewExpr;

@Repository
public class AppServiceSetImpl implements AppServiceSet {
	
	@Autowired
	DatabaseHelper databaseHelper;
	@Override
	public void addDocumentaryMoney(String userId, String money,String documentaryId)throws Exception  {
		
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		StringBuffer buffer =new StringBuffer("select d from Documentary d where d.state=0 and d.fuserinfoid=").append(userId)
				.append(" and d.ffollowuserinfoid=").append(documentaryId);
		Documentary d=(Documentary)(databaseHelper.getResultListByHql(buffer.toString()).get(0));
		if(Double.parseDouble(money)>u.getVirtualcapital()){
			throw new Exception("账户金币不足");
		}
		u.setVirtualcapital(u.getVirtualcapital()-Double.parseDouble(money));
		databaseHelper.updateObject(u);
		d.setMoney(Integer.parseInt(money)+d.getMoney());
		d.setActualyamount(Double.parseDouble(money)+d.getActualyamount());
		databaseHelper.updateObject(d);
		//资金明细表插入一条数据
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		cd.setType(-5);
		cd.setCapital(Double.parseDouble(money)*-1);//支出的金币为负数
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
	@Override
	public void cancelDocumentaryMoney(String userId,String documentaryId,String exampleapplyId)throws Exception {
		Date now = new Date();
		StringBuffer bufferr =new StringBuffer("select d from Documentary d where d.state=0 and d.fuserinfoid=").append(userId)
				.append(" and d.ffollowuserinfoid=").append(exampleapplyId);
		Documentary d=(Documentary)(databaseHelper.getResultListByHql(bufferr.toString()).get(0));
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		double vir;
		if(d.getActualyamount()==null){//如果按手数，比例来跟单就不存在跟单金额
			vir=0.0;
		}else{
			vir=d.getActualyamount();
		}
		
		d.setState(-1);
		d.setActualyamount(0.0);
		databaseHelper.updateObject(d);
		//将所有跟单股票全部卖出
		StringBuffer buffer = new StringBuffer("select s from SharesWareHouse s where s.fexampleid=").append(exampleapplyId)
				.append(" and s.state=1").append(" and s.couldusenums>0");
		List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
		if(list.size()>0){
			for(Object obj:list){
				CapitalDetail c =new CapitalDetail();
				SharesWareHouse sh =(SharesWareHouse)obj;
				/**
				 * 1.由于跟单牛人的委托挂单，是在牛人买入或者卖出的那一刻，才对自己的股票进行买入或者卖出
				 * ，所以委托挂单表不会产生记录，没必要修改委托挂单的状态;
				 * 2.所以对应的资金明细和止盈止损都要进行操作
				 */
				
				StringBuffer hql =new StringBuffer("select u from UserSharesSetting u where u.type=1 and u.fforeignid=").append(sh.getId());
				List<Object> objects = databaseHelper.getResultListByHql(hql.toString());
				if(objects.size()>0){
					UserSharesSetting us=(UserSharesSetting)objects.get(0);
					us.setState(-1);
					databaseHelper.updateObject(us);
				}
				Shares s= (Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
				double a =sh.getCouldusenums()*s.getPrice();
				c.setFuserinfoid(u.getId());
				if(sh.getType()==0){
					c.setType(1);
				}else if(sh.getType()==1){
					c.setType(3);
				}
				c.setCapital(sh.getCouldusenums()*s.getPrice());
				c.setCreatetime(now);
				c.setState(0);
				databaseHelper.persistObject(c);
				
				sh.setCouldusenums(0);
				sh.setMarketvalue(s.getPrice());
				sh.setProfitloss(s.getPrice()-sh.getPrice());
				sh.setTime(now);
				
				vir+=a;
				SharesWareHouse sw =new SharesWareHouse();
				sw.setFuserinfoid(sh.getFuserinfoid());
				sw.setFsharesid(sh.getFsharesid());
				sw.setSharesname(sh.getSharesname());
				sw.setCode(sh.getCode());
				sw.setMarketvalue(s.getPrice());
				sw.setWarehousenums(sh.getWarehousenums());
				sw.setCouldusenums(sh.getCouldusenums());
				sw.setCost(sh.getCost());
				sw.setPrice(s.getPrice());
				sw.setProfitloss(s.getPrice()-sh.getPrice());
				sw.setTime(now);
				sw.setState(-1);
				sw.setIsplan(sh.getIsplan());
				sw.setType(sh.getType());
				sw.setFshareswarehouseid(sh.getId());
				databaseHelper.persistObject(sw);
				databaseHelper.updateObject(sh); 
			}
		}
	
		//将所有外汇全部卖出
		StringBuffer buffe2 = new StringBuffer("select f from ForeignExchange f where f.fexampleid=").append(exampleapplyId)
				.append(" and f.state=1").append(" and f.couldusenums>0");
		List<Object> lst = databaseHelper.getResultListByHql(buffe2.toString());
		if(lst.size()>0){
			for(Object obj:lst){
				CapitalDetail c =new CapitalDetail();
				ForeignExchange sh = (ForeignExchange)obj;
				Shares s= (Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
				/**
				 * 由于跟单牛人的委托挂单，是在牛人买入或者卖出的那一刻，才对自己的股票进行买入或者卖出
				 * ，所以委托挂单表不会产生记录，没必要修改委托挂单的状态
				 */
				StringBuffer hql =new StringBuffer("select u from UserSharesSetting u where u.type=2 and u.fforeignid=").append(sh.getId());
				List<Object> objects = databaseHelper.getResultListByHql(hql.toString());
				if(objects.size()>0){
					UserSharesSetting us=(UserSharesSetting)objects.get(0);
					us.setState(-1);
					databaseHelper.updateObject(us);
				}
				double a =sh.getCouldusenums()*s.getPrice();
				c.setType(2);
				c.setCapital(sh.getCouldusenums()*s.getPrice());
				c.setCreatetime(now);
				c.setState(0);
				databaseHelper.persistObject(c);
				sh.setCouldusenums(0);
				sh.setProfitloss(s.getPrice()-sh.getPrice());
				sh.setSellout(s.getPrice());
				sh.setTime(now);
				vir+=a;
				
				ForeignExchange fe=new ForeignExchange();
				fe.setFuserinfoid(sh.getFuserinfoid());
				fe.setFsharesid(sh.getFsharesid());
				fe.setForeignexchangename(sh.getForeignexchangename());
				fe.setCode(sh.getCode());
				fe.setPrice(sh.getPrice());
				fe.setWarehousenums(sh.getWarehousenums());
				fe.setCouldusenums(sh.getCouldusenums());
				fe.setPurchase(sh.getPurchase());
				fe.setSellout(s.getPrice());
				fe.setProfitloss(s.getPrice()-sh.getPurchase());
				fe.setTime(now);
				fe.setState(-1);
				fe.setIsplan(sh.getIsplan());
				fe.setFforeignexchangeid(sh.getId());
				databaseHelper.persistObject(fe);
				databaseHelper.updateObject(sh);
			}
		}
		
		//股票外汇卖出的钱返回账户金币
		u.setVirtualcapital(u.getVirtualcapital()+vir);
		databaseHelper.updateObject(u);
		
	}
	@Override
	public Map<String, Object> querytrading(String userId, String type,Integer start,Integer limit)throws Exception {
		if(Integer.parseInt(type)==0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(s.id) from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and s.type=0 and s.state=0")
					.append(" and s.fuserinfoid=").append(Long.parseLong(userId));
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s,h from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and s.type=0 and s.state=0")
						.append(" and s.fuserinfoid=").append(Long.parseLong(userId)).append(" order by s.time DESC");
				Sys.out(buffer1);
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(), start, limit);
				for(Object[] obj:list){
					SharesWareHouse sw=(SharesWareHouse)obj[0];
					Shares h=(Shares)obj[1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", h.getId());
					map.put("shareid", sw.getId());
					map.put("sharesname", sw.getSharesname());
					map.put("marketvalue", String.format("%.2f",sw.getMarketvalue()));
					map.put("warehousenums",sw.getWarehousenums());
					map.put("couldusenums",sw.getCouldusenums());
					map.put("cost",sw.getCost());
					map.put("price",sw.getPrice());
					map.put("hprice",h.getPrice());
					map.put("profitloss",String.format("%.2f",h.getPrice()-sw.getPrice()));
					map.put("time", sw.getTime().toString());
					rowList.add(map);
				}
				

			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(*) from SharesEntrust s,Shares h where s.fsharesid=h.id and s.type=0 and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(userId));
					
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s,h from SharesEntrust s,Shares h where s.fsharesid=h.id and s.type=0 and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(userId))
						.append(" order by s.time DESC");
						
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object[] obj:list){
					SharesEntrust se =(SharesEntrust)obj[0];
					Shares h=(Shares)obj[1];
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("id",h.getId());
					map.put("shareid",se.getId());
					map.put("sharesname",se.getSharesname());
					map.put("price",se.getPrice());
					map.put("hprice", h.getPrice());
					map.put("entrustprice",se.getEntrustprice());
					map.put("entrustnums",se.getEntrustnums());
					if(se.getFrozenamount()==null||se.getFrozenamount()==0){
						map.put("frozenamount","委托卖出");
					}else{
						map.put("frozenamount",se.getFrozenamount());
					}
					map.put("frozenamount",se.getFrozenamount());
					map.put("time", se.getTime().toString());
					rowList.add(map);
				}
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==2){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(*) from SharesWareHouse s where s.state=-1 and s.type=0")
					.append(" and s.fuserinfoid=").append(Long.parseLong(userId));
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s from SharesWareHouse s where s.state=-1 and s.type=0")
						.append(" and s.fuserinfoid=").append(Long.parseLong(userId)).append(" order by s.time DESC");
				List<Object> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object obj:list){
					SharesWareHouse sh  =(SharesWareHouse)obj;
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("id", sh.getFsharesid());
					map.put("shareid", sh.getId());
					map.put("sharesname", sh.getSharesname());
					map.put("marketvalue",String.format("%.2f",sh.getMarketvalue()));
					map.put("warehousenums",sh.getWarehousenums());
					map.put("couldusenums",sh.getCouldusenums());
					map.put("cost",sh.getCost());
					map.put("price",sh.getPrice());
					map.put("profitloss", String.format("%.2f",sh.getProfitloss()));
					map.put("time", sh.getTime().toString());
					rowList.add(map);
					
				}
				
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "无记录");
		return map;
	}
	@Override
	public Map<String, Object> queryAmericantrading(String userId, String type, Integer start, Integer limit)throws Exception {
		if(Integer.parseInt(type)==0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(s.id) from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and s.type=1 and s.state=0")
					.append(" and s.fuserinfoid=").append(Long.parseLong(userId));
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s,h from SharesWareHouse s,Shares h where s.fsharesid=h.id and s.couldusenums>0 and s.type=1 and s.state=0")
						.append(" and s.fuserinfoid=").append(Long.parseLong(userId)).append(" order by s.time DESC");
				Sys.out(buffer1);
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object[] obj:list){
					SharesWareHouse sw=(SharesWareHouse)obj[0];
					Shares h=(Shares)obj[1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", sw.getFsharesid());
					map.put("shareid", sw.getId());
					map.put("sharesname", sw.getSharesname());
					map.put("marketvalue", String.format("%.2f",sw.getMarketvalue()));
					map.put("warehousenums",sw.getWarehousenums());
					map.put("couldusenums",sw.getCouldusenums());
					map.put("cost",sw.getCost());
					map.put("price",sw.getPrice());
					map.put("hprice",h.getPrice());
					map.put("profitloss",String.format("%.2f",h.getPrice()-sw.getPrice()));
					map.put("time", sw.getTime().toString());
					rowList.add(map);
				}
				

			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(*) from SharesEntrust s,Shares h where s.fsharesid=h.id and s.type=1 and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(userId));
					
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s,h from SharesEntrust s,Shares h where s.fsharesid=h.id and s.type=1 and (s.state=0 or s.state=-1) and s.fuserinfoid=").append(Long.parseLong(userId))
						.append(" order by s.time DESC");
						
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object[] obj:list){
					SharesEntrust se =(SharesEntrust)obj[0];
					Shares h=(Shares)obj[1];
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("id", se.getFsharesid());
					map.put("shareid", se.getId());
					map.put("sharesname",se.getSharesname());
					map.put("price",se.getPrice());
					map.put("hprice", h.getPrice());
					map.put("entrustprice",se.getEntrustprice());
					map.put("entrustnums",se.getEntrustnums());
					if(se.getFrozenamount()==null||se.getFrozenamount()==0){
						map.put("frozenamount","委托卖出");
					}else{
						map.put("frozenamount",se.getFrozenamount());
					}
					
					map.put("time", se.getTime().toString());
					rowList.add(map);
				}
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==2){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer=new StringBuffer("select count(*) from SharesWareHouse s where s.state=-1 and s.type=1")
					.append(" and s.fuserinfoid=").append(Long.parseLong(userId));
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1=new StringBuffer("select s from SharesWareHouse s where s.state=-1 and s.type=1")
						.append(" and s.fuserinfoid=").append(Long.parseLong(userId)).append(" order by s.time DESC");
				List<Object> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object obj:list){
					SharesWareHouse sh  =(SharesWareHouse)obj;
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("id", sh.getFsharesid());
					map.put("shareid", sh.getId());
					map.put("sharesname", sh.getSharesname());
					map.put("marketvalue",String.format("%.2f",sh.getMarketvalue()));
					map.put("warehousenums",sh.getWarehousenums());
					map.put("couldusenums",sh.getCouldusenums());
					map.put("cost",sh.getCost());
					map.put("price",sh.getPrice());
					map.put("profitloss", String.format("%.2f",sh.getProfitloss()));
					map.put("time", sh.getTime().toString());
					rowList.add(map);
					
				}
				
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "无记录");
		return map;
	}
	@Override
	public Map<String, Object> queryforex(String userId, String type, Integer start, Integer limit)throws Exception {
		if(Integer.parseInt(type)==0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer = new StringBuffer("select count(f.id) from ForeignExchange f,Shares h where f.fsharesid=h.id and f.couldusenums>0 and f.state=0 and f.fuserinfoid=").append(Long.parseLong(userId));
					
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1 = new StringBuffer("select f,h from ForeignExchange f,Shares h where f.fsharesid=h.id and f.couldusenums>0 and f.state=0 and f.fuserinfoid=").append(Long.parseLong(userId))
						.append(" order by f.time DESC");
						
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object[] obj:list){
					ForeignExchange fe =(ForeignExchange)obj[0];
					Shares h=(Shares)obj[1];
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("id", fe.getFsharesid());
					map.put("shareid", fe.getId());
					map.put("foreignexchangename",fe.getForeignexchangename());
					map.put("price",fe.getPrice());
					map.put("hprice", h.getPrice());
					map.put("code", fe.getCode());
					map.put("warehousenums",fe.getWarehousenums());
					map.put("couldusenums", fe.getCouldusenums());
					map.put("purchase",fe.getPurchase());
					map.put("time",fe.getTime().toString());
					map.put("profitloss", String.format("%.2f",fe.getProfitloss()));
					map.put("count",String.format("%.2f",fe.getWarehousenums()*h.getPrice()));
					rowList.add(map);
				}
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==1){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer = new StringBuffer("select count(*) from ForeignExchangeEntrust f,Shares h where f.fsharesid=h.id and (f.state=0 or f.state=-1) and f.fuserinfoid=").append(Long.parseLong(userId))
					;
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1 = new StringBuffer("select f,h from ForeignExchangeEntrust f,Shares h where f.fsharesid=h.id and (f.state=0 or f.state=-1) and f.fuserinfoid=").append(Long.parseLong(userId))
						.append(" order by f.time DESC");
				List<Object[]> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object[] obj :list){
					ForeignExchangeEntrust fe =(ForeignExchangeEntrust)obj[0];
					Shares h=(Shares)obj[1];
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("id", fe.getFsharesid());
					map.put("shareid", fe.getId());
					map.put("foreignexchangename",fe.getForeignexchangename());
					map.put("price",fe.getPrice());
					map.put("code", fe.getCode());
					map.put("hprice", h.getPrice());
					map.put("entrustprice",fe.getEntrustprice());
					map.put("entrustnums",fe.getEntrustnums());
					map.put("time",fe.getTime().toString());
					if(fe.getFrozenamount()==null||fe.getFrozenamount()==0){
						map.put("frozenamount","委托卖出");
					}else{
						map.put("frozenamount",fe.getFrozenamount());
					}
					
					rowList.add(map);
				}
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}else if(Integer.parseInt(type)==2){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			StringBuffer buffer = new StringBuffer("select count(f.id) from ForeignExchange f where f.state=-1 and f.fuserinfoid=").append(Long.parseLong(userId))
					;
			int count = Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
			int pages=ConstantUtil.pages(count, limit);
			if(count>0){
				StringBuffer buffer1 = new StringBuffer("select f from ForeignExchange f where f.state=-1 and f.fuserinfoid=").append(Long.parseLong(userId))
						.append(" order by f.time DESC");	
				List<Object> list = databaseHelper.getResultListByHql(buffer1.toString(),start,limit);
				for(Object obj:list){
					ForeignExchange fe =(ForeignExchange)obj;
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("id", fe.getFsharesid());
					map.put("shareid", fe.getId());
					map.put("foreignexchangename",fe.getForeignexchangename());
					map.put("price",fe.getPrice());
					map.put("code", fe.getCode());
					map.put("warehousenums",fe.getWarehousenums());
					map.put("purchase",fe.getPurchase());
					map.put("profitloss", String.format("%.2f",fe.getProfitloss()));
					map.put("sellout", fe.getSellout());
					map.put("time",fe.getTime().toString());
					rowList.add(map);
				}
			}
			resultMap.put("total", count);
			resultMap.put("pages", pages);
			resultMap.put("rows", rowList);
			
			return resultMap;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "无记录");
		return map;
	}
	@Override
	public Map<String, Object> queryCoverMessage(String userId, String sharesId,String type)throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		if(Integer.parseInt(type)==0){
			SharesWareHouse s = (SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(sharesId));
			Shares sh = (Shares)databaseHelper.getObjectById(Shares.class, s.getFsharesid());
			map.put("price", s.getPrice());
			map.put("nowPrice", sh.getPrice()); 
			double d=(sh.getPrice()-s.getPrice())/s.getPrice()*100;
			map.put("float", String.format("%.2f",d)+"%");//截取小数点后两位
		}else if(Integer.parseInt(type)==1){
			ForeignExchange f=(ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, Long.parseLong(sharesId));
			Shares sh = (Shares)databaseHelper.getObjectById(Shares.class, f.getFsharesid());
			map.put("price", f.getPurchase());
			map.put("nowPrice", sh.getPrice()); 
			double d=(sh.getPrice()-f.getPurchase())/f.getPurchase()*100;
			map.put("float", String.format("%.2f",d)+"%");//截取小数点后两位
		}else{
			map.put("fail", "请传入正确的type");
		}
		return map;
	}
	@Override
	public void queryCover(String userId, String sharesId)throws Exception {
		SharesWareHouse sh =(SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(sharesId));
		Shares s= (Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
		StringBuffer buffer = new StringBuffer("select s from SharesEntrust s where s.state=-1 and s.fshareswarehouseid=").append(sharesId);
		List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
		/*
		 * 止盈止损有设置则修改state
		 * 委托挂单有卖出的话，则撤销
		 */
		StringBuffer hql =new StringBuffer("select u from UserSharesSetting u where u.type=1 and u.fforeignid=").append(sh.getId());
		List<Object> objects = databaseHelper.getResultListByHql(hql.toString());
		if(objects.size()>0){
			UserSharesSetting us=(UserSharesSetting)objects.get(0);
			us.setState(-1);
			databaseHelper.updateObject(us);
		}
		if(list.size()>0){
			for(Object obj :list){
				//将改制
				SharesEntrust se=(SharesEntrust)obj;
				se.setState(1);
				databaseHelper.updateObject(se);
			}
		}
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		SharesWareHouse sw=new SharesWareHouse();
		sw.setFuserinfoid(sh.getFuserinfoid());
		sw.setFsharesid(sh.getFsharesid());
		sw.setSharesname(sh.getSharesname());
		sw.setCode(sh.getCode());
		sw.setMarketvalue(s.getPrice());
		sw.setWarehousenums(sh.getWarehousenums());
		sw.setCouldusenums(sh.getCouldusenums());
		sw.setCost(sh.getCost());
		sw.setPrice(sh.getPrice());
		sw.setProfitloss(s.getPrice()-sh.getPrice());
		sw.setTime(new Date());
		sw.setState(-1);
		sw.setIsplan(sh.getIsplan());
		sw.setType(sh.getType());
		sw.setFshareswarehouseid(sh.getId());
		databaseHelper.persistObject(sw);
		
		sh.setProfitloss(s.getPrice()-sh.getPrice());
		sh.setTime(new Date());
		sh.setMarketvalue(s.getPrice());
		sh.setCouldusenums(0);
		databaseHelper.updateObject(sh);
		double d = s.getPrice()*sh.getCouldusenums();
		u.setVirtualcapital(u.getVirtualcapital()+d);
		databaseHelper.updateObject(u);
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		if(sh.getType()==0){
			cd.setType(1);
		}else if(sh.getType()==1){
			cd.setType(3);
		}
		
		cd.setCapital(d);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
	@Override
	public void revokeShare(String userId, String shareId)throws Exception {
		SharesEntrust fe =(SharesEntrust)databaseHelper.getObjectById(SharesEntrust.class, Long.parseLong(shareId));
		fe.setState(1);
		databaseHelper.updateObject(fe);
		
	}
	@Override
	public void revokeSharebuying(String userId, String shareId)throws Exception {
		SharesEntrust fe =(SharesEntrust)databaseHelper.getObjectById(SharesEntrust.class, Long.parseLong(shareId));
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		u.setVirtualcapital(u.getVirtualcapital()+fe.getFrozenamount());
		databaseHelper.updateObject(u);
		fe.setState(1);
		databaseHelper.updateObject(fe);
		//资金明细表插入一条记录
		CapitalDetail c =new CapitalDetail();
		c.setFuserinfoid(Long.parseLong(userId));
		c.setType(4);//冻结返还为4
				
		c.setCapital(fe.getFrozenamount());//支出金币为负数
		c.setCreatetime(new Date());
		c.setState(0);
		databaseHelper.persistObject(c);
	}
	@Override
	public void revokeForex(String userId, String foreignexId)throws Exception {
		ForeignExchangeEntrust fe=(ForeignExchangeEntrust)databaseHelper.getObjectById(ForeignExchangeEntrust.class, Long.parseLong(foreignexId));
	
		fe.setState(1);
		databaseHelper.updateObject(fe);
		
	}
	@Override
	public void revokeforexbuying(String userId, String foreignexId)throws Exception {
		ForeignExchangeEntrust fe=(ForeignExchangeEntrust)databaseHelper.getObjectById(ForeignExchangeEntrust.class, Long.parseLong(foreignexId));
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		u.setVirtualcapital(u.getVirtualcapital()+fe.getFrozenamount());
		databaseHelper.updateObject(u);
		fe.setState(1);
		databaseHelper.updateObject(fe);
		//资金明细表插入一条记录
		CapitalDetail c =new CapitalDetail();
		c.setFuserinfoid(Long.parseLong(userId));
		c.setType(4);//冻结返还为4
						
		c.setCapital(fe.getFrozenamount());//
		c.setCreatetime(new Date());
		c.setState(0);
		databaseHelper.persistObject(c);
	}
	@Override
	public void addSharesCheck(String userid, String stoploss, String stopprofit, String shareid,String type,String usersharessettingid)throws Exception {
		
		if(Integer.parseInt(usersharessettingid)==-1){
			if(Integer.parseInt(type)==1){//股票
				UserSharesSetting us = new UserSharesSetting();
				SharesWareHouse s=(SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(shareid));
				us.setStoploss(Integer.parseInt(stoploss));
				us.setStopprofit(Integer.parseInt(stopprofit));
				us.setFuserinfoid(Long.parseLong(userid));
				us.setCreatetime(new Date());
				us.setCode(s.getCode());
				us.setState(0);
				us.setFforeignid(Long.parseLong(shareid));
				
				us.setType(Integer.parseInt(type));
				databaseHelper.persistObject(us);
			}else if(Integer.parseInt(type)==2){//外汇
				UserSharesSetting us = new UserSharesSetting();
				ForeignExchange s=(ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, Long.parseLong(shareid));
				us.setStoploss(Integer.parseInt(stoploss));
				us.setStopprofit(Integer.parseInt(stopprofit));
				us.setFuserinfoid(Long.parseLong(userid));
				us.setCreatetime(new Date());
				us.setCode(s.getCode());
				us.setState(0);
				us.setFforeignid(Long.parseLong(shareid));
				
				us.setType(Integer.parseInt(type));
				databaseHelper.persistObject(us);
				
				
			}
		}else {
			UserSharesSetting u=(UserSharesSetting)databaseHelper.getObjectById(UserSharesSetting.class, Long.parseLong(usersharessettingid));
			u.setStoploss(Integer.parseInt(stoploss));
			u.setStopprofit(Integer.parseInt(stopprofit));
			u.setCreatetime(new Date());
			databaseHelper.updateObject(u);
		}
		
		
	}
	@Override
	public void modifySharesCheck(String userId, String stoploss, String stopprofit, String fforeignid,String type)throws Exception {
		StringBuffer buffer =new StringBuffer("select u from UserSharesSetting u where u.fuserinfoid=").append(userId)
				.append(" and u.fforeignid=").append(fforeignid).append(" and u.type=").append(type);
		UserSharesSetting us =(UserSharesSetting)(databaseHelper.getResultListByHql(buffer.toString()).get(0));
		us.setStoploss(Integer.parseInt(stoploss));
		us.setStopprofit(Integer.parseInt(stopprofit));
		databaseHelper.updateObject(us);
	}
	@Override
	public void addforexCheck(String userId, String stoploss, String stopprofit, String fforeignid)throws Exception {
		UserSharesSetting us = new UserSharesSetting();
		SharesWareHouse s=(SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(fforeignid));
		us.setStoploss(Integer.parseInt(stoploss));
		us.setStopprofit(Integer.parseInt(stopprofit));
		us.setCode(s.getCode());
		us.setFuserinfoid(Long.parseLong(userId));
		us.setCreatetime(new Date());
		us.setState(0);
		us.setFforeignid(Long.parseLong(fforeignid));
		us.setType(2);
		databaseHelper.persistObject(us);
		
	}
	@Override
	public void modifyforexCheck(String userId, String stoploss, String stopprofit, String fforeignid, String type)throws Exception{
		StringBuffer buffer =new StringBuffer("select u from UserSharesSetting u where u.fuserinfoid=").append(userId)
				.append(" and u.fforeignid=").append(fforeignid).append(" and u.type=").append(type);
		UserSharesSetting us =(UserSharesSetting)(databaseHelper.getResultListByHql(buffer.toString()).get(0));
		us.setStoploss(Integer.parseInt(stoploss));
		us.setStopprofit(Integer.parseInt(stopprofit));
		databaseHelper.updateObject(us);
		
	}
	@Override
	public void addshares(String userId, String fshareswarehouseid, String entrustprice, String entrustnums)throws Exception  {
		SharesWareHouse sh =(SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(fshareswarehouseid));
		if(Integer.parseInt(entrustnums)>sh.getCouldusenums()){
			throw new Exception("委托手数需小于可用手数");
		}
		SharesEntrust se =new SharesEntrust();
		se.setFuserinfoid(Long.parseLong(userId));
		se.setFsharesid(sh.getFsharesid());
		se.setFshareswarehouseid(Long.parseLong(fshareswarehouseid));
		se.setSharesname(sh.getSharesname());
		se.setCode(sh.getCode());
		se.setPrice(sh.getPrice());
		se.setEntrustnums(Integer.parseInt(entrustnums));
		se.setEntrustprice(Double.parseDouble(entrustprice));
		se.setState(-1);
		se.setTime(new Date());
		se.setIsplan(sh.getIsplan());
		se.setType(sh.getType());
		databaseHelper.persistObject(se);
		
	}
	@Override
	public void addsharesbuying(String userId, String sharesId, String entrustprice, String entrustnums)
			throws Exception {
		Date now =new Date();
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		if(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)>u.getVirtualcapital()){
			throw new Exception("账户金币不足");
		}
		u.setVirtualcapital(u.getVirtualcapital()-(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)));
		databaseHelper.updateObject(u);
		Shares s =(Shares)databaseHelper.getObjectById(Shares.class,Long.parseLong(sharesId));
		SharesEntrust se =new SharesEntrust();
		se.setFuserinfoid(Long.parseLong(userId));
		se.setFsharesid(s.getId());
		se.setSharesname(s.getSharesname());
		se.setCode(s.getCode());
		se.setPrice(s.getPrice());
		se.setEntrustnums(Integer.parseInt(entrustnums));
		se.setEntrustprice(Double.parseDouble(entrustprice));
		se.setFrozenamount(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice));
		se.setState(0);
		se.setTime(now);
		se.setType(s.getType());
		databaseHelper.persistObject(se);
		
		//资金明细表插入一条记录
		CapitalDetail c =new CapitalDetail();
		c.setFuserinfoid(Long.parseLong(userId));
		c.setType(-4);//委托冻结资金为-4
		
		c.setCapital(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)*-1);//支出金币为负数
		c.setCreatetime(now);
		c.setState(0);
		databaseHelper.persistObject(c);
	}
	@Override
	public void addforex(String userId, String fforeignexchangeid, String entrustprice, String entrustnums)
			throws Exception {
		ForeignExchange fe=(ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, Long.parseLong(fforeignexchangeid));
		if(Integer.parseInt(entrustnums)>fe.getCouldusenums()){
			throw new Exception("委托手数需小于可用手数");
		}
		ForeignExchangeEntrust f =new ForeignExchangeEntrust();
		f.setFuserinfoid(Long.parseLong(userId));
		f.setFsharesid(fe.getFsharesid());
		f.setFforeignexchangeid(Long.parseLong(fforeignexchangeid));
		f.setForeignexchangename(fe.getForeignexchangename());
		f.setCode(fe.getCode());
		f.setPrice(fe.getPrice());
		f.setEntrustnums(Integer.parseInt(entrustnums));
		f.setEntrustprice(Double.parseDouble(entrustprice));
		f.setTime(new Date());
		f.setState(-1);
		f.setIsplan(fe.getIsplan());
		databaseHelper.persistObject(f);
		
	}
	@Override
	public void addforexbuying(String userId, String sharesId, String entrustprice, String entrustnums)
			throws Exception {
		Date now = new Date();
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		if(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)>u.getVirtualcapital()){
			throw new Exception("账户金币不足");
		}
		u.setVirtualcapital(u.getVirtualcapital()-(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)));
		databaseHelper.updateObject(u);
		Shares s =(Shares)databaseHelper.getObjectById(Shares.class,Long.parseLong(sharesId));
		ForeignExchangeEntrust f =new ForeignExchangeEntrust();
		f.setFuserinfoid(Long.parseLong(userId));
		f.setFsharesid(s.getId());
		f.setForeignexchangename(s.getSharesname());
		f.setCode(s.getCode());
		f.setPrice(s.getPrice());
		f.setFrozenamount(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice));
		f.setEntrustnums(Integer.parseInt(entrustnums));
		f.setEntrustprice(Double.parseDouble(entrustprice));
		f.setTime(now);
		f.setState(0);
		databaseHelper.persistObject(f);
		//资金明细表插入一条记录
		CapitalDetail c =new CapitalDetail();
		c.setFuserinfoid(Long.parseLong(userId));
		c.setType(-4);//委托冻结资金为-4
				
		c.setCapital(Integer.parseInt(entrustnums)*Double.parseDouble(entrustprice)*-1);//支出金币为负数
		c.setCreatetime(now);
		c.setState(0);
		databaseHelper.persistObject(c);
	}
	@Override
	public void newaddDocumentaryMoney(String userId, String money, String ffollowuserinfoid)throws Exception {
		Documentary d= new Documentary();
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		d.setFuserinfoid(Long.parseLong(userId));
		d.setFfollowuserinfoid(Long.parseLong(ffollowuserinfoid));
		if(Double.parseDouble(money)>u.getVirtualcapital()){
			throw new Exception("账户金币不足");
		}
		u.setVirtualcapital(u.getVirtualcapital()-Integer.parseInt(money));
		databaseHelper.updateObject(u);
		d.setMoney(Integer.parseInt(money));
		d.setActualyamount(Double.parseDouble(money));
		d.setState(0);
		d.setCreatetime(new Date());
		databaseHelper.updateObject(d);
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		cd.setType(-5);
		cd.setCapital(Double.parseDouble(money)*-1);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
	@Override
	public void queryforexCover(String userId, String forexId)throws Exception {
		ForeignExchange sh =(ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, Long.parseLong(forexId));
		Shares s= (Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
		StringBuffer buffer = new StringBuffer("select s from ForeignExchangeEntrust s where s.state=-1 and s.fforeignexchangeid=").append(forexId);
		List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
		
		StringBuffer hql =new StringBuffer("select u from UserSharesSetting u where u.type=2 and u.fforeignid=").append(sh.getId());
		List<Object> objects = databaseHelper.getResultListByHql(hql.toString());
		if(objects.size()>0){
			UserSharesSetting us=(UserSharesSetting)objects.get(0);
			us.setState(-1);
			databaseHelper.updateObject(us);
		}
		if(list.size()>0){
			for(Object obj :list){
				ForeignExchangeEntrust se=(ForeignExchangeEntrust)obj;
				se.setState(1);
				databaseHelper.updateObject(se);
			}
		}
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		ForeignExchange fe= new ForeignExchange();
		fe.setFuserinfoid(sh.getFuserinfoid());
		fe.setFsharesid(sh.getFsharesid());
		fe.setForeignexchangename(sh.getForeignexchangename());
		fe.setCode(sh.getCode());
		fe.setPrice(sh.getPrice());
		fe.setWarehousenums(sh.getWarehousenums());
		fe.setCouldusenums(sh.getCouldusenums());
		fe.setPurchase(sh.getPurchase());
		fe.setSellout(s.getPrice());
		fe.setProfitloss(s.getPrice()-sh.getPurchase());
		fe.setTime(new Date());
		fe.setState(-1);
		fe.setIsplan(sh.getIsplan());
		fe.setFforeignexchangeid(sh.getId());
		databaseHelper.persistObject(fe);
		
		sh.setProfitloss(s.getPrice()-sh.getPrice());
		sh.setTime(new Date());
		sh.setPurchase(s.getPrice());
		sh.setCouldusenums(0);
		databaseHelper.updateObject(sh);
		double d = s.getPrice()*sh.getCouldusenums();
		u.setVirtualcapital(u.getVirtualcapital()+d);
		databaseHelper.updateObject(u);
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		cd.setType(2);
		cd.setCapital(d);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
		
	}
	@Override
	public void cutDocumentaryMoney(String userId, String money, String documentaryId) throws Exception {
		UserInfo u =(UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		
		StringBuffer buffer =new StringBuffer("select d from Documentary d where d.state=0 and d.fuserinfoid=").append(userId)
				.append(" and d.ffollowuserinfoid=").append(documentaryId);
		Documentary d=(Documentary)(databaseHelper.getResultListByHql(buffer.toString()).get(0));
		if(Double.parseDouble(money)>=d.getMoney()){
			throw new Exception("不能减少为负数");
		}
		u.setVirtualcapital(u.getVirtualcapital()+Double.parseDouble(money));
		databaseHelper.updateObject(u);
		d.setMoney(d.getMoney()-Integer.parseInt(money));
		d.setActualyamount(d.getActualyamount()-Double.parseDouble(money));
		databaseHelper.updateObject(d);
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		cd.setType(-5);
		cd.setCapital(Double.parseDouble(money));
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
		
	}
	@Override
	public void cancelDocumentaryMoney1(String userId, String documentaryId, String exampleapplyId)throws Exception {
		StringBuffer bufferr =new StringBuffer("select d from Documentary d where d.state=0 and d.fuserinfoid=").append(userId)
				.append(" and d.ffollowuserinfoid=").append(exampleapplyId);
		Documentary d=(Documentary)(databaseHelper.getResultListByHql(bufferr.toString()).get(0));
		UserInfo u = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userId));
		double vir = d.getActualyamount();
		d.setState(-1);
		d.setActualyamount(0.0);
		databaseHelper.updateObject(d);
		
		StringBuffer buffer = new StringBuffer("select s from SharesWareHouse s where s.fexampleid=").append(exampleapplyId)
				.append(" and s.state=1").append(" and s.couldusenums>0");
		List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
		if(list.size()>0){
			for(Object obj:list){
				SharesWareHouse s =(SharesWareHouse)obj;
				s.setState(0);
				databaseHelper.updateObject(s);
			}
		}
		
		
		StringBuffer buffe2 = new StringBuffer("select f from ForeignExchange f where f.fexampleid=").append(exampleapplyId)
				.append(" and f.state=1").append(" and f.couldusenums>0");
		List<Object> lst = databaseHelper.getResultListByHql(buffe2.toString());
		if(lst.size()>0){
			for(Object obj:lst){
				ForeignExchange f =(ForeignExchange)obj;
				f.setState(0);
				
				databaseHelper.updateObject(f);
			}
		}
		
		
		u.setVirtualcapital(u.getVirtualcapital()+vir);
		databaseHelper.updateObject(u);
		
		
		
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(Long.parseLong(userId));
		cd.setType(-5);
		cd.setCapital(vir);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
	@Override
	public Map<String, Object> queryYield(String month,String userId)throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		int day = Integer.parseInt(month)*30;
		Date date = new Date();
		long time = date.getTime();
		long htime = time-(24*60*60*1000*day);
		Date da=new Date(htime); 
		
		StringBuffer buffer = new StringBuffer("select s from SharesWareHouse s where s.time>").append(da)
				.append(" and s.time<").append(date).append(" and s.fuserinfoid=").append(userId);
		List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
		
		int a=0;
		int b=0;
		for(Object obj:list){
			SharesWareHouse s=(SharesWareHouse)obj;
			Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, s.getFsharesid());
			if((s.getState()==0||s.getState()==1)&&s.getCouldusenums()>0){
				a+=(sh.getPrice()-s.getPrice());
				b+=s.getPrice();
			}
			a+=s.getProfitloss();
			b+=s.getPrice();
		}
		double c =a/b*100;
		//截取小数后两位
		String s =String.format("%.2f",c)+"%";
		map.put("Yield", s);
		return map;
	}
	@Override
	public Map<String, Object> queryYieldtwo(String day, String type, String userId)throws Exception {
		
		Date date=new Date();
		
		Long time = date.getTime();
		Long htime;
		Date date2;
		htime=time-(24*60*60*1000*Integer.parseInt(day));
		date2=new Date(htime);
		
		 
		 
		if(Integer.parseInt(type)==0||Integer.parseInt(type)==1){//股票
			Map<String, Object> map=new HashMap<String, Object>();
			if(time>htime){
				StringBuffer buffer =new StringBuffer("select s from SharesWareHouse s where s.time>").append(date2)
						.append(" and s.time<").append(date).append(" and s.type=").append(Integer.parseInt(type))
						.append(" and s.fuserinfoid=").append(userId);
				List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
				int a=0;int b=0;
				for(Object obj:list){
					SharesWareHouse s =(SharesWareHouse)obj;
					Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, s.getFsharesid());
					if((s.getState()==0||s.getState()==1)&&s.getCouldusenums()>0){
						a+=(sh.getPrice()-s.getPrice());
						b+=s.getPrice();	
					}
					a+=s.getProfitloss();
					b+=s.getPrice();
				}
				double c=a/b*100;
				String s =String.format("%.2f",c)+"%";
				map.put("Yield", s);
				return map;
			}else if(time<htime){
				StringBuffer buffer =new StringBuffer("select s from SharesWareHouse s where s.type=").append(Integer.parseInt(type))
						.append(" and s.fuserinfoid=").append(userId);
						
				List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
				int a=0;int b=0;
				for(Object obj:list){
					SharesWareHouse s =(SharesWareHouse)obj;
					Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, s.getFsharesid());
					if((s.getState()==0||s.getState()==1)&&s.getCouldusenums()>0){
						a+=(sh.getPrice()-s.getPrice());
						b+=s.getPrice();
					}
					a+=s.getProfitloss();
					b+=s.getPrice();
				}
				double c=a/b*100;
				String s =String.format("%.2f",c)+"%";
				map.put("Yield", s);
				return map;	
			}
		}else if(Integer.parseInt(type)==-1){//美股
			Map<String, Object> map=new HashMap<String, Object>();
			if(time>htime){
				StringBuffer buffer = new StringBuffer("select f from ForeignExchange f where s.time>").append(date2)
						.append(" and s.time<").append(date).append(" and f.fuserinfoid=").append(userId);
				List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
				int a=0;int b=0;
				for(Object obj:list){
					ForeignExchange f=(ForeignExchange)obj;
					Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, f.getFsharesid());
					if((f.getState()==0||f.getState()==1)&&f.getCouldusenums()>0){
						a+=(sh.getPrice()-f.getPrice());
						b+=f.getPrice();
					}
					a+=f.getProfitloss();
					b+=f.getPrice();
				}
				double c=a/b*100;
				String s =String.format("%.2f",c)+"%";
				map.put("Yield", s);
				return map;	
			}else if(time<htime){
				StringBuffer buffer = new StringBuffer("select f from ForeignExchange f where f.fuserinfoid=").append(userId);
				
				List<Object> list =databaseHelper.getResultListByHql(buffer.toString());
				int a=0;int b=0;
				for(Object obj:list){
					ForeignExchange f=(ForeignExchange)obj;
					Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, f.getFsharesid());
					if((f.getState()==0||f.getState()==1)&&f.getCouldusenums()>0){
						a+=(sh.getPrice()-f.getPrice());
						b+=f.getPrice();
					}
					a+=f.getProfitloss();
					b+=f.getPrice();
				}
				double c=a/b*100;
				String s =String.format("%.2f",c)+"%";
				map.put("Yield", s);
				return map;	
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "无记录");
		return map;
	}
	@Override
	public Map<String, Object> queryYieldthird(String userId) throws Exception {
		Map<String, Object> map =new HashMap<String, Object>();
		StringBuffer buffer = new StringBuffer("select u.virtualcapital from UserInfo u where u.id=").append(userId);
		int virtualcapital=Integer.parseInt(databaseHelper.getResultListByHql(buffer.toString()).get(0).toString());
		StringBuffer buffer2 =new StringBuffer("select count(s.frozenamount) from SharesEntrust s where s.fuserinfoid=").append(userId);
		int frozenamount=Integer.parseInt(databaseHelper.getResultListByHql(buffer2.toString()).get(0).toString());
		StringBuffer buffer3 =new StringBuffer("select count(s.frozenamount) from ForeignExchangeEntrust s where s.fuserinfoid=").append(userId);
		int frozenamount2=Integer.parseInt(databaseHelper.getResultListByHql(buffer3.toString()).get(0).toString());
		StringBuffer buffer4 =new StringBuffer("select count(s.money) from Documentary s where s.fuserinfoid=").append(userId);
		int frozenamount3=Integer.parseInt(databaseHelper.getResultListByHql(buffer4.toString()).get(0).toString());
		StringBuffer buffer5=new StringBuffer("select s from SharesWareHouse s where (s.state=0 or s.state=1) and s.couldusenums>0 and s.fuserinfoid=")
				.append(userId);
		List<Object> list =databaseHelper.getResultListByHql(buffer5.toString());
		int count=0;//股票持仓总资产
		int count1=0;//外汇持仓总资产
		
		int positions=0;//持仓手数
		int positions1=0;//可用手数
		double profit=0;
		int odds=0;
		for(Object obj:list){
			SharesWareHouse s=(SharesWareHouse)obj;
			Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, s.getFsharesid());
			count+=sh.getPrice()*s.getCouldusenums();
			positions+=s.getWarehousenums();
			positions1+=s.getCouldusenums();
			profit+=(sh.getPrice()-s.getPrice())*s.getCouldusenums();
			
			if(sh.getPrice()-s.getPrice()>0){
				odds+=1;
			}
		}
		StringBuffer buffer6=new StringBuffer("select f from ForeignExchange f where (f.state=0 or f.state=1) and f.couldusenums>0 and f.fuserinfoid=")
				.append(userId);
		List<Object> list1 =databaseHelper.getResultListByHql(buffer6.toString());
		for(Object obj:list1){
			ForeignExchange f=(ForeignExchange)obj;
			Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, f.getFsharesid());
			count1+=sh.getPrice()*f.getCouldusenums();
			positions+=f.getWarehousenums();
			positions1+=f.getCouldusenums();
			profit+=(sh.getPrice()-f.getPrice())*f.getCouldusenums();
			if(sh.getPrice()-f.getPrice()>0){
				odds+=1;
			}
		}
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str =sdf.format(date);
		str=str+" 00:00:00";
		double nowDayCount = 0;//今日总收益
		DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1=sdf1.parse(str);
		StringBuffer buffer7 = new StringBuffer("select count(capital) from CapitalDetail c where c.state=1 or c.state=2 and c.createtime>").append(date1);
		List<Object> list2 = databaseHelper.getResultListByHql(buffer7.toString());
		if(list2.size()==0){
			nowDayCount=0;
		}else if(list2.size()>0){
			nowDayCount=Double.parseDouble(list2.get(0).toString());
		}
		StringBuffer buffer8 = new StringBuffer("select count(*) from SharesWareHouse s where s.couldusenums>0");
		StringBuffer buffer9 = new StringBuffer("select count(*) from ForeignExchange f where f.couldusenums>0");
		int tradingNumber=Integer.parseInt(databaseHelper.getResultListByHql(buffer8.toString()).get(0).toString())+Integer.parseInt(databaseHelper.getResultListByHql(buffer9.toString()).get(0).toString());
		StringBuffer buffer10 = new StringBuffer("select s.time from SharesWareHouse s where s.couldusenums>0 order by time");
		StringBuffer buffer11 = new StringBuffer("select f.time from ForeignExchange f where f.couldusenums>0 order by time");
		List<Object> list3 = databaseHelper.getResultListByHql(buffer10.toString());
		Date d1=sdf.parse(list3.get(0).toString());
		Date d2=sdf.parse(list3.get(list3.size()-1).toString());
		List<Object> list4 = databaseHelper.getResultListByHql(buffer11.toString());
		Date d3=sdf.parse(list4.get(0).toString());
		Date d4=sdf.parse(list4.get(list4.size()-1).toString());
		long l=0;
		if(d1.getTime()>=d2.getTime()){
			if(d3.getTime()>=d4.getTime()){
				 l=d3.getTime()-d2.getTime();
			}else{
				l=d4.getTime()-d2.getTime();
			}
		}else{
			if(d3.getTime()>=d4.getTime()){
				 l=d3.getTime()-d1.getTime();
			}else{
				l=d4.getTime()-d1.getTime();
			}
		}
		double d=l/24*60*60*100*30;
		int l1=(int)d+1;
		
		
		double tradingMon=tradingNumber/l1;
		String tradingMoney=String.format("%.2f",tradingMon)+"%";
		double c =positions1/positions*100;
		String s =String.format("%.2f",c)+"%";
		int allMoney=virtualcapital+frozenamount+frozenamount2+frozenamount3+count+count1;
		
		StringBuffer buffer12=new StringBuffer("select s from SharesWareHouse s where s.state=-1");
		List<Object> list5 =databaseHelper.getResultListByHql(buffer12.toString());
		int countSell=0;
		int countWall=0;
		for(Object obj:list5){
			SharesWareHouse s1=(SharesWareHouse)obj;
			Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, s1.getFsharesid());
			countSell+=1;
			if(sh.getPrice()>s1.getPrice()){
				countWall+=1;
			}
		}
		StringBuffer buffer13=new StringBuffer("select s from ForeignExchange f where f.state=-1");
		List<Object> list6 =databaseHelper.getResultListByHql(buffer13.toString());
		for(Object obj:list6){
			ForeignExchange f=(ForeignExchange)obj;
			Shares sh =(Shares)databaseHelper.getObjectById(Shares.class, f.getFsharesid());
			countSell+=1;
			if(sh.getPrice()>f.getPrice()){
				countWall+=1;
			}
		}
		String wall = String.format("%.2f",countWall/countSell)+"%";
		map.put("allMoney", allMoney);//总资产
		map.put("positions", s);//持仓仓位
		map.put("profit", profit);//持仓盈亏
		map.put("nowDayCount", nowDayCount+profit);//今日收益
		map.put("tradingNumber", tradingNumber);//总交易次数
		map.put("tradingMoney", tradingMoney);//月均交易数
		map.put("wall", wall);//交易胜率
		return map;
	}
	@Override
	public Map<String, Object> queryRevokeMessage(String userId, String shareId, String type)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(Integer.parseInt(type)==0){
			
			SharesEntrust s =(SharesEntrust)databaseHelper.getObjectById(SharesEntrust.class, Long.parseLong(shareId));
			map.put("sharesname", s.getSharesname());
			map.put("entrustprice", s.getEntrustprice());
			map.put("entrustnums", s.getEntrustnums());
			System.out.println(s.getFshareswarehouseid());
			if(s.getFshareswarehouseid()==null){
				map.put("way", "买入");
			}else {
				map.put("way", "卖出");
			}
			
			
		}else if(Integer.parseInt(type)==1){
			ForeignExchangeEntrust f=(ForeignExchangeEntrust)databaseHelper.getObjectById(ForeignExchangeEntrust.class, Long.parseLong(shareId));
			map.put("sharesname", f.getForeignexchangename());
			map.put("entrustprice", f.getEntrustprice());
			map.put("entrustnums", f.getEntrustnums());
			System.out.println();
			if(f.getFforeignexchangeid()==null){
				map.put("way", "买入");
			}else{
				map.put("way", "卖出");
			}
			
		}
		return map;
	}
	@Override
	public Map<String, Object> queryTradingDetail(String userId, String shareId, String type,Integer start,Integer limit)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> rowList2 = new ArrayList<Map<String,Object>>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int count=0;
		int pages=0;
		if(Integer.parseInt(type)==0){//股票
			
			StringBuffer sql =new StringBuffer("select count(s.id) from shareswarehouse s where s.state=-1 and s.fshareswarehouseid=").append(shareId);
			count=databaseHelper.getSqlCount(sql.toString());
			pages = ConstantUtil.pages(count,limit);
			if(count>0){
			StringBuffer buffer =new StringBuffer("select s from SharesWareHouse s where s.state=-1 and s.fshareswarehouseid=").append(shareId)
					.append(" order by s.time DESC");
			List<Object> list =databaseHelper.getResultListByHql(buffer.toString(),start,limit);
			
			
				for(Object obj:list){
					SharesWareHouse s =(SharesWareHouse)obj;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sharesname", s.getSharesname());
					map.put("code", s.getCode());
					map.put("warehousenums", s.getWarehousenums());//仓位
					/*map.put("marketvalue",s.getMarketvalue());//市值
*/					map.put("couldusenums", s.getCouldusenums());//卖出数量
					map.put("price", s.getPrice());//卖出价
					/*map.put("profitloss", s.getProfitloss());//盈亏
*/					map.put("time", formatter.format(s.getTime()));//时间
					rowList.add(map);
					
				}
				
				
			}	
			StringBuffer buffer1 =new StringBuffer("select s from SharesWareHouse s where s.state=0 and s.id=").append(shareId);
			SharesWareHouse ss=(SharesWareHouse)databaseHelper.getResultListByHql(buffer1.toString()).get(0);
			Map<String, Object> m= new HashMap<String, Object>();
			
			Shares sh=(Shares)databaseHelper.getObjectById(Shares.class, ss.getFsharesid());
			m.put("profitloss",String.format("%.2f",ss.getProfitloss()));//浮动盈亏
			m.put("profitb", String.format("%.2f",(ss.getPrice()-sh.getPrice())/sh.getPrice())+"%");//盈亏比例
			m.put("marketvalue", ss.getMarketvalue());//市值金额
			m.put("couldusenums", ss.getCouldusenums());//可用手数
			m.put("cost",ss.getCost());//成本
			m.put("price",sh.getPrice());//现价
			m.put("warehousenums",ss.getWarehousenums());//持仓手数
			rowList2.add(m);
		}else if(Integer.parseInt(type)==1){
			StringBuffer sql =new StringBuffer("select count(f.id) from foreignexchange f where f.state=-1 and f.fforeignexchangeid=").append(shareId);
			count=databaseHelper.getSqlCount(sql.toString());
			pages=ConstantUtil.pages(count, limit);
			if(count>0){
			StringBuffer buffer =new StringBuffer("select f from ForeignExchange f where f.state=-1 and f.fforeignexchangeid=").append(shareId)
					.append(" order by f.time DESC");
			List<Object> list =databaseHelper.getResultListByHql(buffer.toString(),start,limit);
			
				for(Object obj:list){
					Map<String, Object> map = new HashMap<String, Object>();
					ForeignExchange f =(ForeignExchange)obj;
					map.put("sharesname", f.getForeignexchangename());
					map.put("code", f.getCode());
					
					map.put("warehousenums", f.getWarehousenums());//仓位
					map.put("couldusenums", f.getCouldusenums());//卖出数量
					map.put("price", f.getSellout());//卖出价
					map.put("time", formatter.format(f.getTime()));//时间
					/*map.put("profitloss", f.getProfitloss());*/
					rowList.add(map);
				}
				
			}
			StringBuffer buffer1 =new StringBuffer("select s from ForeignExchange s where s.state=0 and s.id=").append(shareId);
			ForeignExchange ss=(ForeignExchange)databaseHelper.getResultListByHql(buffer1.toString()).get(0);
			Map<String, Object> m= new HashMap<String, Object>();
			StringBuffer buffer2 =new StringBuffer("select s from UserSharesSetting s where s.type=2 and s.fforeignid=").append(shareId);
			List<Object> list=databaseHelper.getResultListByHql(buffer2.toString());
			
			Shares sh=(Shares)databaseHelper.getObjectById(Shares.class, ss.getFsharesid());
			m.put("profitloss",ss.getProfitloss());//浮动盈亏
			m.put("profitb", String.format("%.2f",(ss.getPrice()-sh.getPrice())/sh.getPrice())+"%");//盈亏比例
			m.put("marketvalue", ss.getPurchase());//金额
			
			
			m.put("price",ss.getPrice());//买入价
			if(list.size()>0){
				UserSharesSetting uu=(UserSharesSetting)databaseHelper.getResultListByHql(buffer2.toString()).get(0);
				m.put("stoploss", uu.getStoploss());//止损点数
				m.put("stopprofit", uu.getStopprofit());//止盈点数
			}else{
				m.put("stoploss", "未设置");//止损点数
				m.put("stopprofit", "未设置");//止盈点数
			}
			m.put("warehousenums",ss.getWarehousenums());//持仓手数
			
			rowList2.add(m);
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		
		resultMap.put("rows", rowList);
		resultMap.put("rows2", rowList2);
		return resultMap;
	}
	@Override
	public Map<String, Object> querySharesCheck(String userid, String type, String shareid) throws Exception {
		StringBuffer buffer = new StringBuffer("select u.stoploss,u.stopprofit,u.id from UserSharesSetting u where u.fuserinfoid=")
				.append(userid).append(" and u.type = ").append(type).append(" and u.fforeignid=").append(shareid);
		Map<String,Object> map=new HashMap<String, Object>();
		List<Object[]> list =databaseHelper.getResultListByHql(buffer.toString());
		 
		if(Integer.parseInt(type)==1){
			SharesWareHouse sh=(SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, Long.parseLong(shareid));
			Shares s=(Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
			map.put("hprice", sh.getPrice());//买入价
			map.put("price", s.getPrice());//卖出价
		}else if (Integer.parseInt(type)==2) {
			ForeignExchange sh=(ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, Long.parseLong(shareid));
			Shares s=(Shares)databaseHelper.getObjectById(Shares.class, sh.getFsharesid());
			map.put("hprice", sh.getPrice());
			map.put("price", s.getPrice());
		}
		if(list.size()>0){
			map.put("stoploss", list.get(0)[0].toString());
			map.put("stopprofit", list.get(0)[1].toString());
			map.put("usersharessettingid", list.get(0)[2].toString());//传入的id
		}
		return map;
	}
	@Override
	public void ModifySharesCheck(String userid, String type, String stoploss, String stopprofit, String shareid)
			throws Exception {
		if(Integer.parseInt(type)==1){
			
		}
		
	}

}
