package com.cangzhitao.springboot.study.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.springboot.study.base.service.IBaseService;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.entities.Role;

public interface IRoleService extends IBaseService<Role> {
	
	public Page<Perm> findRoleUnAssignPerms(Long roleId, Pageable pageable);

}
