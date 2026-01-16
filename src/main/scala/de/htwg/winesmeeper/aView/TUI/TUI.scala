package de.htwg.winesmeeper.aView.TUI

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec
import scala.io.StdIn.{readLine, readInt}
import scala.util.{Failure, Success, Try}

class TUI(ctrl: ControllerTrait) extends Observer(ctrl):
  update()
  nextTurn
  
  @tailrec
  final def nextTurn: Unit =
    println(TUIHelp.turn(observerID, readLine, ctrl))
    nextTurn

  override def update(): Unit =
    println(TUIHelp.getBoardString(ctrl))
    if !ctrl.inGame then
      println(TUIHelp.gameEndMsg(ctrl))

  override def generate(): Unit =
    for i <- 1 until 6 do
      println(TUIHelp.getPrintString(i-1))
      TUIHelp.initVals(i) = readLine
    ctrl.doSysCmd(observerID, "generate", TUIHelp.initVals.toVector)
