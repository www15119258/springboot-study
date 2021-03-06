package com.cangzhitao.springboot.study.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseTreeEntityController;
import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.service.ICategoryService;

@RestController
@RequestMapping(value = "cms/category")
public class CategoryController extends BaseTreeEntityController<Category> {
	
	@Autowired
	private ICategoryService service;
	
	@Autowired
	private CacheManager cacheManager;
	
	public ICategoryService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "cms/category/list";
	}
	
	@Override
	public String getPageAdd() {
		return "cms/category/add";
	}
	
	@Override
	public String getPageEdit() {
		return "cms/category/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:cms:category:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:cms:category:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:cms:category:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:cms:category:delete";
	}
	
	@Override
	public void updateOld(Category old, Category entity) {
		old.setName(entity.getName());
	}

	@Override
	public void afterSave(Category entity) {
		super.afterSave(entity);
		cacheManager.getCache("category").evict("tree");
	}

	@Override
	public void afterUpdate(Category entity) {
		super.afterUpdate(entity);
		cacheManager.getCache("category").evict("tree");
	}

	@Override
	public void afterDelete(Long id) {
		super.afterDelete(id);
		cacheManager.getCache("category").evict("tree");
	}
	
	

}
