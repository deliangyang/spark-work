package com.party.log.stat

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.functions.udf

object Reduce {

  def main(args: Array[String]): Unit = {
    val input = args(0)
    val spark = SparkSession
      .builder()
      .appName("Last Request Time")
      .getOrCreate()

    // user define function
    val str2time = udf((t: String) => {
      // 这个最好是放在里面，不然会被释放
      val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
      try {
        val t2 = t.substring(0, 19)
        val date = format.parse(t2)
        var d = date.getTime
        if (t.contains("Z")) {
          d += 8 * 3600 * 1000
        }
        new Timestamp(d).toString
      } catch {
        case e: Exception => {
          println(e.getStackTrace)
          ""
        }
      }
    })

    val schema = new StructType()
      .add("user", StringType, nullable = true)
      .add("time", StringType, nullable = true)
    spark.read
      .schema(schema)
      .csv(input)
      .createOrReplaceTempView("log")

    spark.udf.register("str2time", str2time)

    spark.sqlContext
      .sql("select user, max(str2time(time)) from log group by user")
      .write.csv("/tmp/test2.txt")
  }
}
