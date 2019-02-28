package com.hxy.isw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//快递公司表
@Entity
@Table(name="expressage")
public class Expressage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	
	@Column(name="expressagecompany")
	private String expressagecompany;
	
	@Column(name="expressagecode")
	private String expressagecode;
	
	
	@Column(name="state")
	private int state;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getExpressagecompany() {
		return expressagecompany;
	}

	public void setExpressagecompany(String expressagecompany) {
		this.expressagecompany = expressagecompany;
	}

	public String getExpressagecode() {
		return expressagecode;
	}

	public void setExpressagecode(String expressagecode) {
		this.expressagecode = expressagecode;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	
}
