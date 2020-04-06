package com.cangzhitao.springboot.study.cms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cangzhitao.springboot.study.cms.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	@Query("select p from Post p where p.title = :title or p.author = :author")
	public List<Post> queryByTitleOrAuthor(String title, String author); 
	
	@Query("select p from Post p where p.title = :title or p.author = :author")
	public Page<Post> queryByTitleOrAuthor(Pageable pageable, String title, String author); 
	
}
