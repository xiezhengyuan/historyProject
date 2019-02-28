package com.hxy.isw.util;

import java.util.List;

public interface DatabaseHelper {
	@SuppressWarnings({"rawtypes"})
	public List getResultListByHql(String hql);

	@SuppressWarnings({"rawtypes"})
	public List getResultListByHql(String hql, Integer start, Integer limit);

	@SuppressWarnings({"rawtypes"})
	public List getResultListBySql(String sql);

	@SuppressWarnings({"rawtypes"})
	public List getResultListBySql(String sql, Integer start, Integer limit);

	public void persistObject(Object obj);

	public Long persistObject(String sequencesName, Object obj, String idField);

	public Object updateObject(Object obj);

	public int executeNativeSql(String sql);
	
	public int executeHql(String hql);

	public Long getSequenceNextValue(String sequence);
	
	@SuppressWarnings({"rawtypes"})
	public Object getObjectById(Class entityClass ,Long Id);
	
	public Integer getSqlCount(String sql);

	

}
