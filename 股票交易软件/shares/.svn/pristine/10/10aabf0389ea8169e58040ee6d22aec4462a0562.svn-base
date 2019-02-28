package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * subscription 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "subscription")
public class Subscription{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fsubscribedid")
	private Long fsubscribedid;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "state")
	private Integer state;

	@Column(name = "createtime")
	private Date createtime;

	
	@Column(name = "starttime")
	private Date starttime;
	
	@Column(name = "endtime")
	private Date endtime;
	
	@Column(name = "amount")
	private Double amount;
	
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFsubscribedid(Long fsubscribedid){
	this.fsubscribedid=fsubscribedid;
	}
	public Long getFsubscribedid(){
		return fsubscribedid;
	}
	public void setFuserinfoid(Long fuserinfoid){
	this.fuserinfoid=fuserinfoid;
	}
	public Long getFuserinfoid(){
		return fuserinfoid;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public void setCreatetime(Date createtime){
	this.createtime=createtime;
	}
	public Date getCreatetime(){
		return createtime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}

