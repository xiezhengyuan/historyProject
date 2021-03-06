package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="nickname")
	private String nickname;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="state")
	private int state;
	
	@Column(name="createtime")
	private Date createtime;
	
	@Column(name="openid")
	private String openid;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="birthday")
	private String birthday;
	
	@Column(name="selfinfo")
	private String selfinfo;
	
	@Column(name="headimg")
	private String headimg;
	
	@Column(name="balance")
	private int balance;
	
	@Column(name="role")
	private int role;
	
	@Column(name="inviter")
	private Long inviter;
	
	@Column(name="freevoucher")
	private int freevoucher;
	
	@Column(name="level")
	private int level;

	
	@Column(name="uuid")
	private String uuid;
	
	@Column(name="isplay")
	private Integer isplay;
	
	@Column(name="usingmachineid")
	private Long usingmachineid;
	
	@Column(name="experiencenum")
	private Long  experiencenum;	
	
	@Column(name="designation")
	private String designation;	
	
	@Column(name="isdraw")
	private Integer isdraw;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSelfinfo() {
		return selfinfo;
	}

	public void setSelfinfo(String selfinfo) {
		this.selfinfo = selfinfo;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Long getInviter() {
		return inviter;
	}

	public void setInviter(Long inviter) {
		this.inviter = inviter;
	}

	public int getFreevoucher() {
		return freevoucher;
	}

	public void setFreevoucher(int freevoucher) {
		this.freevoucher = freevoucher;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getIsplay() {
		return isplay;
	}

	public void setIsplay(Integer isplay) {
		this.isplay = isplay;
	}

	public Long getUsingmachineid() {
		return usingmachineid;
	}

	public void setUsingmachineid(Long usingmachineid) {
		this.usingmachineid = usingmachineid;
	}

	public Long getExperiencenum() {
		return experiencenum;
	}

	public void setExperiencenum(Long experiencenum) {
		this.experiencenum = experiencenum;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getIsdraw() {
		return isdraw;
	}

	public void setIsdraw(Integer isdraw) {
		this.isdraw = isdraw;
	}
	
	
	
	
	
	
}
