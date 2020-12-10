package com.error.log

import com.fasterxml.jackson.databind.ObjectMapper


object JsonParse {
  def main(args: Array[String]): Unit = {
    val value =
      """
        |Dec  8 20:00:00 standard[18052]: {"schema":"app","t":"2020-12-08T20:00:00.84631+08:00","l":"error","s":"party","c":"exception","e":"prod","m":"Error: model_exception_TreasureCoolingException","ctx":{"runtimeInfo":{}}}
        |""".stripMargin
    val mapper = new ObjectMapper()

    val node = mapper.readValue(
      value.substring(value.indexOf('{')),
      classOf[StandardLog])
    println(node)

    val b = "afddf 123,123"
    println(b.replaceAll("\\d+", "***"))
  }
}
