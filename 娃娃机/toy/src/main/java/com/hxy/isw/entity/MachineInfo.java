package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 娃娃机信息表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "machineinfo")
public class MachineInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "empid")
	private Long empid;
	
	@Column(name = "machineno")
	private String machineno;

	@Column(name = "ftoysid")
	private Long ftoysid;

	@Column(name = "price")
	private int price;

	@Column(name = "popularity")
	private int popularity;

	@Column(name = "createtime")
	private Date createtime;

	@Column(name = "state")
	private int state;

	@Column(name = "isnew")
	private int isnew;

	@Column(name = "facevideo")
	private String facevideo;

	@Column(name = "sidevideo")
	private String sidevideo;

	@Column(name = "views")
	private int views;

	@Column(name = "subscribe")
	private int subscribe;

	@Column(name = "stock")
	private int stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Long getEmpid() {
		return empid;
	}

	public void setEmpid(Long empid) {
		this.empid = empid;
	}

	public String getMachineno() {
		return machineno;
	}

	public void setMachineno(String machineno) {
		this.machineno = machineno;
	}

	public Long getFtoysid() {
		return ftoysid;
	}

	public void setFtoysid(Long ftoysid) {
		this.ftoysid = ftoysid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
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

	public int getIsnew() {
		return isnew;
	}

	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}

	public String getFacevideo() {
		return facevideo;
	}

	public void setFacevideo(String facevideo) {
		this.facevideo = facevideo;
	}

	public String getSidevideo() {
		return sidevideo;
	}

	public void setSidevideo(String sidevideo) {
		this.sidevideo = sidevideo;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
