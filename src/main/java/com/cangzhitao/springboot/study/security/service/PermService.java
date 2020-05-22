package com.cangzhitao.springboot.study.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseService;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.repository.PermRepository;

@Service
public class PermService extends BaseService<Perm> implements IPermService {

	@Autowired
	private PermRepository repository;
	
	@Override
	public BaseRepository<Perm> getRepository() {
		return repository;
	}

}
