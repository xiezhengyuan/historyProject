package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//微信支付信息表
@Entity
@Table(name="payinfobywx")
public class PayInfoByWx {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	
	@Column(name="issubscribe")
	private String issubscribe;
	
	@Column(name="appid")
	private String appid;
	
	@Column(name="feetype")
	private String feetype;

	@Column(name="time")
	private Date time;
	
	@Column(name="noncestr")
	private String noncestr;
	
	@Column(name="outtradeno")
	private String outtradeno;
	
	@Column(name="transactionid")
	private String transactionid;
	
	@Column(name="tradetype")
	private String tradetype;
	
	@Column(name="sign")
	private String sign;
	
	@Column(name="resultcode")
	private String resultcode;
	
	@Column(name="mchid")
	private String mchid;
	
	@Column(name="totalfee")
	private int totalfee;
	
	@Column(name="attach")
	private String attach;
	
	@Column(name="timeend")
	private String timeend;
	
	@Column(name="openid")
	private String openid;
	
	@Column(name="banktype")
	private String banktype;
	
	@Column(name="returncode")
	private String returncode;
	
	@Column(name="cashfee")
	private int cashfee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIssubscribe() {
		return issubscribe;
	}

	public void setIssubscribe(String issubscribe) {
		this.issubscribe = issubscribe;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getOuttradeno() {
		return outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public int getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(int totalfee) {
		this.totalfee = totalfee;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeend() {
		return timeend;
	}

	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getReturncode() {
		return returncode;
	}

	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}

	public int getCashfee() {
		return cashfee;
	}

	public void setCashfee(int cashfee) {
		this.cashfee = cashfee;
	}
	
	

	
}
