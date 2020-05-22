package com.cangzhitao.springboot.study.base.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;

public abstract class BaseService<T extends BaseEntity> implements IBaseService<T> {
	
	protected Class<T> entityClass = null;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			return (Class<T>) types[0];
		}
		return (Class<T>) type;
	}
	
	public BaseService() {
		this.entityClass = getEntityClass();
	}

	@Transient
	@Override
	public T save(T entity) {
		return getRepository().save(entity);
	}

	@Transient
	@Override
	public List<T> save(Iterable<T> entities) {
		return getRepository().saveAll(entities);
	}

	@Transient
	@Override
	public void delete(T entity) {
		getRepository().delete(entity);
	}

	@Transient
	@Override
	public void delete(Iterable<T> entities) {
		getRepository().deleteAll(entities);
	}

	@Transient
	@Override
	public void deleteById(Long id) {
		getRepository().deleteById(id);
	}

	@Transient
	@Override
	public void deleteByIds(Iterable<Long> ids) {
		getRepository().deleteAll(findByIds(ids));
	}

	@Override
	public T get(Long id) {
		Optional<T> option = getRepository().findById(id);
		if (option.isPresent()) {
			return option.get();
		}
		return null;
	}

	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	public List<T> findAll(T domain) {
		return getRepository().findAll(Example.of(domain));
	}

	@Override
	public Page<T> findAll(T domain, Pageable pageable) {
		return getRepository().findAll(Example.of(domain), pageable);
	}

	@Override
	public List<T> findAll(T domain, Sort sort) {
		return getRepository().findAll(Example.of(domain), sort);
	}

	@Override
	public T findOne(T domain) {
		Optional<T> option = getRepository().findOne(Example.of(domain));
		if (option.isPresent()) {
			return option.get();
		}
		return null;
	}

	@Override
	public List<T> findByIds(Iterable<Long> ids) {
		return getRepository().findAllById(ids);
	}
	
	@Override
	public List<T> findAll(String hql, Object[] params) {
		TypedQuery<T> query = this.entityManager.createQuery(hql, this.entityClass);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.getResultList();
	}
	
	@Override
	public Page<T> findAll(String hql, Object[] params1, String countHql, Object[] params2, Pageable pageable) {
		TypedQuery<T> query = this.entityManager.createQuery(hql, this.entityClass);
		if (params1 != null) {
			for (int i = 0; i < params1.length; i++) {
				query.setParameter(i, params1[i]);
			}
		}
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		List<T> list = query.getResultList();
		TypedQuery<Long> countQuery = this.entityManager.createQuery(countHql, Long.class);
		if (params2 != null) {
			for (int i = 0; i < params2.length; i++) {
				countQuery.setParameter(i, params2[i]);
			}
		}
		Long count = countQuery.getSingleResult();
		return new PageImpl<>(list, pageable, count);
	}
	
	@Override
	public T findOne(String hql, Object[] params) {
		TypedQuery<T> query = this.entityManager.createQuery(hql, this.entityClass);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.getSingleResult();
	}

}
