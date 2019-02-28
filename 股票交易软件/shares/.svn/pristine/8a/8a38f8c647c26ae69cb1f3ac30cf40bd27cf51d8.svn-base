package com.hxy.isw.thread;

import java.util.List;

import com.hxy.isw.entity.Shares;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.KLineData;

/**
* @author lcc
* @date 2017年10月25日 下午1:10:15
* @describe
*/
public class UsKlineData implements Runnable{
	public static DatabaseHelper databaseHelper;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Object> lst = getUs();
		while(true){
			try {
				getUsKlineData(lst);
				Thread.sleep(1000l*60*5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	private List<Object> getUs(){
		StringBuffer query = new StringBuffer("select shares from Shares shares where shares.type = 1 and shares.state = 0");
		List<Object> lst = databaseHelper.getResultListByHql(query.toString());
		return lst;
	}
	
	private void getUsKlineData(List<Object> lst) throws Exception{
		for (Object object : lst) {
			
			Shares shares = (Shares)object;
			
			String result = KLineData.getMinKLine(shares.getCode());
			if(result!=null&&result.length()>0)shares.setMinkline(result);
			Thread.sleep(1000l);
			
			result = KLineData.getDayKLine(shares.getCode());
			if(result!=null&&result.length()>0)shares.setDaykline(result);
			Thread.sleep(1000l);
			
			result = KLineData.getWeekKLine(shares.getCode());
			if(result!=null&&result.length()>0)shares.setWeekkline(result);
			Thread.sleep(1000l);
			
			result = KLineData.getMonthKLine(shares.getCode());
			if(result!=null&&result.length()>0)shares.setMonthkline(result);
			Thread.sleep(1000l);
			
			databaseHelper.updateObject(shares);
				
		}
	}
}
