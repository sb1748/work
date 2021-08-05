package com.jxszj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
//	@RequestMapping("/")
//	public String showIndex(){
//		return "login";
//	}
	
	@RequestMapping("{page}")
	public String page(@PathVariable String page){
		return page;
	}
}
