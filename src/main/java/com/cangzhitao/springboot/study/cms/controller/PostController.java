package com.cangzhitao.springboot.study.cms.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseEntityController;
import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.service.IPostService;

@RestController
@RequestMapping(value = "cms/post")
public class PostController extends BaseEntityController<Post> {
	
	@Autowired
	private IPostService service;
	
	@Override
	public IPostService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "cms/post/list";
	}
	
	@Override
	public String getPageAdd() {
		return "cms/post/add";
	}
	
	@Override
	public String getPageEdit() {
		return "cms/post/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:cms:post:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:cms:post:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:cms:post:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:cms:post:delete";
	}
	
	@Override
	public void updateOld(Post old, Post entity) {
		old.setTitle(entity.getTitle());
		old.setContent(entity.getContent());
		old.setSummary(entity.getSummary());
		old.setAuthor(entity.getAuthor());
		old.setPublishDate(entity.getPublishDate());
	}
	
	@Override
	public void beforeSave(Post entity) {
		super.beforeSave(entity);
		entity.setPublishDate(new Date());
	}
	
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@PostMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size, 
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String title) {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(page, size, sort);
		return service.findByAuthorAndTitle(author, title, pageable);
	}

	@PostMapping(value = "updateCategorys")
	public Object updateCategorys(Long postId, Long[] categoryIds) {
		Post post = service.get(postId);
		if (categoryIds == null || categoryIds.length == 0) {
			post.setCategorys(null);
		} else {
			Set<Category> categorys = new HashSet<>();
			for (int i = 0; i < categoryIds.length; i++) {
				Category c = new Category();
				c.setId(categoryIds[i]);
				categorys.add(c);
			}
			post.setCategorys(categorys);
		}
		service.save(post);
		return post;
	}

}
