package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.Implementation.Controller
import de.htwg.winesmeeper.Controller.SysCommands.AbstractCmdCOR

import scala.util.Try
import scala.util.Failure

trait Command:
  def doStep(): Boolean
  def undoStep(): Boolean
  def redoStep(): Boolean

  
trait CommandCOR extends AbstractCmdCOR: // für Hilfsmethode erbt CommandCOR und SysCommandCOR von einem Interface
  val cmd: String
  val helpMsg: String
  val next: CommandCOR
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command]
  def listCmds: List[CommandCOR] = this::next.listCmds
  def getCmd(cmd: String): Option[CommandCOR] = if cmd == this.cmd then Some(this) else next.getCmd(cmd)
  
object LastElemCmdCOR extends CommandCOR:
  override val next: CommandCOR = this
  override val cmd: String = ""
  override val helpMsg: String = ""
  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command] =  Failure(IllegalArgumentException())
  
  override def listCmds: List[CommandCOR] = Nil

  override def getCmd(cmd: String): Option[CommandCOR] = None

  override val specHelpMsg: String = ""
  
object CommandManager:
  val firstCommandCOR: CommandCOR = FlagCOR
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command] = firstCommandCOR.buildCmd(cmd, x, y, ctrl)
  


