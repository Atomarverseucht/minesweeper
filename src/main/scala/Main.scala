package de.htwg.se

import scala.util.Random
import scala.io.StdIn.readInt

@main def start(): Unit =
  val start = new Array[Int](2) 
  val boardX: Int = 10
  val boardY: Int = 10
  for (i <- Seq(0, 1))
    i match
      case 0 => 
        println("Please enter your x starting x coordinate between 0 and " + boardX)
      case 1 => 
        println("Please enter your starting y coordinate between 0 and " + boardY)
    start(i) = readInt()

  val gameBoard: Vector[Vector[Field]] = 
    Vector.tabulate(boardX, boardY)((x, y) => Field(false, x, y, if (Random().nextInt(10) != start(0) + start(1)) Bomb else Number))

  for (row <- gameBoard) println(row.map(_.toString()).mkString)
  
