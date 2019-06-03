package com.kanade.kafka;

import java.util.Timer;

/**
 * @author KanadeM 2019/5/22
 */

public class KafkaSender {

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    System.out.println(startTime);
    for(int i = 0;i < 1000;i++){
      ProducerDemo producerDemo = new ProducerDemo();
      producerDemo.setDaemon(false);
      producerDemo.start();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - startTime);
  }
}
