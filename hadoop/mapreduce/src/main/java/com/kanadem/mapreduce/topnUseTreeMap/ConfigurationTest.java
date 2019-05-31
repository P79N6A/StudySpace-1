package com.kanadem.mapreduce.topnUseTreeMap;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationTest {

  public static void main(String[] args) {
    Configuration conf = new Configuration();
    conf.addResource("mapred-site.xml");
    System.out.println(conf.get("top.n"));
  }

}
