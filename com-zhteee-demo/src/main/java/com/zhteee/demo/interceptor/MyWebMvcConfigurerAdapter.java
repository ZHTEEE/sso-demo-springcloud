package com.zhteee.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

/**
 * 拦截器配置
 *
 */

	@Bean
	MyInterceptor myInterceptor(){
		return new MyInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		//addPathPatterns 添加拦截规则
		//excludePathPatterns 排除拦截
		registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
		
	}
}
