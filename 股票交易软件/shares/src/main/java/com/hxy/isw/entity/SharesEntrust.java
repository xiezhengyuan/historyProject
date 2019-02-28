package com.hxy.isw.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

   /**
    * sharesentrust 实体类
    * Fri Jul 14 17:20:34 CST 2017 lxq
    */ 

@Entity
@Table(name = "sharesentrust")
public class SharesEntrust{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name = "fsharesid")
	private Long fsharesid;
	
	@Column(name = "fshareswarehouseid")
	private Long fshareswarehouseid;

	@Column(name = "sharesname")
	private String sharesname;

	@Column(name = "code")
	private String code;

	@Column(name = "price")
	private Double price;

	@Column(name = "entrustprice")
	private Double entrustprice;

	@Column(name = "entrustnums")
	private Integer entrustnums;

	@Column(name = "frozenamount")
	private Double frozenamount;

	@Column(name = "time")
	private Date time;

	@Column(name = "state")
	private Integer state;//状态0买入 -1卖出 1撤销

	@Column(name = "isplan")
	private Integer isplan;
	
	@Column(name = "type")
	private Integer type;

	@Column(name = "entruststate")
	private Integer entruststate;//挂单状态  0挂单中  1已匹配成功
	
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
	
	public Long getFshareswarehouseid() {
		return fshareswarehouseid;
	}
	public void setFshareswarehouseid(Long fshareswarehouseid) {
		this.fshareswarehouseid = fshareswarehouseid;
	}
	public void setSharesname(String sharesname){
	this.sharesname=sharesname;
	}
	public String getSharesname(){
		return sharesname;
	}
	public void setCode(String code){
	this.code=code;
	}
	public String getCode(){
		return code;
	}
	
	public Long getFsharesid() {
		return fsharesid;
	}
	public void setFsharesid(Long fsharesid) {
		this.fsharesid = fsharesid;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setEntrustprice(Double entrustprice){
	this.entrustprice=entrustprice;
	}
	public Double getEntrustprice(){
		return entrustprice;
	}
	public void setEntrustnums(Integer entrustnums){
	this.entrustnums=entrustnums;
	}
	public Integer getEntrustnums(){
		return entrustnums;
	}
	public void setFrozenamount(Double frozenamount){
	this.frozenamount=frozenamount;
	}
	public Double getFrozenamount(){
		return frozenamount;
	}
	public void setTime(Date time){
	this.time=time;
	}
	public Date getTime(){
		return time;
	}
	public void setState(Integer state){
	this.state=state;
	}
	public Integer getState(){
		return state;
	}
	public void setIsplan(Integer isplan){
	this.isplan=isplan;
	}
	public Integer getIsplan(){
		return isplan;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getEntruststate() {
		return entruststate;
	}
	public void setEntruststate(Integer entruststate) {
		this.entruststate = entruststate;
	}
	
}

