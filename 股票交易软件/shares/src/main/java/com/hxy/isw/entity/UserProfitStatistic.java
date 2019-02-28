package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * userprofitstatistic 实体类
    * Fri Jul 14 10:26:24 CST 2017 lcc
    */ 

@Entity
@Table(name = "userprofitstatistic")
public class UserProfitStatistic{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "isexample")
	private int isexample;

	@Column(name = "costofhs")
	private Double costofhs;

	@Column(name = "profitofhs")
	private Double profitofhs;
	
	@Column(name = "rateofhs")
	private Double rateofhs;
	
	@Column(name = "costofwh")
	private Double costofwh;

	@Column(name = "profitofwh")
	private Double profitofwh;
	
	@Column(name = "rateofwh")
	private Double rateofwh;
	
	@Column(name = "profitofus")
	private Double profitofus;

	@Column(name = "rateofus")
	private Double rateofus;
	
	@Column(name = "costofus")
	private Double costofus;
	
	@Column(name = "totalcost")
	private Double totalcost;

	@Column(name = "totalprofit")
	private Double totalprofit;
	
	@Column(name = "totalrate")
	private Double totalrate;

	@Column(name = "time")
	private Date time;
	
	
	@Column(name = "fans")
	private int fans;//粉丝数
	
	
	@Column(name = "rewordnums")
	private int rewordnums;//获得打赏金币的数量
	
	@Column(name = "hsplansuccess")
	private Double hsplansuccess;//沪深计划成功率
	
	@Column(name = "whplansuccess")
	private Double whplansuccess;//外汇计划成功率
	
	@Column(name = "usplansuccess")
	private Double usplansuccess;//美股计划成功率
	
	@Column(name = "totalplansuccess")
	private Double totalplansuccess;//总计划成功率
	
	
	@Column(name = "hsplanfrofit")
	private Double hsplanfrofit;//沪深计划收益率
	
	@Column(name = "whplanfrofit")
	private Double whplanfrofit;//外汇计划收益率
	
	@Column(name = "usplanfrofit")
	private Double usplanfrofit;//美股计划收益率
	
	@Column(name = "totalplanfrofit")
	private Double totalplanfrofit;//总计划收益率
	
	
	@Column(name = "profitloss")
	private Double profitloss;//总浮动盈亏
	
	
	@Column(name = "profitlossrate")
	private Double profitlossrate;//总浮动盈亏(率)
	
	@Column(name = "profitlossofhs")
	private Double profitlossofhs;//沪深浮动盈亏
	
	
	@Column(name = "profitlossrateofhs")
	private Double profitlossrateofhs;//沪深浮动盈亏率
	
	
	@Column(name = "profitlossofwh")
	private Double profitlossofwh;//外汇浮动盈亏
	
	
	@Column(name = "profitlossrateofwh")
	private Double profitlossrateofwh;//外汇浮动盈亏率
	
	
	@Column(name = "profitlossofus")
	private Double profitlossofus;//美股浮动盈亏
	
	
	@Column(name = "profitlossrateofus")
	private Double profitlossrateofus;//美股浮动盈亏率
	
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public Long getFuserinfoid() {
		return fuserinfoid;
	}
	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}
	public int getIsexample() {
		return isexample;
	}
	public void setIsexample(int isexample) {
		this.isexample = isexample;
	}
	public Double getCostofhs() {
		return costofhs;
	}
	public void setCostofhs(Double costofhs) {
		this.costofhs = costofhs;
	}
	public Double getProfitofhs() {
		return profitofhs;
	}
	public void setProfitofhs(Double profitofhs) {
		this.profitofhs = profitofhs;
	}
	public Double getRateofhs() {
		return rateofhs;
	}
	public void setRateofhs(Double rateofhs) {
		this.rateofhs = rateofhs;
	}
	public Double getCostofwh() {
		return costofwh;
	}
	public void setCostofwh(Double costofwh) {
		this.costofwh = costofwh;
	}
	public Double getProfitofwh() {
		return profitofwh;
	}
	public void setProfitofwh(Double profitofwh) {
		this.profitofwh = profitofwh;
	}
	public Double getRateofwh() {
		return rateofwh;
	}
	public void setRateofwh(Double rateofwh) {
		this.rateofwh = rateofwh;
	}
	public Double getProfitofus() {
		return profitofus;
	}
	public void setProfitofus(Double profitofus) {
		this.profitofus = profitofus;
	}
	public Double getRateofus() {
		return rateofus;
	}
	public void setRateofus(Double rateofus) {
		this.rateofus = rateofus;
	}
	public Double getCostofus() {
		return costofus;
	}
	public void setCostofus(Double costofus) {
		this.costofus = costofus;
	}
	public Double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}
	public Double getTotalprofit() {
		return totalprofit;
	}
	public void setTotalprofit(Double totalprofit) {
		this.totalprofit = totalprofit;
	}
	public Double getTotalrate() {
		return totalrate;
	}
	public void setTotalrate(Double totalrate) {
		this.totalrate = totalrate;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	
	public int getRewordnums() {
		return rewordnums;
	}
	public void setRewordnums(int rewordnums) {
		this.rewordnums = rewordnums;
	}
	public Double getHsplansuccess() {
		return hsplansuccess;
	}
	public void setHsplansuccess(Double hsplansuccess) {
		this.hsplansuccess = hsplansuccess;
	}
	public Double getWhplansuccess() {
		return whplansuccess;
	}
	public void setWhplansuccess(Double whplansuccess) {
		this.whplansuccess = whplansuccess;
	}
	public Double getUsplansuccess() {
		return usplansuccess;
	}
	public void setUsplansuccess(Double usplansuccess) {
		this.usplansuccess = usplansuccess;
	}
	public Double getTotalplansuccess() {
		return totalplansuccess;
	}
	public void setTotalplansuccess(Double totalplansuccess) {
		this.totalplansuccess = totalplansuccess;
	}
	public Double getHsplanfrofit() {
		return hsplanfrofit;
	}
	public void setHsplanfrofit(Double hsplanfrofit) {
		this.hsplanfrofit = hsplanfrofit;
	}
	public Double getWhplanfrofit() {
		return whplanfrofit;
	}
	public void setWhplanfrofit(Double whplanfrofit) {
		this.whplanfrofit = whplanfrofit;
	}
	public Double getUsplanfrofit() {
		return usplanfrofit;
	}
	public void setUsplanfrofit(Double usplanfrofit) {
		this.usplanfrofit = usplanfrofit;
	}
	public Double getTotalplanfrofit() {
		return totalplanfrofit;
	}
	public void setTotalplanfrofit(Double totalplanfrofit) {
		this.totalplanfrofit = totalplanfrofit;
	}
	public Double getProfitloss() {
		return profitloss;
	}
	public void setProfitloss(Double profitloss) {
		this.profitloss = profitloss;
	}
	public Double getProfitlossofhs() {
		return profitlossofhs;
	}
	public void setProfitlossofhs(Double profitlossofhs) {
		this.profitlossofhs = profitlossofhs;
	}
	public Double getProfitlossrateofhs() {
		return profitlossrateofhs;
	}
	public void setProfitlossrateofhs(Double profitlossrateofhs) {
		this.profitlossrateofhs = profitlossrateofhs;
	}
	public Double getProfitlossofwh() {
		return profitlossofwh;
	}
	public void setProfitlossofwh(Double profitlossofwh) {
		this.profitlossofwh = profitlossofwh;
	}
	public Double getProfitlossrateofwh() {
		return profitlossrateofwh;
	}
	public void setProfitlossrateofwh(Double profitlossrateofwh) {
		this.profitlossrateofwh = profitlossrateofwh;
	}
	public Double getProfitlossofus() {
		return profitlossofus;
	}
	public void setProfitlossofus(Double profitlossofus) {
		this.profitlossofus = profitlossofus;
	}
	public Double getProfitlossrateofus() {
		return profitlossrateofus;
	}
	public void setProfitlossrateofus(Double profitlossrateofus) {
		this.profitlossrateofus = profitlossrateofus;
	}
	public Double getProfitlossrate() {
		return profitlossrate;
	}
	public void setProfitlossrate(Double profitlossrate) {
		this.profitlossrate = profitlossrate;
	}
	
	
	
}

