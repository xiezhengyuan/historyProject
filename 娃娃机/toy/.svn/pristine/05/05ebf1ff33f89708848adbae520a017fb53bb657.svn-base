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

import com.hxy.isw.entity.Employee;
import com.hxy.isw.entity.MachineInfo;
import com.hxy.isw.entity.ToysInfo;
import com.hxy.isw.service.MachineService;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;

@Repository
public class MachineServiceImpl implements MachineService {

	@Autowired
	private DatabaseHelper databaseHelper;

	@Override
	public Map<String, Object> findMachineList(Employee em, String agentname, String type, Integer start,
			Integer limit) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(mi.id) FROM machineinfo mi   ");
		StringBuffer hql = new StringBuffer("SELECT mi FROM MachineInfo mi ");

		if(type.equals("1")){
			sql.append(" where empid = "+em.getId()+" ");
			hql.append(" where mi.empid="+em.getId()+"  ");
		}else{
			sql.append(" where mi.empid in (select e.id from employee e  where e.name like '%"+agentname+"%' and e.role =1 and e.state =0  )");
			hql.append(" where mi.empid in (select e.id from Employee e  where e.name like '%"+agentname+"%' and e.role =1 and e.state =0  )");
		}

	
		Integer count = databaseHelper.getSqlCount(sql.toString());
		int pages = ConstantUtil.pages(count, limit);
		resultMap.put("pages", pages);

		// StringBuffer hql = new StringBuffer("SELECT mi,ti FROM MachineInfo
		// mi,ToysInfo ti WHERE mi.ftoysid=ti.id");

		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString(), start, limit);
		for (Object objects : resultList) {
			MachineInfo mi = (MachineInfo) objects;
			// ToysInfo ti = (ToysInfo) objects[1];
			Map<String, Object> map = new HashMap<String, Object>();
			if (mi.getFtoysid() != null) {
				ToysInfo toysInfo = (ToysInfo) databaseHelper.getObjectById(ToysInfo.class, mi.getFtoysid());
				map.put("toysname", toysInfo.getName());
			} else {
				map.put("toysname", "");
			}
			Employee emp=(Employee) databaseHelper.getObjectById(Employee.class, mi.getEmpid());
			map.put("ordername", emp.getName());
			map.put("id", mi.getId());
			map.put("machineno", mi.getMachineno());// 设备编号
			map.put("ftoysid", mi.getFtoysid());// 玩具ID
			map.put("price", mi.getPrice());// 玩具价格（没有绑定则为0）
			map.put("popularity", mi.getPopularity());// 人气（没有绑定则为0）
			map.put("createtime", mi.getCreatetime().toString());
			map.put("state", mi.getState());// -1失效 0空闲 1正在使用
			map.put("isnew", mi.getIsnew());// 是否上新 0否 1是
			map.put("facevideo", mi.getFacevideo());// 正面直播流地址
			map.put("sidevideo", mi.getSidevideo());// 侧面直播流地址
			map.put("views", mi.getViews());// 观看人数
			map.put("subscribe", mi.getSubscribe());// 预约人数
			map.put("stock", mi.getStock());// 玩具数量（没有绑定则为0）
			// map.put("toysname", ti.getName());
			list.add(map);
		}
		resultMap.put("list", list);
		return resultMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addmachine(String machineno,String empid) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM machineinfo mi WHERE mi.machineno='")
				.append(machineno + "'");
		Integer count = databaseHelper.getSqlCount(sql.toString());
		if (count > 0) {
			throw new Exception("该编号已存在");
		}
		MachineInfo ma=new MachineInfo();
		ma.setEmpid(Long.parseLong(empid));
		ma.setMachineno(machineno);
		ma.setPrice(0);
		ma.setPopularity(0);
		ma.setCreatetime(new Date());
		ma.setState(0);
		ma.setIsnew(0);
		ma.setFacevideo("");
		ma.setSidevideo("");
		ma.setViews(0);
		ma.setSubscribe(0);
		ma.setStock(0);
		ma.setState(0);
		databaseHelper.persistObject(ma);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void bindToys(Employee em, MachineInfo machineInfo) throws Exception {
		ToysInfo toysInfo = (ToysInfo) databaseHelper.getObjectById(ToysInfo.class, machineInfo.getFtoysid());
		MachineInfo mi = (MachineInfo) databaseHelper.getObjectById(MachineInfo.class, machineInfo.getId());
		mi.setFtoysid(toysInfo.getId());
		mi.setPrice(toysInfo.getPrice());
	
		mi.setIsnew(machineInfo.getIsnew());
		mi.setStock(machineInfo.getStock());
		mi.setFacevideo(machineInfo.getFacevideo());
		mi.setSidevideo(machineInfo.getSidevideo());
		mi.setState(machineInfo.getState());
		databaseHelper.updateObject(mi);
	}

	@Override
	public Map<String, Object> findAlltoys(String machineinfoid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer hql = new StringBuffer(" SELECT ti FROM ToysInfo ti WHERE ti.state=0 and ");
		hql.append(" ti.empid =(select mi.empid from MachineInfo mi where mi.id="+machineinfoid+" ) ");
		List<Object> resultList = databaseHelper.getResultListByHql(hql.toString());

		for (Object object : resultList) {
			ToysInfo toysInfo = (ToysInfo) object;
			list.add(JsonUtil.obj2map(toysInfo));
		}
		map.put("rows", list);
		return map;
	}
	
	@Override
	public Map<String, Object> findToysPrice(Employee em, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ToysInfo toysInfo=(ToysInfo) databaseHelper.getObjectById(ToysInfo.class, Long.parseLong(id));
		map.put("price", toysInfo.getPrice());
		map.put("op", "success");
		map.put("msg", "");
		return map;
	}

	@Override
	public List<Map<String, Object>> selectagent() throws Exception {
		String sql=" select e.id ,e.name from employee e where  e.role = 1 and e.state = 0 ";
		List<Map<String, Object>> lstmap=new ArrayList<Map<String,Object>>();
		List<Object[]> lst=  databaseHelper.getResultListBySql(sql);
		for (Object[] obj : lst) {
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("id", obj[0]);
			map.put("name", obj[1]);
			lstmap.add(map);
		}
		return lstmap;
	}

}
