package de.htwg.se

import scala.util.Random
import scala.io.StdIn.readInt

@main def start(): Unit =
  val start = new Array[Int](2)
  val boardX: Int = 10
  val boardY: Int = 10
  for i <- Seq(0, 1) do
    i match
      case 0 => println("Please enter your x starting x coordinate between 0 and " + boardX)
      case 1 => println("Please enter your starting y coordinate between 0 and " + boardY)
    start(i) = readInt()

  val bombs = Set.fill(10)((Random.nextInt(boardX), Random.nextInt(boardY)))
  val boardCount: Int = (boardX * boardY)

  val gameBoard: Vector[Vector[Field]] = Vector.tabulate(boardX, boardY) { (x, y) =>
        val surroundingBombs =
          for dx <- -1 to 1; dy <- -1 to 1 if (dx, dy) != (0, 0)
            yield (x + dx, y + dy)
        val count = surroundingBombs.count { case (nx, ny) =>
            bombs.contains((nx, ny))
        }
        val figure = if (bombs.contains((x, y))) Bomb() else Number(count)
        Field(false, x, y, figure)
  }
  for (row <- gameBoard) println(row.map(_.toString()).mkString)

