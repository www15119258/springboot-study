package com.cangzhitao.springboot.study.base.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;
import com.cangzhitao.springboot.study.base.service.IBaseTreeService;

public abstract class BaseTreeEntityController<T extends BaseTreeEntity<T>> extends BaseEntityController<T> {

	@Override
	public abstract IBaseTreeService<T> getService();
	
	@Override
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		this.checkDeletePerm();
		try {
			this.beforeDelete(id);
			deleteById(id);
			this.afterDelete(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	protected void deleteById(Long id) {
		List<T> children = getService().getSubTree(id);
		if (!children.isEmpty()) {
			for (T t : children) {
				deleteById(t.getId());
			}
		}
		getService().deleteById(id);
	}
	
	@GetMapping(value = "getTree")
	public Object getTree() {
		this.checkViewPerm();
		this.beforeGetTree();
		List<T> list = getService().getTree();
		this.afterGetTree(list);
		return list;
	}
	
	public void beforeGetTree() {
	}

	public void afterGetTree(List<T> list) {
	}
	
}
