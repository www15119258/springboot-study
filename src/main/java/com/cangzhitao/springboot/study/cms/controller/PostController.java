package com.cangzhitao.springboot.study.cms.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.service.IPostService;

@RestController
@RequestMapping(value = "cms/post")
public class PostController {
	
	@Autowired
	private IPostService service;
	
	@PreAuthorize("hasAuthority('jbf:cms:post:save')")
	@PostMapping(value = "save")
	public Object save(@RequestBody Post post) {
		post.setPublishDate(new Date());
		service.save(post);
		return post;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return service.get(id);
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:edit')")
	@PutMapping(value = "update")
	public Object update(@RequestBody Post post) {
		if (post.getId() == null) {
			return null;
		}
		Post old = service.get(post.getId());
		if (old == null) {
			return null;
		}
		old.setTitle(post.getTitle());
		old.setContent(post.getContent());
		old.setSummary(post.getSummary());
		old.setAuthor(post.getAuthor());
		old.setPublishDate(post.getPublishDate());
		service.save(old);
		return old;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:delete')")
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			service.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("cms/post/list");
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "findAll")
	public Object findAll() {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(0, 10, sort);
		return service.findAll(pageable);
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(page, size, sort);
		return service.findAll(pageable);
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

	@PreAuthorize("hasAuthority('jbf:cms:post:save')")
	@GetMapping(value = "add")
	public ModelAndView add() {
		return new ModelAndView("cms/post/add");
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:edit')")
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		return new ModelAndView("cms/post/edit");
	}

}
