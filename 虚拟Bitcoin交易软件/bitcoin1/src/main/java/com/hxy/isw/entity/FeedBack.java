package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * feedback 实体类
    * Wed Oct 18 13:47:56 CST 2017 lxq
    */ 

@Entity
@Table(name = "feedback")
public class FeedBack{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "content")
	private String content;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private Integer state;

	@Column(name = "replayto")
	private String replayto;

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
	public void setContent(String content){
	this.content=content;
	}
	public String getContent(){
		return content;
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
	public void setReplayto(String replayto){
	this.replayto=replayto;
	}
	public String getReplayto(){
		return replayto;
	}
}

