package com.cangzhitao.springboot.study.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.security.entities.Menu;
import com.cangzhitao.springboot.study.security.entities.Role;
import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.repository.MenuRepository;

@RestController
@RequestMapping(value = "sys")
public class SysController {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping(value = "index")
	public ModelAndView index() {
		return new ModelAndView("sys/index");
	}
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping(value = "header")
	public ModelAndView header() {
		return new ModelAndView("sys/header");
	}
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping(value = "aside")
	public ModelAndView aside() {
		return new ModelAndView("sys/aside");
	}
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping(value = "dashboard")
	public ModelAndView dashboard() {
		return new ModelAndView("sys/dashboard");
	}
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping(value = "getMenus")
	public Object getMenus() {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<Role> roles = currentUser.getRoles();
		Set<Long> menuIdSet = new HashSet<>();
		roles.forEach(role -> menuIdSet.addAll(role.getMenus().stream().map(Menu::getId).collect(Collectors.toSet())));
		List<Menu> tree = new ArrayList<>();
		List<Menu> menus = menuRepository.findAll();
		Map<Long, Menu> map = new HashMap<>();
		for (Menu m : menus) {
			if (menuIdSet.contains(m.getId())) {
				map.put(m.getId(), m);
			}
		}
		for (Menu m : menus) {
			if (!menuIdSet.contains(m.getId())) {
				continue;
			}
			Menu parent = m.getParent();
			if (parent != null) {
				parent = map.get(parent.getId());
			}
			if (parent == null) {
				tree.add(m);
			} else {
				parent.addChild(m);
			}
			m.setParent(null);
		}
		return tree;
	}

}
