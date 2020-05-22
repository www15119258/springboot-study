package com.cangzhitao.springboot.study.base.service;

import java.util.List;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;

public interface IBaseTreeService<T extends BaseTreeEntity<T>> extends IBaseService<T> {

	/**
	 * 获取整个树结构
	 * @return
	 */
	public List<T> getTree();
	
	/**
	 * 根据条件获取树结构
	 * @param entity
	 * @return
	 */
	public List<T> getTree(T entity);
	
	/**
	 * 获取某个节点下面的直接子节点
	 * @param id
	 * @return
	 */
	public List<T> getSubTree(Long id);
	
}
