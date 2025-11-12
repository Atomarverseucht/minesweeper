package de.htwg.winesmeeper
import scala.io.StdIn.{readInt, readLine}

@main def start(): Unit =
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.start(i) = readInt
  nextTurn(TUI.initController)
  
def nextTurn(ctrl: Controller): Boolean =
  if ctrl.inGame then
    println(TUI.getBoardString(ctrl))
    nextTurn(TUI.turn(readLine, ctrl))
  else
    println(TUI.gameEndMsg(ctrl))
  ctrl.inGame
