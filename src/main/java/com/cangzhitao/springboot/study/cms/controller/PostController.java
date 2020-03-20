package com.cangzhitao.springboot.study.cms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.repository.PostRepository;

@RestController
@RequestMapping(value = "cms/post")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;
	
	@PostMapping(value = "save")
	public Object save(@RequestBody Post post) {
		postRepository.save(post);
		return post;
	}
	
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return postRepository.findById(id);
	}
	
	@PutMapping(value = "update")
	public Object update(@RequestBody Post post) {
		if (post.getId() == null) {
			return null;
		}
		Optional<Post> optional = postRepository.findById(post.getId());
		if (!optional.isPresent()) {
			return null;
		}
		Post old = optional.get();
		old.setTitle(post.getTitle());
		old.setContent(post.getContent());
		old.setSummary(post.getSummary());
		old.setAuthor(post.getAuthor());
		old.setPublishDate(post.getPublishDate());
		postRepository.save(old);
		return old;
	}
	
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			postRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

}
