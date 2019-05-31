package com.kanadem.mapreduce.Index;

import com.kanadem.mapreduce.FlowCount.FlowBean;
import com.kanadem.mapreduce.FlowCount.FlowCountMapper;
import com.kanadem.mapreduce.FlowCount.FlowCountReducer;
import com.kanadem.mapreduce.FlowCount.JobSubmitter;
import com.kanadem.mapreduce.FlowCount.UserDefinedPartitioner;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 根据文件内容形成
 * hello  a.txt-->4  b.txt-->4  c.txt-->4
 * java   c.txt-->1
 * jerry  b.txt-->1  c.txt-->1
 * 编程原理：maptask在启动之前就注定了会处理哪些数据这部分的内容放在context中
 */
public class IndexStepOne {
  public static class IndexStepOneMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      //取得正在处理的一行数据所属的文件
      FileSplit inputSplit = (FileSplit)context.getInputSplit();
      String FileName = inputSplit.getPath().getName();
      String[] words = value.toString().split(" ");
      for(String word : words){
        context.write(new Text(word + "-" + FileName), new IntWritable(1));
      }
    }
  }
  public static class IndexStepOneReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      int count = 0;
      for(IntWritable value : values){
        count += value.get();
      }
      context.write(key, new IntWritable(count));
    }
  }

  public static void main(String[] args)
      throws IOException, ClassNotFoundException, InterruptedException {
    Configuration conf = new Configuration();

    Job job = Job.getInstance(conf);

    job.setJarByClass(IndexStepOne.class);
    job.setMapperClass(IndexStepOneMapper.class);
    job.setReducerClass(IndexStepOneReducer.class);
    /**
     * 设置参数，MapTask在数据分区的时候用哪个分区器
     */
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    File file = new File(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\output\\");
    if(file.exists()){
      FileUtils.deleteDirectory(file);
    }
    Path outputpath = new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\output\\");
    FileInputFormat.setInputPaths(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\Index\\input\\"));
    FileOutputFormat.setOutputPath(job,outputpath);
    job.waitForCompletion(true);
  }

}
