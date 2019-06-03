package com.kanadem.zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Version;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZooKeeperDemo {

  ZooKeeper zooKeeper = null;

  @Before
  public void init() throws IOException {
    zooKeeper = new ZooKeeper("centos1", 2000, null);
  }

  @Test
  public void CreateTest() throws IOException, KeeperException, InterruptedException {
    //连接zookeeper
    String s = zooKeeper
        .create("/test", "HelloWorld".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    System.out.println(s);

    zooKeeper.close();
  }

  /**
   * 修改数据，-1表示修改所有版本
   */
  @Test
  public void UpdateTest() throws KeeperException, InterruptedException {
    Stat test = zooKeeper.setData("/test", "HelloWolrd1".getBytes(), -1);
    System.out.println(test);
    zooKeeper.close();
  }

  @Test
  public void GetTest() throws KeeperException, InterruptedException, UnsupportedEncodingException {
    byte[] data = zooKeeper.getData("/test", false, null);
    System.out.println(new String(data, "UTF-8"));
    zooKeeper.close();
  }

  @Test
  public void ListChildTest() throws KeeperException, InterruptedException {
    List<String> children = zooKeeper.getChildren("/test", false);
    for (String child : children
    ) {
      System.out.println(child);
    }
    zooKeeper.close();
  }

  @Test
  public void DeleteTest() throws KeeperException, InterruptedException {
    zooKeeper.delete("/test", -1);

    zooKeeper.close();
  }

}
