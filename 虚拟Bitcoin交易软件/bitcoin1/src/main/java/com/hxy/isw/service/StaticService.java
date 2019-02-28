package com.hxy.isw.service;

import com.hxy.isw.entity.FileInfo;

public interface StaticService {
	public Object general(Class tableClass,Long id) throws Exception;
	
	public Long savefileinfo(FileInfo fi) throws Exception;
	
}
