package test.com

import org.apache.spark.sql.SparkSession

object TreasureBoom {
  /** *
   * CPU利用率  (user + sys) / real * 100%
   *
   * 4 core
   *
   * real	1m31.208s
   * user	5m48.895s
   * sys	0m54.686s
   *
   * 8 core
   *
   * real	1m25.840s
   * user	7m5.125s
   * sys	1m7.741s
   *
   * bin/spark-submit --jars /tmp/mysql-connector-java-8.0.22.jar --master local[8] /mnt/party-stat/out/artifacts/party_treasure_jar/party-stat.jar
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Treasure boom")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    val conf = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306/zengming")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "123123")

    val obtainDf = conf
      .option("lowerBound", 0) // 决定要fetch的值range
      .option("upperBound", 3506664)
      .option("numPartitions", 1000)
      .option("fetchSize", 10000)
      .option("partitionColumn", "treasure_id") // 用来决定partition切割的column
      .option("dbtable", "treasure_obtain").load()

    obtainDf.createOrReplaceTempView("treasure_obtain")

    val rewardDf = conf
      .option("lowerBound", 0) // 决定要fetch的值range
      .option("upperBound", 3506664)
      .option("numPartitions", 1000)
      .option("fetchSize", 10000)
      .option("partitionColumn", "treasure_id") // 用来决定partition切割的column
      .option("dbtable", "treasure_reward").load()

    rewardDf.createOrReplaceTempView("treasure_reward")

    val sql =
      """select treasure_obtain.user_id, treasure_reward.type, count(1) as c from treasure_obtain
        |  left join treasure_reward
        |    on treasure_obtain.treasure_id=treasure_reward.treasure_id
        |  where treasure_reward.create_time >= unix_timestamp('2020-01-01 00:00:00') * 1000
        |    and treasure_reward.create_time < unix_timestamp('2021-01-01 00:00:00') * 1000
        |  group by treasure_obtain.user_id, treasure_reward.type
        |  order by c desc
        |""".stripMargin

    spark.sqlContext
      .sql(sql)
      .show(10000)

    val query =
      """
        |select user_id, type, count(1) from treasure_reward
        | where create_time >= unix_timestamp('2020-01-01 00:00:00') * 1000
        |    and create_time < unix_timestamp('2021-01-01 00:00:00') * 1000
        | group by user_id, type
        |""".stripMargin

    spark.sqlContext
      .sql(query)
      .show(10000)

    val maxKd =
      """
        |select user_id, max(kd) as max_kd from treasure_obtain
        | where create_time >= unix_timestamp('2020-01-01 00:00:00') * 1000
        |    and create_time < unix_timestamp('2021-01-01 00:00:00') * 1000
        | group by user_id
        | order by max_kd desc
        |""".stripMargin

    spark.sqlContext
      .sql(maxKd)
      .show(10000)

    val totalKd =
      """
        |select user_id, sum(kd), count(1) from treasure_obtain
        | where create_time >= unix_timestamp('2020-01-01 00:00:00') * 1000
        |    and create_time < unix_timestamp('2021-01-01 00:00:00') * 1000
        | group by user_id
        | order by user_id desc
        |""".stripMargin

    spark.sqlContext
      .sql(totalKd)
      .show(10000)
  }
}
