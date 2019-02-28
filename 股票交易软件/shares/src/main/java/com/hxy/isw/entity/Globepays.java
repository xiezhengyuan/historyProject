package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * globepays 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "globepays")
public class Globepays{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;


	@Column(name = "company_code")
	private String company_code;

	@Column(name = "order_number")
	private String order_number;

	@Column(name = "order_amount")
	private String order_amount;


	@Column(name = "order_status")
	private String order_status;

	@Column(name = "sys_order_number")
	private String sys_order_number;

	@Column(name = "sett_date")
	private String sett_date;

	
	@Column(name = "order_fee")
	private String order_fee;

	@Column(name = "timestamp")
	private String timestamp;

	@Column(name = "sign")
	private String sign;

	
	@Column(name = "sign_type")
	private String sign_type;

	@Column(name = "version")
	private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getSys_order_number() {
		return sys_order_number;
	}

	public void setSys_order_number(String sys_order_number) {
		this.sys_order_number = sys_order_number;
	}

	public String getSett_date() {
		return sett_date;
	}

	public void setSett_date(String sett_date) {
		this.sett_date = sett_date;
	}

	public String getOrder_fee() {
		return order_fee;
	}

	public void setOrder_fee(String order_fee) {
		this.order_fee = order_fee;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	

}

