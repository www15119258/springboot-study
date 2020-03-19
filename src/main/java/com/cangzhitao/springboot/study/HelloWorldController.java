package com.cangzhitao.springboot.study;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HelloWorldController {
	
	@GetMapping(value = "hello")
	public Object helloWorld() {
		return "Hello world";
	}

}
