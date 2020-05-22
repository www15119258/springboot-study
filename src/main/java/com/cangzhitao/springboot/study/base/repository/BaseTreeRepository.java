package com.cangzhitao.springboot.study.base.repository;

import com.cangzhitao.springboot.study.base.entities.BaseTreeEntity;

public interface BaseTreeRepository<T extends BaseTreeEntity<T>> extends BaseRepository<T> {

}
