package spark.test.demo

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.redis.RedisFormat

case class Counter(d2: String, c: Int)

case class Person(name: String, age: Int)


object HelloRedis {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("Redis Test")
      .config("spark.redis.host", "127.0.0.1")
      .config("spark.redis.port", "6379")
      .getOrCreate()

    import spark.implicits._
    val df = spark.read.textFile(args(0))
    val d2 = df.flatMap { value =>
      value.split("\\s")
    }
      .filter(value => !value.contains(":"))
      .toDF("d2")

    d2.createOrReplaceTempView("counter")

    d2.sqlContext.sql("select d2, count(1) from counter group by d2")
      .write
      .mode(SaveMode.Append)
      .csv(args(1))
    d2.show(10)

    val personSeq = Seq(Person("John", 30), Person("Peter", 45))
    val df2 = spark.createDataFrame(personSeq)

    df2.write
      .format("org.apache.spark.sql.redis")
      .option("table", "person")
      .save()

    spark.stop()
  }
}
