package com.cangzhitao.springboot.study.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.sys.vo.ModelAndResult;

@RestController
@RequestMapping(value = "/")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping(value = "login.html")
	public ModelAndView index() {
		return new ModelAndView("login");
	}
	
	@PostMapping(value = "login/process")
	public Object login(String username, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(token);
		} catch (AuthenticationException e) {
			return new ModelAndResult(false, e.getMessage());
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		user.setPassword(null);
		return new ModelAndResult(user);
	}
	
	@GetMapping(value = "logout")
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
	}

}
