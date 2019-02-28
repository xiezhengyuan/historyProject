package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="banner")
public class Banner {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="type")
	private int type;
	
	@Column(name="url")
	private String url;
	
	@Column(name="changeurl")
	private String changeurl;
	
	@Column(name="state")
	private int state;
	
	@Column(name="ffileinfoid")
	private Long ffileinfoid;
	
	@Column(name="createtime")
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChangeurl() {
		return changeurl;
	}

	public void setChangeurl(String changeurl) {
		this.changeurl = changeurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getFfileinfoid() {
		return ffileinfoid;
	}

	public void setFfileinfoid(Long ffileinfoid) {
		this.ffileinfoid = ffileinfoid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
}
