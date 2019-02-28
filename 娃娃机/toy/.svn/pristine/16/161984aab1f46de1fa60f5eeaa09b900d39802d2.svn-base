package com.hxy.isw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: 娃娃机用户痕迹记录表
 * @author: lixq
 * @date 2017年6月29日 下午2:28:15
 */
@Entity
@Table(name = "machineusermark")
public class MachineUserMark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fmachineid")
	private Long fmachineid;
	
	@Column(name = "fuserinfoid")
	private Long fuserinfoid;
	
	@Column(name = "type")
	private int type;
	
	@Column(name = "createtime")
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFmachineid() {
		return fmachineid;
	}

	public void setFmachineid(Long fmachineid) {
		this.fmachineid = fmachineid;
	}

	public Long getFuserinfoid() {
		return fuserinfoid;
	}

	public void setFuserinfoid(Long fuserinfoid) {
		this.fuserinfoid = fuserinfoid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
}
