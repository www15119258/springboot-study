package com.cangzhitao.springboot.study.cms.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;

@Entity
@Table(name = "cms_category")
public class Category extends BaseTreeEntity<Category> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3029290520434799360L;
	
	
	private String name;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
