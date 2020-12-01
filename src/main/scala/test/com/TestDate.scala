package test.com

import java.sql.Timestamp
import java.text.SimpleDateFormat

object TestDate {
  def main(args: Array[String]): Unit = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = format.parse("2020-08-24T16:56:41Z".substring(0, 19))
    println(new Timestamp(date.getTime + 8 * 3600 * 1000))

    val date2 = format.parse("2020-08-25T10:56:41.932667+08:00".substring(0, 19))
    val timestamp2 = new Timestamp(date2.getTime)
    println("2020-08-25T10:56:41.932667+08:00".substring(0, 19))

    println(parseTime("2020-08-25T00:23:12.6431+08:00"))

  }

  def parseTime(t: String): String = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
    try {
      val t2 = t.substring(0, 19)
      val date = format.parse(t2)
      var d = date.getTime
      if (!t.contains("Z")) {
        d += 8 * 3600 * 1000
      }
      d.toString
    } catch {
      case _: Exception => {
        println(t)
        ""
      }
    }
  }

}
