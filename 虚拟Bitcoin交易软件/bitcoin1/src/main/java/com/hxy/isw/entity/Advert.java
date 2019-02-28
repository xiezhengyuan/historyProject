package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * advert 实体类
    * Wed Oct 18 13:47:57 CST 2017 lxq
    */ 

@Entity
@Table(name = "advert")
public class Advert{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "minprice")
	private Double minprice;

	@Column(name = "mincost")
	private Double mincost;

	@Column(name = "maxcost")
	private Double maxcost;

	@Column(name = "premium")
	private Integer premium;

	@Column(name = "price")
	private Double price;

	@Column(name = "message")
	private String message;

	@Column(name = "state")
	private Integer state;

	@Column(name = "type")
	private Integer type;

	@Column(name = "time")
	private Date time;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFuserinfoid(Long fuserinfoid){
	this.fuserinfoid=fuserinfoid;
	}
	public Long getFuserinfoid(){
		return fuserinfoid;
	}
	public void setMinprice(Double minprice){
	this.minprice=minprice;
	}
	public Double getMinprice(){
		return minprice;
	}
	public void setMincost(Double mincost){
	this.mincost=mincost;
	}
	public Double getMincost(){
		return mincost;
	}
	public void setMaxcost(Double maxcost){
	this.maxcost=maxcost;
	}
	public Double getMaxcost(){
		return maxcost;
	}
	public void setPremium(Integer premium){
	this.premium=premium;
	}
	public Integer getPremium(){
		return premium;
	}
	public void setPrice(Double price){
	this.price=price;
	}
	public Double getPrice(){
		return price;
	}
	public void setMessage(String message){
	this.message=message;
	}
	public String getMessage(){
		return message;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public void setType(Integer type){
	this.type=type;
	}
	public Integer getType(){
		return type;
	}
	public void setTime(Date time){
	this.time=time;
	}
	public Date getTime(){
		return time;
	}
}

