package com.hxy.isw.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//轮播图信息表
@Entity
@Table(name="advertisement")
public class Advertisement {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="ffileinfoid")
	private Long ffileinfoid;
	
	@Column(name="state")
	private int state;
	
	@Column(name="createtime")
	private Date createtime;
	
	@Column(name="jump")
	private int jump;
	
	@Column(name="jumptype")
	private String jumptype;
	
	@Column(name="jumpcontent")
	private String jumpcontent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getFfileinfoid() {
		return ffileinfoid;
	}

	public void setFfileinfoid(Long ffileinfoid) {
		this.ffileinfoid = ffileinfoid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public String getJumptype() {
		return jumptype;
	}

	public void setJumptype(String jumptype) {
		this.jumptype = jumptype;
	}

	public String getJumpcontent() {
		return jumpcontent;
	}

	public void setJumpcontent(String jumpcontent) {
		this.jumpcontent = jumpcontent;
	}
	
	
}
