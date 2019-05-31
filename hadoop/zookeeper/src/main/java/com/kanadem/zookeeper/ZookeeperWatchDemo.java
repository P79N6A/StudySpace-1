package com.kanadem.zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class ZookeeperWatchDemo {

  ZooKeeper zooKeeper = null;

  @Before
  public void init() throws IOException {
    zooKeeper = new ZooKeeper("centos1", 2000, new Watcher() {
      @Override
      public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected&&watchedEvent.getType() == EventType.NodeDataChanged) {
          System.out.println(watchedEvent.getPath());
          System.out.println(watchedEvent.getType());
          System.out.println("数据更新成功！");
          try {
            zooKeeper.getData("/test", true, null);
          } catch (KeeperException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  /**
   * Zookeeper只监听一次，如果要监听多次可以再上面创建客户端连接的时候启动watcher
   */
  @Test
  public void GetWatchTest()
      throws KeeperException, InterruptedException, UnsupportedEncodingException {
    byte[] data = zooKeeper.getData("/test", true, null);
    System.out.println(new String(data, "UTF-8"));

    Thread.sleep(Long.MAX_VALUE);
  }
}
