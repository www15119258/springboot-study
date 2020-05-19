package com.cangzhitao.springboot.study.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.security.entities.Menu;
import com.cangzhitao.springboot.study.security.repository.MenuRepository;

@RestController
@RequestMapping(value = "security/menu")
public class MenuController {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@PreAuthorize("hasAuthority('jbf:security:menu:save')")
	@PostMapping(value = "save")
	public Object save(@RequestBody Menu menu) {
		menuRepository.save(menu);
		return menu;
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:list')")
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return menuRepository.findById(id);
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:edit')")
	@PutMapping(value = "update")
	public Object update(@RequestBody Menu menu) {
		if (menu.getId() == null) {
			return null;
		}
		Optional<Menu> optional = menuRepository.findById(menu.getId());
		if (!optional.isPresent()) {
			return null;
		}
		Menu old = optional.get();
		old.setName(menu.getName());
		old.setUrl(menu.getUrl());
		menuRepository.save(old);
		return old;
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:delete')")
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void deleteById(Long id) {
		Query query = this.entityManager.createQuery("select m from Menu m where m.parent.id = :parentid");
		query.setParameter("parentid", id);
		List<Menu> children = query.getResultList();
		if (!children.isEmpty()) {
			for (Menu m : children) {
				deleteById(m.getId());
			}
		}
		menuRepository.deleteById(id);
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:list')")
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("security/menu/list");
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:list')")
	@GetMapping(value = "getTree")
	public Object getTree() {
		List<Menu> tree = new ArrayList<>();
		List<Menu> menus = menuRepository.findAll();
		Map<Long, Menu> map = new HashMap<>();
		for (Menu m : menus) {
			map.put(m.getId(), m);
		}
		for (Menu m : menus) {
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
	
	@PreAuthorize("hasAuthority('jbf:security:menu:save')")
	@GetMapping(value = "add")
	public ModelAndView add() {
		return new ModelAndView("security/menu/add");
	}
	
	@PreAuthorize("hasAuthority('jbf:security:menu:edit')")
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		return new ModelAndView("security/menu/edit");
	}
	
}
