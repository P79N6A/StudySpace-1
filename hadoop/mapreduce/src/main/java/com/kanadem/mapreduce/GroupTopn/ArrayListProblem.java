package com.kanadem.mapreduce.GroupTopn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArrayListProblem {

  public static void main(String[] args) throws IOException {
    ArrayList<OrderBean> beans = new ArrayList<>();
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:/test.dat", true));

    OrderBean bean = new OrderBean();
    bean.set("1", "u", "a", 1.0f, 2);
    //beans.add(bean);   这种方法是不对的，因为传入的是一个引用就是内存地址
    //可以使用序列化的方式将其放r入要实现序列化接口
    //oos.writeObject(bean);

  }

}
