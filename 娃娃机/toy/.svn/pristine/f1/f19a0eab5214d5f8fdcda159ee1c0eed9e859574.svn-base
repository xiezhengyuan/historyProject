package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//商家表
@Entity
@Table(name="businessinfo")
public class BusinessInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="businessname")
	private String businessname;
	
	@Column(name="account")
	private String account;
	
	@Column(name="password")
	private String password;
	
	@Column(name="createtime")
	private Date createtime;
	
	@Column(name="mobile")
	private String mobile;

	@Column(name="state")
	private int state;
	
	
	@Column(name="role")
	private int role;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBusinessname() {
		return businessname;
	}


	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public int getRole() {
		return role;
	}


	public void setRole(int role) {
		this.role = role;
	}

	
	
}
