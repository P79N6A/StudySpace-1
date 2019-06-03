package com.kanadem.mapreduce.topn;

import com.kanadem.mapreduce.topnUseTreeMap.PageTopnMapper;
import com.kanadem.mapreduce.topnUseTreeMap.PageTopnReducer;
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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageCountStep1 {
  public static class PageCountStep1Mapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String line = value.toString();
      String[] split = line.split(" ");
      context.write(new Text(split[1]), new IntWritable(1));
    }
  }

  public static class PageCountStep1Reducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      int count = 0;
      for(IntWritable value : values){
        count += value.get();
      }
      context.write(key, new IntWritable(count));
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

      job.setJarByClass(com.kanadem.mapreduce.topn.PageCountStep1.class);

      job.setMapperClass(PageCountStep1Mapper.class);
      job.setReducerClass(PageCountStep1Reducer.class);

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
      job.setNumReduceTasks(3);
      job.waitForCompletion(true);
    }
  }

}
