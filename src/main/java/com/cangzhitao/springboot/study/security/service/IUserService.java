package com.cangzhitao.springboot.study.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.springboot.study.base.service.IBaseService;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.entities.User;

public interface IUserService extends IBaseService<User> {
	
	public Page<Role> findUserUnAssignRoles(Long userId, Pageable pageable);

}
