package com.cangzhitao.springboot.study.cms.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.cangzhitao.springboot.study.base.entities.BaseEntity;

@Entity
@Table(name = "cms_post")
public class Post extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1378460234126727999L;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 正文
	 */
	@Lob
	private String content;
	
	/**
	 * 摘要
	 */
	@Lob
	private String summary;
	
	/**
	 * 作者
	 */
	private String author;
	
	/**
	 * 发布日期
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "publish_date")
	private Date publishDate;
	
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "cms_post_category", joinColumns = @JoinColumn(name = "fk_post"), inverseJoinColumns = @JoinColumn(name = "fk_category"))
	private Set<Category> categorys;
	
	private Integer views = 0;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Set<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}

	public Integer getViews() {
		if (views == null) {
			return 0;
		}
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
	
}
