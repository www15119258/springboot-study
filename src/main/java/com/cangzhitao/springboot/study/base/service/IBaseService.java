package com.cangzhitao.springboot.study.base.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;
import com.cangzhitao.springboot.study.base.repository.BaseRepository;

public interface IBaseService<T extends BaseEntity> {

	public BaseRepository<T> getRepository();
	
	public T save(T entity);
	
	public List<T> save(Iterable<T> entities);
	
	public void delete(T entity);
	
	public void delete(Iterable<T> entities);
	
	public void deleteById(Long id);
	
	public void deleteByIds(Iterable<Long> ids);
	
	public T get(Long id);
	
	public List<T> findAll();
	
	public List<T> findAll(Sort sort);

	public Page<T> findAll(Pageable pageable);
	
	public List<T> findAll(T domain);

	public Page<T> findAll(T domain, Pageable pageable);

	public List<T> findAll(T domain, Sort sort);

	public T findOne(T domain);
	
	public List<T> findByIds(Iterable<Long> ids);
	
	public List<T> findAll(String hql, Object[] params);
	
	public Page<T> findAll(String hql, Object[] params1, String countHql, Object[] params2, Pageable pageable);
	
	public T findOne(String hql, Object[] params);
	
}
