package com.cangzhitao.springboot.study.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.security.entities.Perm;

@Repository
public interface PermRepository extends JpaRepository<Perm, Long>, JpaSpecificationExecutor<Perm> {

}
