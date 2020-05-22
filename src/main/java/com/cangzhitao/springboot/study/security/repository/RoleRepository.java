package com.cangzhitao.springboot.study.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.entities.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

	@Query("select p from Perm p, Role r where r.id=:roleId and p not in elements(r.perms)")
	public Page<Perm> findRoleUnAssignPerms(Long roleId, Pageable pageable);
	
}
