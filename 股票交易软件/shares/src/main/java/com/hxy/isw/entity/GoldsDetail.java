package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * goldsdetail 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "goldsdetail")
public class GoldsDetail{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "type")
	private Integer type;

	@Column(name = "golds")
	private Double golds;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private Integer state;

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
	public void setType(Integer type){
	this.type=type;
	}
	public Integer getType(){
		return type;
	}
	public void setGolds(Double golds){
	this.golds=golds;
	}
	public Double getGolds(){
		return golds;
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
}

