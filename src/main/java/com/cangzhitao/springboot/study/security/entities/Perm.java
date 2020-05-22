package com.cangzhitao.springboot.study.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;

@Entity
@Table(name = "security_perm")
public class Perm extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9008630084169012524L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 权限字符串
	 */
	private String perm;
	
	/**
	 * 描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
