package com.party.log.stat

import org.apache.spark.sql.{SaveMode, SparkSession}

object OldLogLastRequest {
  def main(args: Array[String]): Unit = {
    val input = args(0)
    val output = args(1)
    val spark = SparkSession
      .builder()
      .appName("Last Request Time")
      .getOrCreate()

    val df = spark.sparkContext.textFile(input)
    import spark.implicits._
    val d2 = df.map { value =>
      try {
        val a = value.substring(value.indexOf("]:") + 3)
        val node = a.substring(0, a.indexOf("/")).split("\t")
        Some(node(0).stripMargin, node(2).stripMargin)
      } catch {
        case _: Exception => {
          None
        }
      }
    }
      .filter(x => x.nonEmpty)
      .map(x => x.get)
      .toDF("user", "time")

    d2.createOrReplaceTempView("log")
    d2.sqlContext.sql("select user, max(time) from log where user > '0' group by user")
      .write.mode(SaveMode.Append).csv(output)
    spark.stop()
  }
}
