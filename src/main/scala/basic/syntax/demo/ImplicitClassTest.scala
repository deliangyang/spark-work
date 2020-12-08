package basic.syntax.demo

import java.time.LocalDate

object DateHelperUtils {
  val ago = "ago"
  val from_now = "from_now"

  implicit class ConvertInt2DateHelper(offset: Int) {
    def days(when: String): LocalDate = {
      val now = LocalDate.now
      when match {
        case "ago" => now.minusDays(offset)
        case "from_now" => now.plusDays(offset)
        case _ => now
      }
    }
  }
}

// 因为隐式转换太过强大，隐式类只能是局部定义的类，不可以是全局类，否则编译器会报错

object ImplicitClassTest {
  def main(args: Array[String]): Unit = {
    import DateHelperUtils._
    // 将Int转化为DateHelper的类，隐式转换
    println(2 days ago)
    println(2 days from_now)
  }
}
