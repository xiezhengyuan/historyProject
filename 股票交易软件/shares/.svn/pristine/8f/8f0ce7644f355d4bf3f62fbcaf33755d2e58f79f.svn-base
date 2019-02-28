package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * notice 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "notice")
public class Notice{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "target")
	private Integer target;

	@Column(name = "fcompanyid")
	private Long fcompanyid;

	@Column(name = "noticename")
	private String noticename;

	@Column(name = "noticecontent")
	private String noticecontent;

	@Column(name = "state")
	private Integer state;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "sendtime")
	private Date sendtime;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setTarget(Integer target){
	this.target=target;
	}
	public Integer getTarget(){
		return target;
	}
	public void setFcompanyid(Long fcompanyid){
	this.fcompanyid=fcompanyid;
	}
	public Long getFcompanyid(){
		return fcompanyid;
	}
	public void setNoticename(String noticename){
	this.noticename=noticename;
	}
	public String getNoticename(){
		return noticename;
	}
	public void setNoticecontent(String noticecontent){
	this.noticecontent=noticecontent;
	}
	public String getNoticecontent(){
		return noticecontent;
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
	public void setSendtime(Date sendtime){
	this.sendtime=sendtime;
	}
	public Date getSendtime(){
		return sendtime;
	}
}

