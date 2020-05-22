package com.cangzhitao.springboot.study.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cangzhitao.springboot.study.base.entities.BaseEntity;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
