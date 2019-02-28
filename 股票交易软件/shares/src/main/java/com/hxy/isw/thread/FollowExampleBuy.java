package com.hxy.isw.thread;

import java.util.Date;
import java.util.List;

import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.Documentary;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.TradeInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.util.DatabaseHelper;

/**
 * 牛人买入时，跟随的用户同步操作
 * @author lcc
 *
 */
public class FollowExampleBuy implements Runnable {

	public SharesWareHouse sharesWareHouse;
	public ForeignExchange foreignexchange;
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		followExampleBuyShares(sharesWareHouse,foreignexchange);
	}
	

	private void followExampleBuyShares(SharesWareHouse sharesWareHouse,ForeignExchange foreignexchange){
		
		Long exampleid = sharesWareHouse==null?foreignexchange.getFuserinfoid():sharesWareHouse.getFuserinfoid();
		
		//找出跟随牛人的所有用户
		StringBuffer query = new StringBuffer("select d,ui from Documentary d,UserInfo ui where ui.id = d.fuserinfoid and d.state = 0 ").append(" and d.ffollowuserinfoid = ").append(exampleid);
		List<Object[]> querylst = databaseHelper.getResultListByHql(query.toString());
		
		for (Object[] objects : querylst) {
			Documentary d = (Documentary)objects[0];
			UserInfo ui = (UserInfo)objects[1];
			int type = d.getType();
			
			int userneedusenums = 0;
			Double needamount = 0d;
			
			if(type==1){//用户按金额跟单
				
				//检查用户的设置的跟单资金是否足够
				
				if(sharesWareHouse!=null){
					userneedusenums = sharesWareHouse.getCouldusenums()*d.getUservalues().intValue();
					needamount = userneedusenums*sharesWareHouse.getPrice();
				}else{
					userneedusenums = foreignexchange.getCouldusenums()*d.getUservalues().intValue();
					needamount = userneedusenums*foreignexchange.getPrice();
				}
				
			}else if(type==2){//按手数跟单
				//检查用户资金是否足够
				userneedusenums = d.getUservalues().intValue();
				if(sharesWareHouse!=null){
					needamount = userneedusenums*sharesWareHouse.getPrice();
				}else{
					needamount = userneedusenums*foreignexchange.getPrice();
				}
				
			}else if(type==3){//按比例
				//检查用户的资金是否足够
				
				if(sharesWareHouse!=null){
					userneedusenums = sharesWareHouse.getCouldusenums()*d.getUservalues().intValue();
					needamount = userneedusenums*sharesWareHouse.getPrice();
				}else{
					userneedusenums = foreignexchange.getCouldusenums()*d.getUservalues().intValue();
					needamount = userneedusenums*foreignexchange.getPrice();
				}
				
			}
			
			
			//如果不足，则停止跟单
			if((type==1&&d.getDiffamount()<needamount)||(type>1&&ui.getVirtualcapital()<needamount)){
				d.setState(-1);
				databaseHelper.updateObject(d);
				
				//该用户的所有与此牛人的跟单置为持仓
				StringBuffer update = new StringBuffer("update shareswarehouse set state = 0 where state = 1 and fdocumentaryid = ").append(d.getId())
						.append(" and fuserinfoid = ").append(ui.getId());
				databaseHelper.executeNativeSql(update.toString());
				
				update = new StringBuffer("update foreignexchange set state = 0 where state = 1 and fdocumentaryid = ").append(d.getId())
						.append(" and fuserinfoid = ").append(ui.getId());
				databaseHelper.executeNativeSql(update.toString());
				
				//多余的金额退回到用户帐户
				ui.setVirtualcapital(ui.getVirtualcapital()+d.getDiffamount());
				databaseHelper.updateObject(ui);
				
				continue;
			}
			
			if(sharesWareHouse!=null){
				//检查用户是否跟过此股票
				StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 1 and swh.fdocumentaryid = ").append(d.getId())
						.append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(sharesWareHouse.getFsharesid());
				List<Object> lst = databaseHelper.getResultListByHql(check.toString());
				SharesWareHouse swh = null;
				if(lst.size()==0){
					swh = sharesWareHouse;
					swh.setFdocumentaryid(d.getId());
					swh.setFexampleid(exampleid);
					swh.setFuserinfoid(ui.getId());
					swh.setId(null);
					swh.setIsplan(0);
					swh.setState(1);
					swh.setTime(new Date());
					swh.setCouldusenums(userneedusenums);
					swh.setWarehousenums(userneedusenums);
					swh.setCost(userneedusenums*sharesWareHouse.getPrice());
					
					databaseHelper.persistObject(swh);
				}else{
					
					swh = (SharesWareHouse)lst.get(0);
					swh.setCouldusenums(swh.getCouldusenums()+userneedusenums);
					swh.setWarehousenums(swh.getWarehousenums()+userneedusenums);
					swh.setCost(swh.getCost()+userneedusenums*sharesWareHouse.getPrice());
					swh.setMarketvalue(swh.getCost()+userneedusenums*sharesWareHouse.getPrice());
					
					databaseHelper.updateObject(swh);
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
				
			}else{
				//检查用户是否跟过此外汇
				StringBuffer check = new StringBuffer("select fe from ForeignExchange fe where fe.state = 1 and fe.fdocumentaryid = ").append(d.getId())
						.append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(foreignexchange.getFsharesid());
				List<Object> lst = databaseHelper.getResultListByHql(check.toString());
				ForeignExchange fe = null;
				if(lst.size()==0){
					fe = foreignexchange;
					fe.setCouldusenums(userneedusenums);
					fe.setFdocumentaryid(d.getId());
					fe.setFexampleid(exampleid);
					fe.setFuserinfoid(ui.getId());
					fe.setState(1);
					fe.setId(null);
					fe.setTime(new Date());
					fe.setWarehousenums(userneedusenums);
					fe.setPurchase(userneedusenums*foreignexchange.getPrice());
					fe.setBuytype(foreignexchange.getBuytype());
					databaseHelper.persistObject(fe);
				}else{
					fe = (ForeignExchange)lst.get(0);
					
					fe.setCouldusenums(fe.getCouldusenums()+userneedusenums);
					fe.setWarehousenums(fe.getWarehousenums()+userneedusenums);
					fe.setPurchase(fe.getPurchase()+userneedusenums*foreignexchange.getPrice());
					
					databaseHelper.updateObject(fe);
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
			}
			
			//更新跟单表
			d.setActualyamount(d.getActualyamount()+needamount);
			d.setDiffamount(d.getDiffamount()-needamount);
			databaseHelper.updateObject(d);
			
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
}
