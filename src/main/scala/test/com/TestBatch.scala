package test.com

import org.apache.spark.sql.SparkSession

object TestBatch {
  /***
   * bin/spark-submit --jars /tmp/mysql-connector-java-8.0.22.jar --master local[4] /tmp/party-stat.jar
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

    val userDF = conf.option("dbtable", "users").load()
    val followDF = conf.option("dbtable", "follow").load()

    userDF.createOrReplaceTempView("users")
    followDF.createOrReplaceTempView("follow")

    followDF.sqlContext
      .sql("select follow.target_user_id, users.gender, count(1) from follow" +
        " left join users on follow.user_id = users.user_id" +
        " group by follow.target_user_id, users.gender")
      .show(10000)
  }
}
