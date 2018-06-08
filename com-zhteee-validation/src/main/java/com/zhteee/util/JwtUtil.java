package com.zhteee.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

	/**
     * jwt
     * 
     */

    //由字符串生成加密key
    public   SecretKey generalKey(String JWT_SECRET){
        String stringKey = JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    // 创建jwt
    public String createJWT(Map<String,Object> map, Long ttlMillis, String JWT_SECRET) throws Exception {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey(JWT_SECRET);
        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setSubject(generalSubject(map))
            .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    // 解密jwt
    public   Claims parseJWT(String jwt, String JWT_SECRET) throws Exception{
        SecretKey key = generalKey(JWT_SECRET);
        Claims claims = Jwts.parser()         
           .setSigningKey(key)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }

    // 生成subject信息
    public  String generalSubject(Map<String,Object> map){
        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }
}
