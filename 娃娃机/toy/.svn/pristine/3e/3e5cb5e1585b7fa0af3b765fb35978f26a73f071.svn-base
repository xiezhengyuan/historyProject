package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userallege")
public class UserAllege {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="fuserid")
	private Long fuserid;
	
	@Column(name="fallegeid")
	private Long fallegeid;	
	
	@Column(name="substance")
	private String substance;
	
	@Column(name="videoinfoid")
	private Long videoinfoid;
	
	@Column(name="createtime")
	private Date createtime;
	
	@Column(name="state")
	private Integer state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuserid() {
		return fuserid;
	}

	public void setFuserid(Long fuserid) {
		this.fuserid = fuserid;
	}

	public Long getFallegeid() {
		return fallegeid;
	}

	public void setFallegeid(Long fallegeid) {
		this.fallegeid = fallegeid;
	}

	public String getSubstance() {
		return substance;
	}

	public void setSubstance(String substance) {
		this.substance = substance;
	}

	public Long getVideoinfoid() {
		return videoinfoid;
	}

	public void setVideoinfoid(Long videoinfoid) {
		this.videoinfoid = videoinfoid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}	

	
}
