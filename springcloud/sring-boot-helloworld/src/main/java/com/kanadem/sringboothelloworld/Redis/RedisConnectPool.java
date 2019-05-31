package com.kanadem.sringboothelloworld.Redis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author KanadeM 2019/4/8
 */

public class RedisConnectPool {

    private static  JedisPool jedisPool;
    static {
        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.1.101", 6379, 10000);

    }

    public RedisConnectPool() {
    }

    public String  getValue(){
        final Jedis jedis = jedisPool.getResource();
        System.out.println("Jedis here");
        System.out.println(jedis.get("CEB511Message"));
        return jedis.get("CEB511Message");
    }

}
