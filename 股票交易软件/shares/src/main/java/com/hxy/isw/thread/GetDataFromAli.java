package com.hxy.isw.thread;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.util.AliShares4HSUtil;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;

/**
* @author lcc
* @date 2017年7月27日 上午11:55:32
* @describe
*/

@Service
public class GetDataFromAli implements Runnable{

	
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//AliShares4HSUtil sd = new AliShares4HSUtil();
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		int count_shares = querycountshareofhs();
		
		int times = count_shares/50+(count_shares%50==0?0:1);
		
		while (true) {
			
			try {
				ConstantUtil.lm_index_hs.clear();
				ConstantUtil.lm_index_us.clear();
				ConstantUtil.lm_index_wh.clear();
				
				boolean flag = getData4hsIndex(sd, sdf);//沪深指数
				boolean flag_ndaq = getData4usByJh("ixic");//美股指数--纳斯达克
				boolean flag_gb_ivv = getData4usByJh("inx");//美股指数--标普500
				boolean flag_iyr = getData4usByJh("dji");//美股指数--道琼斯
				//boolean flag_whus = getData4whIndexByJuhe("USD");//美元--外汇指数
				boolean flag_wheur = getData4whIndexByJuhe("EURUSD");//欧元--外汇指数
				//boolean flag_whla = getData4whIndexByJuhe("CNH");//离岸人民币--外汇指数
/*				boolean flag_ndaq = getData4us("gb_ndaq",sdf);//美股指数--纳斯达克
				boolean flag_gb_ivv = getData4us("gb_ivv",sdf);//美股指数--标普500
				boolean flag_iyr = getData4us("gb_iyr",sdf);//美股指数--道琼斯
				boolean flag_whus = getData4whIndex("USD",sdf);//美元--外汇指数
				boolean flag_wheur = getData4whIndex("EUR",sdf);//欧元--外汇指数
				boolean flag_whla = getData4whIndex("CNH",sdf);//离岸人民币--外汇指数
*/			
				
				//更新沪深股票行情
				/*for (int i =1 ;i<=times;i++) {
					updateshareofhs(sd, i, sdf);
				}*/
				
				//30分钟获取一次
				Thread.sleep(1000l*60*30);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					Thread.sleep(1000l*60*10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}

	//获取沪深指数
	private boolean getData4hsIndex(SyncDemo_股票行情 sd,SimpleDateFormat sdf) throws Exception{
		
			ApiResponse response = sd.大盘股指行情_批量();
			
			String result = new String(response.getBody(), "utf-8");
			Sys.out("GetDataFromAli..."+sdf.format(new Date())+"..."+result);
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			if(json.get("showapi_res_code").getAsInt()!=0)return false;
			
			ConstantUtil.lm_index_hs.clear();
			
			JsonArray arr = json.get("showapi_res_body").getAsJsonObject().get("indexList").getAsJsonArray();
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				Map<String, String> map = new HashMap<String, String>();
				//map.put("yestodayClosePrice", jsonObject.get("yestodayClosePrice").getAsString());
				//map.put("todayMax", jsonObject.get("todayMax").getAsString());
				//map.put("todayMin", jsonObject.get("todayMin").getAsString());
				//map.put("max52", jsonObject.get("max52").getAsString());
				map.put("diff_money", jsonObject.get("diff_money").getAsString());
				//map.put("tradeNum", jsonObject.get("tradeNum").getAsString());
				map.put("code", jsonObject.get("code").getAsString());
				//map.put("maxPrice", jsonObject.get("maxPrice").getAsString());
				//map.put("nowPrice", jsonObject.get("nowPrice").getAsString());
				//map.put("min52", jsonObject.get("min52").getAsString());
				map.put("time", jsonObject.get("time").getAsString());
				map.put("name", jsonObject.get("name").getAsString());
				//map.put("tradeAmount", jsonObject.get("tradeAmount").getAsString());
				//map.put("swing", jsonObject.get("swing").getAsString());
				map.put("todayOpenPrice", jsonObject.get("todayOpenPrice").getAsString());
				map.put("diff_rate", jsonObject.get("diff_rate").getAsString());
				//map.put("minPrice", jsonObject.get("minPrice").getAsString());
				
				ConstantUtil.lm_index_hs.add(map);
			}
				
			return true;
	}
	
	//获取美股指数
	private boolean getData4us(String symbol,SimpleDateFormat sdf){
		
		//http://api.k780.com/?app=finance.stock_list&category=us&appkey=27090&sign=e980e495bafd40e9cc3a3b4417a0e325&format=json
		 try {
			 
				
				 String result = NowApiUtil.getShareIndexOfUS(symbol);
				 
				 Sys.out("GetDataFromUS..."+sdf.format(new Date())+"..."+result);
				 
				
				 JsonObject json = (JsonObject) new JsonParser().parse(result);
					
				JsonObject obj = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(symbol).getAsJsonObject();
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("name",obj.get("sname").getAsString());
				map.put("todayOpenPrice",obj.get("last_price").getAsString());
				map.put("code",obj.get("scode").getAsString());
				map.put("diff_money",obj.get("rise_fall").getAsString());
				map.put("diff_rate",obj.get("rise_fall_per").getAsString());
				map.put("time",obj.get("uptime").getAsString());
				
				ConstantUtil.lm_index_us.add(map);
				 
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return false;
	}
	
	//获取美股指数(聚合)
	private boolean getData4usByJh(String symbol){
		
		//http://api.k780.com/?app=finance.stock_list&category=us&appkey=27090&sign=e980e495bafd40e9cc3a3b4417a0e325&format=json
		 try {
			 
				
				 JsonObject jObject = JuheData.getRequest3(symbol);
				 
				 Sys.out("GetDataFromUSByJuhe..."+jObject);
				 
				
				Map<String, String> map = new HashMap<String, String>();
				String name = jObject.get("name").getAsString();
				
				//if(name.indexOf("指数")!=-1)name = name.substring(0,name.indexOf("指数")+2);
				
				map.put("name",name);
				map.put("todayOpenPrice",jObject.get("lastestpri").getAsString());
				map.put("code",jObject.get("gid").getAsString());
				map.put("diff_money",jObject.get("uppic").getAsString());
				map.put("diff_rate",jObject.get("limit").getAsString());
				map.put("time",jObject.get("chtime").getAsString());
				
				ConstantUtil.lm_index_us.add(map);
				 
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return false;
	}
	
	//获取外汇指数(聚合)
	private boolean getData4whIndexByJuhe(String scur){
		 try {
			 
				
			/* String result = NowApiUtil.getWHIndex(scur);
			 
			 Sys.out("getData4whIndex..."+sdf.format(new Date())+"..."+result);
			 
			
			 JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject();
			
			Double price = Double.parseDouble(obj.get("rate").getAsString());*/
			 
			 /*String res = NowApiUtil.getWHIndexByAli(scur);
			 Map<String, String> map = new HashMap<String, String>();
				
			 if(res.indexOf("File not found")!=-1){
				 if("CNH".equals(scur)){
						map.put("name","离岸人民币");
						map.put("todayOpenPrice","0.998");
						map.put("code","CNH");
						map.put("time",new Date().toString());
					}else{
						map.put("name","0");
						map.put("todayOpenPrice","0.998");
						map.put("code","0");
						map.put("time","0");
					}
					map.put("diff_money","0");
					map.put("diff_rate","0%");
					
					ConstantUtil.lm_index_wh.add(map);
					return false;
			 }
			 
			JsonObject json = (JsonObject) new JsonParser().parse(res);
				
			Double price = 0d;
			if("ok".equals(json.get("msg").getAsString())){
				
				JsonObject jo = json.get("result").getAsJsonObject();
				
				price = jo.get("rate").getAsDouble();
				
				map.put("name",jo.get("fromname").getAsString());
				map.put("todayOpenPrice",price.toString());
				map.put("code",jo.get("from").getAsString());
				map.put("time",jo.get("updatetime").getAsString());
			}else{
				if("CNH".equals(scur)){
					map.put("name","离岸人民币");
					map.put("todayOpenPrice","0.998");
					map.put("code","CNH");
					map.put("time",new Date().toString());
				}else{
					map.put("name","0");
					map.put("todayOpenPrice","0.998");
					map.put("code","0");
					map.put("time","0");
				}
				
			}*/
			 Map<String, String> map = new HashMap<String, String>();
			 Double price = Double.parseDouble(JuheData.getnowprice(scur));
			 System.out.println("price.."+price);
			 map.put("todayOpenPrice",price.toString());
			 map.put("code",scur);
			 map.put("time",new Date().toString());
			
			StringBuffer find = new StringBuffer("select shares from Shares shares where  shares.state >-2 and shares.type = 2 and shares.code = '").append(scur).append("'");
			
			List<Object> lst = databaseHelper.getResultListByHql(find.toString());
			if(lst.size()>0){
				
				Shares shares = (Shares)lst.get(0);
				Double lastprice = shares.getPrice();
				System.out.println("lastprice.."+lastprice);
				map.put("name",shares.getSharesname());
				String diffmoney = new BigDecimal(price).subtract(new BigDecimal(lastprice)).setScale(5,BigDecimal.ROUND_HALF_UP).toString();//  String.valueOf(price-lastprice);
				String diffrate =lastprice==null?"0%":lastprice==0d?"0%":String.valueOf(ConstantUtil.formatDouble((price-lastprice)/lastprice*100))+"%";
				map.put("diff_money",diffmoney);
				map.put("diff_rate",diffrate);
				
				shares.setDiffmoney(diffmoney);
				shares.setDiffrate(lastprice==null?"0":lastprice==0d?"0":String.valueOf(ConstantUtil.formatDouble((price-lastprice)/lastprice)));
				System.out.println("diffmoney.."+diffmoney);
				System.out.println("diffrate.."+diffrate);
				databaseHelper.updateObject(shares);
			}else{
				map.put("name","");
				map.put("diff_money","0");
				map.put("diff_rate","0%");
				
			}
			
			
			ConstantUtil.lm_index_wh.add(map);
			 
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		 return false;
	}
	
	//获取外汇指数
	/*private boolean getData4whIndex(String scur){
		 try {
			 
				
			 String result = NowApiUtil.getWHIndex(scur);
			 
			 Sys.out("getData4whIndex..."+sdf.format(new Date())+"..."+result);
			 
			
			 JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject();
			
			Double price = Double.parseDouble(obj.get("rate").getAsString());
			 
			 String res = NowApiUtil.getWHIndexByAli(scur);
			 Map<String, String> map = new HashMap<String, String>();
				
			 if(res.indexOf("File not found")!=-1){
				 if("CNH".equals(scur)){
						map.put("name","离岸人民币");
						map.put("todayOpenPrice","0.998");
						map.put("code","CNH");
						map.put("time",new Date().toString());
					}else{
						map.put("name","0");
						map.put("todayOpenPrice","0.998");
						map.put("code","0");
						map.put("time","0");
					}
					map.put("diff_money","0");
					map.put("diff_rate","0%");
					
					ConstantUtil.lm_index_wh.add(map);
					return false;
			 }
			 
			JsonObject json = (JsonObject) new JsonParser().parse(res);
				
			Double price = 0d;
			if("ok".equals(json.get("msg").getAsString())){
				
				JsonObject jo = json.get("result").getAsJsonObject();
				
				price = jo.get("rate").getAsDouble();
				
				map.put("name",jo.get("fromname").getAsString());
				map.put("todayOpenPrice",price.toString());
				map.put("code",jo.get("from").getAsString());
				map.put("time",jo.get("updatetime").getAsString());
			}else{
				if("CNH".equals(scur)){
					map.put("name","离岸人民币");
					map.put("todayOpenPrice","0.998");
					map.put("code","CNH");
					map.put("time",new Date().toString());
				}else{
					map.put("name","0");
					map.put("todayOpenPrice","0.998");
					map.put("code","0");
					map.put("time","0");
				}
				
			}
			
			
			
			StringBuffer find = new StringBuffer("select shares from Shares shares where  shares.state >-2 and shares.type = 2 and shares.code = '").append(scur).append("'");
			
			List<Object> lst = databaseHelper.getResultListByHql(find.toString());
			if(lst.size()>0){
				
				Shares shares = (Shares)lst.get(0);
				Double lastprice = shares.getPrice();
				
				String diffmoney = String.valueOf(price-lastprice);
				String diffrate =lastprice==null?"0%":lastprice==0d?"0%":String.valueOf(ConstantUtil.formatDouble((price-lastprice)/lastprice*100))+"%";
				map.put("diff_money",diffmoney);
				map.put("diff_rate",diffrate);
				
				shares.setDiffmoney(diffmoney);
				shares.setDiffrate(lastprice==null?"0":lastprice==0d?"0":String.valueOf(ConstantUtil.formatDouble((price-lastprice)/lastprice)));
				
				databaseHelper.updateObject(shares);
			}else{
				
				map.put("diff_money","0");
				map.put("diff_rate","0%");
				
			}
			
			
			ConstantUtil.lm_index_wh.add(map);
			 
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		 return false;
	}*/
	
	//查看沪深股票总数
	private int querycountshareofhs(){
		StringBuffer queryshares = new StringBuffer("select count(s.id) from Shares s where s.state = 0 and s.type = 0 ");
		List lst = databaseHelper.getResultListByHql(queryshares.toString());
		return Integer.parseInt(lst.get(0).toString());
	}
	
	private void updateshareofhs(SyncDemo_股票行情 sd,int start,SimpleDateFormat sdf)  throws Exception{
		StringBuffer query =  new StringBuffer("select s from Shares s where s.state = 0 and s.type = 0 ");
		List<Object> lst = databaseHelper.getResultListByHql(query.toString(),start,50);
		
		String codes = "";
		for (Object object : lst) {
			Shares s = (Shares)object;
			codes += s.getCode()+",";
		}
		
		codes = codes.substring(0, codes.length()-1);
		
		ApiResponse response = sd.股票行情_批量(codes);
		
		StringBuffer getSharesByCode = null; 
		
		String result = new String(response.getBody(), "utf-8");
		Sys.out("GetDataFromAli..."+sdf.format(new Date())+"..."+result);
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		if(json.get("showapi_res_code").getAsInt()!=0)return;
			
		JsonArray arr = json.get("showapi_res_body").getAsJsonObject().get("list").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			String code = jsonObject.get("code").getAsString();//股票代码
			String diff_money = jsonObject.get("diff_money").getAsString();//涨跌金额
			String diff_rate = jsonObject.get("diff_rate").getAsString();//涨跌幅度
			String price = jsonObject.get("nowPrice").getAsString();//当前价
			
			getSharesByCode = new StringBuffer("select s from Shares s where s.code = '").append(code).append("'");
			List<Object> thislst = databaseHelper.getResultListByHql(getSharesByCode.toString());
			
			if(thislst.size()>0){
				Shares thisshare = (Shares)thislst.get(0);
				
				thisshare.setDiffmoney(diff_money);
				thisshare.setDiffrate(diff_rate);
				thisshare.setPrice(Double.parseDouble(price));
				if(Double.parseDouble(diff_money)>=0)thisshare.setUpanddown("涨");
				else thisshare.setUpanddown("跌");
				
				thisshare.setQuotation(jsonObject.toString());
				thisshare.setLastupdate(new Date());
				databaseHelper.updateObject(thisshare);
			}
			
		}
	}
	
	public static void main(String[] args) {
		String s = "dfa,asdfsda,dfasd,adf,";
		s = s.substring(0,s.length()-1);
		Sys.out(s);
	}
}
