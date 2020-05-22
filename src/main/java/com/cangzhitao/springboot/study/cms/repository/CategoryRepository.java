package com.cangzhitao.springboot.study.cms.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.base.repository.BaseTreeRepository;
import com.cangzhitao.springboot.study.cms.entities.Category;

@Repository
public interface CategoryRepository extends BaseTreeRepository<Category> {

}
