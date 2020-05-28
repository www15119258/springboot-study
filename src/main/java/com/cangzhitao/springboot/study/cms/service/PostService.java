package com.cangzhitao.springboot.study.cms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cangzhitao.springboot.study.base.repository.BaseRepository;
import com.cangzhitao.springboot.study.base.service.BaseService;
import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.repository.PostRepository;

@Service
public class PostService extends BaseService<Post> implements IPostService {
	
	@Autowired
	private PostRepository repository;
	
	@Autowired
	@Lazy
	private ICategoryService categoryService;

	@Override
	public BaseRepository<Post> getRepository() {
		return repository;
	}

	@Override
	public Page<Post> findByAuthorAndTitle(String author, String title, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
		Root<Post> root = criteriaQuery.from(this.entityClass);
		Predicate predicate = null;
		List<Predicate> predicateList = new ArrayList<>();
		if (!StringUtils.isEmpty(author)) {
			predicateList.add(criteriaBuilder.like(root.get("author"), "%" + author + "%"));
		}
		if (!StringUtils.isEmpty(title)) {
			predicateList.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
		}
		if (!predicateList.isEmpty()) {
			predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[] { }));
		}
		criteriaQuery.select(root);
		if (predicate != null) {
			criteriaQuery.where(predicate);
		}
		Iterator<Order> it = pageable.getSort().iterator();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
		while(it.hasNext()) {
			Order order = it.next();
			javax.persistence.criteria.Order o = null;
			if (order.isAscending()) {
				o = criteriaBuilder.asc(root.get(order.getProperty()));
			} else {
				o = criteriaBuilder.desc(root.get(order.getProperty()));
			}
			orderList.add(o);
		}
		if (!orderList.isEmpty()) {
			criteriaQuery.orderBy(orderList);
		}
		TypedQuery<Post> typedQuery = this.entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<Post> list = typedQuery.getResultList();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Post> countRoot = countQuery.from(this.entityClass);
		countQuery.select(criteriaBuilder.count(countRoot));
		if (predicate != null) {
			countQuery.where(predicate);
		}
		Long count = this.entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<Post> findByCategory(Long categoryId, Pageable pageable) {
		List<Category> categorys = categoryService.getSubTree(categoryId, true);
		List<Long> idList = new ArrayList<>();
		categorys.forEach(category -> idList.add(category.getId()));
		idList.add(categoryId);
		String hql = "select distinct p from Post p join p.categorys c where c.id in ?0";
		String countHql = "select count(distinct p) from Post p join p.categorys c where c.id in ?0";
		return findAll(hql, new Object[] { idList }, countHql, new Object[] { idList }, pageable);
	}

}
