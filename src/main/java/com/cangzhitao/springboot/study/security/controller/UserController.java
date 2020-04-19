package com.cangzhitao.springboot.study.security.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.repository.RoleRepository;
import com.cangzhitao.springboot.study.security.repository.UserRepository;

@RestController
@RequestMapping(value = "security/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasAuthority('jbf:security:user:save')")
	@PostMapping(value = "save")
	public Object save(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	@PreAuthorize("hasAuthority('jbf:security:user:list')")
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return userRepository.findById(id);
	}

	@PreAuthorize("hasAuthority('jbf:security:user:edit')")
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
		old.setNickname(user.getNickname());
		userRepository.save(old);
		return old;
	}

	@PreAuthorize("hasAuthority('jbf:security:user:delete')")
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	@PreAuthorize("hasAuthority('jbf:security:user:list')")
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("security/user/list");
	}

	@PreAuthorize("hasAuthority('jbf:security:user:list')")
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return userRepository.findAll(pageable);
	}

	@PreAuthorize("hasAuthority('jbf:security:user:save')")
	@GetMapping(value = "add")
	public ModelAndView add() {
		return new ModelAndView("security/user/add");
	}

	@PreAuthorize("hasAuthority('jbf:security:user:edit')")
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		return new ModelAndView("security/user/edit");
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@GetMapping(value = "findUserUnAssignRoles/{userId}/{page}/{size}")
	public Object findUserUnAssignRoles(@PathVariable Long userId, @PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return userRepository.findUserUnAssignRoles(userId, pageable);
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@PostMapping(value = "assignRole")
	public Object assignRole(Long userId, Long roleId) {
		User user = userRepository.getOne(userId);
		Role role = roleRepository.getOne(roleId);
		user.getRoles().add(role);
		userRepository.save(user);
		return role;
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@PostMapping(value = "removeRole")
	public Object removeRole(Long userId, Long roleId) {
		User user = userRepository.getOne(userId);
		Role role = roleRepository.getOne(roleId);
		Set<Role> roles = user.getRoles();
		for (Role r : roles) {
			if (r.getId().equals(roleId)) {
				roles.remove(r);
				break;
			}
		}
		userRepository.save(user);
		return role;
	}

}
