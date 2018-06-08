package com.zhteee.module.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhteee.module.entity.Admin;
import com.zhteee.module.service.AdminService;
import com.zhteee.validation.ValidationService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/sso")
public class AdminController {


	@Autowired
	private AdminService adminService;

	@Autowired
	private ValidationService validationService;
	
	//登录
	@ResponseBody
    @RequestMapping(value="/login",method=RequestMethod.POST)
	public Map<String,Object> login(@RequestParam Map<String,Object>  getJson) throws IOException{
		Map<String,Object> map = new HashMap<>();
		try {
		JSONObject jsonObject = JSONObject.fromObject(getJson);
		Admin admin = (Admin)JSONObject.toBean(jsonObject, Admin.class);
		Map<String,Object>rest = adminService.login(admin);
		String jwt = validationService.signin(rest);
		if(jwt!=null){
			map.put("code","200");
			map.put("jwt",jwt);
		}else{
			map.put("code","300");
			map.put("jwt",null);
		}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "300");
			map.put("jwt",null);
		}
      return map;
    }
	
	//登出
	@ResponseBody
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public Map<String,Object> logOut(HttpServletRequest request ){
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> rest = validationService.logOut(request.getHeader("jwt"));
		try {
			if(Integer.valueOf(rest.get("code").toString())==200){
				map.put("code", "200");
			}else{
				map.put("code", "300");
			}
		} catch (Exception e) {
			map.put("code", "300");
		}
		return map;
	}

}