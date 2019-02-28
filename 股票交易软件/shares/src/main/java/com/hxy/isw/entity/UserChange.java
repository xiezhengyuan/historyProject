package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userchange")
public class UserChange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "infcompanyid")
	private long infcompanyid;
	
	@Column(name = "faccountid")
	private long faccountid;
	
	@Column(name = "fdoitid")
	private long fdoitid;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "state")
	private long state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInfcompanyid() {
		return infcompanyid;
	}

	public void setInfcompanyid(long infcompanyid) {
		this.infcompanyid = infcompanyid;
	}

	public long getFaccountid() {
		return faccountid;
	}

	public void setFaccountid(long faccountid) {
		this.faccountid = faccountid;
	}

	public long getFdoitid() {
		return fdoitid;
	}

	public void setFdoitid(long fdoitid) {
		this.fdoitid = fdoitid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}
	
}
