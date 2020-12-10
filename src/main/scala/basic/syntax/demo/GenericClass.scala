package basic.syntax.demo

class Stack[A] {
  private var elements: List[A] = Nil

  def push(x: A): Unit = elements = x :: elements

  def peek: A = elements.head

  def pop(): A = {
    val currentTop = peek
    elements = elements.tail
    currentTop
  }
}

object GenericClass {
  def main(args: Array[String]): Unit = {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
  }
}
