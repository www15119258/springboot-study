package com.cangzhitao.springboot.study.base.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class BaseTreeEntity<T extends BaseTreeEntity<T>> extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7006454475690221329L;
	
	/**
	 * 父节点
	 */
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "parent_id")
	private T parent;
	
	/**
	 * 直接子节点
	 */
	@Transient
	private List<T> children;

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	@SuppressWarnings("unchecked")
	public T addChild(T child) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
		return (T) this;
	}

}
