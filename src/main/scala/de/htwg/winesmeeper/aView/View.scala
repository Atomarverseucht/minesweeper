package de.htwg.winesmeeper.aView

import scala.io.StdIn.{readInt, readLine}
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec

class View(ctrl: Controller) extends Observer:
  ctrl.addSub(this)
  println(TUI.getBoardString(ctrl))
  nextTurn
  
  def nextTurn: Boolean =
    if !TUI.turn(readLine, ctrl) then println("Nice try")
    if ctrl.inGame then nextTurn
    ctrl.inGame

  override def update(): Unit =
    if ctrl.inGame then
      println(TUI.getBoardString(ctrl))
    else
      println(TUI.getBoardString(ctrl))
      println(TUI.gameEndMsg(ctrl))
