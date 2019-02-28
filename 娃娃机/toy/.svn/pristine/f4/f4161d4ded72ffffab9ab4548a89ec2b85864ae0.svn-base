package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 玩具图片表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "toysphotos")
public class ToysPhotos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "ftoysid")
	private Long ftoysid;
	
	@Column(name = "photourl")
	private String photourl;
	
	@Column(name = "ffileinfoid")
	private Long ffileinfoid;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "state")
	private int state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFtoysid() {
		return ftoysid;
	}

	public void setFtoysid(Long ftoysid) {
		this.ftoysid = ftoysid;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public Long getFfileinfoid() {
		return ffileinfoid;
	}

	public void setFfileinfoid(Long ffileinfoid) {
		this.ffileinfoid = ffileinfoid;
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
