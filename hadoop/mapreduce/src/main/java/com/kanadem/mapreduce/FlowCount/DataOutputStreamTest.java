package com.kanadem.mapreduce.FlowCount;


import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamTest {

  public static void main(String[] args) throws IOException {
    DataOutputStream dataOutputStream = new DataOutputStream(
        new FileOutputStream("E:/projects/hadoop/test/1.dat"));

    dataOutputStream.writeInt(1);
  }
}