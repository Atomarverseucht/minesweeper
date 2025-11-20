package de.htwg.winesmeeper.aView

import scala.io.StdIn.{readInt, readLine}
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec

@main def start(): Unit =
  println(System.getProperty("file.encoding"))
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.start(i) = readInt
  (new View).nextTurn

class View extends Observer:
  val ctrl: Controller = TUI.initController
  ctrl.addSub(this)
  println(TUI.getBoardString(ctrl))

  def nextTurn: Boolean =
    if ctrl.inGame then
      if !TUI.turn(readLine, ctrl) then println("Nice try")
      nextTurn
    else
      println(TUI.gameEndMsg(ctrl))
    ctrl.inGame

  override def update(): Unit =
    println(TUI.getBoardString(ctrl))
