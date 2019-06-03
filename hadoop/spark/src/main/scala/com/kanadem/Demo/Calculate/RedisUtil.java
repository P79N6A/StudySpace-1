package com.kanadem.Demo.Calculate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author KanadeM 2019/4/9
 */

public class RedisUtil {

  private static JedisPool jedisPool = null;

  static {
    JedisPoolConfig conf = new JedisPoolConfig();
    conf.setMaxTotal(1000);
    conf.setMaxIdle(100);
    conf.setMaxWaitMillis(10000);
    conf.setTestOnBorrow(true);
    jedisPool = new JedisPool(conf, "192.168.1.101", 6379);
  }

  public synchronized static Jedis getJedis() {
    if (jedisPool != null) {
      Jedis resource = jedisPool.getResource();
      return resource;
    } else {
      return null;
    }
  }

}
