package com.hxy.isw.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DatabaseHelperImpl implements DatabaseHelper {
	@PersistenceContext
	transient EntityManager entityManager;

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public int executeNativeSql(String sql) {
		return entityManager.createNativeQuery(sql).executeUpdate();
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public List getResultListByHql(String hql) {
		Query query = entityManager.createQuery(hql);
		return query.getResultList();
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public List getResultListByHql(String hql, Integer start, Integer limit) {
		if(start<1){
			return null;
		}
		Query query = entityManager.createQuery(hql);
		if( limit != -1){
		query.setFirstResult((start-1)*limit);
		query.setMaxResults(limit);
		}
		return query.getResultList();
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public List getResultListBySql(String sql) {
		return entityManager.createNativeQuery(sql).getResultList();
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public List getResultListBySql(String sql, Integer start, Integer limit) {
		if(start<1){
			return null;
		}
		Query query = entityManager.createNativeQuery(sql);
		if( limit != -1){
		query.setFirstResult((start-1)*limit);
		query.setMaxResults(limit);
		}
		return query.getResultList();
	}

	@Override
	public Long getSequenceNextValue(String sequence) {
		try {
			String sql = "select " + sequence + ".nextval from dual";
			Object rs = entityManager.createNativeQuery(sql).getSingleResult();
			Long lseq = Long.valueOf(rs.toString()) ;
			return lseq;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void persistObject(Object obj) {
		entityManager.persist(obj);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public Long persistObject(String sequencesName, Object obj, String idField) {
		Long id = null;
	    try {
			if (sequencesName!=null) {
				id = getSequenceNextValue(sequencesName);
				String methodName = "set"+Character.toUpperCase(idField.charAt(0))+idField.substring(1);
				obj.getClass().getDeclaredMethod(methodName,Long.class).invoke(obj, id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityManager.persist(obj);
		return id;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public Object updateObject(Object obj) {
		return entityManager.merge(obj);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Object getObjectById(Class entityClass ,Long id){
		Object obj = entityManager.find(entityClass, id);
		return obj;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int executeHql(String hql){
		return entityManager.createQuery(hql).executeUpdate();
	}
	
	public Integer getSqlCount(String sql){
		String count = entityManager.createNativeQuery(sql).getResultList().get(0).toString();
		return Integer.valueOf(count);
	
	}

}
