package com.zhteee.demo.interceptor;

import com.zhteee.demo.validation.ValidationService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ValidationService validationService;

/**
 *拦截器
 *用来判断是否存在token,token是否合法
 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		boolean flag = true;
		 String jwt = request.getHeader("jwt");
		 try {
			 String newJwt = validationService.vali(jwt);
			if(jwt!=null&&newJwt!=null){
				response.setHeader("jwt", newJwt);
			}else{
				unPass(response);
			 	flag=false;	 
				 }
		 	} catch (Exception e) {
		 		e.printStackTrace();
			 	unPass(response);
				flag=false;
		 }
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {

		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {

		
	}
	/**
	 *添加拦截返回信息
	 */
	private void unPass(HttpServletResponse response) throws IOException {
		Map<String,Object> map = new HashMap<>();
		map.put("code", 999);
		map.put("msg", "jwt验证不通过");
		JSONObject json = JSONObject.fromObject(map);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.append(json.toString());
	}

}
