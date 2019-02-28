package com.hxy.isw.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hxy.isw.entity.FileInfo;
import com.hxy.isw.service.StaticService;
import com.hxy.isw.util.DatabaseHelper;

@Repository
public class StaticServiceImpl implements StaticService {

	@Autowired
	DatabaseHelper databaseHelper;

	@Override
	public Object general(Class tableClass, Long id) {
		// TODO Auto-generated method stub
		Object obj = databaseHelper.getObjectById(tableClass, id);
		return obj;
	}

	@Override
	public Long savefileinfo(FileInfo fi) throws Exception {
		// TODO Auto-generated method stub
		fi.setState(0);
        fi.setTime(new Date());
		databaseHelper.persistObject(fi);
		
		return fi.getId();
	}

	
}
