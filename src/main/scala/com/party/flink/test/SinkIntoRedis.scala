package com.party.flink.test

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import redis.clients.jedis.Jedis

class SinkIntoRedis extends RichSinkFunction[WC] {
  var jedis: Jedis = _

  override def open(parameters: Configuration): Unit = {
    super.open(parameters)
    jedis = new Jedis("localhost")
  }

  override def close(): Unit = {
    super.close()
    jedis.close()
  }

  override def invoke(value: WC, context: SinkFunction.Context[_]): Unit = {
    jedis.set(value.word, value.frequency.toString)
  }
}
