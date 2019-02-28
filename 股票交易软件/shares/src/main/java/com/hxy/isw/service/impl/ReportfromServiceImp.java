package com.hxy.isw.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxy.isw.entity.AccountInfo;
import com.hxy.isw.service.ReportformService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.Sys;

@Service
public class ReportfromServiceImp implements ReportformService {

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public int countmoneyreportforms(String companyid, String gengalid, String salesmanid, String starttime,
			String endtime, String userinfo, AccountInfo acc) throws Exception {
		StringBuffer sql = addcountsql(companyid, gengalid, salesmanid, userinfo, acc);
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0) == null ? 0 : Integer.parseInt(lst.get(0).toString());
	}

	@Override
	public List<Map<String, Object>> moneyreportforms(String companyid, String gengalid, String salesmanid,
			String starttime, String endtime, String userinfo, AccountInfo acc, int start, int limit) throws Exception {
		StringBuffer sql = addsql(companyid, gengalid, salesmanid, starttime, endtime, userinfo, acc);
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString(), start, limit);
		List<Map<String, Object>> lstmap = new ArrayList<Map<String, Object>>();
		for (Object[] o : lst) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", o[0].toString());
			map.put("whatwho", o[1].toString());
			map.put("totalmoney", o[2].toString());
			lstmap.add(map);
		}
		return lstmap;
	}

	private StringBuffer addcountsql(String companyid, String gengalid, String salesmanid, String userinfo,
			AccountInfo acc) {
		int role = acc.getRole();
		StringBuffer sql = new StringBuffer();
		if (role == 0 || role == 1) {

		} else if (role == 2 || role == 3) {
			companyid = acc.getFcompanyid().toString();
		} else if (role == 4) {
			gengalid = acc.getId().toString();
		} else {
			salesmanid = acc.getId().toString();
		}
		if (!userinfo.equals("")) {
			if (companyid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo + "%' and state != -2");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid in (select id from accountinfo where fcompanyid="
						+ companyid + ")");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid in (select id from accountinfo where fparentid=" + gengalid
						+ ")");
				return sql;
			} else {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid =" + salesmanid + "");
				return sql;
			}
		} else {
			if (companyid.equals("0")) {
				sql.append("select count(id) from company where state=0");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select count(id) from accountinfo where  fcompanyid = " + companyid
						+ " and role =4 and state=0");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select count(id) from accountinfo where  fparentid = " + gengalid
						+ " and role =5 and state=0");
				return sql;
			} else {
				sql.append("select count(id) from userinfo where  faccountid = " + salesmanid + " and state != -2");
				return sql;
			}
		}

	}

	private StringBuffer addsql(String companyid, String gengalid, String salesmanid, String starttime, String endtime,
			String userinfo, AccountInfo acc) {
		int role = acc.getRole();
		StringBuffer sql = new StringBuffer();
		if (role == 0 || role == 1) {

		} else if (role == 2 || role == 3) {
			companyid = acc.getFcompanyid().toString();
		} else if (role == 4) {
			gengalid = acc.getId().toString();
		} else {
			salesmanid = acc.getId().toString();
		}
		if (!userinfo.equals("")) {
			if (companyid.equals("0")) {
				sql.append("select u.id, u.nickname,IFNULL(ucc.sumamount,0) from userinfo u LEFT JOIN ");
				sql.append(
						"(select  uc.fuserinfoid,SUM(uc.amount) sumamount from  userrecharge uc  where  uc.paystate=1 ");
				sql.append(addtime(starttime, endtime));
				sql.append("GROUP BY  uc.fuserinfoid)ucc on u.id=fuserinfoid  ");
				sql.append("where u.nickname like '%" + userinfo
						+ "%' and u.state !=-2 ORDER BY IFNULL(ucc.sumamount,0) desc  ");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select u.id, u.nickname,IFNULL(ucc.sumamount,0) from userinfo u LEFT JOIN ");
				sql.append(
						"(select  uc.fuserinfoid,SUM(uc.amount) sumamount from  userrecharge uc  where  uc.paystate=1 ");
				sql.append(addtime(starttime, endtime));
				sql.append("GROUP BY  uc.fuserinfoid)ucc on u.id=fuserinfoid  ");
				sql.append("where u.nickname like '%" + userinfo + "%' and u.state !=-2 ");
				sql.append("and u.faccountid in ( select id from accountinfo where fcompanyid=" + companyid
						+ ") ORDER BY IFNULL(ucc.sumamount,0) desc  ");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select u.id, u.nickname,IFNULL(ucc.sumamount,0) from userinfo u LEFT JOIN ");
				sql.append(
						"(select  uc.fuserinfoid,SUM(uc.amount) sumamount from  userrecharge uc  where  uc.paystate=1 ");
				sql.append(addtime(starttime, endtime));
				sql.append("GROUP BY  uc.fuserinfoid)ucc on u.id=fuserinfoid  ");
				sql.append("where u.nickname like '%" + userinfo + "%' and u.state !=-2 ");
				sql.append("and u.faccountid in ( select id from accountinfo where fparentid=" + gengalid
						+ ") ORDER BY IFNULL(ucc.sumamount,0) desc  ");
				return sql;
			} else {
				sql.append("select u.id, u.nickname,IFNULL(ucc.sumamount,0) from userinfo u LEFT JOIN ");
				sql.append(
						"(select  uc.fuserinfoid,SUM(uc.amount) sumamount from  userrecharge uc  where  uc.paystate=1 ");
				sql.append(addtime(starttime, endtime));
				sql.append("GROUP BY  uc.fuserinfoid)ucc on u.id=fuserinfoid  ");
				sql.append("where u.nickname like '%" + userinfo + "%' and u.state !=-2 ");
				sql.append("and u.faccountid =" + salesmanid + " ORDER BY IFNULL(ucc.sumamount,0) desc  ");
				return sql;
			}
		} else {
			if (companyid.equals("0")) {
				sql.append("select cy.id,cy.company,IFNULL(fam.sumamount,0)  from  company cy  LEFT JOIN  ");
				sql.append("(select SUM(am.amount) sumamount ,am.fcompanyid from  ");
				sql.append("(select uc.amount,ua.fcompanyid from userrecharge  uc left join ");
				sql.append(
						"(select u.id ,a.fcompanyid from userinfo u , accountinfo a where u.faccountid = a.id and u.state !=-2) ua ");
				sql.append("on  uc.fuserinfoid = ua.id where uc.paystate =1 ");
				sql.append(addtime(starttime, endtime));
				sql.append(
						")am GROUP by am.fcompanyid)fam on cy.id=fam.fcompanyid ORDER BY IFNULL(fam.sumamount,0) desc ");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select cy.id,cy.name,IFNULL(fam.sumamount,0)  from  accountinfo cy  LEFT JOIN  ");
				sql.append("(select SUM(am.amount) sumamount ,am.fparentid from  ");
				sql.append("(select uc.amount,ua.fparentid from userrecharge  uc left join ");
				sql.append(
						"(select u.id ,a.fparentid from userinfo u , accountinfo a where u.faccountid = a.id and u.state !=-2 ) ua ");
				sql.append("on  uc.fuserinfoid = ua.id where uc.paystate =1  ");
				sql.append(addtime(starttime, endtime));
				sql.append(")am GROUP by am.fparentid)fam on cy.id=fam.fparentid where cy.role=4 and cy.fcompanyid="
						+ companyid + " ORDER BY IFNULL(fam.sumamount,0) desc ");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select cy.id,cy.name,IFNULL(fam.sumamount,0)  from  accountinfo cy  LEFT JOIN  ");
				sql.append("(select SUM(am.amount) sumamount ,am.geid from  ");
				sql.append("(select uc.amount,ua.geid from userrecharge  uc left join ");
				sql.append(
						"(select u.id ,a.id geid from userinfo u , accountinfo a where u.faccountid = a.id and u.state !=-2 ) ua ");
				sql.append("on  uc.fuserinfoid = ua.id where uc.paystate =1 ");
				sql.append(addtime(starttime, endtime));
				sql.append(")am GROUP by am.geid)fam on cy.id=fam.geid where cy.role=5 and cy.fparentid=" + gengalid
						+ " ORDER BY IFNULL(fam.sumamount,0) desc ");
				return sql;
			} else {
				sql.append("select u.id, u.nickname ,IFNULL(ucc.sumamount,0)  from userinfo u  LEFT JOIN ");
				sql.append(
						"(select uc.fuserinfoid, SUM( uc.amount) sumamount  from userrecharge uc  where uc.paystate=1 ");
				sql.append(addtime(starttime, endtime));
				sql.append("GROUP BY fuserinfoid ) ucc on u.id=ucc.fuserinfoid ");
				sql.append("where u.faccountid=" + salesmanid
						+ " and u.state !=-2  ORDER BY IFNULL(ucc.sumamount,0) desc ");
				return sql;
			}
		}

	}

	private StringBuffer addtime(String starttime, String endtime) {
		StringBuffer timesql = new StringBuffer("");
		if (starttime == "" && endtime == "") {

		} else if (starttime == "" && endtime != "") {
			endtime = endtime + " 23:59:59";
			timesql.append("and uc.createtime<='" + endtime + "' ");

		} else if (starttime != "" && endtime == "") {
			starttime = starttime + " 00:00:00";
			timesql.append("and uc.createtime>='" + starttime + "' ");
		} else {
			starttime = starttime + " 00:00:00";
			endtime = endtime + " 23:59:59";
			timesql.append("and uc.createtime>='" + starttime + "' and uc.createtime<='" + endtime + "' ");
		}
		return timesql;
	}

	@Override
	public int countgoldsreportforms(String companyid, String gengalid, String salesmanid, String userinfo,
			AccountInfo acc) throws Exception {
		StringBuffer sql = addgoldscountsql(companyid, gengalid, salesmanid, userinfo, acc);
		List<Object> lst = databaseHelper.getResultListBySql(sql.toString());
		return lst.get(0) == null ? 0 : Integer.parseInt(lst.get(0).toString());
	}

	/**
	 * @param companyid
	 *            公司id
	 * @param gengalid
	 *            经理id
	 * @param salesmanid
	 *            业务员id
	 * @param starttime
	 *            起始时间
	 * @param endtime
	 *            结束时间
	 * @param userinfo
	 *            昵称
	 * @param acc
	 *            登录人
	 * @param start
	 *            页码
	 * @param limit
	 *            页容量
	 */
	@Override
	public List<Map<String, Object>> goldsreportforms(String companyid, String gengalid, String salesmanid,
			String starttime, String endtime, String userinfo, AccountInfo acc, int start, int limit) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// ============================
		StringBuffer sql = addgoldssql(companyid, gengalid, salesmanid, starttime, endtime, userinfo, acc);

		List<Object[]> resultList = databaseHelper.getResultListBySql(sql.toString(), start, limit);
		for (Object object[] : resultList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", object[0].toString());// 目标id
			map.put("name", object[1].toString());// 目标名称
			map.put("s1", object[2].toString());// 金币兑换虚拟资金
			map.put("s2", object[3].toString());// 管理员扣除
			map.put("s3", object[4].toString());// 打赏别人
			map.put("s4", object[5].toString());// 观摩计划
			map.put("s5", object[6].toString());// 抢购计划
			map.put("s6", object[7].toString());// 提现
			map.put("s7", object[8].toString());// 订阅
			map.put("s8", object[9].toString());// 充值
			map.put("s9", object[10].toString());// 被人打赏
			map.put("s10", object[11].toString());// 邀请好友
			map.put("s11", object[12].toString());// 管理员赠送
			list.add(map);
		}
		return list;
	}

	private StringBuffer addgoldscountsql(String companyid, String gengalid, String salesmanid, String userinfo,
			AccountInfo acc) {

		int role = acc.getRole();
		StringBuffer sql = new StringBuffer();
		System.out.println(role);
		if (role == 0 || role == 1) {

		} else if (role == 2 || role == 3) {
			companyid = acc.getFcompanyid().toString();
		} else if (role == 4) {
			gengalid = acc.getId().toString();
		} else {
			salesmanid = acc.getId().toString();
		}
		if (!userinfo.equals("")) {
			if (companyid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo + "%' and state != -2");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid in (select id from accountinfo where fcompanyid="
						+ companyid + ")");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid in (select id from accountinfo where fparentid=" + gengalid
						+ ")");
				return sql;
			} else {
				sql.append("select count(id) from userinfo where nickname like '%" + userinfo
						+ "%' and state != -2 and faccountid =" + salesmanid + "");
				return sql;
			}
		} else {
			if (companyid.equals("0")) {
				sql.append("select count(id) from company where state=0");
				return sql;
			} else if (gengalid.equals("0")) {
				sql.append("select count(id) from accountinfo where  fcompanyid = " + companyid
						+ " and role =4 and state=0");
				return sql;
			} else if (salesmanid.equals("0")) {
				sql.append("select count(id) from accountinfo where  fparentid = " + gengalid
						+ " and role =5 and state=0");
				return sql;
			} else {
				sql.append("select count(id) from userinfo where  faccountid = " + salesmanid + " and state != -2");
				return sql;
			}
		}
	}

	private StringBuffer addgoldssql(String companyid, String gengalid, String salesmanid, String starttime,
			String endtime, String userinfo, AccountInfo acc) {
		int role = acc.getRole();
		StringBuffer sql = new StringBuffer();
		if (role == 0 || role == 1) {

		} else if (role == 2 || role == 3) {
			companyid = acc.getFcompanyid().toString();
		} else if (role == 4) {
			gengalid = acc.getId().toString();
		} else {
			salesmanid = acc.getId().toString();
		}
		// -7金币兑换虚拟资金 -6 管理员扣除
		// -5 打赏别人 -4观摩计划 -3抢购计划 -2提现 -1订阅
		// 1充值 2被人打赏 3邀请好友 4管理员赠送
		int[] arr = { -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4 };

		if (!StringUtils.isEmpty(userinfo)) {

			String s = "";
			for (int i = 0; i < arr.length; i++) {
				s += ",IFNULL((SELECT SUM(uc.golds) FROM goldsdetail uc WHERE uc.fuserinfoid=ui.id AND uc.type="
						+ arr[i] + " " + addtime(starttime, endtime).toString() + "),0)";
			}

			if (companyid.equals("0")) {// 显示公司底下用户
				sql.append("SELECT ui.id,ui.nickname" + s + " FROM userinfo ui WHERE 1=1 ");
				sql.append(" AND ui.nickname LIKE '%").append(userinfo).append("%'");
				return sql;
			} else if (gengalid.equals("0")) {// 显示经理底下用户
				sql.append("SELECT ui.id,ui.nickname" + s
						+ " FROM userinfo ui LEFT JOIN accountinfo ai ON ui.faccountid=ai.id WHERE ai.fcompanyid=")
						.append(companyid);
				sql.append(" AND ui.nickname LIKE '%").append(userinfo).append("%'");
				return sql;
			} else if (salesmanid.equals("0")) {// 显示业务员底下用户
				sql.append("SELECT ui.id,ui.nickname" + s
						+ " FROM userinfo ui LEFT JOIN accountinfo ai ON ui.faccountid=ai.id WHERE ai.fparentid=")
						.append(gengalid);
				sql.append(" AND ui.nickname LIKE '%").append(userinfo).append("%'");
				return sql;
			} else {// 显示所有用户
				sql.append("SELECT ui.id,ui.nickname" + s + " FROM userinfo ui  WHERE ui.faccountid=")
						.append(salesmanid);
				sql.append(" AND ui.nickname LIKE '%").append(userinfo).append("%'");
				return sql;
			}
		} else {
			if (companyid.equals("0")) {// 显示公司
				String s = "";
				for (int i = 0; i < arr.length; i++) {
					s += ",(SELECT IFNULL(SUM(uc.golds),0) FROM goldsdetail uc "
							+ "LEFT JOIN userinfo ui ON  uc.fuserinfoid=ui.id "
							+ "LEFT JOIN accountinfo ai ON ui.faccountid=ai.id "
							+ "WHERE ai.fcompanyid=c.id AND uc.type=" + arr[i] + " "
							+ addtime(starttime, endtime).toString() + ")";
				}

				sql.append("SELECT c.id,c.company" + s + " FROM company c  ");
				return sql;
			} else if (gengalid.equals("0")) {// 显示经理
				String s = "";
				for (int i = 0; i < arr.length; i++) {
					s += ",(SELECT IFNULL(SUM(uc.golds),0) FROM goldsdetail uc "
							+ "LEFT JOIN userinfo ui ON uc.fuserinfoid=ui.id "
							+ "LEFT JOIN accountinfo ai2 ON ui.faccountid=ai2.id "
							+ "WHERE ai2.fparentid=ai.id AND uc.type=" + arr[i] + " "
							+ addtime(starttime, endtime).toString() + ")";
				}
				sql.append("SELECT ai.id,ai.`name`" + s + " FROM accountinfo ai WHERE ai.role=4 AND ai.fcompanyid=")
						.append(companyid);
				return sql;
			} else if (salesmanid.equals("0")) {// 显示业务员
				String s = "";
				for (int i = 0; i < arr.length; i++) {
					s += ",(SELECT IFNULL(SUM(uc.golds),0) FROM goldsdetail uc "
							+ "LEFT JOIN userinfo ui ON uc.fuserinfoid=ui.id "
							+ "WHERE ui.faccountid=ai.id AND uc.type=" + arr[i] + " "
							+ addtime(starttime, endtime).toString() + ")";
				}
				sql.append("SELECT ai.id,ai.`name`" + s + " FROM accountinfo ai WHERE ai.role=5 AND ai.fparentid=")
						.append(gengalid);

				return sql;
			} else {// 显示用户
				String s = "";
				for (int i = 0; i < arr.length; i++) {
					s += ",(SELECT IFNULL(SUM(uc.golds),0) FROM goldsdetail uc WHERE uc.fuserinfoid=ui.id AND uc.type="
							+ arr[i] + " " + addtime(starttime, endtime) + ")";
				}
				sql.append("SELECT ui.id,ui.nickname" + s + " FROM userinfo ui WHERE ui.faccountid=")
						.append(salesmanid);
				return sql;
			}
		}
	}

	// 导出
	@Override
	public String outportcashlog(String companyid, String gengalid, String salesmanid, String starttime, String endtime,
			String userinfo, AccountInfo acc) throws Exception {

		StringBuffer sql = addgoldssql(companyid, gengalid, salesmanid, starttime, endtime, userinfo, acc);

		Sys.out(sql);
		// List<Object[]> lst =
		// databaseHelper.getResultListByHql(sql.toString());
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString());

		String name = "";
		if (companyid.equals("0")) {
			name = "公司";
		} else if (gengalid.equals("0")) {
			name = "经理";
		} else if (salesmanid.equals("0")) {
			name = "业务员";
		} else {
			name = "用户";
		}

		String filename = gen_excelgolds(lst, name);

		return filename;
	}

	private String gen_excelgolds(List<Object[]> lst, String name) {
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfworkbook.createSheet("new sheet");
		HSSFRow row = sheet.createRow(0);
		int count = 0;

		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue(name);

		row.createCell(2).setCellValue("兑换");
		row.createCell(3).setCellValue("扣除");
		row.createCell(4).setCellValue("打赏");
		row.createCell(5).setCellValue("观摩");
		row.createCell(6).setCellValue("抢购");
		row.createCell(7).setCellValue("提现");
		row.createCell(8).setCellValue("订阅");
		row.createCell(9).setCellValue("充值");
		row.createCell(10).setCellValue("被赏");
		row.createCell(11).setCellValue("邀请");
		row.createCell(12).setCellValue("增加");
		
		for (Object[] objects : lst) {
			HSSFRow _row = sheet.createRow(++count);
			_row.createCell(0).setCellValue(objects[0].toString());
			_row.createCell(1).setCellValue(objects[1].toString());
			_row.createCell(2).setCellValue(objects[2].toString());
			_row.createCell(3).setCellValue(objects[3].toString());
			_row.createCell(4).setCellValue(objects[4].toString());
			_row.createCell(5).setCellValue(objects[5].toString());
			_row.createCell(6).setCellValue(objects[6].toString());
			_row.createCell(7).setCellValue(objects[7].toString());
			_row.createCell(8).setCellValue(objects[8].toString());
			_row.createCell(9).setCellValue(objects[9].toString());
			_row.createCell(10).setCellValue(objects[10].toString());
			_row.createCell(11).setCellValue(objects[11].toString());
			_row.createCell(12).setCellValue(objects[12].toString());
		}

		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = _sdf.format(new Date());
		time = time.replace(" ", "").replace(":", "").replace("-", "");
		// System.out.println("count:"+count);
		/*
		 * String path = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/",
		 * ""); path = path + "html";
		 */
		// String path =
		// ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/html/excel");
		String path = ConstantUtil.environment.equals("maven")
				? ConstantUtil.PROJECT_PATH.replace("target/classes/", "src/main/webapp/excel/")
				: ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "excel/");// tomcat
		// path = ConstantUtil.PROJECT_PATH.replace("target/classes/",
		// "src/main/webapp/excel/");//maven
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		path += "excel_" + time + ".xls";

		try {
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			hssfworkbook.write(fileoutputstream);
			fileoutputstream.flush();
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "excel_" + time + ".xls";
	}

	@Override
	public String outportinmoneylog(String companyid, String gengalid, String salesmanid, String starttime, String endtime,
			String userinfo, AccountInfo acc) throws Exception {
		StringBuffer sql = addsql(companyid, gengalid, salesmanid, starttime, endtime, userinfo, acc);
		Sys.out(sql);
		// List<Object[]> lst =
		// databaseHelper.getResultListByHql(sql.toString());
		List<Object[]> lst = databaseHelper.getResultListBySql(sql.toString());

		String name = "";
		if (companyid.equals("0")) {
			name = "公司";
		} else if (gengalid.equals("0")) {
			name = "经理";
		} else if (salesmanid.equals("0")) {
			name = "业务员";
		} else {
			name = "用户";
		}

		String filename = gen_excelinmoney(lst, name);

		return filename;
	}
	
private String gen_excelinmoney(List<Object[]> lst, String name) {
		
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfworkbook.createSheet("new sheet");
		HSSFRow row = sheet.createRow(0);
		int count = 0;

		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue(name);

		row.createCell(2).setCellValue("入金金额");
		
		for (Object[] objects : lst) {
			HSSFRow _row = sheet.createRow(++count);
			_row.createCell(0).setCellValue(objects[0].toString());
			_row.createCell(1).setCellValue(objects[1].toString());
			_row.createCell(2).setCellValue(objects[2].toString());
		}

		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = _sdf.format(new Date());
		time = time.replace(" ", "").replace(":", "").replace("-", "");
		// System.out.println("count:"+count);
		/*
		 * String path = ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/",
		 * ""); path = path + "html";
		 */
		// String path =
		// ConstantUtil.PROJECT_PATH.replace("target/classes/","src/main/webapp/html/excel");
		String path = ConstantUtil.environment.equals("maven")
				? ConstantUtil.PROJECT_PATH.replace("target/classes/", "src/main/webapp/excel/")
				: ConstantUtil.PROJECT_PATH.replace("WEB-INF/classes/", "excel/");// tomcat
		// path = ConstantUtil.PROJECT_PATH.replace("target/classes/",
		// "src/main/webapp/excel/");//maven
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		path += "excel_" + time + ".xls";

		try {
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			hssfworkbook.write(fileoutputstream);
			fileoutputstream.flush();
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "excel_" + time + ".xls";
	}



}
