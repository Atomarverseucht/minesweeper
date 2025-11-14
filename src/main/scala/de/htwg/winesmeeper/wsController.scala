package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.aView._

class wsController:
  def control =
    for i <- 0 until 5 do
      println(TUI.getPrintString(i))
      TUI.start(i) = readInt
    var gb = TUI.initGameBoard
    while gb.checkGameState do
      println(TUI.getBoardString(gb))
      gb = TUI.turn(readLine, gb)
    println(TUI.gameEndMsg(gb))
