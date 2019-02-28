package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersharessetting")
public class UserSharesSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "stoploss")
	private Integer stoploss;

	@Column(name = "stopprofit")
	private Integer stopprofit;
	
	@Column(name = "fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "state")
	private Integer state;
	
	@Column(name = "fforeignid")
	private Long fforeignid;
	
	@Column(name = "type")
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStoploss() {
		return stoploss;
	}

	public void setStoploss(Integer stoploss) {
		this.stoploss = stoploss;
	}

	public Integer getStopprofit() {
		return stopprofit;
	}

	public void setStopprofit(Integer stopprofit) {
		this.stopprofit = stopprofit;
	}

	public Long getFuserinfoid() {
		return fuserinfoid;
	}

	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getFforeignid() {
		return fforeignid;
	}

	public void setFforeignid(Long fforeignid) {
		this.fforeignid = fforeignid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
