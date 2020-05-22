package com.cangzhitao.springboot.study.cms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.cms.entities.Post;

@Repository
public interface PostRepository extends BaseRepository<Post>{

	@Query("select p from Post p where p.title = :title or p.author = :author")
	public List<Post> queryByTitleOrAuthor(String title, String author); 
	
	@Query("select p from Post p where p.title = :title or p.author = :author")
	public Page<Post> queryByTitleOrAuthor(Pageable pageable, String title, String author); 
	
	@Query(value = "select p.* from cms_post p where p.title = :title or p.author = :author",
			countQuery = "select count(1) from cms_post p where p.title = :title or p.author = :author", nativeQuery = true)
	public Page<Post> queryByTitleOrAuthorNative(Pageable pageable, String title, String author); 
	
}
