package com.cangzhitao.springboot.study.base.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;
import com.cangzhitao.springboot.study.base.service.IBaseService;

public abstract class BaseEntityController<T extends BaseEntity> {
	
	public abstract IBaseService<T> getService();
	
	public abstract String getPageList();
	
	public abstract String getPageAdd();
	
	public abstract String getPageEdit();
	
	@PostMapping(value = "save")
	public Object save(@RequestBody T entity) {
		checkSavePerm();
		this.beforeSave(entity);
		getService().save(entity);
		this.afterSave(entity);
		return entity;
	}
	
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		checkViewPerm();
		this.beforeGet(id);
		T entity = getService().get(id);
		this.afterGet(entity);
		return entity;
	}
	
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
		updateOld(old, entity);
		getService().save(old);
		this.afterUpdate(entity);
		return old;
	}
	
	protected void updateOld(T old, T entity) {
	}
	
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		checkDeletePerm();
		this.beforeDelete(id);
		try {
			getService().deleteById(id);
			this.afterDelete(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@GetMapping(value = "list")
	public ModelAndView list() {
		checkViewPerm();
		return new ModelAndView(getPageList());
	}
	
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		checkViewPerm();
		this.beforeFindPage(page, size);
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<T> p = getService().findAll(pageable);
		this.afterFindPage(p);
		return p;
	}
	
	@GetMapping(value = "add")
	public ModelAndView add() {
		checkSavePerm();
		return new ModelAndView(getPageAdd());
	}
	
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		checkEditPerm();
		return new ModelAndView(getPageEdit());
	}
	
	public String getViewPerm() {
		return null;
	}
	
	public void checkViewPerm() {
		String perm = getViewPerm();
		if (StringUtils.isEmpty(perm)) {
			return;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(perm))) {
			throw new AccessDeniedException(perm);
		}
	}
	
	public String getSavePerm() {
		return null;
	}
	
	public void checkSavePerm() {
		String perm = getSavePerm();
		if (StringUtils.isEmpty(perm)) {
			return;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(perm))) {
			throw new AccessDeniedException(perm);
		}
	}
	
	public String getEditPerm() {
		return null;
	}
	
	public void checkEditPerm() {
		String perm = getEditPerm();
		if (StringUtils.isEmpty(perm)) {
			return;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(perm))) {
			throw new AccessDeniedException(perm);
		}
	}
	
	public String getDeletePerm() {
		return null;
	}
	
	public void checkDeletePerm() {
		String perm = getDeletePerm();
		if (StringUtils.isEmpty(perm)) {
			return;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(perm))) {
			throw new AccessDeniedException(perm);
		}
	}
	
	public void beforeSave(T entity) {
	}
	
	public void afterSave(T entity) {
	}
	
	public void beforeGet(Long id) {
	}
	
	public void afterGet(T entity) {
	}

	public void beforeUpdate(T entity) {
	}
	
	public void afterUpdate(T entity) {
	}
	
	public void beforeDelete(Long id) {
	}
	
	public void afterDelete(Long id) {
	}
	
	public void beforeFindPage(int page, int size) {
	}

	public void afterFindPage(Page<T> page) {
	}
}
