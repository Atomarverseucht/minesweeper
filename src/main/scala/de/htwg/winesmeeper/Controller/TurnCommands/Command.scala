package de.htwg.winesmeeper.Controller.TurnCommands

import de.htwg.winesmeeper.Controller.{Command, CommandCOR, ControllerTrait}
import de.htwg.winesmeeper.Controller.SysCommands.AbstractCmdCOR

import scala.util.Try
import scala.util.Failure
  
object LastElemCmdCOR extends CommandCOR:
  override val next: CommandCOR = this
  override val cmd: String = ""
  override val helpMsg: String = ""
  override def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command] =  Failure(IllegalArgumentException())
  
  override def listCmds: List[CommandCOR] = Nil

  override def getCmd(cmd: String): Option[CommandCOR] = None

  override val specHelpMsg: String = ""
