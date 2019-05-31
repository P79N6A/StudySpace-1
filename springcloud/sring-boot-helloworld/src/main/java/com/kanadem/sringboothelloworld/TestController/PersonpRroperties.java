package com.kanadem.sringboothelloworld.TestController;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "person")
public class PersonpRroperties {
    private String name;
    private Integer age;

    public String getName(){
        return name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge(){
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
