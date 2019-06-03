package com.kanadem.mapreduce.FlowCount;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * l利用TreeMap进行排序
 */
public class TreeMapTest {

  public static void main(String[] args) {
    TreeMap<FlowBean, String> tm1 = new TreeMap<>(new Comparator<FlowBean>() {
      @Override
      public int compare(FlowBean o1, FlowBean o2) {
        if(o2.getAmountFlow() == o1.getAmountFlow()){
          return o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
        }
        return o2.getAmountFlow() - o1.getAmountFlow();
      }
    });


    FlowBean fb1 = new FlowBean(500, 300, "0123456");
    FlowBean fb2 = new FlowBean(400, 300, "1234567");
    FlowBean fb3 = new FlowBean(300, 300, "2345678");
    FlowBean fb4 = new FlowBean(200, 300, "3456789");
    FlowBean fb5 = new FlowBean(100, 300, "4567890");

    tm1.put(fb1, null);
    tm1.put(fb2, null);
    tm1.put(fb3, null);
    tm1.put(fb4, null);
    tm1.put(fb5, null);
    Set<Entry<FlowBean, String>> entries = tm1.entrySet();
    for(Entry<FlowBean, String> entry : entries){
      System.out.println(entry);
    }


  }

}
