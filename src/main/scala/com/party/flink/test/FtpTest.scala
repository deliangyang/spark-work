package com.party.flink.test

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

object FtpTest {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val ds = env.readTextFile("pom.xml")
    ds.map(s => println(s))

    ds.print()
  }
}
