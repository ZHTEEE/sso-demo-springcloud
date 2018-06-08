package com.zhteee.service;

import com.zhteee.redis.JedisPoolUtil;
import com.zhteee.util.Encrypt;
import com.zhteee.util.JwtUtil;
import io.jsonwebtoken.Claims;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class ValidationService {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JedisPoolUtil jedisPoolUtil;
	
	@Autowired
	private Encrypt encrypt;
	
	private String JWT_SECRET = "6d3453583c7bdbeb7e31e67fd0d0562396eab830e69005f4d96535b8e26393a3";
	
	private Long ttlMillis = 1800000l;
	
	
	/**
	 *验证jwt是否通过验证且未被注销
	 */
	public boolean vali(String jwt){
		try {
			Claims claims = jwtUtil.parseJWT(jwt, JWT_SECRET);
			String sub =claims.get("sub").toString();
			JSONObject json = JSONObject.fromObject(sub);
			String key = json.get("name").toString()+json.get("id").toString();
		if(jedisPoolUtil.getString(key)!=null){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 *刷新JWT
	 */
	public String refreshJwt(String jwt){
		String newJwt = null;
		String [] jwtArray = jwt.split("\\.");
		String payload = encrypt.deBASE64(jwtArray[1]);
		
		JSONObject json = JSONObject.fromObject(payload);
		JSONObject sub = JSONObject.fromObject(json.get("sub"));
		@SuppressWarnings("unchecked")
		Map<String,Object> mapp = (Map<String,Object>)sub;
		try {
			newJwt = jwtUtil.createJWT(mapp, ttlMillis, JWT_SECRET);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newJwt;	
	}
	/**
	 *生成新JWT
	 */
	public String newJwt(Map<String,Object> map){
		String newJwt = null;
		try {
			newJwt = jwtUtil.createJWT(map,ttlMillis, JWT_SECRET);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newJwt;	
	}
	/**
	 *注销jwt，实际为向redis中写入jwt对应key
	 */
	public boolean killJwt(String jwt){
		try {
			Claims claims = jwtUtil.parseJWT(jwt, JWT_SECRET);
			String sub =claims.get("sub").toString();
			JSONObject json = JSONObject.fromObject(sub);
			String key = json.get("name").toString()+json.get("id").toString();
			jedisPoolUtil.setString(key, Integer.valueOf(String.valueOf(ttlMillis))/1000 , "out");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			return true;
	}
	/**
	 *解除注销jwt，实际为向redis中移除jwt对应key
	 */
	public boolean removeRedis(Map<String, Object> getJson){
		try {
			String key =getJson.get("name").toString()+getJson.get("id").toString();
			jedisPoolUtil.removeJwt(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
