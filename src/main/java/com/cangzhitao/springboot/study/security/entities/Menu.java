package com.cangzhitao.springboot.study.security.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "security_menu")
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396919484304841564L;
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * URL
	 */
	private String url;
	
	/**
	 * 父节点
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "parent_id")
	private Menu parent;
	
	/**
	 * 直接子节点
	 */
	@Transient
	private List<Menu> children;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
	public Menu addChild(Menu menu) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(menu);
		return this;
	}

}
