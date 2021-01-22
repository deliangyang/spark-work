package basic.syntax.demo

object Loop {
  def main(args: Array[String]): Unit = {
    val nums = Seq(1, 2, 3)
    for (n <- nums) println(n)

    for (n <- nums) {
      println(n)
    }

    nums.foreach(println)

    val test = Map(
      "a" -> 1,
      "b" -> 2
    )
    for ((k, v) <- test) println(s"k: $k, v: $v")

    println(isTrue("a"))
    println(isTrue(0))
  }

  def isTrue(a: Any) = a match {
    case 0 | "" => false
    case _ => true
  }
}
