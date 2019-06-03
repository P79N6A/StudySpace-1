package com.kanadem.mapreduce.topn;

import com.kanadem.mapreduce.topn.PageCount;
import com.kanadem.mapreduce.topn.PageCountStep1.PageCountStep1Mapper;
import com.kanadem.mapreduce.topn.PageCountStep1.PageCountStep1Reducer;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageCountStep2 {

  public static class PageCountStep2Mapper extends Mapper<LongWritable, Text, PageCount, NullWritable>  {

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String[] split = value.toString().split("\t");
      PageCount pageCount = new PageCount();
      pageCount.set(split[0], Integer.parseInt(split[1]));
      context.write(pageCount, NullWritable.get());
    }
  }
  public static class PageCountStep2Reducer extends Reducer<PageCount, NullWritable, PageCount, NullWritable>{

    @Override
    protected void reduce(PageCount key, Iterable<NullWritable> values, Context context)
        throws IOException, InterruptedException {
      context.write(key, NullWritable.get());
    }
  }
  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    Configuration conf = new Configuration();
    //conf.setInt("top.n", 3);
    //conf.setInt("top.n", Integer.parseInt(arg[0]));
    /**
     * 以上两种为1、conf传参  2、main函数传参 3、目前使用的是直接读取mapred-site.xml文件内容传参
     */
    Job job = Job.getInstance(conf);

    job.setJarByClass(com.kanadem.mapreduce.topn.PageCountStep2.class);

    job.setMapperClass(PageCountStep2Mapper.class);
    job.setReducerClass(PageCountStep2Reducer.class);

    job.setMapOutputKeyClass(PageCount.class);
    job.setMapOutputValueClass(NullWritable.class);

    job.setOutputKeyClass(PageCount.class);
    job.setOutputValueClass(NullWritable.class);
    File outputFile = new File(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\output2\\");
    if(outputFile.exists()){
      FileUtils.deleteDirectory(outputFile);
    }
    Path outputpath = new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\output2\\");
/**
 * 下划线开头的和.开头的会自动过滤
 *
 */
    FileInputFormat.setInputPaths(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\output\\"));
    FileOutputFormat.setOutputPath(job, outputpath);
    job.waitForCompletion(true);
  }


}
