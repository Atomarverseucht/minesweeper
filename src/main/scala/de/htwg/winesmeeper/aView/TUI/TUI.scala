package de.htwg.winesmeeper.aView.TUI

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec
import scala.io.StdIn.readLine

class TUI(ctrl: ControllerTrait) extends Observer:
  ctrl.addSub(this)
  update()
  nextTurn

  @tailrec
  final def nextTurn: Unit =
    println(TUIHelper.turn(readLine, ctrl))
    nextTurn

  override def update(): Unit =
    println(TUIHelper.getBoardString(ctrl))
    if !ctrl.inGame then
      println(TUIHelper.gameEndMsg(ctrl))

