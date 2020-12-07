package table.api.demo

import org.apache.flink.api.scala._
import org.apache.flink.table.api.Expressions._
import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.types.Row

case class WC(word: String, frequency: Long)

object CountQuery {
  def main(args: Array[String]): Unit = {
    val settings = EnvironmentSettings.newInstance.build
    val tEnv = TableEnvironment.create(settings)

    val sql =
      """
        |CREATE TABLE users (
        |     treasure_id INT,
        |     user_id INT
        | ) WITH (
        |     'connector.type' = 'jdbc',
        |     'connector.url' = 'jdbc:mysql://127.0.0.1:3306/zengming',
        |     'connector.table' = 'treasure_obtain',
        |     'connector.driver' = 'com.mysql.cj.jdbc.Driver',
        |     'connector.username' = 'root',
        |     'connector.password' = '123123',
        |     'connector.read.partition.column' = 'treasure_id',
        |     'connector.read.partition.num' = '1000',
        |     'connector.read.partition.lower-bound' = '0',
        |     'connector.read.partition.upper-bound' = '10000000',
        |     'connector.read.fetch-size' = '10000'
        |)""".stripMargin
    tEnv.executeSql(sql)

    tEnv.executeSql("CREATE TABLE spend_report (\n" +
      "    user_id BIGINT,\n" +
      "    c     BIGINT,\n" +
      " PRIMARY KEY (user_id) NOT ENFORCED" +
      ") WITH (\n" +
      "    'connector'  = 'jdbc',\n" +
      "    'url'        = 'jdbc:mysql://127.0.0.1:3306/zengming',\n" +
      "    'table-name' = 'spend_report',\n" +
      "    'driver'     = 'com.mysql.cj.jdbc.Driver',\n" +
      "    'username'   = 'root',\n" +
      "    'password'   = '123123'\n" +
      ")")

    // 302972 rows in set (15.80 sec)
    val table = tEnv.from("users")
      .select(
        $("treasure_id"),
        $("user_id"))
      // .filter($("user_id") === 21075118)
      .groupBy($("user_id"))
      .select(
        $("user_id"),
        $("user_id").count().as("c"))
    table.toDataSet[Row].print()
    // table.executeInsert("spend_report")
  }
}
