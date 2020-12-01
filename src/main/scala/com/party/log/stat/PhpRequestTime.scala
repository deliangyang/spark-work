package com.party.log.stat

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.spark.sql.{SaveMode, SparkSession}

object PhpRequestTime {
  def main(args: Array[String]): Unit = {
    val input = args(0)
    val output = args(1)
    val mapper = new ObjectMapper()

    val spark = SparkSession
      .builder()
      .appName("PHP Last Request Time")
      .getOrCreate()

    val df = spark.sparkContext.textFile(input)
    import spark.implicits._
    val d2 = df.map { value =>
      try {
        val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
        Some(node)
      } catch {
        case _: Exception => {
          None
        }
      }
    }
      .filter(x => x.nonEmpty)
      .map(x => x.get)
      .toDF()

    d2.createOrReplaceTempView("log")
    d2.sqlContext.sql("select user, max(time) from log where user > '0' group by user")
      .write.mode(SaveMode.Append).csv(output)
    spark.stop()

  }
}
