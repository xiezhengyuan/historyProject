package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * usernotifystate 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "usernotifystate")
public class UserNotifyState{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;


	@Column(name = "fuserinfoid")
	private Long fuserinfoid;

	@Column(name = "fnotifyinfoid")
	private Long fnotifyinfoid;


	@Column(name = "time")
	private Date time;

	@Column(name = "state")
	private Integer state;

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

	public Long getFnotifyinfoid() {
		return fnotifyinfoid;
	}

	public void setFnotifyinfoid(Long fnotifyinfoid) {
		this.fnotifyinfoid = fnotifyinfoid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	
}

