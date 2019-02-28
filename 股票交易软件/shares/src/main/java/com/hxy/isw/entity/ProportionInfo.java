package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author lcc
* @date 2017年8月18日 下午1:17:55
* @describe 分成记录表
*/


@Entity
@Table(name="proportioninfo")
public class ProportionInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	@Column(name="faccountinfoid")
	private Long faccountinfoid;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="fcompanyid")
	private Long fcompanyid;
	
	
	@Column(name="fuserrechargeid")
	private Long fuserrechargeid;
	
	@Column(name="role")
	private int role;
	
	@Column(name="time")
	private Date time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFaccountinfoid() {
		return faccountinfoid;
	}

	public void setFaccountinfoid(Long faccountinfoid) {
		this.faccountinfoid = faccountinfoid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getFcompanyid() {
		return fcompanyid;
	}

	public void setFcompanyid(Long fcompanyid) {
		this.fcompanyid = fcompanyid;
	}

	public Long getFuserrechargeid() {
		return fuserrechargeid;
	}

	public void setFuserrechargeid(Long fuserrechargeid) {
		this.fuserrechargeid = fuserrechargeid;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
	
}
