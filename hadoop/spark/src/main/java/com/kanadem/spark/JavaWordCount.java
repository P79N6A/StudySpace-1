package com.kanadem.spark;

import java.util.Arrays;
import java.util.Iterator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

/**
 * @author Kanadem 2019/4/3
 */

public class JavaWordCount {

  public static void main(String[] args) {
    SparkConf conf = new SparkConf().setAppName("JavaWordCount");
    //创建sparkContext
    JavaSparkContext jsc = new JavaSparkContext(conf);
    JavaRDD<String> lines = jsc.textFile(args[0]);
    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
      @Override
      public Iterator<String> call(String line) throws Exception {
        return Arrays.asList(line.split(" ")).iterator();
      }
    });
    //组合单词和1
    JavaPairRDD<String, Integer> wordAndOne = words
        .mapToPair(new PairFunction<String, String, Integer>() {
          @Override
          public Tuple2<String, Integer> call(String s) throws Exception {
            return new Tuple2<>(s, 1);
          }
        });
    //聚合
    JavaPairRDD<String, Integer> reduced = wordAndOne
        .reduceByKey(new Function2<Integer, Integer, Integer>() {
          @Override
          public Integer call(Integer integer, Integer integer2) throws Exception {
            return integer + integer2;
          }
        });
    //排序,由于只有sortbykey所以需要先将key和value调换顺序
    JavaPairRDD<Integer, String> swapped = reduced
        .mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
          @Override
          public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2)
              throws Exception {
            return stringIntegerTuple2.swap();
            //return new Tuple2<>(stringIntegerTuple2._2, stringIntegerTuple2._1);
          }
        });
    JavaPairRDD<Integer, String> sorted = swapped.sortByKey(false);
    JavaPairRDD<String, Integer> result = sorted
        .mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
          @Override
          public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2)
              throws Exception {
            return integerStringTuple2.swap();
          }
        });

    result.saveAsTextFile(args[1]);

    jsc.stop();
  }
}
