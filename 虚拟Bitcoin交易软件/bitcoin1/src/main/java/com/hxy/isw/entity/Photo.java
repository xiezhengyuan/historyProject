package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * photo 实体类
    * Wed Oct 18 13:47:56 CST 2017 lxq
    */ 

@Entity
@Table(name = "photo")
public class Photo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "photourl")
	private String photourl;

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
	public void setPhotourl(String photourl){
	this.photourl=photourl;
	}
	public String getPhotourl(){
		return photourl;
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

