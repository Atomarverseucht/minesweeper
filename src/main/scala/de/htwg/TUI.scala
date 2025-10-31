package de.htwg

@main def loadTui(): Unit =
  val start = new Array[Int](5)

    val gameBoard = Board(start(2), start(3), start(0), start(1), start(4))

    for y <- 0 until start(1) do
      for x <- 0 until start(0) do
        print(gameBoard.getField(x, y))
      println()
    println(gameBoard.getSize)
