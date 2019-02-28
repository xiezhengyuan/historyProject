package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * userprofit 实体类
    * Fri Jul 14 10:26:24 CST 2017 lcc
    */ 

@Entity
@Table(name = "userprofit")
public class UserProfit{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "type")
	private int type;//0沪深股票 1美股 2外汇
	
	@Column(name = "foreignkeyid")
	private Long foreignkeyid; //type为0沪深股票 1美股时  关联 shareswarehouse表 type为2外汇时 关联foreignexchange表

	@Column(name = "tags")
	private int tags;//0 卖出  1持仓
	
	@Column(name = "cost")
	private Double cost;//成本（单价）

	@Column(name = "price")
	private Double price;//当前市价
	
	@Column(name = "nums")
	private int nums;//数量
	
	@Column(name = "diffprice")
	private Double diffprice;//差价（单价）

	@Column(name = "profit")
	private Double profit;//收益（总）= diffprice*nums
	
	
	@Column(name = "profitrate")
	private Double profitrate;//收益率（总）= diffprice/cost
	
	@Column(name = "diffpriceoflasttime")
	private Double diffpriceoflasttime;//与上次市价的差值
	
	@Column(name = "diffprofitoflasttime")
	private Double diffprofitoflasttime;//与上次市价的差值收益 = diffpriceoflasttime*nums
	
	@Column(name = "diffprofitoflasttimerate")
	private Double diffprofitoflasttimerate;//与上次市价的差值收益率 = diffpriceoflasttime/cost

	@Column(name = "time")
	private Date time;

	@Column(name = "isplan")
	private int isplan;//0非计划  1计划
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuserinfoid() {
		return fuserinfoid;
	}

	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getForeignkeyid() {
		return foreignkeyid;
	}

	public void setForeignkeyid(Long foreignkeyid) {
		this.foreignkeyid = foreignkeyid;
	}

	public int getTags() {
		return tags;
	}

	public void setTags(int tags) {
		this.tags = tags;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public Double getDiffprice() {
		return diffprice;
	}

	public void setDiffprice(Double diffprice) {
		this.diffprice = diffprice;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getDiffpriceoflasttime() {
		return diffpriceoflasttime;
	}

	public void setDiffpriceoflasttime(Double diffpriceoflasttime) {
		this.diffpriceoflasttime = diffpriceoflasttime;
	}

	public Double getDiffprofitoflasttime() {
		return diffprofitoflasttime;
	}

	public void setDiffprofitoflasttime(Double diffprofitoflasttime) {
		this.diffprofitoflasttime = diffprofitoflasttime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getProfitrate() {
		return profitrate;
	}

	public void setProfitrate(Double profitrate) {
		this.profitrate = profitrate;
	}

	public Double getDiffprofitoflasttimerate() {
		return diffprofitoflasttimerate;
	}

	public void setDiffprofitoflasttimerate(Double diffprofitoflasttimerate) {
		this.diffprofitoflasttimerate = diffprofitoflasttimerate;
	}

	public int getIsplan() {
		return isplan;
	}

	public void setIsplan(int isplan) {
		this.isplan = isplan;
	}
	
	
	
}

