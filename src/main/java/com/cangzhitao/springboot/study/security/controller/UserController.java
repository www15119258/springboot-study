package com.cangzhitao.springboot.study.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.repository.UserRepository;

@RestController
@RequestMapping(value = "security/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(value = "save")
	public Object save(@RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return userRepository.findById(id);
	}
	
	@PutMapping(value = "update")
	public Object update(@RequestBody User user) {
		if (user.getId() == null) {
			return null;
		}
		Optional<User> optional = userRepository.findById(user.getId());
		if (!optional.isPresent()) {
			return null;
		}
		User old = optional.get();
		//TODO 将user属性赋值给old
		userRepository.save(old);
		return old;
	}
	
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("security/user/list");
	}
	
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return userRepository.findAll(pageable);
	}
	
}
