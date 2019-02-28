package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="videoinfo")
public class VideoInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="fuserid")
	private Long fuserid;
	
	@Column(name="fmachineid")
	private Long fmachineid;
	
	@Column(name="videourl")
	private String videourl;
	
	@Column(name="successed")
	private int successed;
	
	@Column(name="state")
	private int state;
	
	@Column(name="time")
	private Date time;

	
	@Column(name="toyname")
	private String toyname; 
	
	
	@Column(name="toyphoto")
	private String  toyphoto;
	
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

	public Long getFmachineid() {
		return fmachineid;
	}

	public void setFmachineid(Long fmachineid) {
		this.fmachineid = fmachineid;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getSuccessed() {
		return successed;
	}

	public void setSuccessed(int successed) {
		this.successed = successed;
	}

	public String getToyname() {
		return toyname;
	}

	public void setToyname(String toyname) {
		this.toyname = toyname;
	}

	public String getToyphoto() {
		return toyphoto;
	}

	public void setToyphoto(String toyphoto) {
		this.toyphoto = toyphoto;
	}

	
}
