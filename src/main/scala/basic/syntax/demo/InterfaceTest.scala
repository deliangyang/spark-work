package basic.syntax.demo


trait TailWagger {
  def startTail(): Unit
  def stopTail(): Unit
}

trait Runner {
  def startRunning(): Unit
  def endRunning(): Unit
}

class Dog extends TailWagger with Runner {
  override def startTail(): Unit = println("tail is wagging")

  override def stopTail(): Unit = println("tail is stopped")

  override def startRunning(): Unit = println("start running")

  override def endRunning(): Unit = println("stopped running")
}

class Test(d: TailWagger) {

  def t(): Unit = {
    d.startTail()
    d.stopTail()
  }
}

object InterfaceTest {
  def main(args: Array[String]): Unit = {
    val d = new Dog
    d.startTail()
    d.stopTail()
    d.startRunning()
    d.endRunning()

    val t = new Test(d)
    t.t()
  }
}
