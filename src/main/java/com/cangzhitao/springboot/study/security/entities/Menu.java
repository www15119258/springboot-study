package com.cangzhitao.springboot.study.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;

@Entity
@Table(name = "security_menu")
public class Menu extends BaseTreeEntity<Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396919484304841564L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * URL
	 */
	private String url;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
