package com.hxy.isw.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.ForeignExchangeEntrust;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesEntrust;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.TradeInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;
import com.hxy.isw.util.ThreadPoolManager;

/**
 * 检查挂单
 * @author lcc
 *
 */
public class CheckEntrust implements Runnable {
	SimpleDateFormat sdf = null;
	public static DatabaseHelper databaseHelper;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		try {
			while(true){
				checkSharesEntrust(sd);
				checkForeignexchangeEntrust();
				Thread.sleep(1000l*60*30);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//股票挂单
	public void checkSharesEntrust(SyncDemo_股票行情 sd) throws Exception{
		//找出所有的股票委托
		StringBuffer find = new StringBuffer("select se,s,ui from SharesEntrust se,Shares s,UserInfo ui where s.state >-2 and s.type < 2 and se.fsharesid = s.id and se.fuserinfoid = ui.id and se.state < 1 and se.entruststate = 0 ");
		List<Object[]> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object[] objects : lst) {
			SharesEntrust se = (SharesEntrust)objects[0];
			Shares s = (Shares)objects[1];
			UserInfo ui = (UserInfo)objects[2];
			
			if(s.getType()==0){//沪深股票
				//查看股票行情
				ApiResponse response = sd.股票行情(s.getCode(), "0", "0");
				
				String result = new String(response.getBody(), "utf-8");
				Sys.out("checkEntrust......"+result);
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				
				if(json.get("showapi_res_code").getAsInt()!=0)continue;
				
				JsonObject jsonObject = json.get("showapi_res_body").getAsJsonObject().get("stockMarket").getAsJsonObject();
				
				String code = jsonObject.get("code").getAsString();//股票代码
				String diff_money = jsonObject.get("diff_money").getAsString();//涨跌金额
				String diff_rate = jsonObject.get("diff_rate").getAsString();//涨跌幅度
				String price = jsonObject.get("nowPrice").getAsString();//当前价
				
				s.setDiffmoney(diff_money);
				s.setDiffrate(diff_rate);
				s.setPrice(Double.parseDouble(price));
				if(Double.parseDouble(diff_money)>=0)s.setUpanddown("涨");
				else s.setUpanddown("跌");
				
				s.setQuotation(jsonObject.toString());
				s.setLastupdate(new Date());
				databaseHelper.updateObject(s);
				
				
			}else {//美股
				/*String result = NowApiUtil.getShareIndexOfUS(s.getCode());
				 
				 Sys.out("GetDataFromUS..."+sdf.format(new Date())+"..."+result);
				
				 JsonObject json = (JsonObject) new JsonParser().parse(result);
					
				JsonObject obj = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(s.getCode()).getAsJsonObject();
				
				String price = obj.get("last_price").getAsString();*/
				
				String price = JuheData.getnowprice(s.getPinyin());
				
				s.setPrice(Double.parseDouble(price));
				
				databaseHelper.updateObject(s);
			}
			
			if(se.getState()==0){//买入
				//当股票市价小于等于委托价时，买入
				if(s.getPrice()<=se.getEntrustprice()){
					//检查用户是否有此股票的持仓
					
					StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 0 ")
							.append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(se.getFsharesid());
					List<Object> clst = databaseHelper.getResultListByHql(check.toString());
					
					SharesWareHouse swh = null;
					if(clst.size()==0){
						swh = new SharesWareHouse();
						swh.setFdocumentaryid(0l);
						swh.setFexampleid(0l);
						swh.setFuserinfoid(ui.getId());
						swh.setIsplan(se.getIsplan());
						swh.setState(0);
						swh.setTime(new Date());
						swh.setCouldusenums(se.getEntrustnums());
						swh.setWarehousenums(se.getEntrustnums());
						swh.setCost(se.getEntrustnums()*s.getPrice());
						swh.setCode(s.getCode());
						swh.setFexampleplanid(0l);//?存疑，是否有计划id
						swh.setFsharesid(s.getId());
						swh.setFshareswarehouseid(0l);
						swh.setMarket(s.getMarket());
						swh.setMarketvalue(se.getEntrustnums()*s.getPrice());
						swh.setPrice(s.getPrice());
						swh.setProfitloss(0d);
						swh.setSharesname(s.getSharesname());
						swh.setType(s.getType());
						
						databaseHelper.persistObject(swh);
					}else{
						
						swh = (SharesWareHouse)clst.get(0);
						swh.setCouldusenums(swh.getCouldusenums()+se.getEntrustnums());
						swh.setWarehousenums(swh.getWarehousenums()+se.getEntrustnums());
						swh.setCost(swh.getCost()+se.getEntrustnums()*s.getPrice());
						swh.setMarketvalue(swh.getCost()+se.getEntrustnums()*s.getPrice());
						swh.setTime(new Date());
						databaseHelper.updateObject(swh);
					}
					
					
					//添加到资金明细表
					CapitalDetail cd = new CapitalDetail();
					cd.setFuserinfoid(ui.getId());
					cd.setType(swh.getType()==0?-1:-3);
					cd.setCapital(se.getEntrustnums()*s.getPrice());
					cd.setCreatetime(new Date());
					cd.setState(0);
					databaseHelper.persistObject(cd);
					
					//将单置为已匹配
					se.setEntruststate(1);
					databaseHelper.updateObject(se);
					//检查是否还有剩余资金
					if(se.getFrozenamount()>se.getEntrustnums()*s.getPrice()){
						//返还给用户
						ui.setVirtualcapital(ui.getVirtualcapital()+(se.getFrozenamount()-se.getEntrustnums()*s.getPrice()));
						databaseHelper.updateObject(ui);
					}
					
					//添加到tradeinfo表
					TradeInfo ti = new TradeInfo();
					ti.setAmount(swh.getCost());
					ti.setFexampleid(swh.getFexampleid());
					ti.setFuserid(swh.getFuserinfoid());
					ti.setNums(swh.getCouldusenums());
					ti.setProfit(swh.getProfitloss());
					ti.setSharename(swh.getSharesname());
					ti.setTime(swh.getTime());
					ti.setTradetype(1);
					ti.setType(swh.getType());
					databaseHelper.persistObject(ti);
					
					if(ui.getIsexample()==1&&"0".equals(se.getIsplan())){//启动线程
						FollowExampleBuy febs = new FollowExampleBuy();
						febs.sharesWareHouse = swh;
						ThreadPoolManager.exec(febs);
					}

				}
			}else if(se.getState()==-1){//卖出
				//当股票市价大于等于委托价时，卖出
				if(s.getPrice()>=se.getEntrustprice()){
					//检查用户是否有此股票的持仓
					StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 0 ")
							.append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(se.getFsharesid());
					List<Object> clst = databaseHelper.getResultListByHql(check.toString());
					
					if(clst.size()>0){
						SharesWareHouse swh = (SharesWareHouse)clst.get(0);
						
						swh.setCouldusenums(swh.getCouldusenums()-se.getEntrustnums());
						swh.setMarketvalue(swh.getMarketvalue()-se.getEntrustnums()*s.getPrice());
						databaseHelper.updateObject(swh);
						
						StringBuffer checklog = new StringBuffer("select swh from SharesWareHouse swh where swh.state = -1 ").append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(se.getFsharesid());
						List<Object> checklst = databaseHelper.getResultListByHql(checklog.toString());
						
						SharesWareHouse sellswh =null;
						if(checklst.size()==0){
							sellswh = new SharesWareHouse();
							sellswh.setFdocumentaryid(0l);
							sellswh.setFexampleid(0l);
							sellswh.setFuserinfoid(ui.getId());
							sellswh.setIsplan(se.getIsplan());
							sellswh.setState(-1);
							sellswh.setTime(new Date());
							sellswh.setCouldusenums(se.getEntrustnums());
							sellswh.setWarehousenums(se.getEntrustnums());
							sellswh.setCost(se.getEntrustnums()*s.getPrice());
							sellswh.setCode(s.getCode());
							sellswh.setFexampleplanid(0l);//?存疑，是否有计划id
							sellswh.setFsharesid(s.getId());
							sellswh.setFshareswarehouseid(swh.getId());
							sellswh.setMarket(s.getMarket());
							sellswh.setMarketvalue(se.getEntrustnums()*s.getPrice());
							sellswh.setPrice(s.getPrice());
							sellswh.setProfitloss(0d);
							sellswh.setSharesname(s.getSharesname());
							sellswh.setType(s.getType());
							
							databaseHelper.persistObject(sellswh);
						}else{
							sellswh = (SharesWareHouse)checklst.get(0);
							sellswh.setCouldusenums(sellswh.getCouldusenums()+se.getEntrustnums());
							sellswh.setWarehousenums(sellswh.getWarehousenums()+se.getEntrustnums());
							sellswh.setCost(sellswh.getCost()+se.getEntrustnums()*s.getPrice());
							
							databaseHelper.updateObject(sellswh);
						}
						
						//添加到资金明细表
						CapitalDetail cd = new CapitalDetail();
						cd.setFuserinfoid(ui.getId());
						cd.setType(swh.getType()==0?1:3);
						cd.setCapital(se.getEntrustnums()*s.getPrice());
						cd.setCreatetime(new Date());
						cd.setState(0);
						databaseHelper.persistObject(cd);
						
						//将单置为已匹配
						se.setEntruststate(1);
						databaseHelper.updateObject(se);
						
						//用户虚拟资金
						ui.setVirtualcapital(ui.getVirtualcapital()+se.getEntrustnums()*s.getPrice());
						databaseHelper.updateObject(ui);
						
						//添加到tradeinfo表
						TradeInfo ti = new TradeInfo();
						ti.setAmount(swh.getCost());
						ti.setFexampleid(swh.getFexampleid());
						ti.setFuserid(swh.getFuserinfoid());
						ti.setNums(swh.getCouldusenums());
						ti.setProfit(swh.getProfitloss());
						ti.setSharename(swh.getSharesname());
						ti.setTime(swh.getTime());
						ti.setTradetype(-1);
						ti.setType(swh.getType());
						databaseHelper.persistObject(ti);
						
						if(ui.getIsexample()==1&&"0".equals(se.getIsplan())){//启动线程
							FollowExampleSell fes = new FollowExampleSell();
							fes.sharesWareHouse = sellswh;
							ThreadPoolManager.exec(fes);
						}
					}
				}
			}
		}
	}
	
	
	//外汇挂单
	private void checkForeignexchangeEntrust() throws Exception{
		//找出所有的外汇委托
		StringBuffer find = new StringBuffer("select fe,s,ui from ForeignExchangeEntrust fe,Shares s,UserInfo ui where  s.state >-2 and s.type = 2 and fe.fsharesid = s.id and fe.fuserinfoid = ui.id and fe.state < 1 and fe.entruststate = 0 ");
		List<Object[]> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object[] objects : lst) {
			ForeignExchangeEntrust se = (ForeignExchangeEntrust)objects[0];
			Shares s = (Shares)objects[1];
			UserInfo ui = (UserInfo)objects[1];
			
			/*String result = NowApiUtil.getWHIndex(s.getCode());
			 
			Sys.out("getData4whIndex..."+result);
			 
			
			JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject();
			
			String name = obj.get("ratenm").getAsString();
			String price = obj.get("rate").getAsString();
			String code = obj.get("scur").getAsString();
		
			s.setPrice(Double.parseDouble(price));
			s.setLastupdate(new Date());
			databaseHelper.updateObject(s);*/
			
			
			/*String res = NowApiUtil.getWHIndexByAli(s.getCode());
			
			JsonObject json = (JsonObject) new JsonParser().parse(res);
			
			Double price = 0d;
			if("ok".equals(json.get("msg").getAsString())){
			
				JsonObject jo = json.get("result").getAsJsonObject();
			
				price = jo.get("rate").getAsDouble();
				
				String name = jo.get("fromname").getAsString();
				String code = jo.get("scur").getAsString();
			
				s.setPrice(price);
				s.setLastupdate(new Date());
				databaseHelper.updateObject(s);
			}*/
			Double price = Double.parseDouble(JuheData.getnowprice(s.getCode()));
			
			s.setPrice(price);
			s.setLastupdate(new Date());
			databaseHelper.updateObject(s);
			
			if(se.getState()==0){//买入
				//当外汇市价小于等于委托价时，买入
				if(s.getPrice()<=se.getEntrustprice()){
					//检查用户是否有此外汇的持仓
					
					StringBuffer check = new StringBuffer("select fe from ForeignExchange fe where fe.state = 0 ")
							.append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(se.getFsharesid());
					List<Object> clst = databaseHelper.getResultListByHql(check.toString());
					
					ForeignExchange fe = null;
					if(clst.size()==0){
						fe = new ForeignExchange();
						fe.setFdocumentaryid(0l);
						fe.setFexampleid(0l);
						fe.setFuserinfoid(ui.getId());
						fe.setIsplan(se.getIsplan());
						fe.setState(0);
						fe.setTime(new Date());
						fe.setCouldusenums(se.getEntrustnums());
						fe.setWarehousenums(se.getEntrustnums());
						fe.setPurchase(se.getEntrustnums()*s.getPrice());
						fe.setCode(s.getCode());
						fe.setFexampleplanid(0l);//?存疑，是否有计划id
						fe.setFsharesid(s.getId());
						fe.setFforeignexchangeid(0l);
						fe.setSellout(0d);
						fe.setPrice(s.getPrice());
						fe.setProfitloss(0d);
						fe.setForeignexchangename(s.getSharesname());
						fe.setBuytype(se.getBuytype());
						databaseHelper.persistObject(fe);
					}else{
						
						fe = (ForeignExchange)clst.get(0);
						fe.setCouldusenums(fe.getCouldusenums()+se.getEntrustnums());
						fe.setWarehousenums(fe.getWarehousenums()+se.getEntrustnums());
						fe.setPurchase(fe.getPurchase()+se.getEntrustnums()*s.getPrice());
						fe.setTime(new Date());
						databaseHelper.updateObject(fe);
					}
					
					
					//添加到资金明细表
					CapitalDetail cd = new CapitalDetail();
					cd.setFuserinfoid(ui.getId());
					cd.setType(-2);
					cd.setCapital(se.getEntrustnums()*s.getPrice());
					cd.setCreatetime(new Date());
					cd.setState(0);
					databaseHelper.persistObject(cd);
					
					//将单置为已匹配
					se.setEntruststate(1);
					databaseHelper.updateObject(se);
					//检查是否还有剩余资金
					if(se.getFrozenamount()>se.getEntrustnums()*s.getPrice()){
						//返还给用户
						ui.setVirtualcapital(ui.getVirtualcapital()+(se.getFrozenamount()-se.getEntrustnums()*s.getPrice()));
						databaseHelper.updateObject(ui);
					}
					
					//添加到tradeinfo表
					TradeInfo ti = new TradeInfo();
					ti.setAmount(fe.getPurchase());
					ti.setFexampleid(fe.getFexampleid());
					ti.setFuserid(fe.getFuserinfoid());
					ti.setNums(fe.getCouldusenums());
					ti.setProfit(fe.getProfitloss());
					ti.setSharename(fe.getForeignexchangename());
					ti.setTime(fe.getTime());
					ti.setTradetype(1);
					ti.setType(2);
					databaseHelper.persistObject(ti);
					
					if(ui.getIsexample()==1&&"0".equals(se.getIsplan())){//启动线程
						FollowExampleBuy feb = new FollowExampleBuy();
						feb.foreignexchange = fe;
						ThreadPoolManager.exec(feb);
					}

				}
			}else if(se.getState()==-1){//卖出
				//当外汇市价大于等于委托价时，卖出
				if(s.getPrice()>=se.getEntrustprice()){
					//检查用户是否有此外汇的持仓
					StringBuffer check = new StringBuffer("select fe from ForeignExchange fe where fe.state = 0 ")
							.append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(se.getFsharesid());
					List<Object> clst = databaseHelper.getResultListByHql(check.toString());
					
					if(clst.size()>0){
						ForeignExchange fe = (ForeignExchange)clst.get(0);
						
						fe.setCouldusenums(fe.getCouldusenums()-se.getEntrustnums());
						databaseHelper.updateObject(fe);
						
						StringBuffer checklog = new StringBuffer("select fe from ForeignExchange fe where fe.state = -1 ").append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(se.getFsharesid());
						List<Object> checklst = databaseHelper.getResultListByHql(checklog.toString());
						
						ForeignExchange sellfe =null;
						if(checklst.size()==0){
							sellfe = new ForeignExchange();
							sellfe.setFdocumentaryid(0l);
							sellfe.setFexampleid(0l);
							sellfe.setFuserinfoid(ui.getId());
							sellfe.setIsplan(se.getIsplan());
							sellfe.setState(-1);
							sellfe.setTime(new Date());
							sellfe.setCouldusenums(se.getEntrustnums());
							sellfe.setWarehousenums(se.getEntrustnums());
							sellfe.setPurchase(se.getEntrustnums()*s.getPrice());
							sellfe.setCode(s.getCode());
							sellfe.setFexampleplanid(0l);//?存疑，是否有计划id
							sellfe.setFsharesid(s.getId());
							sellfe.setFforeignexchangeid(fe.getId());
							sellfe.setSellout(se.getEntrustnums()*s.getPrice());
							sellfe.setPrice(s.getPrice());
							sellfe.setProfitloss(0d);
							sellfe.setForeignexchangename(s.getSharesname());
							sellfe.setBuytype(se.getBuytype());
							databaseHelper.persistObject(sellfe);
						}else{
							sellfe = (ForeignExchange)checklst.get(0);
							sellfe.setCouldusenums(sellfe.getCouldusenums()+se.getEntrustnums());
							sellfe.setWarehousenums(sellfe.getWarehousenums()+se.getEntrustnums());
							sellfe.setSellout(sellfe.getSellout()+se.getEntrustnums()*s.getPrice());
							
							databaseHelper.updateObject(sellfe);
						}
						
						//添加到资金明细表
						CapitalDetail cd = new CapitalDetail();
						cd.setFuserinfoid(ui.getId());
						cd.setType(2);
						cd.setCapital(se.getEntrustnums()*s.getPrice());
						cd.setCreatetime(new Date());
						cd.setState(0);
						databaseHelper.persistObject(cd);
						
						//将单置为已匹配
						se.setEntruststate(1);
						databaseHelper.updateObject(se);
						
						//用户虚拟资金
						ui.setVirtualcapital(ui.getVirtualcapital()+se.getEntrustnums()*s.getPrice());
						databaseHelper.updateObject(ui);
						
						//添加到tradeinfo表
						TradeInfo ti = new TradeInfo();
						ti.setAmount(fe.getPurchase());
						ti.setFexampleid(fe.getFexampleid());
						ti.setFuserid(fe.getFuserinfoid());
						ti.setNums(fe.getCouldusenums());
						ti.setProfit(fe.getProfitloss());
						ti.setSharename(fe.getForeignexchangename());
						ti.setTime(fe.getTime());
						ti.setTradetype(-1);
						ti.setType(2);
						databaseHelper.persistObject(ti);
						
						if(ui.getIsexample()==1&&"0".equals(se.getIsplan())){//启动线程
							FollowExampleSell fes = new FollowExampleSell();
							fes.foreignexchange = sellfe;
							ThreadPoolManager.exec(fes);
						}
					}
				}
			}
		}
	}
}
