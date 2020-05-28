package com.cangzhitao.springboot.study.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;

public abstract class BaseTreeService<T extends BaseTreeEntity<T>> extends BaseService<T> implements IBaseTreeService<T> {

	@Override
	public List<T> getTree() {
		List<T> entities = getRepository().findAll();
		return listToTree(entities);
	}

	@Override
	public List<T> getTree(T entity) {
		List<T> entities = this.findAll(entity);
		return listToTree(entities);
	}

	@Override
	public List<T> getSubTree(Long id) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
		Root<T> root = criteriaQuery.from(this.entityClass);
		Predicate predicate = null;
		if (id != null) {
			predicate = criteriaBuilder.equal(root.get("parent").get("id"), id);
		} else {
			predicate = criteriaBuilder.isNull(root.get("parent"));
		}
		criteriaQuery.select(root).where(predicate);
		TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<T> getSubTree(Long id, boolean recursion) {
		if (!recursion) {
			return this.getSubTree(id);
		}
		List<T> result = new ArrayList<>();
		List<T> list = this.getSubTree(id);
		if (list != null && !list.isEmpty()) {
			result.addAll(list);
		}
		for (T t : list) {
			List<T> children = this.getSubTree(t.getId(), true);
			if (children != null && !children.isEmpty()) {
				result.addAll(children);
			}
		}
		return result;
	}
	
	private List<T> listToTree(List<T> list) {
		List<T> tree = new ArrayList<>();
		Map<Long, T> map = new HashMap<>();
		for (T t : list) {
			map.put(t.getId(), t);
		}
		for (T t : list) {
			T parent = t.getParent();
			if (parent != null) {
				parent = map.get(parent.getId());
			}
			if (parent == null) {
				tree.add(t);
			} else {
				parent.addChild(t);
			}
			t.setParent(null);
		}
		return tree;
	}
}
