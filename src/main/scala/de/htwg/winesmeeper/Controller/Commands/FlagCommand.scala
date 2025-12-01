package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.Commands.OpenFieldCOR.next
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.Board
import de.htwg.winesmeeper.Model.Field

import scala.util.{Success, Try}

case class FlagCommand(ctrl: Controller, x: Int, y: Int) extends Command:
  
  override def doStep(): Boolean =
    val f = ctrl.gb.getFieldAt(x, y)
    if (!f.isOpened) then
      ctrl.gb = Board(ctrl.gb.board.updated(x, ctrl.gb.board(x).updated(y, Field(f.isBomb, f.isOpened, !f.isFlag))))
      true
    else false

  override def undoStep(): Boolean = doStep()

  override def redoStep(): Boolean = doStep()
  
object CORFlag extends CommandCOR:
  override val cmd = "flag"
  override val helpMsg: String = "sets a flag at the given coordinate"
  override val next: CommandCOR = OpenFieldCOR

  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command] =
    if cmd == this.cmd then Success(FlagCommand(ctrl, x, y)) else next.buildCmd(cmd,x,y,ctrl)