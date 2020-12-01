package test.com

import org.apache.spark.sql.SparkSession

object TestFastFood {
  /** *
   * bin/spark-submit --jars /tmp/mysql-connector-java-8.0.22.jar --master local[4] /mnt/party-stat/out/artifacts/fastfood_jar/fastfood.jar
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Query the gender percent of user's fans")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val conf = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/fastfood")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "123123")

    val memberDf = conf
      .option("lowerBound", 0) // 决定要fetch的值range
      .option("upperBound", 2000)
      .option("numPartitions", 10)
      .option("fetchSize", 1000)
      .option("partitionColumn", "memberid") // 用来决定partition切割的column
      .option("dbtable", "member").load()

    /**
     * `memberid` < 200 or `memberid` is null,
     * `memberid` >= 200 AND `memberid` < 400,
     * `memberid` >= 400 AND `memberid` < 600,
     * `memberid` >= 600 AND `memberid` < 800,
     * `memberid` >= 800 AND `memberid` < 1000,
     * `memberid` >= 1000 AND `memberid` < 1200,
     * `memberid` >= 1200 AND `memberid` < 1400,
     * `memberid` >= 1400 AND `memberid` < 1600,
     * `memberid` >= 1600 AND `memberid` < 1800,
     * `memberid` >= 1800
     */

    memberDf.createOrReplaceTempView("member")

    memberDf.sqlContext
      .sql("select count(1) from member")
      .show(10000)

    val orderDf = conf
      .option("lowerBound", "2018-07-06 00:00:00") // 决定要fetch的值range
      .option("upperBound", "2020-07-06 00:00:00")
      .option("numPartitions", 10)
      .option("oracle.jdbc.mapDateToTimestamp", "false")
      .option("partitionColumn", "operdate") // 用来决定partition切割的column
      .option("dbtable", "sale_detail_hist")
      .load()

    /**
     * operdate` < '2018-09-17 02:24:00' or `operdate` is null,
     * `operdate` >= '2018-09-17 02:24:00' AND `operdate` < '2018-11-29 04:48:00',
     * `operdate` >= '2018-11-29 04:48:00' AND `operdate` < '2019-02-10 07:12:00',
     * `operdate` >= '2019-02-10 07:12:00' AND `operdate` < '2019-04-24 09:36:00',
     * `operdate` >= '2019-04-24 09:36:00' AND `operdate` < '2019-07-06 12:00:00',
     * `operdate` >= '2019-07-06 12:00:00' AND `operdate` < '2019-09-17 14:24:00',
     * `operdate` >= '2019-09-17 14:24:00' AND `operdate` < '2019-11-29 16:48:00',
     * `operdate` >= '2019-11-29 16:48:00' AND `operdate` < '2020-02-10 19:12:00',
     * `operdate` >= '2020-02-10 19:12:00' AND `operdate` < '2020-04-23 21:36:00',
     * `operdate` >= '2020-04-23 21:36:00'
     */

    orderDf.createOrReplaceTempView("sale_detail_hist")

//    orderDf.sqlContext
//      .sql("select count(1) from sale_detail_hist")
//      .show(10000)

    orderDf.sqlContext
      .sql("select count(1) from sale_detail_hist where operdate > '2020-01-01 00:00:00'")
      .show(10000)


  }
}
