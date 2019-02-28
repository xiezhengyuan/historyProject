package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
* @Description: 意见反馈
* @author: lixq
* @date 2017年6月29日 上午11:52:16 
*/
@Entity
@Table(name = "feedback")
public class FeedBack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "replayto")
	private String replayto;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getReplayto() {
		return replayto;
	}

	public void setReplayto(String replayto) {
		this.replayto = replayto;
	}

}
