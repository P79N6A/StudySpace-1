package com.kanadem.mapreduce.FlowCount;

import java.util.HashMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class UserDefinedPartitioner extends Partitioner<Text, FlowBean> {
  static HashMap<String, Integer> codeMap = new HashMap<>();
  static{
    codeMap.put("135", 0);
    codeMap.put("136", 1);
    codeMap.put("137", 2);
    codeMap.put("138", 3);

  }
  @Override
  public int getPartition(Text text, FlowBean flowBean, int i) {
    Integer code = codeMap.get(text.toString().substring(0, 3));

    return code == null?4:code;
  }
}
