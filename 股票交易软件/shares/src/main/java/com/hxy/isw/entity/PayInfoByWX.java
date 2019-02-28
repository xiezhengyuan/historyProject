package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * payinfobywx 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "payinfobywx")
public class PayInfoByWX{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "issubscribe")
	private String issubscribe;

	@Column(name = "appid")
	private String appid;

	@Column(name = "feetype")
	private String feetype;

	@Column(name = "noncestr")
	private String noncestr;

	@Column(name = "outtradeno")
	private String outtradeno;

	@Column(name = "transactionid")
	private String transactionid;

	@Column(name = "tradetype")
	private String tradetype;

	@Column(name = "sign")
	private String sign;

	@Column(name = "resultcode")
	private String resultcode;

	@Column(name = "mchid")
	private String mchid;

	@Column(name = "totalfee")
	private Integer totalfee;

	@Column(name = "attach")
	private String attach;

	@Column(name = "timeend")
	private String timeend;

	@Column(name = "openid")
	private String openid;

	@Column(name = "banktype")
	private String banktype;

	@Column(name = "returncode")
	private String returncode;

	@Column(name = "cashfee")
	private Integer cashfee;

	@Column(name = "time")
	private Date time;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setIssubscribe(String issubscribe){
	this.issubscribe=issubscribe;
	}
	public String getIssubscribe(){
		return issubscribe;
	}
	public void setAppid(String appid){
	this.appid=appid;
	}
	public String getAppid(){
		return appid;
	}
	public void setFeetype(String feetype){
	this.feetype=feetype;
	}
	public String getFeetype(){
		return feetype;
	}
	public void setNoncestr(String noncestr){
	this.noncestr=noncestr;
	}
	public String getNoncestr(){
		return noncestr;
	}
	public void setOuttradeno(String outtradeno){
	this.outtradeno=outtradeno;
	}
	public String getOuttradeno(){
		return outtradeno;
	}
	public void setTransactionid(String transactionid){
	this.transactionid=transactionid;
	}
	public String getTransactionid(){
		return transactionid;
	}
	public void setTradetype(String tradetype){
	this.tradetype=tradetype;
	}
	public String getTradetype(){
		return tradetype;
	}
	public void setSign(String sign){
	this.sign=sign;
	}
	public String getSign(){
		return sign;
	}
	public void setResultcode(String resultcode){
	this.resultcode=resultcode;
	}
	public String getResultcode(){
		return resultcode;
	}
	public void setMchid(String mchid){
	this.mchid=mchid;
	}
	public String getMchid(){
		return mchid;
	}
	public void setTotalfee(Integer totalfee){
	this.totalfee=totalfee;
	}
	public Integer getTotalfee(){
		return totalfee;
	}
	public void setAttach(String attach){
	this.attach=attach;
	}
	public String getAttach(){
		return attach;
	}
	public void setTimeend(String timeend){
	this.timeend=timeend;
	}
	public String getTimeend(){
		return timeend;
	}
	public void setOpenid(String openid){
	this.openid=openid;
	}
	public String getOpenid(){
		return openid;
	}
	public void setBanktype(String banktype){
	this.banktype=banktype;
	}
	public String getBanktype(){
		return banktype;
	}
	public void setReturncode(String returncode){
	this.returncode=returncode;
	}
	public String getReturncode(){
		return returncode;
	}
	public void setCashfee(Integer cashfee){
	this.cashfee=cashfee;
	}
	public Integer getCashfee(){
		return cashfee;
	}
	public void setTime(Date time){
	this.time=time;
	}
	public Date getTime(){
		return time;
	}
}

