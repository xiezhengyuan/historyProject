package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * userinfo 实体类
    * Wed Oct 18 13:47:55 CST 2017 lxq
    */ 

@Entity
@Table(name = "userinfo")
public class UserInfo{

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

	@Column(name = "code")
	private String code;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "name")
	private String name;

	@Column(name = "intro")
	private String intro;

	@Column(name = "wxid")
	private String wxid;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "email")
	private String email;

	@Column(name = "paparno")
	private String paparno;

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
	public void setCode(String code){
	this.code=code;
	}
	public String getCode(){
		return code;
	}
	public void setNickname(String nickname){
	this.nickname=nickname;
	}
	public String getNickname(){
		return nickname;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setIntro(String intro){
	this.intro=intro;
	}
	public String getIntro(){
		return intro;
	}
	public void setWxid(String wxid){
	this.wxid=wxid;
	}
	public String getWxid(){
		return wxid;
	}
	public void setAmount(Double amount){
	this.amount=amount;
	}
	public Double getAmount(){
		return amount;
	}
	public void setEmail(String email){
	this.email=email;
	}
	public String getEmail(){
		return email;
	}
	public void setPaparno(String paparno){
	this.paparno=paparno;
	}
	public String getPaparno(){
		return paparno;
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

