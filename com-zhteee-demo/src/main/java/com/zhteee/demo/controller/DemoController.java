package com.zhteee.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/demo")
public class DemoController {


	@RequestMapping("/")
	@ResponseBody
	public String index (){
		return "Hello World !";
	}

}