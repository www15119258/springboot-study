package com.cangzhitao.springboot.study.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseTreeService;
import com.cangzhitao.springboot.study.security.entities.Menu;
import com.cangzhitao.springboot.study.security.repository.MenuRepository;

@Service
public class MenuService extends BaseTreeService<Menu> implements IMenuService {

	@Autowired
	private MenuRepository repository;
	
	@Override
	public BaseRepository<Menu> getRepository() {
		return repository;
	}

}
