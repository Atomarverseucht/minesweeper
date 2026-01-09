package de.htwg.winesmeeper.Controller.ImplTurnCommands

import de.htwg.winesmeeper.Controller.{CommandTrait, CommandCORTrait, ControllerTrait}
import de.htwg.winesmeeper.Model.FieldTrait
import de.htwg.winesmeeper.WinesmeeperModule

import scala.util.{Success, Try}
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

case class FlagCommand(observerID_ : Int, ctrl: ControllerTrait, x: Int, y: Int) extends CommandTrait(observerID_):
  
  override def doStep(): Boolean =
    val f = ctrl.gb.getFieldAt(x, y)
    if (!f.isOpened) then
      val injector = Guice.createInjector(WinesmeeperModule)
      val fMake: (Boolean, Boolean, Boolean) => FieldTrait = injector.instance
      ctrl.gb = ctrl.gb.updateField(x, y, fMake(f.isBomb, f.isOpened, !f.isFlag))
      true
    else false

  override def undoStep(): Boolean = doStep()

  override def redoStep(): Boolean = doStep()

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

