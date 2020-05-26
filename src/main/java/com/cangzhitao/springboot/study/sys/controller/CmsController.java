package com.cangzhitao.springboot.study.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping(value = "/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@GetMapping(value = "/getCategoryTree")
	public Object getCategoryTree() {
		return categoryService.getTree();
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
	
}
