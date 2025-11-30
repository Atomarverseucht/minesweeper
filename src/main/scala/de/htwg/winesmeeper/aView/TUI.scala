package de.htwg.winesmeeper.aView

import scala.io.StdIn.{readInt, readLine}
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec

class TUI(ctrl: Controller) extends Observer:
  ctrl.addSub(this)
  println(TUIHelper.getBoardString(ctrl))
  nextTurn
  
  def nextTurn: Boolean =
    println(TUIHelper.turn(readLine, ctrl))
    if ctrl.inGame then nextTurn
    ctrl.inGame

  override def update(): Unit =
    if ctrl.inGame then
      println(TUIHelper.getBoardString(ctrl))
    else
      println(TUIHelper.getBoardString(ctrl))
      println(TUIHelper.gameEndMsg(ctrl))
