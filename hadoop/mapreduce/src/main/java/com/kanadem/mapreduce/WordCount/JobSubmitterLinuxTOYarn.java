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

public class JobSubmitterLinuxTOYarn {

  public static void main(String[] args)
      throws IOException, URISyntaxException, InterruptedException, ClassNotFoundException {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf);

    job.setJarByClass(JobSubmitterLinuxTOYarn.class);

    job.setMapperClass(WordCountMapper.class);
    job.setReducerClass(WordCountReduce.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    FileSystem fs = FileSystem.get(new URI("hdfs://kanadem1:9000"), conf, "hadoop");
    //封装参数4：本次job要处理的输入数据集的目录,最终结果的输出路径
    Path output = new Path("/mapreduce/output/");
    if(fs.exists(output)){
      fs.delete(output, true);
    }

    FileInputFormat.setInputPaths(job, new Path("/wordcount/input/"));
    FileOutputFormat.setOutputPath(job, output);//注意输出路径必须不存在

    //封装参数5：想要启动的reduce task数量
    job.setNumReduceTasks(1);

    //向yarn提交本次的job
    boolean result = job.waitForCompletion(true);
    System.out.println("流程结束");
    System.exit(result?0:-1);

  }
}
