package com.zhteee.demo.validation;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@FeignClient(name= "validation")
public interface ValidationService {

	 @RequestMapping(value = "/vali/logOut",method = RequestMethod.POST)
	 Map<String,Object> logOut(@RequestParam(value = "jwt") String jwt);

	 @RequestMapping(value = "/vali/vali",method = RequestMethod.POST)
	 String vali(@RequestParam(value = "jjwt") String jwt);


}
