package de.htwg.winesmeeper.Controller.TurnCommands

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.{Command, CommandCOR, ControllerTrait}
import de.htwg.winesmeeper.Model.BoardImplementation.Board
import de.htwg.winesmeeper.Model.ImplField.Field

import scala.util.{Success, Try}

case class FlagCommand(ctrl: ControllerTrait, x: Int, y: Int) extends Command:
  
  override def doStep(): Boolean =
    val f = ctrl.gb.getFieldAt(x, y)
    if (!f.isOpened) then
      ctrl.gb = ctrl.gb.updateField(x, y, Config.standardField(f.isBomb, f.isOpened, !f.isFlag))
      true
    else false

  override def undoStep(): Boolean = doStep()

  override def redoStep(): Boolean = doStep()

  override def toString: String = f"flag($x, $y)"
  
object FlagCOR extends CommandCOR:
  override val cmd = "flag"
  override val helpMsg: String = "flag or unflag the given coordinate"
  override val next: CommandCOR = OpenFieldCOR
  override val specHelpMsg: String =
    """flag <x> <y>:
      |  mark this position as flag or remove the flag
      |""".stripMargin

  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command] =
    if cmd == this.cmd then Success(FlagCommand(ctrl, x, y)) else next.buildCmd(cmd,x,y,ctrl)

