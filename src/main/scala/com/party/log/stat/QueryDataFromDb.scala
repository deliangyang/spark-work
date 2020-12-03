package com.party.log.stat

import org.apache.spark.sql.SparkSession

object QueryDataFromDb {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Query the gender percent of user's fans")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val conf = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql://127.0.0.1:6432/party_test")
      .option("driver", "org.postgresql.Driver")
      .option("user", "test")
      .option("password", "test")

    conf.option("dbtable", "timeline.feeds")
      .load()
      .createOrReplaceTempView("feeds")

    val sql =
      """select substring(create_time, 0, 10) as day, count(1) from feeds
        |group by day""".stripMargin

    spark.sqlContext.sql(sql)
      .show(truncate = false)
  }
}
