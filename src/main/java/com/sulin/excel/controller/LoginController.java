package com.sulin.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/hello")
	public String hello(){
		System.out.println("接收到请求 ，Hello");
		return "add";
	}
	
	@RequestMapping(value = "/login")
	public String login(String name, String password){
		
		return null;
	}
}