package com.cangzhitao.springboot.study.security.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;

@Entity
@Table(name = "security_user")
public class User extends BaseEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258590542688739091L;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "security_user_role", joinColumns = @JoinColumn(name = "fk_user"), inverseJoinColumns = @JoinColumn(name = "fk_role"))
	private Set<Role> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auths = new HashSet<>();
		if (roles != null) {
			roles.forEach(role -> {
				auths.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
				Set<Perm> perms = role.getPerms();
				if (perms != null) {
					perms.forEach(perm -> auths.add(new SimpleGrantedAuthority(perm.getPerm())));
				}
			});
		}
		return auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
