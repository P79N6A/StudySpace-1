package com.kanadem.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    //Conf必须要设置应用的名字
    val conf = new SparkConf().setAppName("ScalaWordCount").setMaster("local[4]")
    //val conf = new SparkConf().setAppName("ScalaWordCount")
    //创建spark执行入口
    val sc = new SparkContext(conf)
    //指定以后从哪里读取数据创建RDD(弹性分布式数据集)
    val lines : RDD[String] = sc.textFile("hdfs://192.168.0.114:9000/spark/wc")
    val words: RDD[String] =lines.flatMap(_.split(" "))
    val unit: RDD[(String, Int)] = words.map((_, 1))
    val reduce: RDD[(String,Int)] = unit.reduceByKey(_+_)
    val sorted: RDD[(String, Int)] = reduce.sortBy(_._2, false)
    sorted.saveAsTextFile(args(1))
    sc.stop()
  }
}
