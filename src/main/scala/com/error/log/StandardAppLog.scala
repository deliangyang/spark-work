package com.error.log

import org.apache.flink.api.common.functions.{MapFunction, ReduceFunction}
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.api.common.io.OutputFormat
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._


case class Log(content: String)

case class WC(error: String, count: BigInt, level: String)

object StandardAppLog {
  def main(args: Array[String]): Unit = {
    val params = ParameterTool.fromArgs(args)

    val env = ExecutionEnvironment.getExecutionEnvironment
    val mapper = new ObjectMapper()

    val text = env.readTextFile(params.get("input"))

    val res = text.map(value => {
      try {
        val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[StandardLog])
        Some(WC(node.m, 1, node.l))
      } catch {
        case _: Throwable => {
          None
        }
      }
    })
      .filter(x => x.nonEmpty)
      .map(x => x.get)
      //.filter(x => x.level == "error")
      .map(new ReplaceError)
      .groupBy(x => x.error)
      .reduce(new WordCount)

    res.writeAsCsv(params.get("output"))

    res.output(new OutPutTest)

    env.execute("app")
  }
}

class SelectWord extends KeySelector[WC, String] {
  override def getKey(in: WC): String = in.error
}

class WordCount extends ReduceFunction[WC] {
  override def reduce(t: WC, t1: WC): WC = WC(t.error, t.count + t1.count, t.level)
}

class ReplaceError extends MapFunction[WC, WC] {
  override def map(t: WC): WC = {
    val m = t.error.replaceAll("\\d+", "***")
    WC(m, t.count, t.level)
  }
}

class OutPutTest extends OutputFormat[WC] {
  override def configure(configuration: Configuration): Unit = {

  }

  override def open(i: Int, i1: Int): Unit = {

  }

  override def writeRecord(it: WC): Unit = {
    System.out.println(it.error)
  }

  override def close(): Unit = {}
}