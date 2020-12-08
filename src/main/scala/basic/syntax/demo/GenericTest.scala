package basic.syntax.demo

object GenericTest {

  def itemRepeat[A](x: A, count: Int): List[A] = {
    if (count < 1) {
      Nil
    } else {
      x :: itemRepeat(x, count - 1)
    }
  }

  def main(args: Array[String]): Unit = {
    println(itemRepeat[Int](1, 10))
    println(itemRepeat[String]("h", 10))
    println(itemRepeat("h", 10))
  }
}
