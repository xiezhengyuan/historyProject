package com.hxy.isw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * menuinfo 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "menuinfo")
public class MenuInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "menuname")
	private String menuname;

	@Column(name = "url")
	private String url;

	@Column(name = "img")
	private String img;

	@Column(name = "order")
	private Integer order;

	@Column(name = "type")
	private Integer type;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setMenuname(String menuname){
	this.menuname=menuname;
	}
	public String getMenuname(){
		return menuname;
	}
	public void setUrl(String url){
	this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setImg(String img){
	this.img=img;
	}
	public String getImg(){
		return img;
	}
	public void setOrder(Integer order){
	this.order=order;
	}
	public Integer getOrder(){
		return order;
	}
	public void setType(Integer type){
	this.type=type;
	}
	public Integer getType(){
		return type;
	}
}

