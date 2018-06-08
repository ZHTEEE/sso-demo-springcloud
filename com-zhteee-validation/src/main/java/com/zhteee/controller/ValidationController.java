package com.zhteee.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zhteee.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/vali")
public class ValidationController {
	
	@Autowired
	ValidationService validationService;
	
	/**
	 * 验证JWT
	 */
	@ResponseBody
    @RequestMapping(value="/vali",method=RequestMethod.POST)
	public String vali(@RequestParam String  jjwt) throws IOException{
		String jwt = null; 
		if(validationService.vali(jjwt)){
			jwt = validationService.refreshJwt(jjwt);
			return jwt;
		}else{
			return jwt;
		}
    }
	
	/**
	 *登陆,根据提供用户信息生成新的JWT
	 */
	@ResponseBody
	@RequestMapping(value="/logIn",method=RequestMethod.POST)
	public String logIn(@RequestParam Map<String,Object>  getJson) throws IOException{
		validationService.removeRedis(getJson);
		String jwt = validationService.newJwt(getJson);
		return jwt;
	}
	
	/**
	 * 注销JWT，验证通过判断拥有注销权限，然后向redis中写入jwt 的key
	 */
	@ResponseBody
	@RequestMapping(value="/logOut",method=RequestMethod.POST)
	public Map<String,Object> logOut(@RequestParam String  jwt) throws IOException{
		Map<String,Object> map = new HashMap<>();
		try{
			validationService.killJwt(jwt);
			map.put("code", 200);
		}catch(Exception e){
			map.put("code", 300);
		}
		return map;
	}
}
