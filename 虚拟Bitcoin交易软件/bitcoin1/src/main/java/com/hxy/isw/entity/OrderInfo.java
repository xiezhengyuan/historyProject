package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * orderinfo 实体类
    * Wed Oct 18 13:47:57 CST 2017 lxq
    */ 

@Entity
@Table(name = "orderinfo")
public class OrderInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "bfuserinfoid")
	private Long bfuserinfoid;

	@Column(name = "mfuserinfoid")
	private Long mfuserinfoid;

	@Column(name = "cost")
	private Double cost;

	@Column(name = "price")
	private Double price;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "deadtime")
	private Date deadtime;

	@Column(name = "time")
	private Date time;

	@Column(name = "state")
	private Integer state;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setBfuserinfoid(Long bfuserinfoid){
	this.bfuserinfoid=bfuserinfoid;
	}
	public Long getBfuserinfoid(){
		return bfuserinfoid;
	}
	public void setMfuserinfoid(Long mfuserinfoid){
	this.mfuserinfoid=mfuserinfoid;
	}
	public Long getMfuserinfoid(){
		return mfuserinfoid;
	}
	public void setCost(Double cost){
	this.cost=cost;
	}
	public Double getCost(){
		return cost;
	}
	public void setPrice(Double price){
	this.price=price;
	}
	public Double getPrice(){
		return price;
	}
	public void setAmount(Double amount){
	this.amount=amount;
	}
	public Double getAmount(){
		return amount;
	}
	public void setDeadtime(Date deadtime){
	this.deadtime=deadtime;
	}
	public Date getDeadtime(){
		return deadtime;
	}
	public void setTime(Date time){
	this.time=time;
	}
	public Date getTime(){
		return time;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
}

