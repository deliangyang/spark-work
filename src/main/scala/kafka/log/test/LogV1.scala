package kafka.log.test

import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
import org.apache.flink.api.scala._
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema
import org.apache.kafka.clients.consumer.ConsumerConfig

// bin/kafka-console-consumer.sh --bootstrap-server internal.kafka.2.haochang.me:9092 --topic docker_origin | jq .
object LogV1 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "internal.kafka.2.haochang.me:9092")
    properties.setProperty("group.id", "helloworld")
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[ByteArrayDeserializer].getName)

    val mapper = new ObjectMapper()
    val stream = env
      .addSource(new FlinkKafkaConsumer[ObjectNode]("docker_origin", new JSONKeyValueDeserializationSchema(true), properties))
    stream
      .map(x => x.get("value").get("log"))
        .filter(_!= null)
        .map(x => {
          val obj = mapper.readValue(x.toString, classOf[Object])
          println(obj)
        })

    stream.print()
    env.execute("a")
  }

}

