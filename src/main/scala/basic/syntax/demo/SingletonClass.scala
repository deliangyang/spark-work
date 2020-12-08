package basic.syntax.demo


class Logger {
  def apply(): Logger = new Logger() {
    println("2")
  }
}

object Logger {
  def info(msg: String): Unit = {
    println(msg)
  }
}

object SingletonClass {
  def main(args: Array[String]): Unit = {
    Logger.info("hello world")
    Logger.info("hello world2")
  }
}