package com.hxy.isw.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserSharesSetting;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;
import com.hxy.isw.util.ThreadPoolManager;

/**
 * 检查用户止损止盈
 * @author lcc
 *
 */
public class CheckUserSharesSetting implements Runnable {
	SimpleDateFormat sdf = null;
	public static DatabaseHelper databaseHelper;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		try {
			while(true){
				checkUserSharesSetting(sd);
				Thread.sleep(1000l*60*30);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkUserSharesSetting(SyncDemo_股票行情 sd) throws Exception{
		//找出用户的止损止盈设置
		StringBuffer find = new StringBuffer("select uss,ui from  UserSharesSetting uss,UserInfo ui where uss.fuserinfoid = ui.id and uss.state = 0 ");
		List<Object[]> lst = databaseHelper.getResultListByHql(find.toString());
		
		for (Object[] objects : lst) {
			UserSharesSetting uss = (UserSharesSetting)objects[0];
			UserInfo ui = (UserInfo)objects[1];
			
			if(uss.getType()==1){//股票
				
				check4shares(sd, uss, ui);
				
			}else{//外汇
				
				check4foreignexchange(uss, ui);
				
			}
		}
	}
	
	private void check4shares(SyncDemo_股票行情 sd,UserSharesSetting uss,UserInfo ui) throws Exception{
		String price = null;
		Double diff_rate = 0d;
		if(uss.getCode().length()==6){//沪深股票
			//查看股票行情
			ApiResponse response = sd.股票行情(uss.getCode(), "0", "0");
			
			String result = new String(response.getBody(), "utf-8");
			
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			if(json.get("showapi_res_code").getAsInt()!=0)return;
			
			JsonObject jsonObject = json.get("showapi_res_body").getAsJsonObject().get("stockMarket").getAsJsonObject();
			
			price = jsonObject.get("nowPrice").getAsString();//当前价
			
			diff_rate = jsonObject.get("diff_rate").getAsDouble();//涨跌率
		}else{//美股
			/*String result = NowApiUtil.getShareIndexOfUS(uss.getCode());
			 
			 Sys.out("GetDataFromUS..."+sdf.format(new Date())+"..."+result);
			
			 JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(uss.getCode()).getAsJsonObject();
			
			price = obj.get("last_price").getAsString();*/
			
			StringBuffer query = new StringBuffer("select s from Shares s where s.code = '").append(uss.getCode()).append("' and s.state > -2 "); 
			List<Object> lst = databaseHelper.getResultListByHql(query.toString());
			
			if(lst.size()>0){
				Shares s = (Shares)lst.get(0);
				
				price = JuheData.getnowprice(s.getPinyin());
			}
		}
		
		//检查是否达到止损或止盈线
		if((uss.getStoploss()!=null&&diff_rate<0&&((-diff_rate)>=ConstantUtil.intdevice2(uss.getStoploss(), 100, false) ))||(uss.getStopprofit()!=null&&diff_rate>00&&(diff_rate>=ConstantUtil.intdevice2(uss.getStopprofit(), 100, false) ))){
			//卖出
			SharesWareHouse swh = (SharesWareHouse)databaseHelper.getObjectById(SharesWareHouse.class, uss.getFforeignid());
			int nums = swh.getCouldusenums();//持仓
			if(swh!=null){
				if(swh.getState()==0&&swh.getCouldusenums()>0){
					swh.setCouldusenums(0);
					swh.setMarketvalue(0d);
					databaseHelper.updateObject(swh);
					
					//检查用户是否卖过此股票
					StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = -1 ").append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.code = '").append(uss.getCode()).append("'");
					List<Object> checklst = databaseHelper.getResultListByHql(check.toString());
					
					SharesWareHouse sellswh =null;
					if(checklst.size()==0){
						sellswh = new SharesWareHouse();
						sellswh.setFdocumentaryid(0l);
						sellswh.setFexampleid(0l);
						sellswh.setFuserinfoid(ui.getId());
						sellswh.setIsplan(0);
						sellswh.setState(-1);
						sellswh.setTime(new Date());
						sellswh.setCouldusenums(nums);
						sellswh.setWarehousenums(nums);
						sellswh.setCost(nums*Double.parseDouble(price));
						sellswh.setCode(uss.getCode());
						sellswh.setFexampleplanid(0l);//?存疑，是否有计划id
						sellswh.setFsharesid(swh.getFsharesid());
						sellswh.setFshareswarehouseid(swh.getId());
						sellswh.setMarket(swh.getMarket());
						sellswh.setMarketvalue(nums*Double.parseDouble(price));
						sellswh.setPrice(Double.parseDouble(price));
						sellswh.setProfitloss(0d);
						sellswh.setSharesname(swh.getSharesname());
						sellswh.setType(swh.getType());
						
						databaseHelper.persistObject(sellswh);
					}else{
						sellswh = (SharesWareHouse)checklst.get(0);
						sellswh.setCouldusenums(sellswh.getCouldusenums()+nums);
						sellswh.setWarehousenums(sellswh.getWarehousenums()+nums);
						sellswh.setCost(sellswh.getCost()+nums*Double.parseDouble(price));
						
						databaseHelper.updateObject(sellswh);
					}
					
					//添加到资金明细表
					CapitalDetail cd = new CapitalDetail();
					cd.setFuserinfoid(ui.getId());
					cd.setType(swh.getType()==0?1:3);
					cd.setCapital(nums*Double.parseDouble(price));
					cd.setCreatetime(new Date());
					cd.setState(0);
					databaseHelper.persistObject(cd);
					
					
					//用户虚拟资金
					ui.setVirtualcapital(ui.getVirtualcapital()+nums*Double.parseDouble(price));
					databaseHelper.updateObject(ui);
					
					
					if(ui.getIsexample()==1){//启动线程
						FollowExampleSell fes = new FollowExampleSell();
						fes.sharesWareHouse = sellswh;
						ThreadPoolManager.exec(fes);
					}
					
				}
			}
			
		}
	}
	
	private void check4foreignexchange(UserSharesSetting uss,UserInfo ui) throws Exception{
		
		 /*String result = NowApiUtil.getWHIndex(uss.getCode());
		 
		 Sys.out("getData4whIndex..."+sdf.format(new Date())+"..."+result);
		 
		 JsonObject json = (JsonObject) new JsonParser().parse(result);
			
		 JsonObject obj = json.get("result").getAsJsonObject();
		 
		 String price = obj.get("rate").getAsString();*/
		
		/*String res = NowApiUtil.getWHIndexByAli(uss.getCode());
		
		JsonObject json = (JsonObject) new JsonParser().parse(res);
		
		String price = "0";
		if("ok".equals(json.get("msg").getAsString())){
		
			JsonObject jo = json.get("result").getAsJsonObject();
		
			price = jo.get("rate").getAsString();
		}*/
		String price = JuheData.getnowprice(uss.getCode());
		 
		StringBuffer find = new StringBuffer("select s from Shares s where s.code = '").append(uss.getCode()).append("'");
		List<Object> flst = databaseHelper.getResultListByHql(find.toString());
		
		Double diff_rate = flst.size()==0?0d:((Shares)flst.get(0)).getDiffrate()==null?0d:Double.parseDouble(((Shares)flst.get(0)).getDiffrate());
		ForeignExchange fe = (ForeignExchange)databaseHelper.getObjectById(ForeignExchange.class, uss.getFforeignid());
		diff_rate = fe.getBuytype()==1?diff_rate:-diff_rate;//判断是买涨还是买跌
		
		//检查是否达到止损或止盈线
		if((uss.getStoploss()!=null&&diff_rate<0&&((-diff_rate)>=ConstantUtil.intdevice2(uss.getStoploss(), 100, false) ))||(uss.getStopprofit()!=null&&diff_rate>00&&(diff_rate>=ConstantUtil.intdevice2(uss.getStopprofit(), 100, false) ))){
			//卖出
			int nums = fe.getCouldusenums();//持仓
			if(fe!=null){
				if(fe.getState()==0&&fe.getCouldusenums()>0){
					
					//检查用户是否卖过此外汇
					fe.setCouldusenums(0);
					databaseHelper.updateObject(fe);
					
					StringBuffer checklog = new StringBuffer("select fe from ForeignExchange fe where fe.state = -1 ").append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.code = '").append(uss.getCode()).append("'");
					List<Object> checklst = databaseHelper.getResultListByHql(checklog.toString());
					
					ForeignExchange sellfe =null;
					if(checklst.size()==0){
						sellfe = new ForeignExchange();
						sellfe.setFdocumentaryid(0l);
						sellfe.setFexampleid(0l);
						sellfe.setFuserinfoid(ui.getId());
						sellfe.setIsplan(0);
						sellfe.setState(-1);
						sellfe.setTime(new Date());
						sellfe.setCouldusenums(nums);
						sellfe.setWarehousenums(nums);
						sellfe.setPurchase(nums*Double.parseDouble(price));
						sellfe.setCode(uss.getCode());
						sellfe.setFexampleplanid(0l);//?存疑，是否有计划id
						sellfe.setFsharesid(fe.getFsharesid());
						sellfe.setFforeignexchangeid(fe.getId());
						sellfe.setSellout(nums*Double.parseDouble(price));
						sellfe.setPrice(Double.parseDouble(price));
						sellfe.setProfitloss(0d);
						sellfe.setForeignexchangename(fe.getForeignexchangename());
						sellfe.setBuytype(fe.getBuytype());
						databaseHelper.persistObject(sellfe);
					}else{
						sellfe = (ForeignExchange)checklst.get(0);
						sellfe.setCouldusenums(sellfe.getCouldusenums()+nums);
						sellfe.setWarehousenums(sellfe.getWarehousenums()+nums);
						sellfe.setSellout(sellfe.getSellout()+nums*Double.parseDouble(price));
						
						databaseHelper.updateObject(sellfe);
					}
					
					//添加到资金明细表
					CapitalDetail cd = new CapitalDetail();
					cd.setFuserinfoid(ui.getId());
					cd.setType(2);
					cd.setCapital(nums*Double.parseDouble(price));
					cd.setCreatetime(new Date());
					cd.setState(0);
					databaseHelper.persistObject(cd);
				
					
					//用户虚拟资金
					ui.setVirtualcapital(ui.getVirtualcapital()+nums*Double.parseDouble(price));
					databaseHelper.updateObject(ui);
					
					
					if(ui.getIsexample()==1){//启动线程
						FollowExampleSell fes = new FollowExampleSell();
						fes.foreignexchange = sellfe;
						ThreadPoolManager.exec(fes);
					}
				}
			}
		}
		 
	}
}
