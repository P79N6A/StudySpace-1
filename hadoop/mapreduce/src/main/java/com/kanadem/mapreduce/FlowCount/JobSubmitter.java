package com.kanadem.mapreduce.FlowCount;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobSubmitter {
  public static void main(String[] arge)
      throws IOException, ClassNotFoundException, InterruptedException {
    Configuration conf = new Configuration();

    Job job = Job.getInstance(conf);

    job.setJarByClass(JobSubmitter.class);
    job.setMapperClass(FlowCountMapper.class);
    /**
     * 设置参数，MapTask在数据分区的时候用哪个分区器
     */
    job.setPartitionerClass(UserDefinedPartitioner.class);
    job.setNumReduceTasks(5);

    job.setReducerClass(FlowCountReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FlowBean.class);

    FileInputFormat.setInputPaths(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\FlowCount\\input\\"));
    FileOutputFormat.setOutputPath(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\FlowCount\\output2\\"));
    job.waitForCompletion(true);
  }

}
