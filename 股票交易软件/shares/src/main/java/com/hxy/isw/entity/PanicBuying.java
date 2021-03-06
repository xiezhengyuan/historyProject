package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * panicbuying 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "panicbuying")
public class PanicBuying{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fexampleplanid")
	private Long fexampleplanid;

	@Column(name = "fgoldsdetailid")
	private Long fgoldsdetailid;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private Integer state;
	
	
	@Column(name = "amount")
	private Double amount;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFexampleplanid(Long fexampleplanid){
	this.fexampleplanid=fexampleplanid;
	}
	public Long getFexampleplanid(){
		return fexampleplanid;
	}
	public void setFgoldsdetailid(Long fgoldsdetailid){
	this.fgoldsdetailid=fgoldsdetailid;
	}
	public Long getFgoldsdetailid(){
		return fgoldsdetailid;
	}
	public void setFuserinfoid(Long fuserinfoid){
	this.fuserinfoid=fuserinfoid;
	}
	public Long getFuserinfoid(){
		return fuserinfoid;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}

