package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * rewardlog 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "rewardlog")
public class RewardLog{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fpostingsid")
	private Long fpostingsid;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "money")
	private Integer money;

	@Column(name = "createtime")
	private Date createtime;

	
	@Column(name = "frewardedid")
	private Long frewardedid;//被打赏人用户id
	
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFpostingsid(Long fpostingsid){
	this.fpostingsid=fpostingsid;
	}
	public Long getFpostingsid(){
		return fpostingsid;
	}
	public void setFuserinfoid(Long fuserinfoid){
	this.fuserinfoid=fuserinfoid;
	}
	public Long getFuserinfoid(){
		return fuserinfoid;
	}
	public void setMoney(Integer money){
	this.money=money;
	}
	public Integer getMoney(){
		return money;
	}
	public void setCreatetime(Date createtime){
	this.createtime=createtime;
	}
	public Date getCreatetime(){
		return createtime;
	}
	public Long getFrewardedid() {
		return frewardedid;
	}
	public void setFrewardedid(Long frewardedid) {
		this.frewardedid = frewardedid;
	}
	
}

