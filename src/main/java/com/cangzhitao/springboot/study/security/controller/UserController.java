package com.cangzhitao.springboot.study.security.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseEntityController;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.service.IRoleService;
import com.cangzhitao.springboot.study.security.service.IUserService;

@RestController
@RequestMapping(value = "security/user")
public class UserController extends BaseEntityController<User> {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public IUserService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "security/user/list";
	}
	
	@Override
	public String getPageAdd() {
		return "security/user/add";
	}
	
	@Override
	public String getPageEdit() {
		return "security/user/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:security:user:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:security:user:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:security:user:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:security:user:delete";
	}
	
	@Override
	public void updateOld(User old, User entity) {
		old.setNickname(entity.getNickname());
	}
	
	@Override
	public void beforeSave(User entity) {
		super.beforeSave(entity);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@GetMapping(value = "findUserUnAssignRoles/{userId}/{page}/{size}")
	public Object findUserUnAssignRoles(@PathVariable Long userId, @PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return service.findUserUnAssignRoles(userId, pageable);
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@PostMapping(value = "assignRole")
	public Object assignRole(Long userId, Long roleId) {
		User user = service.get(userId);
		Role role = roleService.get(roleId);
		user.getRoles().add(role);
		service.save(user);
		return role;
	}

	@PreAuthorize("hasAuthority('jbf:security:user:assignRole')")
	@PostMapping(value = "removeRole")
	public Object removeRole(Long userId, Long roleId) {
		User user = service.get(userId);
		Role role = roleService.get(roleId);
		Set<Role> roles = user.getRoles();
		for (Role r : roles) {
			if (r.getId().equals(roleId)) {
				roles.remove(r);
				break;
			}
		}
		service.save(user);
		return role;
	}

}
