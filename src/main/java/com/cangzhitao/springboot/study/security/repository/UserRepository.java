package com.cangzhitao.springboot.study.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.entities.User;

@Repository
public interface UserRepository extends BaseRepository<User> {

	@Query("select r from Role r, User u where u.id=:userId and r not in elements(u.roles)")
	public Page<Role> findUserUnAssignRoles(Long userId, Pageable pageable);
	
}
