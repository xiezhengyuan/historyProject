package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * postings 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "postings")
public class Postings{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "postingstitle")
	private String postingstitle;

	@Column(name = "postingscontent")
	private String postingscontent;

	@Column(name="fpostingstyleid")
	private Long fpostingstyleid;
	
	@Column(name = "reward")
	private Integer reward;

	@Column(name = "comments")
	private Integer comments;

	@Column(name = "praise")
	private Integer praise;

	@Column(name = "share")
	private Integer share;

	@Column(name = "state")
	private Integer state;

	@Column(name = "ishot")
	private Integer ishot;

	@Column(name = "createtime")
	private Date createtime;

	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setFuserinfoid(Long fuserinfoid){
	this.fuserinfoid=fuserinfoid;
	}
	public Long getFuserinfoid(){
		return fuserinfoid;
	}
	public void setPostingstitle(String postingstitle){
	this.postingstitle=postingstitle;
	}
	public String getPostingstitle(){
		return postingstitle;
	}
	public void setPostingscontent(String postingscontent){
	this.postingscontent=postingscontent;
	}
	public String getPostingscontent(){
		return postingscontent;
	}
	public void setReward(Integer reward){
	this.reward=reward;
	}
	public Integer getReward(){
		return reward;
	}
	public void setComments(Integer comments){
	this.comments=comments;
	}
	public Integer getComments(){
		return comments;
	}
	public void setPraise(Integer praise){
	this.praise=praise;
	}
	public Integer getPraise(){
		return praise;
	}
	public void setShare(Integer share){
	this.share=share;
	}
	public Integer getShare(){
		return share;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public void setIshot(Integer ishot){
	this.ishot=ishot;
	}
	public Integer getIshot(){
		return ishot;
	}
	public void setCreatetime(Date createtime){
	this.createtime=createtime;
	}
	public Date getCreatetime(){
		return createtime;
	}
	public Long getFpostingstyleid() {
		return fpostingstyleid;
	}
	public void setFpostingstyleid(Long fpostingstyleid) {
		this.fpostingstyleid = fpostingstyleid;
	}
	
	
}

