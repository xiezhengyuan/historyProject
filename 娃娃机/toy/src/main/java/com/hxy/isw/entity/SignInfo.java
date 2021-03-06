package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 签到表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "signinfo")
public class SignInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "golds")
	private int golds;

	@Column(name = "signcount")
	private int signcount;

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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getGolds() {
		return golds;
	}

	public void setGolds(int golds) {
		this.golds = golds;
	}

	public int getSigncount() {
		return signcount;
	}

	public void setSigncount(int signcount) {
		this.signcount = signcount;
	}

}
