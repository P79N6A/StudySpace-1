package com.kanadem.sparkstreaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author KanadeM 2019/4/5
 */

object StreamingWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("StreamingWordCount").setMaster("local[4]")
    val sc = new SparkContext(conf)
    //StreamingContext是对SparkContext的包装，包装之后就增加了实时功能
    //第二个参数是小批次产生的时间间隔
    val ssc = new StreamingContext(sc, Milliseconds(5000))
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.1.101", 8888)
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val wordAndOne: DStream[(String, Int)] = words.map((_, 1))
    val reduced = wordAndOne.reduceByKey(_+_)
    reduced.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
