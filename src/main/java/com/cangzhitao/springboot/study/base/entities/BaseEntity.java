package com.cangzhitao.springboot.study.base.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 343841036058071587L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
