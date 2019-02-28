package com.hxy.isw.thread;

import java.util.List;

import com.hxy.isw.entity.Shares;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.KLineData;

/**
* @author lcc
* @date 2017年10月25日 上午11:06:49
* @describe
*/
public class MinKLineOfUs  implements Runnable {
	
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				updateMinKline();
				Thread.sleep(1000l*60*30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateMinKline(){
		
		try {
			StringBuffer query = new StringBuffer("select shares from Shares shares where shares.type = 1 and shares.state = 0");
			List<Object> lst = databaseHelper.getResultListByHql(query.toString());
			for (Object object : lst) {
				Shares shares = (Shares)object;
				String result = KLineData.getMinKLine(shares.getCode());
				if(result!=null&&result.length()>0){
					shares.setMinkline(result);
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
