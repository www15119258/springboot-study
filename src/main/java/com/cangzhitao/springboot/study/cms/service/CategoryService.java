package com.cangzhitao.springboot.study.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseTreeService;
import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.repository.CategoryRepository;

@Service
public class CategoryService extends BaseTreeService<Category> implements ICategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Override
	public BaseRepository<Category> getRepository() {
		return repository;
	}

}
