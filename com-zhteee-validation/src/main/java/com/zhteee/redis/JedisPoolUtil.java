package com.zhteee.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class JedisPoolUtil extends CachingConfigurerSupport{
	
	
    static Logger logger = LoggerFactory.getLogger(JedisPoolUtil.class);
    
    private static JedisPool jedisPool;
    
    private String host = "localhost";

    private int port = 6379;

    private int timeout = 1000;

    private int maxIdle = 100;

    private long maxWaitMillis  = -1;

    private String password = "redismaster";
    
    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(true);//在获取连接的时候检查有效性, 默认false
        jedisPoolConfig.setMinIdle(8);//设置最小空闲数
        jedisPoolConfig.setTestOnReturn(true);
        //Idle时进行连接扫描
        jedisPoolConfig.setTestWhileIdle(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);

        this.jedisPool = jedisPool;
        System.out.println("JedisPool建立！");
        return jedisPool;
    }
    
	/**获得jedis对象*/  
	public synchronized   Jedis getJedis(){ 
		return jedisPool.getResource();  
	}
  
  
	/**回收jedis对象*/  
	public synchronized  void closeJedis(Jedis jedis){  
		jedis.close();
	} 
	
	
	//写入
	public  void setString(String key,Integer time,String value ){
		Jedis jedis = getJedis();
		try {
				jedis.setex(key, time, value);
			} catch (Exception e) {
				closeJedis(jedis);
			}finally{
				closeJedis(jedis);
			}	
	}
	
	//读取
	public  String getString(String key){
		String str = null;
		Jedis jedis = getJedis();
		try {
				str = jedis.get(key);
				return str;
			} catch (Exception e) {
				
			}finally{
				closeJedis(jedis);
			}
				return str;
		}
	//验证
	public  boolean valiDateToken (String token){
		boolean bool = false;
		if(getString(token)!=null){
			bool=false;
		}else{
			bool=true;
		}
		return bool;
	}
	
	//移除
	public void removeJwt(String jwt){
		Jedis jedis = getJedis();
		try {
			jedis.del(jwt);
		} catch (Exception e) {
			closeJedis(jedis);
		}finally{
			closeJedis(jedis);
		}
		
	}
}
