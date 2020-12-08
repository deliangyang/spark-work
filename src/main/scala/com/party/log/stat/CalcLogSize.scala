package com.party.log.stat

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.spark.sql.{SaveMode, SparkSession}

case class LogData(method: String, path: String, size: BigInt)

object CalcLogSize {

  def main(args: Array[String]): Unit = {
    val input = args(0)
    val output = args(1)
    val output2 = args(2)

    val spark = SparkSession
      .builder()
      .appName("Calc Request Log Size")
      .getOrCreate()
    val df = spark.sparkContext
      .textFile(input)
      .cache()
      .repartition(10)

    val mapper = new ObjectMapper()

    val d2 = df.map { value =>
      try {
        val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
        Some(LogData(node.method, node.path, value.length))
      } catch {
        case _: Exception => {
          None
        }
      }
    }
      .filter(x => x.nonEmpty)
      .map(x => x.get)

    d2.saveAsTextFile(output)

    import spark.implicits._
    val tb = d2.toDF()

    tb.createOrReplaceTempView("log")
    tb.printSchema()

    spark.sqlContext.sql(
      """
        |select method, path, sum(size) as s, avg(size), count(1) from log
        |   group by method, path
        |   order by s
        |""".stripMargin)
      .write.mode(SaveMode.Append).csv(output2)
    spark.stop()
  }
}
