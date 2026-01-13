package de.htwg.winesmeeper.Controller.Commands.ImplTurnCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.{CommandCORTrait, CommandTrait}

import scala.util.{Success, Try, Failure}

case class FlagCommand(observerID_ : Int, ctrl: ControllerTrait, x: Int, y: Int) extends CommandTrait(observerID_):
  
  override def doStep(): Try[String] =
    val f = ctrl.gb.getFieldAt(x, y)
    if (!f.isOpened) then
      ctrl.gb = ctrl.gb.updateField(x, y, Config.mkField(f.isBomb, f.isOpened, !f.isFlag))
      Success("flag successful")
    else Failure(IllegalArgumentException("flag cannot be set on a opened field"))

  override def undoStep(): String = doStep().get

  override def redoStep(): String = doStep().get

  override def startStep(): Try[String] = Failure(IllegalArgumentException("You cannot start with the flag command"))

  override def toString: String = f"flag($observerID, $x, $y)"



object FlagCOR extends CommandCORTrait:
  override val cmd = "flag"
  override val helpMsg: String = "flag or unflag the given coordinate"
  override val next: CommandCORTrait = OpenFieldCOR
  override val specHelpMsg: String =
    """flag <x> <y>:
      |  mark this position as flag or remove the flag
      |""".stripMargin

  override def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait] =
    if cmd == this.cmd then Success(FlagCommand(observerID, ctrl, x, y)) else next.buildCmd(observerID, cmd,x,y,ctrl)

