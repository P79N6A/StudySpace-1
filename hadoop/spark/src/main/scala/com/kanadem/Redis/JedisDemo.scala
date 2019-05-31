package com.kanadem.Redis

import java.util

import redis.clients.jedis.{HostAndPort, JedisCluster, JedisPoolConfig}

/**
  * @author KanadeM 2019/4/6
  */

object JedisDemo {
  private val hostAndPortsSet = new util.HashSet[HostAndPort]()
  hostAndPortsSet.add(new HostAndPort("192.168.0.114", 6379))
  hostAndPortsSet.add(new HostAndPort("192.168.0.113", 6379))
  hostAndPortsSet.add(new HostAndPort("192.168.0.113", 6380))
  // Jedis连接池配置
  val jedisPoolConfig = new JedisPoolConfig
  // 最大空闲连接数, 默认8个
  jedisPoolConfig.setMaxIdle(100)
  // 最大连接数, 默认8个
  jedisPoolConfig.setMaxTotal(500)
  //最小空闲连接数, 默认0
  jedisPoolConfig.setMinIdle(0)
  // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
  jedisPoolConfig.setMaxWaitMillis(2000) // 设置2秒

  //对拿到的connection进行validateObject校验
  jedisPoolConfig.setTestOnBorrow(true)
  val jedis = new JedisCluster(hostAndPortsSet, jedisPoolConfig)
  def main(args: Array[String]): Unit = {
    print(jedis.get("10"))
    jedis.close()
  }
}
