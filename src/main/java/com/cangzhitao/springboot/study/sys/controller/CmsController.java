package com.cangzhitao.springboot.study.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.springboot.study.cms.entities.Category;
import com.cangzhitao.springboot.study.cms.entities.Post;
import com.cangzhitao.springboot.study.cms.service.ICategoryService;
import com.cangzhitao.springboot.study.cms.service.IPostService;
import com.cangzhitao.springboot.study.security.entities.User;
import com.cangzhitao.springboot.study.security.service.IUserService;

@RestController
@RequestMapping(value = "/")
public class CmsController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private CacheManager cacheManager;
	
	public CmsController() {
		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				List<Category> tree = categoryService.getTree();
				cacheManager.getCache("category").put("tree", tree);
			}
		}).start();
	}
	
	@GetMapping(value = "/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/getCategoryTree")
	public Object getCategoryTree() {
		List<Category> tree = null;
		tree = cacheManager.getCache("category").get("tree", List.class);
		if (tree != null) {
			return tree;
		}
		tree = categoryService.getTree();
		cacheManager.getCache("category").put("tree", tree);
		return tree;
	}
	
	@GetMapping(value = "/post/findPage/{page}/{size}")
	public Object findPage(@PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Post> p = postService.findAll(pageable);
		List<Long> idList = new ArrayList<>();
		p.getContent().forEach(post -> {
			if (post.getCreateBy() != null) {
				idList.add(post.getCreateBy());
			}
		});
		List<User> users = new ArrayList<>();
		if (!idList.isEmpty()) {
			users = userService.findByIds(idList);
		}
		Map<Long, String> usernameMap = users.stream().collect(Collectors.toMap(User::getId, User::getUsername));
		p.getContent().forEach(post -> {
			String username = usernameMap.get(post.getCreateBy());
			if (StringUtils.isEmpty(username)) {
				username = "佚名";
			}
			post.setAuthor(username);
		});
		return p;
	}
	
	@GetMapping(value = "/post/hots")
	public Object postHots() {
		Sort sort = Sort.by(Direction.DESC, "views");
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Post> p = postService.findAll(pageable);
		return p.getContent();
	}
	
	@GetMapping(value = "/category/{id}")
	public ModelAndView categoryIndex(@PathVariable Long id) {
		return new ModelAndView("categoryIndex");
	}
	
	@GetMapping(value = "/category/get/{id}")
	public Object categoryGetById(@PathVariable Long id) {
		return categoryService.get(id);
	}
	
	@GetMapping(value = "/post/findPageByCategory/{category}/{page}/{size}")
	public Object findPostPageByCategory(@PathVariable Long category, @PathVariable int page, @PathVariable int size) {
		Sort sort = Sort.by(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Post> p = postService.findByCategory(category, pageable);
		List<Long> idList = new ArrayList<>();
		p.getContent().forEach(post -> {
			if (post.getCreateBy() != null) {
				idList.add(post.getCreateBy());
			}
		});
		List<User> users = new ArrayList<>();
		if (!idList.isEmpty()) {
			users = userService.findByIds(idList);
		}
		Map<Long, String> usernameMap = users.stream().collect(Collectors.toMap(User::getId, User::getUsername));
		p.getContent().forEach(post -> {
			String username = usernameMap.get(post.getCreateBy());
			if (StringUtils.isEmpty(username)) {
				username = "佚名";
			}
			post.setAuthor(username);
		});
		return p;
	}
	
	@GetMapping(value = "/category/hots/{category}")
	public Object categoryHots(@PathVariable Long category) {
		Sort sort = Sort.by(Direction.DESC, "views");
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Post> p = postService.findByCategory(category, pageable);
		return p.getContent();
	}
	
	@GetMapping(value = "/post/{id}")
	public ModelAndView postIndex(@PathVariable Long id) {
		return new ModelAndView("postIndex");
	}
	
	@GetMapping(value = "/post/get/{id}")
	public Object postGetById(@PathVariable Long id) {
		return postService.get(id);
	}
	
	@GetMapping(value = "/post/recommend/{post}")
	public Object postRecommend(@PathVariable Long post) {
		Post spost = postService.get(post);
		if (spost == null) {
			return new ArrayList<>();
		}
		Set<Category> categorys = spost.getCategorys();
		if (categorys == null || categorys.isEmpty()) {
			Pageable pageable = PageRequest.of(0, 10);
			String hql = "select p from Post p order by rand()";
			String countHql = "select count(p) from Post p";
			Page<Post> p = postService.findAll(hql, new Object[] {}, countHql, new Object[] {}, pageable);
			return p.getContent();
		}
		Integer maxLevel = -1;
		Long maxCategoryId = null;
		for (Category category : categorys) {
			int level = 0;
			Category parent = null;
			Category temp = category;
			while((parent = temp.getParent()) != null) {
				level++;
				temp = parent;
			}
			if (level > maxLevel) {
				maxLevel = level;
				maxCategoryId = category.getId();
			}
		}
		return postService.findByCategoryRandom(maxCategoryId, 10);
	}
	
}
