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

import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.repository.RoleRepository;

@RestController
@RequestMapping(value = "security/role")
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostMapping(value = "save")
	public Object save(@RequestBody Role role) {
		roleRepository.save(role);
		return role;
	}
	
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return roleRepository.findById(id);
	}
	
	@PutMapping(value = "update")
	public Object update(@RequestBody Role role) {
		if (role.getId() == null) {
			return null;
		}
		Optional<Role> optional = roleRepository.findById(role.getId());
		if (!optional.isPresent()) {
			return null;
		}
		Role old = optional.get();
		old.setRolename(role.getRolename());
		old.setNickname(role.getNickname());
		old.setDescription(role.getDescription());
		roleRepository.save(old);
		return old;
	}
	
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			roleRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("security/role/list");
	}
	
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return roleRepository.findAll(pageable);
	}
	
	@GetMapping(value = "add")
	public ModelAndView add() {
		return new ModelAndView("security/role/add");
	}
	
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		return new ModelAndView("security/role/edit");
	}
	
}
