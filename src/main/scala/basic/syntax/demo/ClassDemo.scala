package basic.syntax.demo



class Pizza(var crustSize: Int, var crustType: String) {



  def this(crustSize: Int) {
    this(crustSize, ClassDemo.defaultCursType)
  }

  def this(crustType: String) {
    this(ClassDemo.defaultCursSize, crustType)
  }

  def this() = {
    this(ClassDemo.defaultCursSize, ClassDemo.defaultCursType)
  }

  override def toString: String = s"A $crustSize inch pizza with a $crustType crust"

}

object ClassDemo {
  val defaultCursSize = 12
  val defaultCursType = "THIN"


  def main(args: Array[String]): Unit = {
    val a = new Pizza()

    println(a)
  }
}
