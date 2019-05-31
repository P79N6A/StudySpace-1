package com.kanadem.mapreduce.GroupTopn;

import com.kanadem.mapreduce.topn.PageCountStep1.PageCountStep1Mapper;
import com.kanadem.mapreduce.topn.PageCountStep1.PageCountStep1Reducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils.Null;
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

public class OrderTopn {

  public static class OrderTopnMapper extends Mapper<LongWritable, Text, Text, OrderBean>{
    OrderBean orderBean = new OrderBean();

    /**
     * 如果是ArrayList的话，使用add同一个对象的话，传入的是对象的引用，所以当对象之后发生修改时，ArrayList的内部数据也会
     * 被修改，但是context没有问题，context传入的是一个已经序列化的结果，所以不会出现问题
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String[] split = value.toString().split(",");
      orderBean.set(split[0], split[1], split[2],Float.parseFloat(split[3]), Integer.parseInt(split[4]));

      context.write(new Text(orderBean.getOrderId()), orderBean);
    }





  }
  public static class OrderTopnReducer extends Reducer<Text, OrderBean, OrderBean, NullWritable>{
    ArrayList<OrderBean> beanList = new ArrayList<>();

    @Override
    protected void reduce(Text key, Iterable<OrderBean> values, Context context)
        throws IOException, InterruptedException {
      //reduce task提供的迭代器，每次迭代返回来的都是同一个对象只是set了不同的值，所以使用ArrayList会出现问题
      for(OrderBean value : values){
        OrderBean orderBean = new OrderBean();
        orderBean.set(value.getOrderId(), value.getUserId(), value.getPdtName(), value.getPrice(), value.getNumber());
        beanList.add(orderBean);
      }

      //对list排序，按总金额排序，如果金额相同比较商品名称
      Collections.sort(beanList);
      for(int i = 0 ;i <3; i++){
        context.write(beanList.get(i), NullWritable.get());
      }

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

    job.setMapperClass(OrderTopnMapper.class);
    job.setReducerClass(OrderTopnReducer.class);

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(OrderBean.class);

    job.setOutputKeyClass(OrderBean.class);
    job.setOutputValueClass(NullWritable.class);
    File outputFile = new File(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\GroupTopn\\output\\");
    if(outputFile.exists()){
      FileUtils.deleteDirectory(outputFile);
    }
    Path outputpath = new Path(
        "E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\GroupTopn\\output\\");

    FileInputFormat.setInputPaths(job, new Path("E:\\Projects\\StudySpace\\hadoop\\mapreduce\\src\\main\\java\\com\\kanadem\\mapreduce\\GroupTopn\\input\\"));
    FileOutputFormat.setOutputPath(job, outputpath);
    job.setNumReduceTasks(1);
    job.waitForCompletion(true);
  }

}
