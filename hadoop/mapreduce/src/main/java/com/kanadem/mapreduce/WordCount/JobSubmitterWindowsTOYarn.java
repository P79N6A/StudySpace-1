package com.kanadem.mapreduce.WordCount;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
* 提交MapReduce job的客户端
* 功能：
* 1.封装yarn参数
* 2.提交job,运行
* */
public class JobSubmitterWindowsTOYarn {
  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
   // BasicConfigurator.configure();//自动快速地使用缺省Log4j环境。
    Configuration conf = new Configuration();
    System.setProperty("HADOOP_USER_NAME", "hadoop");
    //设置要提交的文件系统
    conf.set("fs.defaultFS", "hdfs://centos1:9000");
    conf.set("mapreduce.framework.name", "yarn");
    conf.set("yarn.resourcemanager.hostname", "centos1");
    //如果要从windows系统上提交job程序，则需要加跨平台提交参数
    conf.set("mapreduce.app-submission.cross-platform", "true");
    conf.set("yarn.app.mapreduce.am.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");
    conf.set("mapreduce.map.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");
    conf.set("mapreduce.reduce.env", "HADOOP_MAPRED_HOME=${HADOOP_HOME}");

    //conf.set("yarn.nodemanager.resource.memory-mb", "2048");
    //conf.set("yarn.nodemanager.resource.cpu-vcores", "1");


    Job job = Job.getInstance(conf);

    //封装参数1：jar包所在的位置，获得当前类所在的jar包

    //job.setJarByClass(JobSubmitterWindowsTOYarn.class);
    job.setJar("E:\\Projects\\hadoop\\jar\\mapreduce-1.0-SNAPSHOT.jar");
    //封装参数2：本次job所要调用的Mapper实现类
    job.setMapperClass(WordCountMapper.class);
    job.setReducerClass(WordCountReduce.class);

    //封装参数3：本次job的Mapper输出的结果数据的key\value类型
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileSystem fs = FileSystem.get(new URI("hdfs://centos1:9000"), conf, "hadoop");
    //封装参数4：本次job要处理的输入数据集的目录,最终结果的输出路径
    Path output = new Path("/mapreduce/output/");
    if(fs.exists(output)){
      fs.delete(output, true);
    }

    FileInputFormat.setInputPaths(job, new Path("/wordcount/input/"));
    FileOutputFormat.setOutputPath(job, output);//注意输出路径必须不存在

    //封装参数5：想要启动的reduce task数量
    job.setNumReduceTasks(2);

    //向yarn提交本次的job
    boolean result = job.waitForCompletion(true);
    System.out.println("流程结束");
    System.exit(result?0:-1);
  }

}
