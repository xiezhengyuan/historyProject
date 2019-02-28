package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * messageinfo 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "messageinfo")
public class MessageInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "fsenduserid")
	private Long fsenduserid;

	@Column(name = "content")
	private String content;

	@Column(name = "state")
	private Integer state;

	@Column(name = "fnotifyid")
	private Long fnotifyid;
	
	@Column(name = "createtime")
	private Date createtime;

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
	public void setFsenduserid(Long fsenduserid){
	this.fsenduserid=fsenduserid;
	}
	public Long getFsenduserid(){
		return fsenduserid;
	}
	public void setContent(String content){
	this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public void setFnotifyid(Long fnotifyid){
	this.fnotifyid=fnotifyid;
	}
	public Long getFnotifyid(){
		return fnotifyid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}

