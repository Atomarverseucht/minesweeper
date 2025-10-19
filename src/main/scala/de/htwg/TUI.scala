package de.htwg

import scala.io.StdIn.readInt
import scala.util.Random

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
  val gameBoard = Board(start(0), start(1), boardX, boardY, 10)
  
