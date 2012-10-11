package com.exttech.security.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("indexController")
public class IndexController {
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "index";
	}

}
