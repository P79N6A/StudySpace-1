package com.kanadem.zookeeper;

public class DaemonTest {

  /**
   * 守护线程展示 问题出现：既然zookeeper已经启动了一个后台线程，为什么前台线程需要sleep才能收到消息
   */
  public static void main(String[] args) {
    System.out.println("启动主线程！");
    System.out.println("准备启动子线程！");
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("子线程开始执行");
        while (true) {
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("子线程正在打印！");
        }
      }
    });
    /**
     * setDaemon(true) 给线程设置属性，子线程是主线程的守护线程
     * 所以当主线程结束的时候子线程认为自己也没有必要了
     */
    thread.setDaemon(true);
    thread.start();
    System.out.println("主线程创建子线程完成！");
  }
  /**
   * 打印结果：
   * 启动主线程！
   * 准备启动子线程！
   * 主线程创建子线程完成！
   */

}
