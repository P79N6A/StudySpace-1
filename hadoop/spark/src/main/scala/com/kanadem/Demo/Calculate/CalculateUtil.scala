package com.kanadem.Demo.Calculate

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import scala.collection.immutable
import scala.xml.{Elem, NodeSeq, XML}

/**
  * @author KanadeM 2019/4/6
  */

object CalculateUtil {
  def calculateOrderAmount(fields: RDD[Array[String]]) = {
    val messageTypeAndOne: RDD[(String, Int)] = fields.map(arr => {
      if (arr.length >= 2) {

        val item = arr(2).split("ceb:")
        val i = 1
        (item(1), i)
      } else {
        ("Null", 0)
      }
    })
    val reduced: RDD[(String, Int)] = messageTypeAndOne.reduceByKey(_ + _)
    reduced.foreachPartition(part => {
      val jedis = RedisUtil.getJedis;
      part.foreach(t => {
        jedis.incrByFloat(t._1, t._2)
        println(t._1 + "  Amount:  " + t._2)
      })
    })
  }

  /**
    *
    * @param lines
    * *
    * def calculateMessageTypeAmount(lines: RDD[String])={
    * *
    * val messageTypeAndEbc: RDD[(String,Int)] = lines.map(line =>{
    * val xml: Elem = XML.loadString(line)
    * val ebcName = (xml\"Inventory"\"InventoryHead").map(_\"ebcName")
    * if(ebcName.length > 0  ){
    * if(ebcName(0).text!=null) {
    * println("11111111111")
    * (ebcName(0).text,1)
    * }
    * }
    * else {
    * println("222222222")
    * ("Null", 0)
    * }
    * ("Null", 0)
    * })
    * println("33333333333")
    * *
    *
    * val reducedEbc: RDD[(String, Int)] = messageTypeAndEbc.reduceByKey(_ + _)
    *reducedEbc.foreachPartition(part => {
    * val jedis = RedisUtil.getJedis
    *part.foreach(t => {
    *jedis.incrByFloat(t._1, t._2)
    * println(t._1 + "  Amount:  " + t._2)
    * })
    * })
    * println("444444444444")
    * }
    */
  def calculateMessageTypeAmount(lines: RDD[String]) = {
    val messageTypeAndEbc: RDD[(String, Int)] = lines.map(line => {
      val xml: Elem = XML.loadString(line)
      val ebcName: immutable.Seq[NodeSeq] = (xml \ "Inventory" \ "InventoryHead").map(_ \ "ebcName")
      if (ebcName.length > 0) {
        (ebcName(0).text, 1)
      }
      else {
        ("Null", 0)
      }
    })

    val reducedEbc: RDD[(String, Int)] = messageTypeAndEbc.reduceByKey(_ + _)
    reducedEbc.foreachPartition(part => {
      val jedis = RedisUtil.getJedis
      part.foreach(t => {
        jedis.incrByFloat(t._1, t._2)
        println(t._1 + "  Amount:  " + t._2)
      })
      jedis.close();
    })

    val messageTypeAndPrice: RDD[(String, Double)] = lines.map(line => {
      val xml: Elem = XML.loadString(line)
      val ebcName: immutable.Seq[NodeSeq] = (xml \ "Inventory" \ "InventoryHead").map(_ \ "ebcName")
      val totalPrice: immutable.Seq[NodeSeq] = (xml \ "Inventory" \ "InventoryList").map(_ \ "totalPrice")
      if (ebcName.length > 0) {
        (ebcName(0).text, totalPrice(0).text.toDouble)
      }
      else {
        ("Null", 0)
      }
      if (totalPrice.length > 0) {
        ("TOTAL_INCOME", totalPrice(0).text.toDouble)
      }
      else {
        ("Null", 0)
      }
    })

    val reducedPrice: RDD[(String, Double)] = messageTypeAndPrice.reduceByKey(_ + _)
    reducedPrice.foreachPartition(part => {
      val jedis = RedisUtil.getJedis
      part.foreach(t => {
        jedis.incrByFloat(t._1, t._2)
        println(t._1 + "  Amount:  " + t._2)
      })
      jedis.close();
    })


  }

}
