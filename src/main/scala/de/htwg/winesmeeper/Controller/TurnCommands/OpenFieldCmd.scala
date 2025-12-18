package de.htwg.winesmeeper.Controller.TurnCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}

import scala.util.{Success, Try}

case class OpenFieldCmd(ctrl: ControllerTrait, x: Int, y: Int) extends Command:

  val isFlag: Boolean = ctrl.gb.getFieldAt(x, y).isBomb
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
      ctrl.gb = gb.updateField(x, y, f.isBomb, discover, !discover && isFlag)
      if !discover && !ctrl.inGame then ctrl.changeState("running")
      if f.isBomb && discover then ctrl.changeState("lose");
      else if gb.getBombNeighbour(x, y) == 0 then
          for fx <- x - 1 to x + 1
            fy <- y - 1 to y + 1 do
            if gb.in(fx, fy) && !gb.getFieldAt(fx, fy).isOpened == discover then
              OpenFieldCmd(ctrl, fx, fy).step(discover)
      if ctrl.isVictory && discover then ctrl.changeState("win")
      true

  override def toString: String = f"open($x, $y)"

object OpenFieldCOR extends CommandCOR:
  override val cmd = "open"
  override val helpMsg = "opens the field of the given coordinate"
  override val next: CommandCOR = LastElemCmdCOR
  override val specHelpMsg: String =
    """open <x> <y>:
      |  Opens a field and if you hit a bomb, you loose!
      |  But no pressure you can undo your fault!
      |""".stripMargin

  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command] =
    if cmd == this.cmd then Success(OpenFieldCmd(ctrl, x, y)) else next.buildCmd(cmd,x,y,ctrl)

