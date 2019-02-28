package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userfollow")
public class UserFollow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="fuserid")
	private long fuserid;
	
	@Column(name="followuserid")
	private long followuserid;
	
	@Column(name="createtime")
	private Date createtime;
	
	@Column(name="state")
	private int state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFuserid() {
		return fuserid;
	}

	public void setFuserid(long fuserid) {
		this.fuserid = fuserid;
	}

	public long getFollowuserid() {
		return followuserid;
	}

	public void setFollowuserid(long followuserid) {
		this.followuserid = followuserid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	
}
