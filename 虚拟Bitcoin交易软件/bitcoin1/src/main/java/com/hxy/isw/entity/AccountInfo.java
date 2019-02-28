package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * accountinfo 实体类
    * Fri Jul 14 10:26:24 CST 2017 lxq
    */ 

@Entity
@Table(name = "accountinfo")
public class AccountInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private Integer type;

	@Column(name = "role")
	private Integer role;

	@Column(name = "fcompanyid")
	private Long fcompanyid;

	@Column(name = "proportion")
	private Double proportion;

	@Column(name = "extensionurl")
	private String extensionurl;

	@Column(name = "fparentid")
	private Long fparentid;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private Integer state;

	@Column(name = "defaultaccount")
	private Integer defaultaccount;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setUsername(String username){
	this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
	this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setMobile(String mobile){
	this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setType(Integer type){
	this.type=type;
	}
	public Integer getType(){
		return type;
	}
	public void setRole(Integer role){
	this.role=role;
	}
	public Integer getRole(){
		return role;
	}
	public void setFcompanyid(Long fcompanyid){
	this.fcompanyid=fcompanyid;
	}
	public Long getFcompanyid(){
		return fcompanyid;
	}
	public void setProportion(Double proportion){
	this.proportion=proportion;
	}
	public Double getProportion(){
		return proportion;
	}
	public void setExtensionurl(String extensionurl){
	this.extensionurl=extensionurl;
	}
	public String getExtensionurl(){
		return extensionurl;
	}
	public void setFparentid(Long fparentid){
	this.fparentid=fparentid;
	}
	public Long getFparentid(){
		return fparentid;
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
	public void setDefaultaccount(Integer defaultaccount){
	this.defaultaccount=defaultaccount;
	}
	public Integer getDefaultaccount(){
		return defaultaccount;
	}
}

