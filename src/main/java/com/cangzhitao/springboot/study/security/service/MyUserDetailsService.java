package com.cangzhitao.springboot.study.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("username", 
				GenericPropertyMatcher.of(StringMatcher.EXACT, false));
		Example<User> example = Example.of(user, exampleMatcher);
		Optional<User> optional = userRepository.findOne(example);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return optional.get();
	}

}
