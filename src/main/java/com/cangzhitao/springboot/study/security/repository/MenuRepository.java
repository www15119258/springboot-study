package com.cangzhitao.springboot.study.security.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.base.repository.BaseTreeRepository;
import com.cangzhitao.springboot.study.security.entities.Menu;

@Repository
public interface MenuRepository extends BaseTreeRepository<Menu> {

}
