package com.hxy.isw.thread;

import java.util.List;

import com.hxy.isw.entity.Shares;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.KLineData;

/**
* @author lcc
* @date 2017年10月25日 上午11:06:49
* @describe
*/
public class WeekKLineOfUs  implements Runnable {
	
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			long diff = ConstantUtil.setThreadStartTimer("00","30",null);
			
			if(diff<0){//凌晨0点30分之前则等待
				Thread.sleep(0-diff);
				updateWeekKline();
			}else{//凌晨0点30分后则立即启动
				updateWeekKline();
			}
		
			while (true){
				long diff11 = ConstantUtil.setThreadStartTimer("00","30",null);
				Thread.sleep(1000l*60*60*24-diff11);
				updateWeekKline();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateWeekKline(){
		
		try {
			StringBuffer query = new StringBuffer("select shares from Shares shares where shares.type = 1 and shares.state = 0");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString());
			for (Object object : lst) {
				Shares shares = (Shares)object;
				String result = KLineData.getWeekKLine(shares.getCode());
				if(result!=null&&result.length()>0){
					shares.setWeekkline(result);
					databaseHelper.updateObject(shares);
					Thread.sleep(5*1000l);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
