package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * tradeinfo 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "tradeinfo")
public class TradeInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "sharename")
	private String sharename;

	@Column(name = "type")
	private int type;

	@Column(name = "nums")
	private int nums;

	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "profit")
	private Double profit;
	
	@Column(name = "tradetype")
	private int tradetype;

	
	@Column(name = "fexampleid")
	private Long fexampleid;

	
	@Column(name = "fuserid")
	private Long fuserid;


	@Column(name = "time")
	private Date time;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSharename() {
		return sharename;
	}


	public void setSharename(String sharename) {
		this.sharename = sharename;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getNums() {
		return nums;
	}


	public void setNums(int nums) {
		this.nums = nums;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Double getProfit() {
		return profit;
	}


	public void setProfit(Double profit) {
		this.profit = profit;
	}


	public int getTradetype() {
		return tradetype;
	}


	public void setTradetype(int tradetype) {
		this.tradetype = tradetype;
	}


	public Long getFexampleid() {
		return fexampleid;
	}


	public void setFexampleid(Long fexampleid) {
		this.fexampleid = fexampleid;
	}


	public Long getFuserid() {
		return fuserid;
	}


	public void setFuserid(Long fuserid) {
		this.fuserid = fuserid;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}

	
	
}

