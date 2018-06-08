package com.zhteee.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/demo")
public class DemoController {


	//测试访问
	@RequestMapping("/")
	@ResponseBody
	public String index (){
		return "Hello World !";
	}

}