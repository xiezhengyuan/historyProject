package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * userrecharge 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "userrecharge")
public class UserRecharge{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "outtradno")
	private String outtradno;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "golds")
	private Double golds;

	@Column(name = "paystate")
	private Integer paystate;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private Integer state;

	
	@Column(name = "paymentway")
	private String paymentway;
	
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
	public void setOuttradno(String outtradno){
	this.outtradno=outtradno;
	}
	public String getOuttradno(){
		return outtradno;
	}
	public void setAmount(Double amount){
	this.amount=amount;
	}
	public Double getAmount(){
		return amount;
	}
	public void setGolds(Double golds){
	this.golds=golds;
	}
	public Double getGolds(){
		return golds;
	}
	public void setPaystate(Integer paystate){
	this.paystate=paystate;
	}
	public Integer getPaystate(){
		return paystate;
	}
	public void setCreatetime(Date createtime){
	this.createtime=createtime;
	}
	public Date getCreatetime(){
		return createtime;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public String getPaymentway() {
		return paymentway;
	}
	public void setPaymentway(String paymentway) {
		this.paymentway = paymentway;
	}
	
}

