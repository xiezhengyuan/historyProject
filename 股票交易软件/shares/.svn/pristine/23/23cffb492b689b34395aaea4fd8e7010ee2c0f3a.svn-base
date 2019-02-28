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
 * 牛人卖出时，跟随的用户同步操作
 * @author lcc
 *
 */
public class FollowExampleSell implements Runnable {

	public SharesWareHouse sharesWareHouse;
	public ForeignExchange foreignexchange;
	
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		followExampleSellShares(sharesWareHouse,foreignexchange);
	}
	

	private void followExampleSellShares(SharesWareHouse sharesWareHouse,ForeignExchange foreignexchange){
		
		Long exampleid = sharesWareHouse==null?foreignexchange.getFuserinfoid():sharesWareHouse.getFuserinfoid();
		
		//找出跟随牛人的所有用户
		StringBuffer query = new StringBuffer("select d,ui from Documentary d,UserInfo ui where ui.id = d.fuserinfoid and d.state = 0 ").append(" and d.ffollowuserinfoid = ").append(exampleid);
		List<Object[]> querylst = databaseHelper.getResultListByHql(query.toString());
		
		for (Object[] objects : querylst) {
			Documentary d = (Documentary)objects[0];
			UserInfo ui = (UserInfo)objects[1];
			int type = d.getType();
			
			if(sharesWareHouse!=null){
				sellshares(type, ui, d, exampleid,sharesWareHouse);
			}else{
				sellforeign(type, ui, d, exampleid, foreignexchange);
			}
		}
	}
	
	private void sellshares(int type,UserInfo ui,Documentary d,Long exampleid,SharesWareHouse sharesWareHouse){
		//找出用户跟单的此只股票
		StringBuffer find = new StringBuffer("select swh from SharesWareHouse swh where swh.state = 1 and swh.fdocumentaryid = ").append(d.getId())
				.append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(sharesWareHouse.getFsharesid());
		
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		Double needamount = 0d;
		if(lst.size()>0){
			SharesWareHouse swh = (SharesWareHouse)lst.get(0);
			
			int sellnums = type==2?d.getUservalues().intValue():d.getUservalues().intValue()*sharesWareHouse.getCouldusenums();
			needamount = sellnums*sharesWareHouse.getPrice();
			//检查用户的持仓是否足够
			if(sellnums>swh.getCouldusenums()){//持仓不足，则停止跟单
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
				
				return;
			}
			
			//检查用户是否卖过此股票
			StringBuffer check = new StringBuffer("select swh from SharesWareHouse swh where swh.state = -1 ").append(" and swh.fuserinfoid = ").append(ui.getId()).append(" and swh.fsharesid = ").append(sharesWareHouse.getFsharesid());
			List<Object> checklst = databaseHelper.getResultListByHql(check.toString());
			SharesWareHouse newswh = null;
			if(checklst.size()==0){
				newswh = sharesWareHouse;
				newswh.setFdocumentaryid(d.getId());
				newswh.setFexampleid(exampleid);
				newswh.setFuserinfoid(ui.getId());
				newswh.setId(null);
				newswh.setIsplan(0);
				newswh.setState(-1);
				newswh.setTime(new Date());
				newswh.setCouldusenums(sellnums);
				newswh.setWarehousenums(sellnums);
				newswh.setCost(sellnums*sharesWareHouse.getPrice());
				newswh.setFshareswarehouseid(swh.getId());
				
				databaseHelper.persistObject(newswh);
				
				swh.setCouldusenums(swh.getCouldusenums()-sellnums);
				databaseHelper.updateObject(swh);
				
			}else{
				
				newswh = (SharesWareHouse)lst.get(0);
				newswh.setCouldusenums(swh.getCouldusenums()+sellnums);
				newswh.setWarehousenums(swh.getWarehousenums()+sellnums);
				newswh.setCost(swh.getCost()+sellnums*sharesWareHouse.getPrice());
				
				databaseHelper.updateObject(newswh);
				
				swh.setCouldusenums(swh.getCouldusenums()-sellnums);
				swh.setMarketvalue(swh.getMarketvalue()-sellnums*sharesWareHouse.getPrice());
				databaseHelper.updateObject(swh);
			}
			
			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(newswh.getCost());
			ti.setFexampleid(newswh.getFexampleid());
			ti.setFuserid(newswh.getFuserinfoid());
			ti.setNums(newswh.getCouldusenums());
			ti.setProfit(newswh.getProfitloss());
			ti.setSharename(newswh.getSharesname());
			ti.setTime(newswh.getTime());
			ti.setTradetype(-1);
			ti.setType(newswh.getType());
			databaseHelper.persistObject(ti);
		}
		
		//更新跟单表
		d.setActualyamount(d.getActualyamount()-needamount);
		d.setDiffamount(d.getDiffamount()+needamount);
		databaseHelper.updateObject(d);
		
		//添加到资金明细表
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(ui.getId());
		cd.setType(sharesWareHouse.getType()==0?1:3);
		cd.setCapital(needamount);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
	
	
	private void sellforeign(int type,UserInfo ui,Documentary d,Long exampleid,ForeignExchange foreignexchange){
		//找出用户跟单的此只外汇
		StringBuffer find = new StringBuffer("select swh from ForeignExchange fe where fe.state = 1 and fe.fdocumentaryid = ").append(d.getId())
				.append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(sharesWareHouse.getFsharesid());
		
		List<Object> lst = databaseHelper.getResultListByHql(find.toString());
		
		Double needamount = 0d;
		if(lst.size()>0){
			ForeignExchange fe = (ForeignExchange)lst.get(0);
			
			int sellnums = type==2?d.getUservalues().intValue():d.getUservalues().intValue()*foreignexchange.getCouldusenums();
			needamount = sellnums*sharesWareHouse.getPrice();
			//检查用户的持仓是否足够
			if(sellnums>fe.getCouldusenums()){//持仓不足，则停止跟单
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
				
				return;
			}
			
			//检查用户是否卖过此外汇
			StringBuffer check = new StringBuffer("select fe from ForeignExchange fe where fe.state = -1 ").append(" and fe.fuserinfoid = ").append(ui.getId()).append(" and fe.fsharesid = ").append(foreignexchange.getFsharesid());
			List<Object> checklst = databaseHelper.getResultListByHql(check.toString());
			ForeignExchange newfe = null;
			if(checklst.size()==0){
				newfe = foreignexchange;
				newfe.setFdocumentaryid(d.getId());
				newfe.setFexampleid(exampleid);
				newfe.setFuserinfoid(ui.getId());
				newfe.setId(null);
				newfe.setIsplan(0);
				newfe.setState(-1);
				newfe.setTime(new Date());
				newfe.setCouldusenums(sellnums);
				newfe.setWarehousenums(sellnums);
				newfe.setPurchase(sellnums*foreignexchange.getPrice());
				newfe.setFforeignexchangeid(fe.getId());
				
				databaseHelper.persistObject(newfe);
				
				fe.setCouldusenums(fe.getCouldusenums()-sellnums);
				databaseHelper.updateObject(fe);
				
			}else{
				
				newfe = (ForeignExchange)lst.get(0);
				newfe.setCouldusenums(fe.getCouldusenums()+sellnums);
				newfe.setWarehousenums(fe.getWarehousenums()+sellnums);
				newfe.setPurchase(fe.getPurchase()+sellnums*foreignexchange.getPrice());
				
				databaseHelper.updateObject(newfe);
				
				fe.setCouldusenums(fe.getCouldusenums()-sellnums);
				databaseHelper.updateObject(fe);
			}
			
			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(newfe.getPurchase());
			ti.setFexampleid(newfe.getFexampleid());
			ti.setFuserid(newfe.getFuserinfoid());
			ti.setNums(newfe.getCouldusenums());
			ti.setProfit(newfe.getProfitloss());
			ti.setSharename(newfe.getForeignexchangename());
			ti.setTime(newfe.getTime());
			ti.setTradetype(-1);
			ti.setType(2);
			databaseHelper.persistObject(ti);
		}
		
		//更新跟单表
		d.setActualyamount(d.getActualyamount()-needamount);
		d.setDiffamount(d.getDiffamount()+needamount);
		databaseHelper.updateObject(d);
		
		//添加到资金明细表
		CapitalDetail cd = new CapitalDetail();
		cd.setFuserinfoid(ui.getId());
		cd.setType(2);
		cd.setCapital(needamount);
		cd.setCreatetime(new Date());
		cd.setState(0);
		databaseHelper.persistObject(cd);
	}
}
