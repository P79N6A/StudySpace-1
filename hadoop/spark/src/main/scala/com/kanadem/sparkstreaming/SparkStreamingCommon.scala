package com.kanadem.sparkstreaming

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}

/**
  * @author KanadeM 2019/4/6
  */

object SparkStreamingCommon {
  def main(args: Array[String]): Unit = {
    val group = "g001"
    val conf = new SparkConf().setAppName("KafkaDirectWordCount").setMaster("spark://192.168.1.101:7077")
    val ssc = new StreamingContext(conf, Duration(5000))
    val topic = "test"
    val zkQuorum = "192.168.0.114:2181"
    val topics: Set[String] = Set(topic)
    val topicDirs = new ZKGroupTopicDirs(group, topic)
    val zkTopicPath = s"${topicDirs.consumerOffsetDir}"
    val kafkaParams = Map(
      "metadata.broker.list" -> "192.168.0.114:9092",
      "group.id" -> group,
      "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString
    )
    val zkClient = new ZkClient(zkQuorum)
    val children = zkClient.countChildren(zkTopicPath)
    var kafkaStream: InputDStream[(String, String)] = null
    var fromOffsets: Map[TopicAndPartition, Long] = Map()
    if (children > 0) {
      for (i <- 0 until children) {
        val partitionOffset = zkClient.readData[String](s"$zkTopicPath/${i}")
        val tp = TopicAndPartition(topic, i)
        fromOffsets += (tp -> partitionOffset.toLong)
      }
      val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.key(), mmd.message())
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messageHandler)
    } else {
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    }
    val offsetRanges = Array[OffsetRange]()
    kafkaStream.foreachRDD { kafkaRDD =>
      kafkaRDD.asInstanceOf[HasOffsetRanges].offsetRanges

      /**业务逻辑Begin*/

      /**业务逻辑End*/
      for (o <- offsetRanges) {
        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
      }
    }

    ssc.start()
    ssc.awaitTermination()
  }
}
