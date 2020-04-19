package com.cangzhitao.springboot.study.cms.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.repository.PostRepository;

@RestController
@RequestMapping(value = "cms/post")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PreAuthorize("hasAuthority('jbf:cms:post:save')")
	@PostMapping(value = "save")
	public Object save(@RequestBody Post post) {
		post.setPublishDate(new Date());
		postRepository.save(post);
		return post;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "get/{id}")
	public Object get(@PathVariable Long id) {
		return postRepository.findById(id);
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:edit')")
	@PutMapping(value = "update")
	public Object update(@RequestBody Post post) {
		if (post.getId() == null) {
			return null;
		}
		Optional<Post> optional = postRepository.findById(post.getId());
		if (!optional.isPresent()) {
			return null;
		}
		Post old = optional.get();
		old.setTitle(post.getTitle());
		old.setContent(post.getContent());
		old.setSummary(post.getSummary());
		old.setAuthor(post.getAuthor());
		old.setPublishDate(post.getPublishDate());
		postRepository.save(old);
		return old;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:delete')")
	@DeleteMapping(value = "delete/{id}")
	public Object delete(@PathVariable Long id) {
		try {
			postRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("cms/post/list");
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "findAll")
	public Object findAll() {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(0, 10, sort);
		return postRepository.findAll(pageable);
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@GetMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(page, size, sort);
		return postRepository.findAll(pageable);
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:list')")
	@PostMapping(value = "findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size, 
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String title) {
		Sort sort = Sort.by(Direction.DESC, "publishDate");
		Pageable pageable = PageRequest.of(page, size, sort);
		Post post = new Post();
		ExampleMatcher exampleMatcher = ExampleMatcher.matching(); 
		if (author != null && !"".equals(author)) {
			post.setAuthor(author);
			exampleMatcher = exampleMatcher.withMatcher("author", 
					GenericPropertyMatcher.of(StringMatcher.CONTAINING, false));
		}
		if (title != null && !"".equals(title)) {
			post.setTitle(title);
			exampleMatcher = exampleMatcher.withMatcher("title", 
					GenericPropertyMatcher.of(StringMatcher.CONTAINING, false));
		}
		Example<Post> example = Example.of(post, exampleMatcher);
		return postRepository.findAll(example, pageable);
	}

	@PreAuthorize("hasAuthority('jbf:cms:post:save')")
	@GetMapping(value = "add")
	public ModelAndView add() {
		return new ModelAndView("cms/post/add");
	}
	
	@PreAuthorize("hasAuthority('jbf:cms:post:edit')")
	@GetMapping(value = "edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		return new ModelAndView("cms/post/edit");
	}
	
	@GetMapping(value = "testQuery1")
	public Object testQuery1() {
		String title = "测试";
		String author = "苍之涛";
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root).where(criteriaBuilder.or(criteriaBuilder.like(root.get("title"), title), criteriaBuilder.like(root.get("author"), author)));
		TypedQuery<Post> typedQuery = this.entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	@GetMapping(value = "testQuery2")
	public Object testQuery2() {
		String title = "测试";
		String author = "苍之涛";
		Pageable pageable = PageRequest.of(0, 2);
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root).where(criteriaBuilder.or(criteriaBuilder.like(root.get("title"), title), criteriaBuilder.like(root.get("author"), author)));
		TypedQuery<Post> typedQuery = this.entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		return typedQuery.getResultList();
	}
	
	@GetMapping(value = "testQuery3")
	public Object testQuery3() {
		String title = "测试";
		String author = "苍之涛";
		Pageable pageable = PageRequest.of(0, 2);
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(root.get("title"), title), criteriaBuilder.like(root.get("author"), author));
		
		criteriaQuery.select(root).where(predicate);
		TypedQuery<Post> typedQuery = this.entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Post> countRoot = countQuery.from(Post.class);
		countQuery.select(criteriaBuilder.count(countRoot));
		countQuery.where(predicate);
		List<Post> list = typedQuery.getResultList();
		Long count = this.entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(list, pageable, count);
	}
	
	@GetMapping(value = "testQuery4")
	public Object testQuery4() {
		String title = "测试";
		String author = "苍之涛";
		Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("title"), Order.desc("publishDate")));
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> root = criteriaQuery.from(Post.class);
		
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(root.get("title"), title), criteriaBuilder.like(root.get("author"), author));
		
		criteriaQuery.select(root).where(predicate);
		
		List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
		Iterator<Order> orderIt = pageable.getSort().iterator();
		while(orderIt.hasNext()) {
			Order order = orderIt.next();
			if (order.isAscending()) {
				orderList.add(criteriaBuilder.asc(root.get(order.getProperty())));
			} else {
				orderList.add(criteriaBuilder.desc(root.get(order.getProperty())));
			}
		}
		criteriaQuery.orderBy(orderList);
		
		TypedQuery<Post> typedQuery = this.entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Post> countRoot = countQuery.from(Post.class);
		countQuery.select(criteriaBuilder.count(countRoot));
		countQuery.where(predicate);
		List<Post> list = typedQuery.getResultList();
		Long count = this.entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(list, pageable, count);
	}
	
	@GetMapping(value = "testQuery5")
	public Object testQuery5() {
		String title = "测试";
		String author = "苍之涛";
		return postRepository.queryByTitleOrAuthor(title, author);
	}
	
	@GetMapping(value = "testQuery6")
	public Object testQuery6() {
		String title = "测试";
		String author = "苍之涛";
		Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("title"), Order.desc("publishDate")));
		return postRepository.queryByTitleOrAuthor(pageable, title, author);
	}
	
	@GetMapping(value = "testQuery7")
	public Object testQuery7() {
		String title = "测试";
		String author = "苍之涛";
		Query query = this.entityManager.createQuery("select p from Post p where p.title = :title or p.author = :author");
		query.setParameter("title", title);
		query.setParameter("author", author);
		return query.getResultList();
	}
	
	@GetMapping(value = "testQuery8")
	public Object testQuery8() {
		String title = "测试";
		String author = "苍之涛";
		Pageable pageable = PageRequest.of(0, 2, Sort.by(Order.asc("title"), Order.desc("publish_date")));
		return postRepository.queryByTitleOrAuthorNative(pageable, title, author);
	}

	@GetMapping(value = "testQuery9")
	public Object testQuery9() {
		String title = "测试";
		String author = "苍之涛";
		Query query = this.entityManager.createNativeQuery("select p.* from cms_post p where p.title = :title or p.author = :author");
		query.setParameter("title", title);
		query.setParameter("author", author);
		return query.getResultList();
	}
	
	@GetMapping(value = "testQuery10")
	public Object testQuery10() {
		String title = "测试";
		String author = "苍之涛";
		String sql = "select p.* from cms_post p where p.title = ? or p.author = ?";
		return this.jdbcTemplate.query(sql, new Object[] { title, author }, new RowMapper<Post>() {
			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				post.setAuthor(rs.getString("author"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getLong("id"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setSummary(rs.getString("summary"));
				post.setTitle(rs.getString("title"));
				return post;
			}
		});
	}
	
	@GetMapping(value = "testQuery11")
	public Object testQuery11() {
		String title = "测试";
		String author = "苍之涛";
		String sql = "select p.* from cms_post p where p.title = ? or p.author = ?";
		return this.jdbcTemplate.query(sql, new Object[] { title, author }, new BeanPropertyRowMapper<Post>(Post.class));
	}
	
	
}
