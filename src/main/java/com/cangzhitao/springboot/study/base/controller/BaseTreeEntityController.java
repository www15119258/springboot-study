package com.cangzhitao.springboot.study.base.controller;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;
import com.cangzhitao.springboot.study.base.service.IBaseTreeService;

public abstract class BaseTreeEntityController<T extends BaseTreeEntity<T>> extends BaseEntityController<T> {

	@Override
	public abstract IBaseTreeService<T> getService();
	
	@Override
	@PostMapping(value = "save")
	public Object save(@RequestBody T entity) {
		checkSavePerm();
		this.beforeSave(entity);
		T parent = entity.getParent();
		if (parent == null || parent.getId() == null) {
			entity.setParent(null);
		} else {
			parent = getService().get(parent.getId());
			if (parent == null) {
				throw new RuntimeException("父id不存在！");
			}
		}
		getService().save(entity);
		this.afterSave(entity);
		return entity;
	}
	
	@Override
	@PutMapping(value = "update")
	public Object update(@RequestBody T entity) {
		checkEditPerm();
		this.beforeUpdate(entity);
		if (entity.getId() == null) {
			return null;
		}
		T old = getService().get(entity.getId());
		if (old == null) {
			return null;
		}
		T parent = entity.getParent();
		if (parent == null || parent.getId() == null) {
			old.setParent(null);
		} else {
			parent = getService().get(parent.getId());
			if (parent == null) {
				throw new RuntimeException("父id不存在！");
			} else {
				old.setParent(parent);
			}
		}
		updateOld(old, entity);
		getService().save(old);
		this.afterUpdate(entity);
		return old;
	}
	
	
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
