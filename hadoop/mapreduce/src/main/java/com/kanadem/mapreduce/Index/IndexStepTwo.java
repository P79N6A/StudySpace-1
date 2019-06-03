package com.kanadem.mapreduce.Index;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 根据文件内容形成 hello  a.txt-->4  b.txt-->4  c.txt-->4 java   c.txt-->1 jerry  b.txt-->1  c.txt-->1
 * 编程原理：maptask在启动之前就注定了会处理哪些数据这部分的内容放在context中
 */
public class IndexStepTwo {

  public static class IndexStepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String[] words = value.toString().split("-");
      context.write(new Text(words[0]), new Text(words[1].replaceAll("\t", "-->")));
    }
  }

  public static class IndexStepTwoReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {
      //StringBuffer是线程安全的，StringBuilder是非线程安全的
      StringBuffer sb = new StringBuffer();
      for(Text value : values){
        sb.append(value.toString()).append("\t");
      }
      context.write(key, new Text(sb.toString()));
    }
  }

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    Configuration conf = new Configuration();

    Job job = Job.getInstance(conf);

    job.setJarByClass(IndexStepTwo.class);
    job.setMapperClass(IndexStepTwoMapper.class);
    job.setReducerClass(IndexStepTwoReducer.class);
    /**
     * 设置参数，MapTask在数据分区的时候用哪个分区器
     */
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    File file = new File(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\output2\\");
    if (file.exists()) {
      FileUtils.deleteDirectory(file);
    }
    Path outputpath = new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\output2\\");
    FileInputFormat.setInputPaths(job, new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\output\\"));
    FileOutputFormat.setOutputPath(job, outputpath);
    job.waitForCompletion(true);
  }

}
