package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 邀请好友记录表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "invitelog")
public class InviteLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "finviteid")
	private Long finviteid;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "createtime")
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFinviteid() {
		return finviteid;
	}

	public void setFinviteid(Long finviteid) {
		this.finviteid = finviteid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

}
