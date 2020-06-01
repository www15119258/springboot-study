package com.cangzhitao.springboot.study.sys.timer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.service.ICategoryService;

@Component
public class CmsTimer {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Scheduled(cron = "0 * * * * ?")
	public void refreshCategoryTree() {
		List<Category> tree = categoryService.getTree();
		cacheManager.getCache("category").put("tree", tree);
	}

}
