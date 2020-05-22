package com.cangzhitao.springboot.study.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseTreeEntityController;
import com.cangzhitao.springboot.study.security.entities.Menu;
import com.cangzhitao.springboot.study.security.service.IMenuService;

@RestController
@RequestMapping(value = "security/menu")
public class MenuController extends BaseTreeEntityController<Menu> {
	
	@Autowired
	private IMenuService service;
	
	public IMenuService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "security/menu/list";
	}
	
	@Override
	public String getPageAdd() {
		return "security/menu/add";
	}
	
	@Override
	public String getPageEdit() {
		return "security/menu/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:security:menu:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:security:menu:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:security:menu:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:security:menu:delete";
	}
	
	@Override
	public void updateOld(Menu old, Menu entity) {
		old.setName(entity.getName());
		old.setUrl(entity.getUrl());
	}
	
}
