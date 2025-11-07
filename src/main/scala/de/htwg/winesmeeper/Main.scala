package de.htwg

import scala.io.StdIn.{readInt, readLine}

@main def start(): Unit =
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.start(i) = readInt
  var gb = TUI.initGameBoard
  while gb.checkGameState do
    println(TUI.getBoardString(gb))
    gb = TUI.turn(readLine, gb)
  println(TUI.gameEndMsg(gb))
