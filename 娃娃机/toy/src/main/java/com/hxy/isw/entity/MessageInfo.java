package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 私信表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "messageinfo")
public class MessageInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name = "fsenduserid")
	private Long fsenduserid;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "fnotifyid")
	private Long fnotifyid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuserinfoid() {
		return fuserinfoid;
	}

	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}

	public Long getFsenduserid() {
		return fsenduserid;
	}

	public void setFsenduserid(Long fsenduserid) {
		this.fsenduserid = fsenduserid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Long getFnotifyid() {
		return fnotifyid;
	}

	public void setFnotifyid(Long fnotifyid) {
		this.fnotifyid = fnotifyid;
	}

}
