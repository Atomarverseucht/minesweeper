package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Model.{Board, Field}
import de.htwg.winesmeeper.Controller.Controller

import scala.util.{Success, Try}

case class OpenFieldCmd(ctrl: Controller, x: Int, y: Int) extends Command:

  val isFlag: Boolean = ctrl.gb.board(x)(y).isFlag
  override def doStep(): Boolean =
    step(true)

  override def undoStep(): Boolean =
    step(false)

  override def redoStep(): Boolean =
    step(true)

  private def step(discover: Boolean): Boolean =
    val gb = ctrl.gb
    val f = gb.getFieldAt(x, y)
    if discover == f.isOpened then false
    else
      val newVector = gb.board.updated(x, gb.board(x).updated(y, Field(f.isBomb, discover, !discover && isFlag)))
      ctrl.gb = new Board(newVector)
      if f.isBomb && !discover then ctrl.changeState("running")
      if f.isBomb && discover then ctrl.changeState("lose");
      else
        if gb.getBombNeighbour(x, y) == 0 then
          for fx <- x - 1 to x + 1
            fy <- y - 1 to y + 1 do
            if gb.in(fx, fy) && !gb.board(fx)(fy).isOpened == discover then
              OpenFieldCmd(ctrl, fx, fy).step(discover)
      if ctrl.isVictory then ctrl.changeState("win")
      true

object OpenFieldCOR extends CommandCOR:
  override val cmd = "open"
  override val helpMsg = "opens the field of the given coordinate"
  override val next: CommandCOR = LastElemCmdCOR

  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command] =
    if cmd == this.cmd then Success(OpenFieldCmd(ctrl, x, y)) else next.buildCmd(cmd,x,y,ctrl)