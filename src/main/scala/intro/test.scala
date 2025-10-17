package intro

import scala.io.StdIn

@main def run =
  println("Folgende Inputs: \n Breite \n Höhe \n Text,cgcj welcher in der Mitte steht\n")
  val x = StdIn.readInt()
  val y = StdIn.readInt()
  test.rectangle(x, y, StdIn.readLine())

object test:
  def rectangle(col_ : Int, row_ : Int, text : String) =
    val col: Int = col_ * 2
    row(col)
    for h <- 0 until row_ do
      print("|")
      if h == row_ / 2 then
        print(" " + text + " ")
        if text.length + 2 < col then
          for i <- (text.length + 2) until col do print(" ")
          print("|\n")
        else print("\n")
      else
        for j <- 0 until col do
          print(" ")
        print("|\n")
    row(col)

  def row(l: Int) =
    print("+")
    for i <- 0 until l do
      print("-")
    print("+\n")