package com.cangzhitao.springboot.study.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseEntityController;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.service.IPermService;

@RestController
@RequestMapping(value = "security/perm")
public class PermController extends BaseEntityController<Perm> {
	
	@Autowired
	private IPermService service;
	
	@Override
	public IPermService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "security/perm/list";
	}
	
	@Override
	public String getPageAdd() {
		return "security/perm/add";
	}
	
	@Override
	public String getPageEdit() {
		return "security/perm/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:security:perm:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:security:perm:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:security:perm:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:security:perm:delete";
	}
	
	@Override
	public void updateOld(Perm old, Perm entity) {
		old.setPerm(entity.getPerm());
		old.setDescription(entity.getDescription());
	}
	
}
