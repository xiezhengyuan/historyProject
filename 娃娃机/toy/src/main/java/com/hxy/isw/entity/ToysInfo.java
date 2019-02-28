package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 玩具信息表
 * @author: lixq
 * @date 2017年6月29日 上午10:31:23
 */
@Entity
@Table(name = "toysinfo")
public class ToysInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
  
	
	
	@Column(name = "empid")
	private Long empid;
	
	
	public Long getEmpid() {
		return empid;
	}

	public void setEmpid(Long empid) {
		this.empid = empid;
	}

	@Column(name = "name")
	private String name;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "ffileinfoid")
	private Long ffileinfoid;
	
	@Column(name = "ftoystypeid")
	private Long ftoystypeid;
	
	@Column(name = "specifications")
	private String specifications;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "material")
	private String material;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "price")
	private int price;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getFfileinfoid() {
		return ffileinfoid;
	}

	public void setFfileinfoid(Long ffileinfoid) {
		this.ffileinfoid = ffileinfoid;
	}

	public Long getFtoystypeid() {
		return ftoystypeid;
	}

	public void setFtoystypeid(Long ftoystypeid) {
		this.ftoystypeid = ftoystypeid;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
