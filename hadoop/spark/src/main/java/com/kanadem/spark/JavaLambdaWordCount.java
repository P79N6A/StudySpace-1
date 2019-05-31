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

public class JavaLambdaWordCount {

  public static void main(String[] args) {
    SparkConf conf = new SparkConf().setAppName("JavaWordCount");
    //创建sparkContext
    JavaSparkContext jsc = new JavaSparkContext(conf);
    JavaRDD<String> lines = jsc.textFile(args[0]);
    JavaRDD<String> words = lines
        .flatMap(line -> Arrays.asList(line.split(" ")).iterator());
    //组合单词和1
    JavaPairRDD<String, Integer> wordAndOne = words
        .mapToPair(word -> new Tuple2<>(word, 1));
    JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey((m, n) -> m + n);
    JavaPairRDD<Integer, String> swaped = reduced.mapToPair(tp -> tp.swap());
    JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);
    JavaPairRDD<String, Integer> result = sorted.mapToPair(tp -> tp.swap());
    result.saveAsTextFile(args[1]);
    jsc.stop();

  }
}
