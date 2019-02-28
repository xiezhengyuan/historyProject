package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shares")
public class Shares {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "sharesname")
	private String sharesname;

	@Column(name = "code")
	private String code;//股票代码

	@Column(name = "price")
	private Double price;//当前价

	@Column(name = "upanddown")
	private String upanddown;
	
	@Column(name = "createtime")
	private Date createtime;
	
	@Column(name = "type")
	private Integer type;//0股票 1美股 2外汇
	
	@Column(name = "state")
	private Integer state; //0上架 -1下架
	
	@Column(name = "img")
	private String img;

	
	@Column(name = "stocktype")
	private String stocktype; //A股  B股
	
	@Column(name = "market")
	private String market;//市场   hs  us 
	
	@Column(name = "currcapital")
	private String currcapital;//流通股本，万股
	
	@Column(name = "profit_four")
	private String profit_four;//四季度净利润（亿元）
	
	@Column(name = "totalcapital")
	private String totalcapital;//总股本，万股
	
	@Column(name = "mgjzc")
	private String mgjzc;//每股净资产（元）
	
	
	@Column(name = "pinyin")
	private String pinyin;
	
	@Column(name = "listing_date")
	private String listing_date;
	
	@Column(name = "ct")
	private String ct;
	
	@Column(name = "sharestate")
	private String sharestate;
	
	@Column(name = "lastupdate")
	private Date lastupdate;
	
	@Column(name = "diffmoney")
	private String diffmoney; //涨跌金额
	
	@Column(name = "diffrate")
	private String diffrate;//涨跌幅度
	
	@Column(name = "quotation")
	private String quotation;//行情(json)
	
	@Column(name = "minkline")
	private String minkline;
	
	@Column(name = "daykline")
	private String daykline;
	
	@Column(name = "weekkline")
	private String weekkline;
	
	@Column(name = "monthkline")
	private String monthkline;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSharesname() {
		return sharesname;
	}

	public void setSharesname(String sharesname) {
		this.sharesname = sharesname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUpanddown() {
		return upanddown;
	}

	public void setUpanddown(String upanddown) {
		this.upanddown = upanddown;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getStocktype() {
		return stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getCurrcapital() {
		return currcapital;
	}

	public void setCurrcapital(String currcapital) {
		this.currcapital = currcapital;
	}

	public String getProfit_four() {
		return profit_four;
	}

	public void setProfit_four(String profit_four) {
		this.profit_four = profit_four;
	}

	public String getTotalcapital() {
		return totalcapital;
	}

	public void setTotalcapital(String totalcapital) {
		this.totalcapital = totalcapital;
	}

	public String getMgjzc() {
		return mgjzc;
	}

	public void setMgjzc(String mgjzc) {
		this.mgjzc = mgjzc;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getListing_date() {
		return listing_date;
	}

	public void setListing_date(String listing_date) {
		this.listing_date = listing_date;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getSharestate() {
		return sharestate;
	}

	public void setSharestate(String sharestate) {
		this.sharestate = sharestate;
	}

	

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getDiffmoney() {
		return diffmoney;
	}

	public void setDiffmoney(String diffmoney) {
		this.diffmoney = diffmoney;
	}

	public String getDiffrate() {
		return diffrate;
	}

	public void setDiffrate(String diffrate) {
		this.diffrate = diffrate;
	}

	public String getQuotation() {
		return quotation;
	}

	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}

	public String getMinkline() {
		return minkline;
	}

	public void setMinkline(String minkline) {
		this.minkline = minkline;
	}

	public String getDaykline() {
		return daykline;
	}

	public void setDaykline(String daykline) {
		this.daykline = daykline;
	}

	public String getWeekkline() {
		return weekkline;
	}

	public void setWeekkline(String weekkline) {
		this.weekkline = weekkline;
	}

	public String getMonthkline() {
		return monthkline;
	}

	public void setMonthkline(String monthkline) {
		this.monthkline = monthkline;
	}

	
}
