package basic.syntax.demo


abstract class Monoid[A] {
  def add(x: A, y: A): A
  def unit: A
}

object ImplicitGeneric {
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    override def add(x: String, y: String): String = x concat y

    override def unit: String = ""
  }

  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    override def add(x: Int, y: Int): Int = x + y

    override def unit: Int = 0
  }

  def sum[A](xs: List[A])(implicit m: Monoid[A]): A = {
    if (xs.isEmpty) m.unit
    else m.add(xs.head, sum(xs.tail))
  }

  def main(args: Array[String]): Unit = {
    println(sum(List(1, 2, 3, 4, 5)))
    println(sum(List("a", "b", "c")))
  }
}
