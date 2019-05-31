package com.kanadem.mapreduce.WordCount;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/*
* KEYIN:map task 读取到的数据的key类型，是一行的其实偏移量Long
* VALUEIN：是map task 读取到的数据的value的类型，是一行的内容String
*
* KEYOUT:是用户自定义的map方法要返回的结果key-value数据的key类型，在wordcount逻辑中，我们需要返回的是单词String
* VALUEOUT:是用户自定义的map方法要返回的结果key-value数据的value的类型，在wordcount逻辑中，我们需要返回的是整数Integer
*
* 但是在MapReduce中，map产生的数据需要传递给reduce，需要进行序列化和反序列化，而jdk中的序列化机制产生的数据量比较冗余，就会导致数据在
* MapReduce运行过程中传输效率地下过慢，所以，hadoop专门设计了自己的序列化机制，那么MapReduce中传输的数据类型就必须实现自己的序列化接口
*
* hadoop 为jdk中的常用基本类型Long String Integer Float等数据类型封装了自己的实现类，hadoop序列化接口的类型：LongWritable,Text,IntWritable
*
* */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    String line = value.toString();
    String[] words = line.split(" ");
    for(String word : words){
      context.write(new Text(word), new IntWritable(1));
    }
  }
}
