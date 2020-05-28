package com.cangzhitao.springboot.study.cms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.springboot.study.base.service.IBaseService;
import com.cangzhitao.springboot.study.cms.entities.Post;

public interface IPostService extends IBaseService<Post> {
	
	public Page<Post> findByAuthorAndTitle(String author, String title, Pageable pageable);
	
	public Page<Post> findByCategory(Long categoryId, Pageable pageable);

}
