package de.htwg

import scala.io.StdIn.readInt

import scala.io.StdIn.readLine
import de.htwg.TUI

@main def start(): Unit =
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.start(i) = readInt
  var gb = TUI.initGameBoard
  while gb.checkGameState do
    println(TUI.getBoardString(gb))
    gb = TUI.turn(readLine, gb)
  println(TUI.gameEndMsg(gb))
