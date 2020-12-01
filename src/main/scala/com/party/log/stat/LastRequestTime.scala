package com.party.log.stat

import org.apache.parquet.Files
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{StringType, StructType}

object LastRequestTime {
  /**
   * bin/spark-submit --master local[4] /tmp/last-request-time.jar  /tmp/http.request.v1-2020112419 /tmp/35.txt
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val input = args(0)
    val output = args(1)

    val spark = SparkSession
      .builder()
      .appName("Golang Last Request Time")
      .getOrCreate()
    val schema = new StructType()
      .add("user", StringType, nullable = true)
      .add("time", StringType, nullable = true)
    val df = spark.read.schema(schema).json(input)

    df.createOrReplaceTempView("log")
    val data = spark.sqlContext
      .sql("select user, max(time) from log where user > '0' group by user")

    data.write.mode(SaveMode.Append).csv(output)
    spark.stop()
  }
}
