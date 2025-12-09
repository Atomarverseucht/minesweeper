package de.htwg.winesmeeper.aView.TUI

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec
import scala.io.StdIn.{readInt, readLine}

class TUI(ctrl: Controller) extends Observer:
  ctrl.addSub(this)
  update()
  nextTurn

  @tailrec
  final def nextTurn: Unit =
    println(TUIHelper.turn(readLine, ctrl))
    if !ctrl.isQuitted then nextTurn

  override def update(): Unit =
    if ctrl.inGame then
      println(TUIHelper.getBoardString(ctrl))
    else
      println(TUIHelper.getBoardString(ctrl))
      println(TUIHelper.gameEndMsg(ctrl))
