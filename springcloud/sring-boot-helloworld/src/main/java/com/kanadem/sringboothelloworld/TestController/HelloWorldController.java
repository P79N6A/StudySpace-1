package com.kanadem.sringboothelloworld.TestController;

import com.kanadem.sringboothelloworld.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kanadem.sringboothelloworld.Redis.RedisCluster;
import redis.clients.jedis.JedisCluster;

@RestController
public class HelloWorldController {

    @Autowired
    private PersonpRroperties personpRroperties;
    @Value("${hello.msg}")
    private String msg;

    @Value("${name}")
    private String name;

    @Value("${content}")
    private String content;

    @RequestMapping(value = "/redistest", method = RequestMethod.GET)
    public String RedisTest() {
        RedisCluster jedis = new RedisCluster();
        return "CEB511Message报文数量： " + jedis.getValue();
    }


    @RequestMapping(value = "/hellotest", method = RequestMethod.GET)
    public String hellotest() {
        return msg;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String say(@RequestParam(value = "id", defaultValue = "0", required = false) Integer myId) {
        throw new BusinessException("100", "用户名密码错误！");
//        return "My id is " + myId;
    }
}
