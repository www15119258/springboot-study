package com.cangzhitao.springboot.study.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseService;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.repository.UserRepository;

@Service
public class UserService extends BaseService<User> implements IUserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public BaseRepository<User> getRepository() {
		return repository;
	}
	
	@Override
	public Page<Role> findUserUnAssignRoles(Long userId, Pageable pageable) {
		return repository.findUserUnAssignRoles(userId, pageable);
	}

}
