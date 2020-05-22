package com.cangzhitao.springboot.study.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cangzhitao.springboot.study.base.controller.BaseEntityController;
import com.cangzhitao.springboot.study.security.entities.Menu;
import com.cangzhitao.springboot.study.security.entities.Perm;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.service.IPermService;
import com.cangzhitao.springboot.study.security.service.IRoleService;

@RestController
@RequestMapping(value = "security/role")
public class RoleController extends BaseEntityController<Role> {
	
	@Autowired
	private IRoleService service;
	
	@Autowired
	private IPermService permService;
	
	@Override
	public IRoleService getService() {
		return service;
	}
	
	@Override
	public String getPageList() {
		return "security/role/list";
	}
	
	@Override
	public String getPageAdd() {
		return "security/role/add";
	}
	
	@Override
	public String getPageEdit() {
		return "security/role/edit";
	}
	
	@Override
	public String getViewPerm() {
		return "jbf:security:role:list";
	}
	
	@Override
	public String getSavePerm() {
		return "jbf:security:role:save";
	}
	
	@Override
	public String getEditPerm() {
		return "jbf:security:role:edit";
	}
	
	@Override
	public String getDeletePerm() {
		return "jbf:security:role:delete";
	}
	
	@Override
	public void updateOld(Role old, Role entity) {
		old.setRolename(entity.getRolename());
		old.setNickname(entity.getNickname());
		old.setDescription(entity.getDescription());
	}
	
	
	@PreAuthorize("hasAuthority('jbf:security:role:assignPerm')")
	@GetMapping(value = "findRoleUnAssignPerms/{roleId}/{page}/{size}")
	public Object findRoleUnAssignPerms(@PathVariable Long roleId, @PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		return service.findRoleUnAssignPerms(roleId, pageable);
	}
	
	@PreAuthorize("hasAuthority('jbf:security:role:assignPerm')")
	@PostMapping(value = "assignPerm")
	public Object assignPerm(Long roleId, Long permId) {
		Role role = service.get(roleId);
		Perm perm = permService.get(permId);
		role.getPerms().add(perm);
		service.save(role);
		return perm;
	}
	
	@PreAuthorize("hasAuthority('jbf:security:role:assignPerm')")
	@PostMapping(value = "removePerm")
	public Object removePerm(Long roleId, Long permId) {
		Role role = service.get(roleId);
		Perm perm = permService.get(permId);
		Set<Perm> perms = role.getPerms();
		for (Perm p : perms) {
			if (p.getId().equals(permId)) {
				perms.remove(p);
				break;
			}
		}
		service.save(role);
		return perm;
	}
	
	@PreAuthorize("hasAuthority('jbf:security:role:assignMenu')")
	@PostMapping(value = "updateMenus")
	public Object updateMenus(Long roleId, Long[] menuIds) {
		Role role = service.get(roleId);
		if (menuIds == null || menuIds.length == 0) {
			role.setMenus(null);
		} else {
			Set<Menu> menus = new HashSet<>();
			for (int i = 0; i < menuIds.length; i++) {
				Menu m = new Menu();
				m.setId(menuIds[i]);
				menus.add(m);
			}
			role.setMenus(menus);
		}
		service.save(role);
		return role;
	}
}
