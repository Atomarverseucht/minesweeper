package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.Controller
import scala.util.Try
import scala.util.Failure

trait Command:
  def doStep(): Boolean
  def undoStep(): Boolean
  def redoStep(): Boolean

  
trait CommandCOR:
  val cmd: String
  val helpMsg: String
  val next: CommandCOR
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command]
  def listCmds: List[CommandCOR] = this::next.listCmds
  
object LastElemCmdCOR extends CommandCOR:
  override val next: CommandCOR = this
  override val cmd: String = ""
  override val helpMsg: String = ""
  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: Controller): Try[Command] =  Failure(IllegalArgumentException())

  override def listCmds: List[CommandCOR] = Nil


