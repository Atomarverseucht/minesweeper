package de.htwg

import scala.io.StdIn.readInt

object TUI:
  val start = new Array[Int](5)

  def getPrintString(indx: Int) =
    indx match
      case 0 =>
        "Please enter the size of the x coordinate. It must be >= 10"
      case 1 =>
        "Please enter the size of the y coordinate. It must be >= 10"
      case 2 =>
        "Please enter your x starting coordinate between 0 and " + (start(0) - 1)
      case 3 =>
        "Please enter your starting y coordinate between 0 and " + (start(1) - 1)
      case 4 =>
        "Please enter the count of bombs. It must be between 1 and " + (start(0) * start(1) - 9)

  def setStart(indx: Int, value: Int): Int =
    start(indx) = value
    value


  def initGameBoard: Board = Board(start(2), start(3), start(0), start(1), start(4))

