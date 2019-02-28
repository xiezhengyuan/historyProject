package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
* @Description: 计划收益记录表
* @author: lixq
* @date 2017年6月29日 下午2:28:15 
*/
@Entity
@Table(name = "exampleplanlog")
public class ExamplePlanLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fexampleplanid")
	private Long fexampleplanid;
	
	@Column(name = "profitrate")
	private Double profitrate;
	
	@Column(name = "time")
	private Date time;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFexampleplanid() {
		return fexampleplanid;
	}

	public void setFexampleplanid(Long fexampleplanid) {
		this.fexampleplanid = fexampleplanid;
	}

	public Double getProfitrate() {
		return profitrate;
	}

	public void setProfitrate(Double profitrate) {
		this.profitrate = profitrate;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
}
