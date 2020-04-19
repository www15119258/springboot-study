package com.cangzhitao.springboot.study.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "security")
public class SecurityController {
	
	@GetMapping(value = "hasPerm")
	public Object hasPerm(String perm) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.isAuthenticated()) {
			return false;
		}
		SimpleGrantedAuthority p = new SimpleGrantedAuthority(perm);
		return authentication.getAuthorities().contains(p);
	}

}
