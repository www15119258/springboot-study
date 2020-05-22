package com.cangzhitao.springboot.study.security.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;

@Entity
@Table(name = "security_role")
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3556393258280284642L;

	/**
	 * 角色名称（英文）
	 */
	private String rolename;

	/**
	 * 角色昵称（中文）
	 */
	private String nickname;

	/**
	 * 描述
	 */
	private String description;

	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "security_role_perm", joinColumns = @JoinColumn(name = "fk_role"), inverseJoinColumns = @JoinColumn(name = "fk_perm"))
	private Set<Perm> perms;
	
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "security_role_menu", joinColumns = @JoinColumn(name = "fk_role"), inverseJoinColumns = @JoinColumn(name = "fk_menu"))
	private Set<Menu> menus;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Perm> getPerms() {
		return perms;
	}

	public void setPerms(Set<Perm> perms) {
		this.perms = perms;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
}
