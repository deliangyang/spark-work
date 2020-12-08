package com.party.flink.test

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.BatchTableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.bridge.scala._

case class WC(word: String, frequency: Long)

object TestInfo {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.createLocalEnvironment()

    val tEnv = BatchTableEnvironment.create(env)
    val input = env.fromElements(WC("hello", 1), WC("hello", 1), WC("ciao", 1))
    // register the DataSet as a view "WordCount"
    tEnv.createTemporaryView("WordCount", input, $"word", $"frequency")

    // run a SQL query on the Table and retrieve the result as a new Table
    //    val table = tEnv.sqlQuery("SELECT word, SUM(frequency) FROM WordCount GROUP BY word")
    //
    //    table.toDataSet[WC].print()
    val query = tEnv.from("WordCount")
      .groupBy($"word")
      .select($"word", $"frequency".sum)

    val tb = query.toDataSet[WC]
    println(tb.collect())
    // b.print()
    tb.output(new OutPutTest)
  }
}
