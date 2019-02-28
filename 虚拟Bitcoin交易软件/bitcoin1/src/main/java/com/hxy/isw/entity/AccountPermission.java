package com.hxy.isw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * accountpermission 实体类
    * Fri Jul 14 10:26:24 CST 2017 lxq
    */ 

@Entity
@Table(name = "accountpermission")
public class AccountPermission{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "faccountid")
	private Long faccountid;

	@Column(name = "fmenuid")
	private Long fmenuid;

	@Column(name = "state")
	private Integer state;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFaccountid(Long faccountid){
	this.faccountid=faccountid;
	}
	public Long getFaccountid(){
		return faccountid;
	}
	public void setFmenuid(Long fmenuid){
	this.fmenuid=fmenuid;
	}
	public Long getFmenuid(){
		return fmenuid;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
}

