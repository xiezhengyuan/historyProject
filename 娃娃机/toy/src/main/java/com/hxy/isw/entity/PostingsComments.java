package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "postingscomments")
public class PostingsComments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fpostingsid")
	private Long fpostingsid;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "comment")
	private String comment;

	@Column(name = "state")
	private int state;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "fcommentid")
	private Long fcommentid;

	@Column(name = "isanonymous")
	private Integer isanonymous;

	public Integer getIsanonymous() {
		return isanonymous;
	}

	public void setIsanonymous(Integer isanonymous) {
		this.isanonymous = isanonymous;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFpostingsid() {
		return fpostingsid;
	}

	public void setFpostingsid(Long fpostingsid) {
		this.fpostingsid = fpostingsid;
	}

	public Long getFuserinfoid() {
		return fuserinfoid;
	}

	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Long getFcommentid() {
		return fcommentid;
	}

	public void setFcommentid(Long fcommentid) {
		this.fcommentid = fcommentid;
	}

}
