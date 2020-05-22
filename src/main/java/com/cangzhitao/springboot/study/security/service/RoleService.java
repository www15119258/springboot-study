package com.cangzhitao.springboot.study.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseService;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.repository.RoleRepository;

@Service
public class RoleService extends BaseService<Role> implements IRoleService {
	
	@Autowired
	private RoleRepository repository;

	@Override
	public BaseRepository<Role> getRepository() {
		return repository;
	}
	
	@Override
	public Page<Perm> findRoleUnAssignPerms(Long roleId, Pageable pageable) {
		return repository.findRoleUnAssignPerms(roleId, pageable);
	}

}
