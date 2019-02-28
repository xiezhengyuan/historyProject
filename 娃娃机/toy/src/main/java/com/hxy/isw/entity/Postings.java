package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="postings")
public class Postings {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name="postingsname")
	private String postingsname;
	
	@Column(name="postingscontent")
	private String postingscontent;
	
	@Column(name="fpostingstyleid")
	private Long fpostingstyleid;
	
	@Column(name="views")
	private int views;
	
	@Column(name="comments")
	private int comments;
	
	@Column(name="praise")
	private int praise;
	
	@Column(name="state")
	private int state;
	
	@Column(name="createtime")
	private Date createtime;

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

	public String getPostingsname() {
		return postingsname;
	}

	public void setPostingsname(String postingsname) {
		this.postingsname = postingsname;
	}

	public String getPostingscontent() {
		return postingscontent;
	}

	public void setPostingscontent(String postingscontent) {
		this.postingscontent = postingscontent;
	}

	public Long getFpostingstyleid() {
		return fpostingstyleid;
	}

	public void setFpostingstyleid(Long fpostingstyleid) {
		this.fpostingstyleid = fpostingstyleid;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
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
