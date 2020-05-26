package com.cangzhitao.springboot.study.aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cangzhitao.springboot.study.security.entities.User;

@Component
public class DefaultAuditorAwareImpl implements AuditorAware<Long> {
	
	@Override
	public Optional<Long> getCurrentAuditor() {
		Long id = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.isAuthenticated()) {
				return Optional.empty();
			}
			User user = (User) authentication.getPrincipal();
			id = user.getId();
		} catch(Exception e) {
			return Optional.empty();
		}
		return Optional.ofNullable(id);
	}

}
