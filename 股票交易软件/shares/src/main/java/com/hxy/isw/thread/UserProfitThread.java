package com.hxy.isw.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.ExamplePlan;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserProfit;
import com.hxy.isw.entity.UserProfitStatistic;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年8月11日 下午2:27:53
* @describe 用户收益记录
*/
public class UserProfitThread  implements Runnable {

	SimpleDateFormat sdf = null;
	public static DatabaseHelper databaseHelper;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		try {
			while(true){
				userProfit(sd);
				userProfit4wh();
				Thread.sleep(1000l*60*30);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void userProfit(SyncDemo_股票行情 sd) throws Exception{
		
		//找出所有用户的股票持仓记录
		StringBuffer find = new StringBuffer("select swh,ui from SharesWareHouse swh,UserInfo ui where ui.id = swh.fuserinfoid and swh.state > -1 ");
		List<Object[]> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object[] objects : lst) {
			SharesWareHouse swh = (SharesWareHouse)objects[0];
			UserInfo ui = (UserInfo)objects[1];
			
			String price = "0",diffrate="0",diffmoney="0";
			
			if(swh.getType()==0){//沪深股票
				//查看股票行情
				ApiResponse response = sd.股票行情(swh.getCode(), "0", "0");
				
				String result = new String(response.getBody(), "utf-8");
				Sys.out("checkEntrust......"+result);
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				
				if(json.get("showapi_res_code").getAsInt()!=0)return;
				
				JsonObject jsonObject = json.get("showapi_res_body").getAsJsonObject().get("stockMarket").getAsJsonObject();
				
				price = jsonObject.get("nowPrice").getAsString();//当前价
				diffrate = jsonObject.get("diff_rate").getAsString();//涨跌幅度
				diffmoney = jsonObject.get("diff_money").getAsString();//涨跌金额
				
			}else{//美股
				/*String result = NowApiUtil.getShareIndexOfUS(swh.getCode());
				 
				 Sys.out("GetDataFromUS......"+result);
				
				 JsonObject json = (JsonObject) new JsonParser().parse(result);
					if(json.get("success").getAsInt()==1){
					JsonObject obj = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(swh.getCode()).getAsJsonObject();
					
					price = obj.get("last_price").getAsString();
					
					diffrate = obj.get("rise_fall").getAsString();
					diffmoney = obj.get("rise_fall_per").getAsString();
				}else{
					price = "0";
					
					diffrate = "0";
					diffmoney = "0";
				}*/
				
				Shares s = (Shares)databaseHelper.getObjectById(Shares.class, swh.getFsharesid());
				
				price = JuheData.getnowprice(s.getPinyin());
				
				 JsonObject jObject = JuheData.getRequest3(s.getCode());
				 if(jObject!=null){
					 diffrate = jObject.get("limit").getAsString();
					 diffmoney = jObject.get("uppic").getAsString();
				 }
			}
			
			
			UserProfit userprofit = new UserProfit();
			userprofit.setCost(swh.getCost());
			userprofit.setDiffprice(Double.parseDouble(price)-swh.getPrice());
			
			//找出最后一次的收益记录
			find = new StringBuffer("select userprofit from UserProfit userprofit  where userprofit.type =").append(swh.getType()).append(" and userprofit.fuserinfoid = ").append(swh.getFuserinfoid()).append(" order by userprofit.time desc");
			List<Object> last = databaseHelper.getResultListByHql(find.toString());
			if(last.size()==0){
				userprofit.setDiffpriceoflasttime(0d);
				userprofit.setDiffprofitoflasttime(0d);
				userprofit.setDiffprofitoflasttimerate(0d);
			}else{
				UserProfit lastuserprofit = (UserProfit)last.get(0);
				userprofit.setDiffpriceoflasttime(Double.parseDouble(price)-lastuserprofit.getPrice());//与上次市价的差值
				userprofit.setDiffprofitoflasttime(userprofit.getDiffpriceoflasttime()*swh.getCouldusenums());//与上次市价的差值收益 = diffpriceoflasttime*nums
				userprofit.setDiffprofitoflasttimerate(userprofit.getCost()==0d?0:userprofit.getDiffprofitoflasttime()/userprofit.getCost());//与上次市价的差值收益率 = diffpriceoflasttime/cost
			}
			
			userprofit.setForeignkeyid(swh.getId());
			userprofit.setFuserinfoid(swh.getFuserinfoid());
			userprofit.setNums(swh.getCouldusenums());
			userprofit.setPrice(Double.parseDouble(price));
			userprofit.setProfit(userprofit.getDiffprice()*swh.getCouldusenums());
			userprofit.setProfitrate(userprofit.getCost()==0d?0:userprofit.getDiffprice()/userprofit.getCost());
			userprofit.setTags(swh.getState()==0?1:2);
			userprofit.setTime(new Date());
			userprofit.setType(swh.getType());
			userprofit.setIsplan(swh.getIsplan());
			databaseHelper.persistObject(userprofit);
			
			//查看用户粉丝数
			find = new StringBuffer("select count(f.id) from Follow f where f.state = 0 and f.ffollowedid = ").append(ui.getId());
			
			List<Object> flst = databaseHelper.getResultListByHql(find.toString());
			int fans =  Integer.parseInt(flst.get(0).toString());
			
			Double hsplansuccess = 0d,usplansuccess = 0d,whplansuccess = 0d,plansuccess = 0d;
			Double hsplanfrofit = 0d,usplanfrofit = 0d,whplanfrofit = 0d,totalplanfrofit = 0d;
			int money = 0;
			//如果用户是牛人，计算用户的计划成功率，收到的打赏金币
			if(ui.getIsexample()==1){
				find = new StringBuffer("select exampleplan from ExamplePlan exampleplan where exampleplan.state = 2 and exampleplan.fuserinfoid = ").append(ui.getId());
				List<Object> examplelst = databaseHelper.getResultListByHql(find.toString());
				
				int examplenums = examplelst.size();
				int successnums = 0,hsplansuccessnums = 0,usplansuccessnums = 0,whplansuccessnums = 0;
				int hsplannums = 0,usplannums = 0,whplannums = 0;
				for (Object object : examplelst) {
					ExamplePlan exampleplan = (ExamplePlan)object;
					
					totalplanfrofit+=exampleplan.getActualprofit();
						if(exampleplan.getType()==0){
							hsplannums++;
							hsplanfrofit += exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								hsplansuccessnums++;
								successnums++;
							}
						}else if(exampleplan.getType()==1){
							usplannums++;
							usplanfrofit+=exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								usplansuccessnums++;
								successnums++;
							}
						}else if(exampleplan.getType()==2){
							whplannums++;
							whplanfrofit+=exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								whplansuccessnums++;
								successnums++;
							}
						}
					
				}
				if(examplenums>0){
					plansuccess = ConstantUtil.intdevice2(successnums,examplenums,true);
					if(hsplannums>0)hsplansuccess = ConstantUtil.intdevice2(hsplansuccessnums,hsplannums,true);
					if(usplannums>0)usplansuccess = ConstantUtil.intdevice2(usplansuccessnums,usplannums,true);
					if(whplannums>0)whplansuccess = ConstantUtil.intdevice2(whplansuccessnums,whplannums,true);
				}
				
				
				find = new StringBuffer("select sum(rl.money) 'a' from rewardlog rl where rl.frewardedid = ").append(ui.getId());
				List<Object> rlst = databaseHelper.getResultListBySql(find.toString());
				
				money = flst==null?0:flst.size()==0?0:flst.get(0)==null?0:Integer.parseInt(flst.get(0).toString());
			}
			
			//找出用户的收益统计记录
			find = new StringBuffer("select ups from UserProfitStatistic ups where ups.fuserinfoid = ").append(swh.getFuserinfoid());
			List<Object> statistic = databaseHelper.getResultListByHql(find.toString());
			
			if(statistic.size()==0){
				UserProfitStatistic ups = new UserProfitStatistic();
				ups.setCostofhs(swh.getType()==0?swh.getCost():0d);
				ups.setCostofus(swh.getType()==1?swh.getCost():0d);
				ups.setCostofwh(0d);
				ups.setFans(fans);
				ups.setFuserinfoid(ui.getId());
				ups.setHsplanfrofit(hsplanfrofit);
				ups.setHsplansuccess(hsplansuccess);
				ups.setIsexample(ui.getIsexample());
				ups.setProfitofhs(swh.getType()==0?userprofit.getProfit():0d);
				ups.setProfitofus(swh.getType()==1?userprofit.getProfit():0d);
				ups.setProfitofwh(0d);
				ups.setRateofhs(swh.getType()==0?userprofit.getProfitrate():0d);
				ups.setRateofus(swh.getType()==1?userprofit.getProfitrate():0d);
				ups.setRateofwh(0d);
				ups.setRewordnums(money);
				ups.setTime(new Date());
				ups.setTotalcost(swh.getCost());
				ups.setTotalplanfrofit(totalplanfrofit);
				ups.setTotalplansuccess(plansuccess);
				ups.setTotalprofit(userprofit.getProfit());
				ups.setTotalrate(userprofit.getProfitrate());
				ups.setUsplanfrofit(usplanfrofit);
				ups.setUsplansuccess(usplansuccess);
				ups.setWhplanfrofit(whplanfrofit);
				ups.setWhplansuccess(whplansuccess);
				ups.setProfitloss(Double.parseDouble(diffmoney)*swh.getCouldusenums());
				ups.setProfitlossrate(userprofit.getCost()==0d?0:Double.parseDouble(diffmoney)*swh.getCouldusenums()/swh.getCost());
				ups.setProfitlossofhs(swh.getType()==0?Double.parseDouble(diffmoney):0d);
				ups.setProfitlossofus(swh.getType()==1?Double.parseDouble(diffmoney):0d);
				ups.setProfitlossofwh(0d);
				ups.setProfitlossrateofhs(swh.getType()==0?Double.parseDouble(diffrate):0d);
				ups.setProfitlossrateofus(swh.getType()==1?Double.parseDouble(diffrate):0d);
				ups.setProfitlossrateofwh(0d);
				databaseHelper.persistObject(ups);
			}else{
				UserProfitStatistic ups = (UserProfitStatistic)statistic.get(0);
				ups.setCostofhs(ups.getCostofhs()+(swh.getType()==0?swh.getCost():0d));
				ups.setCostofus(ups.getCostofus()+(swh.getType()==1?swh.getCost():0d));
				ups.setFans(fans);
				ups.setHsplanfrofit(hsplanfrofit);
				ups.setHsplansuccess(hsplansuccess);
				ups.setIsexample(ui.getIsexample());
				ups.setProfitofhs(ups.getProfitofhs()+(swh.getType()==0?userprofit.getProfit():0d));
				ups.setProfitofus(ups.getProfitofus()+(swh.getType()==1?userprofit.getProfit():0d));
				ups.setRateofhs(ups.getRateofhs()+(swh.getType()==0?userprofit.getProfitrate():0d));
				ups.setRateofus(ups.getRateofus()+(swh.getType()==1?userprofit.getProfitrate():0d));
				ups.setRewordnums(money);
				ups.setTime(new Date());
				ups.setTotalcost(ups.getTotalcost()+swh.getCost());
				ups.setTotalplanfrofit(totalplanfrofit);
				ups.setTotalplansuccess(plansuccess);
				ups.setTotalprofit(ups.getTotalprofit()+userprofit.getProfit());
				ups.setTotalrate(ups.getTotalrate()+userprofit.getProfitrate());
				ups.setUsplanfrofit(usplanfrofit);
				ups.setUsplansuccess(usplansuccess);
				ups.setWhplanfrofit(whplanfrofit);
				ups.setWhplansuccess(whplansuccess);
				ups.setProfitloss(ups.getProfitloss()+Double.parseDouble(diffmoney)*swh.getCouldusenums());
				ups.setProfitlossrate(ups.getProfitlossrate()+(userprofit.getCost()==0d?0:Double.parseDouble(diffmoney)*swh.getCouldusenums()/ups.getTotalcost()));
				ups.setProfitlossofhs(ups.getProfitlossofhs()+(swh.getType()==0?Double.parseDouble(diffmoney):0d));
				ups.setProfitlossofus(ups.getProfitlossofus()+(swh.getType()==1?Double.parseDouble(diffmoney):0d));
				ups.setProfitlossrateofhs(ups.getProfitlossrateofhs()+(swh.getType()==0?Double.parseDouble(diffrate):0d));
				ups.setProfitlossrateofus(ups.getProfitlossrateofus()+(swh.getType()==1?Double.parseDouble(diffrate):0d));
				databaseHelper.updateObject(ups);
			}
		}
		
	}
	
	
	private void userProfit4wh() throws Exception{
		//找出所有用户的外汇持仓记录
		StringBuffer find = new StringBuffer("select fe,ui from ForeignExchange fe,UserInfo ui where ui.id = fe.fuserinfoid and fe.state > -1 ");
		List<Object[]> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object[] objects : lst) {
			ForeignExchange fe = (ForeignExchange)objects[0];
			UserInfo ui = (UserInfo)objects[1];
			
			/*String result = NowApiUtil.getWHIndex(fe.getCode());
			 
			 Sys.out("getData4whIndex..."+sdf.format(new Date())+"..."+result);
			 
			
			 JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject();
			
			String price = obj.get("rate").getAsString()*/
			
			//String res = NowApiUtil.getWHIndexByAli(fe.getCode());
			String price = "0",diffrate="0",diffmoney="0";
			
			/*if(res.indexOf("File not found")!=-1){
				JsonObject json = (JsonObject) new JsonParser().parse(res);
				
				if("ok".equals(json.get("msg").getAsString())){
				
					JsonObject jo = json.get("result").getAsJsonObject();
				
					price = jo.get("rate").getAsString();
				}
			
			}*/
			price  =  JuheData.getnowprice(fe.getCode());
			
			StringBuffer xfind = new StringBuffer("select shares from Shares shares where  shares.state >-2 and shares.type = 2 and shares.code = '").append(fe.getCode()).append("'");
			
			List<Object> xlst = databaseHelper.getResultListByHql(xfind.toString());
			
			if(xlst.size()>0){
				
				Shares shares = (Shares)xlst.get(0);
				Double lastprice = shares.getPrice();
				
				diffmoney = fe.getBuytype()==1?String.valueOf(Double.parseDouble(price)-lastprice):String.valueOf(lastprice-Double.parseDouble(price));
				
				shares.setDiffmoney(diffmoney);
				shares.setDiffrate(lastprice==0?"0":String.valueOf(((Double.parseDouble(price)-lastprice)/lastprice)));
				diffrate = shares.getDiffrate().toString();
				databaseHelper.updateObject(shares);
			}
			
			UserProfit userprofit = new UserProfit();
			userprofit.setCost(fe.getPurchase());
			userprofit.setDiffprice(Double.parseDouble(price)-fe.getPrice());
			
			//找出最后一次的收益记录
			find = new StringBuffer("select userprofit from UserProfit userprofit  where userprofit.type = 2 and userprofit.fuserinfoid = ").append(fe.getFuserinfoid()).append(" order by userprofit.time desc");
			List<Object> last = databaseHelper.getResultListByHql(find.toString());
			if(last.size()==0){
				userprofit.setDiffpriceoflasttime(0d);
				userprofit.setDiffprofitoflasttime(0d);
				userprofit.setDiffprofitoflasttimerate(0d);
			}else{
				UserProfit lastuserprofit = (UserProfit)last.get(0);
				userprofit.setDiffpriceoflasttime(Double.parseDouble(price)-lastuserprofit.getPrice());//与上次市价的差值
				userprofit.setDiffprofitoflasttime(userprofit.getDiffpriceoflasttime()*fe.getCouldusenums());//与上次市价的差值收益 = diffpriceoflasttime*nums
				userprofit.setDiffprofitoflasttimerate(userprofit.getCost()==0?0:userprofit.getDiffprofitoflasttime()/userprofit.getCost());//与上次市价的差值收益率 = diffpriceoflasttime/cost
			}
			
			userprofit.setForeignkeyid(fe.getId());
			userprofit.setFuserinfoid(fe.getFuserinfoid());
			userprofit.setNums(fe.getCouldusenums());
			userprofit.setPrice(Double.parseDouble(price));
			userprofit.setProfit(userprofit.getDiffprice()*fe.getCouldusenums());
			userprofit.setProfitrate(userprofit.getCost()==0?0:userprofit.getDiffprice()/userprofit.getCost());
			userprofit.setTags(fe.getState()==0?1:2);
			userprofit.setTime(new Date());
			userprofit.setType(2);
			userprofit.setIsplan(fe.getIsplan());
			databaseHelper.persistObject(userprofit);
			
			//查看用户粉丝数
			find = new StringBuffer("select count(f.id) from Follow f where f.state = 0 and f.ffollowedid = ").append(ui.getId());
			
			List<Object> flst = databaseHelper.getResultListByHql(find.toString());
			int fans =  Integer.parseInt(flst.get(0).toString());
			
			Double hsplansuccess = 0d,usplansuccess = 0d,whplansuccess = 0d,plansuccess = 0d;
			Double hsplanfrofit = 0d,usplanfrofit = 0d,whplanfrofit = 0d,totalplanfrofit = 0d;
			int money = 0;
			//如果用户是牛人，计算用户的计划成功率，收到的打赏金币
			if(ui.getIsexample()==1){
				find = new StringBuffer("select exampleplan from ExamplePlan exampleplan where exampleplan.state = 2 and exampleplan.fuserinfoid = ").append(ui.getId());
				List<Object> examplelst = databaseHelper.getResultListByHql(find.toString());
				
				int examplenums = examplelst.size();
				int successnums = 0,hsplansuccessnums = 0,usplansuccessnums = 0,whplansuccessnums = 0;
				int hsplannums = 0,usplannums = 0,whplannums = 0;
				for (Object object : examplelst) {
					ExamplePlan exampleplan = (ExamplePlan)object;
					
					totalplanfrofit+=exampleplan.getActualprofit();
						if(exampleplan.getType()==0){
							hsplannums++;
							hsplanfrofit += exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								hsplansuccessnums++;
								successnums++;
							}
						}else if(exampleplan.getType()==1){
							usplannums++;
							usplanfrofit+=exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								usplansuccessnums++;
								successnums++;
							}
						}else if(exampleplan.getType()==2){
							whplannums++;
							whplanfrofit+=exampleplan.getActualprofit();
							if(exampleplan.getSuccessed()==1){
								whplansuccessnums++;
								successnums++;
							}
						}
					
				}
				if(examplenums>0){
					plansuccess = ConstantUtil.intdevice2(successnums,examplenums,true);
					if(hsplannums>0)hsplansuccess = ConstantUtil.intdevice2(hsplansuccessnums,hsplannums,true);
					if(usplannums>0)usplansuccess = ConstantUtil.intdevice2(usplansuccessnums,usplannums,true);
					if(whplannums>0)whplansuccess = ConstantUtil.intdevice2(whplansuccessnums,whplannums,true);
				}
				
				
				find = new StringBuffer("select sum(rl.money) 'a' from rewardlog rl where rl.frewardedid = ").append(ui.getId());
				List<Object> rlst = databaseHelper.getResultListBySql(find.toString());
				
				money = rlst==null?0:rlst.size()==0?0:rlst.get(0)==null?0:Integer.parseInt(rlst.get(0).toString());
			}
			
			//找出用户的收益统计记录
			find = new StringBuffer("select ups from UserProfitStatistic ups where ups.fuserinfoid = ").append(fe.getFuserinfoid());
			List<Object> statistic = databaseHelper.getResultListByHql(find.toString());
			
			if(statistic.size()==0){
				UserProfitStatistic ups = new UserProfitStatistic();
				ups.setCostofhs(0d);
				ups.setCostofus(0d);
				ups.setCostofwh(fe.getPurchase());
				ups.setFans(fans);
				ups.setFuserinfoid(ui.getId());
				ups.setHsplanfrofit(hsplanfrofit);
				ups.setHsplansuccess(hsplansuccess);
				ups.setIsexample(ui.getIsexample());
				ups.setProfitofhs(0d);
				ups.setProfitofus(0d);
				ups.setProfitofwh(userprofit.getProfit());
				ups.setRateofhs(0d);
				ups.setRateofus(0d);
				ups.setRateofwh(userprofit.getProfitrate());
				ups.setRewordnums(money);
				ups.setTime(new Date());
				ups.setTotalcost(fe.getPurchase());
				ups.setTotalplanfrofit(totalplanfrofit);
				ups.setTotalplansuccess(plansuccess);
				ups.setTotalprofit(userprofit.getProfit());
				ups.setTotalrate(userprofit.getProfitrate());
				ups.setUsplanfrofit(usplanfrofit);
				ups.setUsplansuccess(usplansuccess);
				ups.setWhplanfrofit(whplanfrofit);
				ups.setWhplansuccess(whplansuccess);
				ups.setProfitloss(Double.parseDouble(diffmoney)*fe.getCouldusenums());
				ups.setProfitlossrate(fe.getPurchase()==0?0:Double.parseDouble(diffmoney)*fe.getCouldusenums()/fe.getPurchase());
				ups.setProfitlossofhs(0d);
				ups.setProfitlossofus(0d);
				ups.setProfitlossofwh(Double.parseDouble(diffmoney));
				ups.setProfitlossrateofhs(0d);
				ups.setProfitlossrateofus(0d);
				ups.setProfitlossrateofwh(Double.parseDouble(diffrate));
				databaseHelper.persistObject(ups);
			}else{
				UserProfitStatistic ups = (UserProfitStatistic)statistic.get(0);
				ups.setCostofwh(ups.getCostofwh()+fe.getPurchase());
				ups.setFans(fans);
				ups.setHsplanfrofit(hsplanfrofit);
				ups.setHsplansuccess(hsplansuccess);
				ups.setIsexample(ui.getIsexample());
				ups.setProfitofwh(ups.getProfitofwh()+userprofit.getProfit());
				ups.setRateofwh(ups.getRateofhs()+userprofit.getProfitrate());
				ups.setRewordnums(money);
				ups.setTime(new Date());
				ups.setTotalcost(ups.getTotalcost()+fe.getPurchase());
				ups.setTotalplanfrofit(totalplanfrofit);
				ups.setTotalplansuccess(plansuccess);
				ups.setTotalprofit(ups.getTotalprofit()+userprofit.getProfit());
				ups.setTotalrate(ups.getTotalrate()+userprofit.getProfitrate());
				ups.setUsplanfrofit(usplanfrofit);
				ups.setUsplansuccess(usplansuccess);
				ups.setWhplanfrofit(whplanfrofit);
				ups.setWhplansuccess(whplansuccess);
				ups.setProfitloss(ups.getProfitloss()+Double.parseDouble(diffmoney)*fe.getCouldusenums());
				ups.setProfitlossrate(ups.getProfitlossrate()+(ups.getTotalcost()==0?0:Double.parseDouble(diffmoney)*fe.getCouldusenums()/ups.getTotalcost()));
				ups.setProfitlossofwh(ups.getProfitlossofwh()+Double.parseDouble(diffmoney));
				ups.setProfitlossrateofwh(ups.getProfitlossrateofwh()+Double.parseDouble(diffrate));
				databaseHelper.updateObject(ups);
			}
		}
	}
}
