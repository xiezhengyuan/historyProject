package com.hxy.isw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.entity.CapitalDetail;
import com.hxy.isw.entity.ForeignExchange;
import com.hxy.isw.entity.ForeignExchangeEntrust;
import com.hxy.isw.entity.Shares;
import com.hxy.isw.entity.SharesCollect;
import com.hxy.isw.entity.SharesEntrust;
import com.hxy.isw.entity.SharesWareHouse;
import com.hxy.isw.entity.TradeInfo;
import com.hxy.isw.entity.UserInfo;
import com.hxy.isw.entity.UserProfit;
import com.hxy.isw.service.AppServiceTradeService;
import com.hxy.isw.thread.FollowExampleBuy;
import com.hxy.isw.thread.FollowExampleSell;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JuheData;
import com.hxy.isw.util.NowApiUtil;
import com.hxy.isw.util.SyncDemo_股票行情;
import com.hxy.isw.util.Sys;
import com.hxy.isw.util.ThreadPoolManager;

@Repository
public class AppServiceTradeServiceImpl implements AppServiceTradeService {

	@Autowired
	private DatabaseHelper databaseHelper;
	
	@Override
	public Map<String, Object> optionalSharesList(String userid, Integer start, Integer limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		UserInfo userInfo=(UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		StringBuffer sqlCount = new StringBuffer("SELECT COUNT(*) FROM sharescollect sc "
				+ "LEFT JOIN shares s ON sc.`code`=s.`code` WHERE sc.state=0 AND sc.fuserinfoid=").append(userid);

		Integer count = databaseHelper.getSqlCount(sqlCount.toString());

		int pages = ConstantUtil.pages(count, limit);

		StringBuffer sql = new StringBuffer(
				"SELECT s.id,s.sharesname,s.`code`,s.img,s.diffrate,s.diffmoney,s.price,DATE_FORMAT(s.createtime,'%Y-%m-%d %H:%i:%s'),s.quotation,s.type,s.pinyin FROM sharescollect sc "
						+ "LEFT JOIN shares s ON sc.`code`=s.`code` WHERE sc.state=0 AND sc.fuserinfoid=")
								.append(userid)
								.append("" + " ORDER BY sc.`rank` IS NULL,sc.`rank` ASC,sc.createtime DESC");

		List<Object[]> resultList = databaseHelper.getResultListBySql(sql.toString(), start, limit);
		for (Object object[] : resultList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", object[0] == null ? "" : object[0].toString());// 股票表id
			map.put("sharesname", object[1] == null ? "" : object[1].toString());// 股票名称
			map.put("code", object[2] == null ? "" : object[2].toString());// 股票代码
			map.put("img", object[3] == null ? "" : object[3].toString());// 图标
			map.put("diffrate", object[4] == null ? "" : object[4].toString());// 涨跌幅度
			map.put("diffmoney", object[5] == null ? "" : object[5].toString());// 涨跌金额
			map.put("price", object[6] == null ? "" : object[6].toString());// 股票价格
			map.put("createtime", object[7] == null ? "" : object[7].toString());// 时间
			String result=object[8]==null?"":object[8].toString();
			String type=object[9].toString();//0股票 1美股 2外汇
			if(result.length()>0){
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				if("0".equals(type)){
					map.put("highLimit", json.get("highLimit").getAsString());//涨停价
					map.put("downLimit", json.get("downLimit").getAsString());//跌停价
					String nowPrice = json.get("nowPrice").getAsString();
					map.put("nowPrice", nowPrice);//当前价
					Integer allCount=(int) (userInfo.getVirtualcapital()/Double.parseDouble(nowPrice));
					map.put("maxBuy", allCount);//最多可买股数
				}else{
					map.put("highLimit", json.get("maxpri").getAsString());//涨停价
					map.put("downLimit", json.get("minpri").getAsString());//跌停价
					String pinyin=object[10].toString();
					String nowPrice = JuheData.getnowprice(pinyin);
					map.put("nowPrice", nowPrice);//当前价
					Integer allCount=(int) (userInfo.getVirtualcapital()/Double.parseDouble(nowPrice));
					map.put("maxBuy", allCount);//最多可买股数
				}
			}
			map.put("type", type);
			if(type.equals("2")){
				StringBuffer sql2=new StringBuffer("SELECT SUM(fe.couldusenums) FROM foreignexchange fe WHERE fe.state=0 AND fe.isplan=0 AND fe.fuserinfoid=")
						.append(userid).append(" AND fe.`code`='").append(object[2].toString()).append("'");
				
				List sum = databaseHelper.getResultListBySql(sql2.toString());
				
				map.put("maxSell", sum.get(0)==null?"0":sum.get(0).toString());
				
			}else{
				StringBuffer sql2=new StringBuffer("SELECT SUM(swh.couldusenums) FROM shareswarehouse swh WHERE swh.state=0 AND swh.isplan=0 AND swh.fuserinfoid=")
						.append(userid).append(" AND swh.`code`='").append(object[2].toString()+"'").append(" AND swh.type=").append(type);
				
				List sum = databaseHelper.getResultListBySql(sql2.toString());
				map.put("maxSell", sum.get(0)==null?"0":sum.get(0).toString());
			}
			
			list.add(map);
		}
		resultMap.put("total", count);
		resultMap.put("pages", pages);
		resultMap.put("rows", list);
		resultMap.put("msg", "success");
		resultMap.put("info", "");
		return resultMap;
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addOptionalShares(String userid, String code) throws Exception{
		
		//write by lcc ==================start====2017.08.21 11:44=====================
		if(userid==null||userid.length()==0)throw new Exception("userid参数错误");
		
		//用户信息
		UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		//检查代码类型
		StringBuffer check = new StringBuffer("select shares from Shares shares where shares.state = 0 and shares.code = '").append(code).append("'");
		List<Object> lst = databaseHelper.getResultListByHql(check.toString());
		if(lst.size()==0)throw new Exception("此代码不存在");
		
		Shares shares = (Shares)lst.get(0);
		
		//检查是否是牛人
		if(userInfo.getIsexample()==1){
			
			if(userInfo.getTag()!=shares.getType())throw new Exception("你不是此类型的牛人，无法添加");
		}
		
		
		//检查用户是否已经添加过
		check = new StringBuffer("select sharesCollect from SharesCollect sharesCollect where sharesCollect.state = 0 and sharesCollect.code = '").append(code).append("'")
				.append(" and sharesCollect.fuserinfoid = ").append(userInfo.getId());
		lst = databaseHelper.getResultListByHql(check.toString());
		if(lst.size()>0)throw new Exception("你已添加过此股，请不要重复添加");
		
		//write by lcc ==================end====2017.08.21 11:45=====================
		
		SharesCollect sharesCollect = new SharesCollect();
		sharesCollect.setFuserinfoid(Long.parseLong(userid));
		sharesCollect.setCode(code);
		sharesCollect.setCreatetime(new Date());
		sharesCollect.setState(0);

		databaseHelper.persistObject(sharesCollect);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delOptionalShares(String userid, String code)  throws Exception{
		StringBuffer sql = new StringBuffer("UPDATE sharescollect sc SET sc.state=-1 WHERE sc.fuserinfoid=")
				.append(userid).append(" AND sc.code=").append(code);
		databaseHelper.executeNativeSql(sql.toString());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void buyShares(String userid, String entrustType, String entrustPrice, String count, String sharesid,
			String positions, String type,String isplan) throws Exception {

		if(isplan==null||isplan.length()==0)throw new Exception("参数isplan不能为空");
		
		Shares shares = (Shares) databaseHelper.getObjectById(Shares.class, Long.parseLong(sharesid));
		// 股票代码
		String code = shares.getCode();
		// 股票名称
		String sharesname = shares.getSharesname();
		// 当前股票价格
		Double price = shares.getPrice();
		//type
		type=shares.getType().toString();
		// 用户信息
		UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		//write by lcc ==================start====2017.08.21 11:30=====================
		//检查是否是牛人
		if(userInfo.getIsexample()==1){
			if(!userInfo.getTag().equals(Integer.parseInt(type)))throw new Exception("你不是此类型的牛人，无法购买");
		}
		//write by lcc ==================start====2017.08.21 11:30=====================
		
		if (entrustType.equals("1")) {// 限价委托
			//write by lcc==================start==============================
			//检查是否有此股票的限价委托单(委托价一致)
			StringBuffer check = new StringBuffer("select sharesentrust from SharesEntrust sharesentrust where sharesentrust.entrustprice =").append(entrustPrice).append(" and sharesentrust.entruststate = 0 and sharesentrust.state = 0 and sharesentrust.fsharesid = ").append(shares.getId());
			List<Object> lst = databaseHelper.getResultListByHql(check.toString());
			if(lst.size()>0){
				SharesEntrust sharesentrust = (SharesEntrust)lst.get(0);
				Double realVirtualcapital = 0d;
				
				if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
					// 获取用户现有虚拟资金总数
					Double virtualcapital = userInfo.getVirtualcapital();
					// 此次委托购买总价格
					// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
					Double money = virtualcapital * (Double.parseDouble(positions));
					// 购买价格除以限价获得总股数 向下取整(股数为整数)
//					Double realCount = Math.floor(money / (Double.parseDouble(entrustPrice)));
					Integer realCount=(int) (money / (Double.parseDouble(entrustPrice)));
					/* 真实购买数量 */
					sharesentrust.setEntrustnums(sharesentrust.getEntrustnums()+realCount);// 委托数量
					// 校验价格 真实股数乘以限价
					realVirtualcapital = realCount * (Double.parseDouble(entrustPrice));
					
					// 冻结资金
					sharesentrust.setFrozenamount(sharesentrust.getFrozenamount()+realVirtualcapital);
					
					
				} else {
					sharesentrust.setEntrustnums(sharesentrust.getEntrustnums()+Integer.parseInt(count));// 委托数量
					realVirtualcapital = Integer.parseInt(count) * Double.parseDouble(entrustPrice);
					sharesentrust.setFrozenamount(sharesentrust.getFrozenamount()+realVirtualcapital);// 冻结资金
					
				}
				
				databaseHelper.updateObject(sharesentrust);
				
				if("0".equals(isplan)){//非计划
					// 股数乘以限价 冻结资金
	
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
				
				
				
				//write by lcc==================end==============================
			}else{
			
			SharesEntrust sharesEntrust = new SharesEntrust();

			sharesEntrust.setFuserinfoid(Long.parseLong(userid));// 用户id
			sharesEntrust.setFsharesid(Long.parseLong(sharesid));// 股票表ID
			sharesEntrust.setFshareswarehouseid(null);// 外键关联用户股票表 卖出时有此值
			sharesEntrust.setSharesname(sharesname);// 股票名称
			sharesEntrust.setCode(code);// 股票代码

			sharesEntrust.setPrice(price);// 单价

			sharesEntrust.setEntrustprice(Double.parseDouble(entrustPrice));// 委托价

			
			if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// 获取用户现有虚拟资金总数
				Double virtualcapital = userInfo.getVirtualcapital();
				// 此次委托购买总价格
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Double money = virtualcapital * (Double.parseDouble(positions));
				// 购买价格除以限价获得总股数 向下取整(股数为整数)
//				Double realCount = Math.floor(money / (Double.parseDouble(entrustPrice)));
				Integer realCount=(int) (money / (Double.parseDouble(entrustPrice)));
				/* 真实购买数量 */
				sharesEntrust.setEntrustnums(realCount);// 委托数量
				// 校验价格 真实股数乘以限价
				Double realVirtualcapital = realCount * (Double.parseDouble(entrustPrice));
				
				// 冻结资金
				sharesEntrust.setFrozenamount(realVirtualcapital);
				
				if("0".equals(isplan)){//非计划
				
	
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			} else {
				sharesEntrust.setEntrustnums(Integer.parseInt(count));// 委托数量
				Double realVirtualcapital = Integer.parseInt(count) * Double.parseDouble(entrustPrice);
				sharesEntrust.setFrozenamount(realVirtualcapital);// 冻结资金
				if("0".equals(isplan)){//非计划
					// 股数乘以限价 冻结资金
	
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			}
			sharesEntrust.setTime(new Date());// 时间
			sharesEntrust.setState(0);// 状态
			// 此处为非计划买入入口
			sharesEntrust.setIsplan(0);// 0非计划 1计划（计划中为纯数据）是否为牛人计划中购买股票标识
			sharesEntrust.setType(shares.getType());// 0股票 1美股

			sharesEntrust.setEntruststate(0);//挂单状态  0挂单中  1已匹配成功  ----write by lcc 
			databaseHelper.persistObject(sharesEntrust);
			}
		} else if (entrustType.equals("2")) {// 市价委托
			SharesWareHouse sharesWareHouse = new SharesWareHouse();
			sharesWareHouse.setFuserinfoid(Long.parseLong(userid));

			sharesWareHouse.setFsharesid(Long.parseLong(sharesid));// shares表主键id

			sharesWareHouse.setSharesname(sharesname);// 股票名称
			sharesWareHouse.setCode(code);// 股票代码

			
			if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// 获取用户现有虚拟资金总数
				Double virtualcapital = userInfo.getVirtualcapital();
				// 此次委托购买总价格
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Double money = virtualcapital * (Double.parseDouble(positions));
				// 购买总价格除以当前股价获得总股数 向下取整(股数为整数)
//				Double realCount = Math.floor(money / price);
				Integer realCount=(int) (money / price);
				/* 真实购买数量 */
				// 持仓数量
				sharesWareHouse.setWarehousenums(realCount);
				// 可用数量
				sharesWareHouse.setCouldusenums(realCount);
				// 校验价格 真实股数乘以限价
				Double realVirtualcapital = realCount * price;
				// 市值
				sharesWareHouse.setMarketvalue(realVirtualcapital);
				// 成本
				sharesWareHouse.setCost(realVirtualcapital);
				// 单价
				sharesWareHouse.setPrice(price);
				// 盈亏
				sharesWareHouse.setProfitloss(0D);

				if("0".equals(isplan)){//非计划
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					if (shares.getType().equals("0")) {// 股票
						capitalDetail.setType(-1);// -5跟单金额 -4 委托冻结资金 -3
													// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
													// 3美股卖出
					} else {// 美股
						capitalDetail.setType(-3);// -5跟单金额 -4 委托冻结资金 -3
													// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
													// 3美股卖出
					}
	
					databaseHelper.persistObject(capitalDetail);
				}
			} else {
				// 持仓数量
				sharesWareHouse.setWarehousenums(Integer.parseInt(count));
				// 可用数量
				sharesWareHouse.setCouldusenums(Integer.parseInt(count));
				// 购买金额
				Double realVirtualcapital = Integer.parseInt(count) * price;
				// 市值
				sharesWareHouse.setMarketvalue(realVirtualcapital);
				// 成本
				sharesWareHouse.setCost(realVirtualcapital);
				// 单价
				sharesWareHouse.setPrice(price);
				// 盈亏
				sharesWareHouse.setProfitloss(0D);

				if("0".equals(isplan)){//非计划
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					if (shares.getType().equals("0")) {// 股票
						capitalDetail.setType(-1);// -5跟单金额 -4 委托冻结资金 -3
													// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
													// 3美股卖出
					} else {// 美股
						capitalDetail.setType(-3);// -5跟单金额 -4 委托冻结资金 -3
													// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
													// 3美股卖出
					}
					databaseHelper.persistObject(capitalDetail);
				}
			}
			sharesWareHouse.setTime(new Date());
			sharesWareHouse.setState(0);// 状态 0持仓 -1卖出
			sharesWareHouse.setIsplan(Integer.parseInt(isplan));// 需要判断该用户是否是牛人 且是否是计划中购买

			sharesWareHouse.setType(shares.getType());// 0股票1美股

			databaseHelper.persistObject(sharesWareHouse);
			
			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(sharesWareHouse.getCost());
			ti.setFexampleid(sharesWareHouse.getFexampleid());
			ti.setFuserid(sharesWareHouse.getFuserinfoid());
			ti.setNums(sharesWareHouse.getCouldusenums());
			ti.setProfit(sharesWareHouse.getProfitloss());
			ti.setSharename(sharesWareHouse.getSharesname());
			ti.setTime(sharesWareHouse.getTime());
			ti.setTradetype(1);
			ti.setType(shares.getType());
			databaseHelper.persistObject(ti);
			
			if(userInfo.getIsexample()==1&&"0".equals(isplan)){//启动线程
				FollowExampleBuy febs = new FollowExampleBuy();
				febs.sharesWareHouse = sharesWareHouse;
				ThreadPoolManager.exec(febs);
			}

		} else {
			throw new Exception("参数错误");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void buyForeign(String userid, String entrustType, String entrustPrice, String count, String sharesid,
			String positions,String isplan,String buytype) throws Exception {

		if(isplan==null||isplan.length()==0)throw new Exception("参数isplan不能为空");
		if(buytype==null||buytype.length()==0)throw new Exception("参数buytype不能为空");
		
		// 用户信息
		UserInfo userInfo = (UserInfo) databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		//write by lcc ==================start====2017.08.21 11:31=====================
		//检查是否是牛人
		if(userInfo.getIsexample()==1){
			if(userInfo.getTag()!=2)throw new Exception("你不是外汇牛人，无法购买");
		}
		//write by lcc ==================end====2017.08.21 11:31=====================
		
		Shares shares = (Shares) databaseHelper.getObjectById(Shares.class, Long.parseLong(sharesid));
		// 外汇代码
		String code = shares.getCode();
		// 外汇名称
		String sharesname = shares.getSharesname();
		// 当前外汇价格
		Double price = shares.getPrice();
		if (entrustType.equals("1")) {// 限价委托
			//write by lcc==================start==============================
			//检查是否有此股票的限价委托单(委托价一致)
			StringBuffer check = new StringBuffer("select foreignexchangeentrust from ForeignExchangeEntrust foreignexchangeentrust where foreignexchangeentrust.entrustprice =").append(entrustPrice).append(" and foreignexchangeentrust.entruststate = 0 and sharesentrust.state = 0 and foreignexchangeentrust.fsharesid = ").append(shares.getId());
			List<Object> lst = databaseHelper.getResultListByHql(check.toString());
			if(lst.size()>0){
				ForeignExchangeEntrust foreignexchangeentrust = (ForeignExchangeEntrust)lst.get(0);
				Double realVirtualcapital = 0d;
				if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
					// 获取用户现有虚拟资金总数
					Double virtualcapital = userInfo.getVirtualcapital();
					// 此次委托购买总价格
					// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
					Double money = virtualcapital * (Double.parseDouble(positions));
					// 购买价格除以限价获得总股数 向下取整(股数为整数)
//					Double realCount = Math.floor(money / (Double.parseDouble(entrustPrice)));
					Integer realCount=(int) (money / (Double.parseDouble(entrustPrice)));
					/* 真实购买数量 */
					foreignexchangeentrust.setEntrustnums(foreignexchangeentrust.getEntrustnums()+realCount);// 委托数量
					// 校验价格 真实股数乘以限价
					realVirtualcapital = realCount * (Double.parseDouble(entrustPrice));
					
					
					foreignexchangeentrust.setFrozenamount(foreignexchangeentrust.getFrozenamount()+realVirtualcapital);
					
				} else {
					foreignexchangeentrust.setEntrustnums(foreignexchangeentrust.getEntrustnums()+Integer.parseInt(count));// 委托数量
					// 股数乘以限价 冻结资金
					realVirtualcapital = Integer.parseInt(count) * Double.parseDouble(entrustPrice);
					foreignexchangeentrust.setFrozenamount(foreignexchangeentrust.getFrozenamount()+realVirtualcapital);// 冻结资金
					
					
				}
				foreignexchangeentrust.setBuytype(Integer.parseInt(buytype));
				databaseHelper.updateObject(foreignexchangeentrust);
				
				if("0".equals(isplan)){//非计划
					
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			}else{
			ForeignExchangeEntrust foreignExchangeEntrust = new ForeignExchangeEntrust();

			foreignExchangeEntrust.setFuserinfoid(Long.parseLong(userid));// 用户id
			foreignExchangeEntrust.setFsharesid(Long.parseLong(sharesid));// 外汇表ID
			foreignExchangeEntrust.setFforeignexchangeid(null);// 外键关联用户外汇表
																// 卖出时有此值
			foreignExchangeEntrust.setForeignexchangename(sharesname);// 外汇名称
			foreignExchangeEntrust.setCode(code);// 股票代码

			foreignExchangeEntrust.setPrice(price);// 单价
			foreignExchangeEntrust.setEntrustprice(Double.parseDouble(entrustPrice));// 委托价

			if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// 获取用户现有虚拟资金总数
				Double virtualcapital = userInfo.getVirtualcapital();
				// 此次委托购买总价格
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Double money = virtualcapital * (Double.parseDouble(positions));
				// 购买价格除以限价获得总股数 向下取整(股数为整数)
//				Double realCount = Math.floor(money / (Double.parseDouble(entrustPrice)));
				Integer realCount=(int) (money / (Double.parseDouble(entrustPrice)));
				/* 真实购买数量 */
				foreignExchangeEntrust.setEntrustnums(realCount);// 委托数量
				// 校验价格 真实股数乘以限价
				Double realVirtualcapital = realCount * (Double.parseDouble(entrustPrice));
				
				
				// 冻结资金
				foreignExchangeEntrust.setFrozenamount(realVirtualcapital);
				if("0".equals(isplan)){//非计划
	
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			} else {
				foreignExchangeEntrust.setEntrustnums(Integer.parseInt(count));// 委托数量
				// 股数乘以限价 冻结资金
				Double realVirtualcapital = Integer.parseInt(count) * Double.parseDouble(entrustPrice);
				foreignExchangeEntrust.setFrozenamount(realVirtualcapital);// 冻结资金
				if("0".equals(isplan)){//非计划
	
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细表
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-4);// -5跟单金额 -4 委托冻结资金 -3 美股购买-2外汇购买
												// -1股票购买 1股票卖出 2外汇卖出 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			}

			foreignExchangeEntrust.setTime(new Date());// 时间
			foreignExchangeEntrust.setState(0);// 状态
			// 此处为非计划买入入口
			foreignExchangeEntrust.setIsplan(0);// 0非计划
												// 1计划（计划中为纯数据）是否为牛人计划中购买股票标识

			foreignExchangeEntrust.setEntruststate(0);//挂单状态  0挂单中  1已匹配成功  ----write by lcc 
			foreignExchangeEntrust.setBuytype(Integer.parseInt(buytype));
			databaseHelper.persistObject(foreignExchangeEntrust);
		}
		} else if (entrustType.equals("2")) {// 市价委托
			ForeignExchange foreignExchange = new ForeignExchange();

			foreignExchange.setFuserinfoid(Long.parseLong(userid));

			foreignExchange.setFsharesid(Long.parseLong(sharesid));// shares表主键id

			foreignExchange.setForeignexchangename(sharesname);// 外汇名称
			foreignExchange.setCode(code);// 股票代码
			// 单价
			foreignExchange.setPrice(price);

			
			if (!StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// 获取用户现有虚拟资金总数
				Double virtualcapital = userInfo.getVirtualcapital();
				// 此次委托购买总价格
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Double money = virtualcapital * (Double.parseDouble(positions));
				// 购买总价格除以当前股价获得总股数 向下取整(股数为整数)
//				Double realCount = Math.floor(money / price);
				Integer realCount=(int) (money / price);
				/* 真实购买数量 */
				// 持仓数量
				foreignExchange.setWarehousenums(realCount);
				// 可用数量
				foreignExchange.setCouldusenums(realCount);
				// 校验价格 真实股数乘以限价
				Double realVirtualcapital = realCount * price;
				// 买入
				foreignExchange.setPurchase(realVirtualcapital);
				// 卖出
				foreignExchange.setSellout(0D);
				// 盈亏
				foreignExchange.setProfitloss(0D);

				
				if("0".equals(isplan)){//非计划
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-2);// -5跟单金额 -4 委托冻结资金 -3
												// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
												// 3美股卖出
	
					databaseHelper.persistObject(capitalDetail);
				}
			} else {
				// 持仓数量
				foreignExchange.setWarehousenums(Integer.parseInt(count));
				// 可用数量
				foreignExchange.setCouldusenums(Integer.parseInt(count));
				// 购买金额
				Double realVirtualcapital = Integer.parseInt(count) * price;
				// 买入
				foreignExchange.setPurchase(realVirtualcapital);
				// 卖出
				foreignExchange.setSellout(0D);
				// 盈亏
				foreignExchange.setProfitloss(0D);

				if("0".equals(isplan)){//非计划
					StringBuffer sql = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital-")
							.append(realVirtualcapital).append(" WHERE ui.virtualcapital-").append(realVirtualcapital)
							.append(">=0 AND ui.id=").append(userid);
					// 修改用户虚拟资金
					int result = databaseHelper.executeNativeSql(sql.toString());
					if (result == 0) {
						throw new Exception("余额不足");
					}
	
					// 添加资金明细
					CapitalDetail capitalDetail = new CapitalDetail();
					capitalDetail.setCapital(-realVirtualcapital);
					capitalDetail.setCreatetime(new Date());
					capitalDetail.setFuserinfoid(Long.parseLong(userid));
					capitalDetail.setState(0);
					capitalDetail.setType(-2);// -5跟单金额 -4 委托冻结资金 -3
												// 美股购买-2外汇购买-1股票购买 1股票卖出 2外汇卖出
												// 3美股卖出
					databaseHelper.persistObject(capitalDetail);
				}
			}
			foreignExchange.setTime(new Date());
			foreignExchange.setState(0);// 状态 0持仓 -1卖出
			foreignExchange.setIsplan(Integer.parseInt(isplan));// 需要判断该用户是否是牛人 且是否是计划中购买
			foreignExchange.setBuytype(Integer.parseInt(buytype));
			databaseHelper.persistObject(foreignExchange);

			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(foreignExchange.getPurchase());
			ti.setFexampleid(foreignExchange.getFexampleid());
			ti.setFuserid(foreignExchange.getFuserinfoid());
			ti.setNums(foreignExchange.getCouldusenums());
			ti.setProfit(foreignExchange.getProfitloss());
			ti.setSharename(foreignExchange.getForeignexchangename());
			ti.setTime(foreignExchange.getTime());
			ti.setTradetype(1);
			ti.setType(2);
			databaseHelper.persistObject(ti);
			
			if(userInfo.getIsexample()==0&&"0".equals(isplan)){//启动线程
				FollowExampleBuy feb = new FollowExampleBuy();
				feb.foreignexchange = foreignExchange;
				ThreadPoolManager.exec(feb);
			}
			
		} else {
			throw new Exception("参数错误");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sellShares(String userid, String entrustType, String entrustPrice, String count, String sharesid,
			String positions, String type,String isplan) throws Exception {
		if(isplan==null||isplan.length()==0)throw new Exception("参数isplan不能为空");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		Shares shares = (Shares) databaseHelper.getObjectById(Shares.class, Long.parseLong(sharesid));
		// 股票代码
		String code = shares.getCode();
		// 股票名称
		String sharesname = shares.getSharesname();
		// 当前股票价格
		//Double price = shares.getPrice();
		Double price = 0d;
		SyncDemo_股票行情 sd = new SyncDemo_股票行情();
		
		if(shares.getType()==0){//沪深股票
			//查看股票行情
			ApiResponse response = sd.股票行情(code, "0", "0");
			
			String result = new String(response.getBody(), "utf-8");
			JsonObject json = (JsonObject) new JsonParser().parse(result);
			
			if(json.get("showapi_res_code").getAsInt()!=0)return;
			
			JsonObject jsonObject = json.get("showapi_res_body").getAsJsonObject().get("stockMarket").getAsJsonObject();
			
			price = Double.parseDouble(jsonObject.get("nowPrice").getAsString());//当前价
		}else{//美股
			/*String result = NowApiUtil.getShareIndexOfUS(shares.getCode());
			
			JsonObject json = (JsonObject) new JsonParser().parse(result);
				
			JsonObject obj = json.get("result").getAsJsonObject().get("lists").getAsJsonObject().get(shares.getCode()).getAsJsonObject();
			
			price = Double.parseDouble(obj.get("last_price").getAsString());*/
			
			
			price = Double.parseDouble(JuheData.getnowprice(shares.getPinyin()));
			
		}
		
		//该股票总股数
		StringBuffer sql = new StringBuffer(
				"SELECT SUM(swh.couldusenums) 'a' FROM shareswarehouse swh WHERE swh.state=0 AND swh.isplan=0")
						.append(" AND swh.type=").append(shares.getType()).append(" AND swh.fuserinfoid=").append(userid)
						.append(" AND swh.fsharesid=").append(sharesid);
		//该股票信息
		StringBuffer hql = new StringBuffer("SELECT swh FROM SharesWareHouse swh WHERE swh.state=0 AND swh.isplan=0")
				.append(" AND swh.type=").append(shares.getType()).append(" AND swh.fuserinfoid=").append(userid)
				.append(" AND swh.fsharesid=").append(sharesid).append(" ORDER BY swh.time");

		List resultList = databaseHelper.getResultListByHql(hql.toString());

		// 该股票总股数
//		Integer allCouldusenums = (Integer) databaseHelper.getResultListBySql(sql.toString()).get(0);//有错误 -- edit by lcc
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		Integer allCouldusenums = lst==null?0:lst.size()==0?0:lst.get(0)==null?0:Integer.parseInt(lst.get(0).toString());
		System.err.println("股票总股数==：" + allCouldusenums);

		if (allCouldusenums == 0) {
			throw new Exception("该股票可用手数不足");
		}
		if (entrustType.equals("1")) {// 限价委托
			SharesEntrust sharesEntrust = new SharesEntrust();
			sharesEntrust.setFuserinfoid(Long.parseLong(userid));// 用户id
			sharesEntrust.setFsharesid(Long.parseLong(sharesid));// 股票表ID
			sharesEntrust.setSharesname(sharesname);// 股票名称
			sharesEntrust.setCode(code);// 股票代码
			sharesEntrust.setPrice(price);// 单价
			sharesEntrust.setEntrustprice(Double.parseDouble(entrustPrice));// 委托价
			sharesEntrust.setFrozenamount(0D);// 冻结资金
			// 委托卖出的总股数
			Integer realCouldusenums = 0;
			if (StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Integer couldusenums = (int) (allCouldusenums * (Double.parseDouble(positions)));
				// 卖出股数 向上取整(股数为整数)
//				realCouldusenums = Math.ceil(couldusenums);
				realCouldusenums=couldusenums+1;
			} else {
				if (allCouldusenums < Integer.parseInt(count)) {
					throw new Exception("该股票可用手数不足");
				} else {
					realCouldusenums = Integer.parseInt(count);
				}
			}
			sharesEntrust.setTime(new Date());
			sharesEntrust.setState(-1);
			sharesEntrust.setIsplan(0);
			sharesEntrust.setType(shares.getType());
			sharesEntrust.setEntruststate(0);//挂单状态  0挂单中  1已匹配成功  ----write by lcc 
			for (Object object : resultList) {
				SharesWareHouse house = (SharesWareHouse) object;
				sharesEntrust.setFshareswarehouseid(house.getId());// 外键关联用户股票表
																	// 卖出时有此值

				// 如果卖出的总股数小于获取的可用手数
				if (house.getCouldusenums() >= realCouldusenums) {
					sharesEntrust.setEntrustnums(Integer.parseInt(realCouldusenums.toString()));
					databaseHelper.persistObject(sharesEntrust);

					// 跳出循环
					break;
				} else {
					sharesEntrust.setEntrustnums(house.getCouldusenums());
					realCouldusenums -= house.getCouldusenums();
					databaseHelper.persistObject(sharesEntrust);
				}
			}

		} else if (entrustType.equals("2")) {// 市价委托
			SharesWareHouse sharesWareHouse = new SharesWareHouse();
			sharesWareHouse.setFuserinfoid(Long.parseLong(userid));// 用户id
			sharesWareHouse.setFsharesid(Long.parseLong(sharesid));// 股票表ID
			sharesWareHouse.setSharesname(sharesname);// 股票名称
			sharesWareHouse.setCode(code);// 股票代码
			sharesWareHouse.setPrice(price);// 单价

			// 委托卖出的总股数
			Integer realCouldusenums = 0;
			if (StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Integer couldusenums = (int) (allCouldusenums * (Double.parseDouble(positions)));
				// 卖出股数 向上取整(股数为整数)
//				realCouldusenums = Math.ceil(couldusenums);
				realCouldusenums = couldusenums+1;
			} else {
				if (allCouldusenums < Integer.parseInt(count)) {
					throw new Exception("该股票可用手数不足");
				} else {
					realCouldusenums = Integer.parseInt(count);
				}
			}
			sharesWareHouse.setTime(new Date());
			sharesWareHouse.setState(-1);// 状态 0持仓 1跟单 -1卖出
			sharesWareHouse.setIsplan(Integer.parseInt(isplan));// 0非计划 1计划（计划中为纯数据） 是否为牛人计划中购买股票标识
			sharesWareHouse.setType(shares.getType());// 0股票1美股
			Double lastprice = 0d;
			for (Object object : resultList) {
				SharesWareHouse house = (SharesWareHouse) object;
				sharesWareHouse.setMarketvalue(house.getMarketvalue());// 市值
				sharesWareHouse.setWarehousenums(house.getWarehousenums());// 持仓手数
				sharesWareHouse.setCost(house.getCost());// 成本

				sharesWareHouse.setFshareswarehouseid(house.getId());// State=-1时
																		// 关联此值
				lastprice = house.getPrice();
				// 如果卖出的总股数小于获取的可用手数
				if (house.getCouldusenums() >= realCouldusenums) {

					sharesWareHouse.setCouldusenums(Integer.parseInt(realCouldusenums.toString()));// 可用手数
					Double profitloss = realCouldusenums * price - realCouldusenums * house.getPrice();
					sharesWareHouse.setProfitloss(profitloss);// 盈亏
					//修改原股可用手数
					StringBuffer sql2 = new StringBuffer("UPDATE shareswarehouse swh SET swh.couldusenums= swh.couldusenums-")
							.append(realCouldusenums).append(" WHERE swh.id=").append(house.getId())
							.append(" AND swh.couldusenums>=").append(realCouldusenums);
					int result = databaseHelper.executeNativeSql(sql2.toString());
					if (result == 0) {
						throw new Exception("该股票可用手数不足");
					} else {
						if("0".equals(isplan)){//非计划
							// 加虚拟资金
							Double money = realCouldusenums * price;
							StringBuffer sql3 = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital = ui.virtualcapital+").append(money)
									.append(" WHERE ui.id=").append(userid);
							databaseHelper.executeNativeSql(sql3.toString());
							// 加资金明细
							CapitalDetail capitalDetail = new CapitalDetail();
							capitalDetail.setFuserinfoid(Long.parseLong(userid));
							capitalDetail.setCapital(money);
							capitalDetail.setCreatetime(new Date());
							capitalDetail.setState(0);
							if (shares.getType().equals("0")) {
								capitalDetail.setType(1);
							} else {
								capitalDetail.setType(3);
							}
							databaseHelper.persistObject(capitalDetail);
						}
					}
					databaseHelper.persistObject(sharesWareHouse);

					// 跳出循环
					break;
				} else {
					Double profitloss = house.getCouldusenums() * price - house.getCouldusenums() * house.getPrice();
					sharesWareHouse.setProfitloss(profitloss);// 盈亏
					//修改原股可用手数
					StringBuffer sql2 = new StringBuffer("UPDATE shareswarehouse swh SET swh.couldusenums = swh.couldusenums - ")
							.append(house.getCouldusenums()).append(" WHERE swh.id=").append(house.getId())
							.append(" AND swh.couldusenums >= ").append(house.getCouldusenums());
					int result = databaseHelper.executeNativeSql(sql2.toString());
					if (result == 0) {
						throw new Exception("该股票可用手数不足");
					} else {
						if("0".equals(isplan)){//非计划
							// 加虚拟资金
							Double money = house.getCouldusenums() * price;
							StringBuffer sql3 = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital = ui.virtualcapital +").append(money)
									.append(" WHERE ui.id=").append(userid);
							databaseHelper.executeNativeSql(sql3.toString());
							// 加资金明细
							CapitalDetail capitalDetail = new CapitalDetail();
							capitalDetail.setFuserinfoid(Long.parseLong(userid));
							capitalDetail.setCapital(money);
							capitalDetail.setCreatetime(new Date());
							capitalDetail.setState(0);
							if (shares.getType().equals("0")) {
								capitalDetail.setType(1);
							} else {
								capitalDetail.setType(3);
							}
							databaseHelper.persistObject(capitalDetail);
						}
					}

					sharesWareHouse.setCouldusenums(house.getCouldusenums());
					realCouldusenums -= house.getCouldusenums();
					databaseHelper.persistObject(sharesWareHouse);
				}
			}
			
			//write by lcc =====start=====================
			
			//添加收益到用户收益记录表
			UserProfit userprofit = new UserProfit();
			userprofit.setCost(sharesWareHouse.getCost());
			userprofit.setDiffprice(price-lastprice);
			//找出最后一次的收益记录
			StringBuffer find = new StringBuffer("select userprofit from UserProfit userprofit  where userprofit.type =").append(sharesWareHouse.getType()).append(" and userprofit.fuserinfoid = ").append(sharesWareHouse.getFuserinfoid()).append(" order by userprofit.time desc");
			List<Object> last = databaseHelper.getResultListByHql(find.toString());
			if(last.size()==0){
				userprofit.setDiffpriceoflasttime(0d);
				userprofit.setDiffprofitoflasttime(0d);
				userprofit.setDiffprofitoflasttimerate(0d);
			}else{
				UserProfit lastuserprofit = (UserProfit)last.get(0);
				userprofit.setDiffpriceoflasttime(price-lastuserprofit.getPrice());//与上次市价的差值
				userprofit.setDiffprofitoflasttime(userprofit.getDiffpriceoflasttime()*sharesWareHouse.getCouldusenums());//与上次市价的差值收益 = diffpriceoflasttime*nums
				userprofit.setDiffprofitoflasttimerate(userprofit.getDiffprofitoflasttime()/userprofit.getCost());//与上次市价的差值收益率 = diffpriceoflasttime/cost
			}
			
			userprofit.setForeignkeyid(sharesWareHouse.getId());
			userprofit.setFuserinfoid(sharesWareHouse.getFuserinfoid());
			userprofit.setNums(sharesWareHouse.getCouldusenums());
			userprofit.setPrice(price);
			userprofit.setProfit(userprofit.getDiffprice()*sharesWareHouse.getCouldusenums());
			userprofit.setProfitrate(userprofit.getDiffprice()/userprofit.getCost());
			userprofit.setTags(0);
			userprofit.setTime(new Date());
			userprofit.setType(sharesWareHouse.getType());
			userprofit.setIsplan(sharesWareHouse.getIsplan());
			databaseHelper.persistObject(userprofit);
			
			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(sharesWareHouse.getCost());
			ti.setFexampleid(sharesWareHouse.getFexampleid());
			ti.setFuserid(sharesWareHouse.getFuserinfoid());
			ti.setNums(sharesWareHouse.getCouldusenums());
			ti.setProfit(sharesWareHouse.getProfitloss());
			ti.setSharename(sharesWareHouse.getSharesname());
			ti.setTime(sharesWareHouse.getTime());
			ti.setTradetype(-1);
			ti.setType(shares.getType());
			databaseHelper.persistObject(ti);
			
			if(ui.getIsexample()==1&&"0".equals(isplan)){//启动线程
				FollowExampleSell fess = new FollowExampleSell();
				fess.sharesWareHouse = sharesWareHouse;
				ThreadPoolManager.exec(fess);
			}
			
			//write by lcc =====end=====================
		} else {
			throw new Exception("参数错误");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sellForeign(String userid, String entrustType, String entrustPrice, String count, String sharesid,
			String positions,String isplan) throws Exception {
		if(isplan==null||isplan.length()==0)throw new Exception("参数isplan不能为空");
		
		UserInfo ui = (UserInfo)databaseHelper.getObjectById(UserInfo.class, Long.parseLong(userid));
		
		Shares shares = (Shares) databaseHelper.getObjectById(Shares.class, Long.parseLong(sharesid));
		// 外汇代码
		String code = shares.getCode();
		// 外汇名称
		String sharesname = shares.getSharesname();
		// 当前外汇价格
		//Double price = shares.getPrice();
		/*String res = NowApiUtil.getWHIndex(code);
		 
		JsonObject json = (JsonObject) new JsonParser().parse(res);
			
		JsonObject obj = json.get("result").getAsJsonObject();
		
		Double price = Double.parseDouble(obj.get("rate").getAsString());*/
		
		/*String res = NowApiUtil.getWHIndexByAli(code);
		
		JsonObject json = (JsonObject) new JsonParser().parse(res);
		
		Double price = 0d;
		if("ok".equals(json.get("msg").getAsString())){
		
			JsonObject jo = json.get("result").getAsJsonObject();
		
			price = jo.get("rate").getAsDouble();
		}*/
		
		Double price = Double.parseDouble(JuheData.getnowprice(code));
		
		//该外汇总数
		StringBuffer sql = new StringBuffer(
				"SELECT SUM(fe.couldusenums) FROM foreignexchange fe WHERE fe.state=0 AND fe.isplan=0")
						.append(" AND fe.fuserinfoid=").append(userid)
						.append(" AND fe.fsharesid=").append(sharesid);
		//该股票信息
		StringBuffer hql = new StringBuffer("SELECT fe FROM ForeignExchange fe WHERE fe.state=0 AND fe.isplan=0")
				.append(" AND fe.fuserinfoid=").append(userid)
				.append(" AND fe.fsharesid=").append(sharesid).append(" ORDER BY fe.time");

		List resultList = databaseHelper.getResultListByHql(hql.toString());

		// 该外汇总数
		Integer allCouldusenums = (Integer) databaseHelper.getResultListBySql(sql.toString()).get(0);
		System.err.println("外汇总股数==：" + allCouldusenums);

		if (allCouldusenums == 0) {
			throw new Exception("该外汇可用手数不足");
		}
		if (entrustType.equals("1")) {// 限价委托
			ForeignExchangeEntrust exchangeEntrust=new ForeignExchangeEntrust();
			
			exchangeEntrust.setFuserinfoid(Long.parseLong(userid));// 用户id
			exchangeEntrust.setFsharesid(Long.parseLong(sharesid));// 股票表ID
			exchangeEntrust.setForeignexchangename(sharesname);// 外汇名称
			exchangeEntrust.setCode(code);// 外汇代码
			exchangeEntrust.setPrice(price);// 单价
			exchangeEntrust.setEntrustprice(Double.parseDouble(entrustPrice));// 委托价
			exchangeEntrust.setFrozenamount(0D);// 冻结资金
			// 委托卖出的总股数
			Integer realCouldusenums = 0;
			if (StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Integer couldusenums = (int) (allCouldusenums * (Double.parseDouble(positions)));
				// 卖出股数 向上取整(股数为整数)
//				realCouldusenums = Math.ceil(couldusenums);
				realCouldusenums = couldusenums+1;
			} else {
				if (allCouldusenums < Integer.parseInt(count)) {
					throw new Exception("该外汇可用手数不足");
				} else {
					realCouldusenums = Integer.parseInt(count);
				}
			}
			exchangeEntrust.setTime(new Date());
			exchangeEntrust.setState(-1);
			exchangeEntrust.setIsplan(Integer.parseInt(isplan));
			exchangeEntrust.setEntruststate(0);//挂单状态  0挂单中  1已匹配成功  ----write by lcc 
			for (Object object : resultList) {
				ForeignExchange exchange = (ForeignExchange) object;
				// 外键关联用户股票表 卖出时有此值
				exchangeEntrust.setFforeignexchangeid(exchange.getId());
				// 如果卖出的总股数小于获取的可用手数
				if (exchange.getCouldusenums() >= realCouldusenums) {
					exchangeEntrust.setEntrustnums(Integer.parseInt(realCouldusenums.toString()));
					databaseHelper.persistObject(exchangeEntrust);

					// 跳出循环
					break;
				} else {
					exchangeEntrust.setEntrustnums(exchange.getCouldusenums());
					realCouldusenums -= exchange.getCouldusenums();
					databaseHelper.persistObject(exchangeEntrust);
				}
			}

		} else if (entrustType.equals("2")) {// 市价委托
			ForeignExchange foreignExchange=new ForeignExchange();
			foreignExchange.setFuserinfoid(Long.parseLong(userid));// 用户id
			foreignExchange.setFsharesid(Long.parseLong(sharesid));// 股票表ID
			foreignExchange.setForeignexchangename(sharesname);// 外汇名称
			foreignExchange.setCode(code);// 外汇代码
			foreignExchange.setPrice(price);// 单价
			foreignExchange.setSellout(price);//卖出
			// 委托卖出的总股数
			Integer realCouldusenums = 0;
			if (StringUtils.isEmpty(positions)) {// 如果仓位选择不为空 则股数失效
				// （1/4仓=>0.25） (1/3仓=>0.33) (半仓=>0.5) (全仓=>1) 总资金除以仓位获取此次购买资金数
				Integer couldusenums = (int) (allCouldusenums * (Double.parseDouble(positions)));
				// 卖出股数 向上取整(股数为整数)
//				realCouldusenums = Math.ceil(couldusenums);
				realCouldusenums =couldusenums+1;
			} else {
				if (allCouldusenums < Integer.parseInt(count)) {
					throw new Exception("该股票可用手数不足");
				} else {
					realCouldusenums =Integer.parseInt(count);
				}
			}
			foreignExchange.setTime(new Date());
			foreignExchange.setState(-1);// 状态 0持仓 1跟单 -1卖出
			foreignExchange.setIsplan(Integer.parseInt(isplan));// 0非计划 1计划（计划中为纯数据） 是否为牛人计划中购买股票标识

			Double lastprice = 0d;
			for (Object object : resultList) {
				ForeignExchange exchange = (ForeignExchange) object;
				foreignExchange.setWarehousenums(exchange.getWarehousenums());// 持仓手数
				foreignExchange.setPurchase(exchange.getPrice());//买入

				foreignExchange.setFforeignexchangeid(exchange.getId());// State=-1时关联此值
				lastprice = exchange.getPrice();
				// 如果卖出的总股数小于获取的可用手数
				if (exchange.getCouldusenums() >= realCouldusenums) {

					foreignExchange.setCouldusenums(Integer.parseInt(realCouldusenums.toString()));// 可用手数
					Double profitloss = realCouldusenums * price - realCouldusenums * exchange.getPrice();
					foreignExchange.setProfitloss(profitloss);// 盈亏
					//修改原股可用手数
					StringBuffer sql2 = new StringBuffer("UPDATE foreignexchange fe SET fe.couldusenums=fe.couldusenums-")
							.append(realCouldusenums).append(" WHERE fe.id=").append(exchange.getId())
							.append(" AND fe.couldusenums>=").append(realCouldusenums);
					int result = databaseHelper.executeNativeSql(sql2.toString());
					if (result == 0) {
						throw new Exception("该外汇可用手数不足");
					} else {
						if("0".equals(isplan)){//非计划
							// 加虚拟资金
							Double money = realCouldusenums * price;
							StringBuffer sql3 = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital+").append(money)
									.append(" WHERE ui.id=").append(userid);
							databaseHelper.executeNativeSql(sql3.toString());
							// 加资金明细
							CapitalDetail capitalDetail = new CapitalDetail();
							capitalDetail.setFuserinfoid(Long.parseLong(userid));
							capitalDetail.setCapital(money);
							capitalDetail.setCreatetime(new Date());
							capitalDetail.setState(0);
							capitalDetail.setType(2);
							databaseHelper.persistObject(capitalDetail);
						}
					}
					databaseHelper.persistObject(foreignExchange);

					// 跳出循环
					break;
				} else {
					Double profitloss = exchange.getCouldusenums() * price - exchange.getCouldusenums() * exchange.getPrice();
					foreignExchange.setProfitloss(profitloss);// 盈亏
					//修改原股可用手数
					StringBuffer sql2 = new StringBuffer("UPDATE foreignexchange fe SET fe.couldusenums =fe.couldusenums-")
							.append(exchange.getCouldusenums()).append(" WHERE fe.id=").append(exchange.getId())
							.append(" AND fe.couldusenums>=").append(exchange.getCouldusenums());
					int result = databaseHelper.executeNativeSql(sql2.toString());
					if (result == 0) {
						throw new Exception("该股票可用手数不足");
					} else {
						if("0".equals(isplan)){//非计划
							// 加虚拟资金
							Double money = exchange.getCouldusenums() * price;
							StringBuffer sql3 = new StringBuffer("UPDATE userinfo ui SET ui.virtualcapital=ui.virtualcapital+").append(money)
									.append(" WHERE ui.id=").append(userid);
							databaseHelper.executeNativeSql(sql3.toString());
							// 加资金明细
							CapitalDetail capitalDetail = new CapitalDetail();
							capitalDetail.setFuserinfoid(Long.parseLong(userid));
							capitalDetail.setCapital(money);
							capitalDetail.setCreatetime(new Date());
							capitalDetail.setState(0);
							capitalDetail.setType(2);
							databaseHelper.persistObject(capitalDetail);
						}
					}

					foreignExchange.setCouldusenums(exchange.getCouldusenums());
					realCouldusenums -= exchange.getCouldusenums();
					databaseHelper.persistObject(foreignExchange);
				}
			}
			
			//write by lcc ---start --------------
			
			UserProfit userprofit = new UserProfit();
			userprofit.setCost(foreignExchange.getPurchase());
			userprofit.setDiffprice(price-lastprice);
			
			//找出最后一次的收益记录
			StringBuffer find = new StringBuffer("select userprofit from UserProfit userprofit  where userprofit.type = 2 and userprofit.fuserinfoid = ").append(foreignExchange.getFuserinfoid()).append(" order by userprofit.time desc");
			List<Object> last = databaseHelper.getResultListByHql(find.toString());
			if(last.size()==0){
				userprofit.setDiffpriceoflasttime(0d);
				userprofit.setDiffprofitoflasttime(0d);
				userprofit.setDiffprofitoflasttimerate(0d);
			}else{
				UserProfit lastuserprofit = (UserProfit)last.get(0);
				userprofit.setDiffpriceoflasttime(price-lastuserprofit.getPrice());//与上次市价的差值
				userprofit.setDiffprofitoflasttime(userprofit.getDiffpriceoflasttime()*foreignExchange.getCouldusenums());//与上次市价的差值收益 = diffpriceoflasttime*nums
				userprofit.setDiffprofitoflasttimerate(userprofit.getDiffprofitoflasttime()/userprofit.getCost());//与上次市价的差值收益率 = diffpriceoflasttime/cost
			}
			
			userprofit.setForeignkeyid(foreignExchange.getId());
			userprofit.setFuserinfoid(foreignExchange.getFuserinfoid());
			userprofit.setNums(foreignExchange.getCouldusenums());
			userprofit.setPrice(price);
			userprofit.setProfit(userprofit.getDiffprice()*foreignExchange.getCouldusenums());
			userprofit.setProfitrate(userprofit.getDiffprice()/userprofit.getCost());
			userprofit.setTags(0);
			userprofit.setTime(new Date());
			userprofit.setType(2);
			userprofit.setIsplan(foreignExchange.getIsplan());
			databaseHelper.persistObject(userprofit);
			
			//添加到tradeinfo表
			TradeInfo ti = new TradeInfo();
			ti.setAmount(foreignExchange.getPurchase());
			ti.setFexampleid(foreignExchange.getFexampleid());
			ti.setFuserid(foreignExchange.getFuserinfoid());
			ti.setNums(foreignExchange.getCouldusenums());
			ti.setProfit(foreignExchange.getProfitloss());
			ti.setSharename(foreignExchange.getForeignexchangename());
			ti.setTime(foreignExchange.getTime());
			ti.setTradetype(1);
			ti.setType(2);
			databaseHelper.persistObject(ti);
			
			if(ui.getIsexample()==0&&"0".equals(isplan)){//启动线程
				FollowExampleSell fesf = new FollowExampleSell();
				fesf.foreignexchange = foreignExchange;
				ThreadPoolManager.exec(fesf);
			}
			
			//write by lcc ---end --------------
		} else {
			throw new Exception("参数错误");
		}
		
		

	}
}
