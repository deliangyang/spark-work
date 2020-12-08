package com.party.flink.test

import org.apache.flink.api.common.io.{FileOutputFormat, OutputFormat, RichOutputFormat}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.SinkFunction
import redis.clients.jedis.Jedis

class SinkFunctionTest extends SinkFunction[WC] {

  override def invoke(value: WC, context: SinkFunction.Context[_]): Unit = {
    println(value.word, value.frequency)
  }
}


class OutPutTest extends RichOutputFormat[WC] {
  var jedis: Jedis = _

  @Exception
  override def writeRecord(it: WC): Unit = {
    try {
      System.out.println(it.word, it.frequency)
      jedis.set(it.word, it.frequency.toString)
    } catch {
      case e: Throwable => System.err.println(e)
    }
  }

  override def configure(configuration: Configuration): Unit = {
  }

  @Exception
  override def open(i: Int, i1: Int): Unit = {
    try {
      jedis = new Jedis("localhost")
    } catch {
      case e: Throwable => System.err.println(e)
    }
  }

  override def close(): Unit = {
    jedis.close()
  }
}