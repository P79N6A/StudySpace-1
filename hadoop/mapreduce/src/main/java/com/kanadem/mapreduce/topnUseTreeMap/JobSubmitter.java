package com.kanadem.mapreduce.topnUseTreeMap;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobSubmitter {
  public static void main(String[] arg)
      throws IOException, ClassNotFoundException, InterruptedException {
    Configuration conf = new Configuration();
    //conf.setInt("top.n", 3);
    //conf.setInt("top.n", Integer.parseInt(arg[0]));
    /**
     * 以上两种为1、conf传参  2、main函数传参 3、目前使用的是直接读取mapred-site.xml文件内容传参
     */
    Job job = Job.getInstance(conf);

    job.setJarByClass(com.kanadem.mapreduce.FlowCount.JobSubmitter.class);

    job.setMapperClass(PageTopnMapper.class);
    job.setReducerClass(PageTopnReducer.class);

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    File outputFile = new File(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\output\\");
    if(outputFile.exists()){
      FileUtils.deleteDirectory(outputFile);
    }
    Path outputpath = new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\output\\");
    
    FileInputFormat.setInputPaths(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\topn\\input\\"));
    FileOutputFormat.setOutputPath(job, outputpath);
    job.waitForCompletion(true);
  }

}
