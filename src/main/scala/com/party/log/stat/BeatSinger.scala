package com.party.log.stat

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{rank, desc}
import org.apache.spark.sql.expressions.Window

case class
BeatSinger(
            user_id: BigInt, beat_id: BigInt,
            singer_id: BigInt, count: BigInt,
            singer_name: String, beat_name: String
          )

object BeatSinger {
  def main(args: Array[String]): Unit = {
    val output = args(0)

    val spark = SparkSession
      .builder()
      .appName("Query the gender percent of user's fans")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val conf = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/dev")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "dev")
      .option("password", "devs")
      .option("lowerBound", 0) // 决定要fetch的值range
      .option("upperBound", 21117686)
      .option("numPartitions", 1000)
      .option("fetchSize", 10000)
      .option("partitionColumn", "user_id") // 用来决定partition切割的column

    val rsbg = conf.option("dbtable", "report_singing_beat_group")
      .load()

    rsbg.createOrReplaceTempView("report_singing_beat_group")

    val w = Window.partitionBy("user_id")
      .orderBy(
        desc("count"),
        desc("beat_id")
      )

    rsbg.where("beat_id > 0")
      .withColumn("rank", rank.over(w))
      .where("rank <= 6")
      .write
      .csv(output)
  }
}
