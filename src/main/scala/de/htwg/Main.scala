package de.htwg

import scala.io.StdIn.readInt


@main def start(): Unit =
  val start = new Array[Int](5)

  try
    for i <- 0 until 5 do
      i match
      case 0 =>
        println("Please enter the size of the x coordinate. It must be >= 10")
      case 1 =>
        println("Please enter the size of the y coordinate. It must be >= 10")
      case 2 =>
        println("Please enter your x starting coordinate between 0 and " + (start(0) - 1))
      case 3 =>
        println("Please enter your starting y coordinate between 0 and " + (start(1) - 1))
      case 4 =>
        println("Please enter the count of bombs. It must be between 1 and " + (start(0) * start(1) - 9))
      start(i) = readInt()
    catch
      case _ => println("")

    loadTui()