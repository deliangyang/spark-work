package basic.syntax.demo

object ImplicitParametersTest {
  // 隐式参数
  implicit val name: String = "default"
  log("init")

  def log(msg: String)(implicit name: String): Unit = println(s"[$name] $msg")

  def process(): Unit = {
    implicit val name: String = "process"
    log("do something")
  }

  def main(args: Array[String]): Unit = {
    implicit val name: String = "main"
    log("start")
    process()
    log("end")("custom name")
  }
}
