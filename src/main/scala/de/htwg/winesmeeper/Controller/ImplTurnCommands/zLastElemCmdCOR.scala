package de.htwg.winesmeeper.Controller.ImplTurnCommands

import de.htwg.winesmeeper.Controller.{CommandTrait, CommandCORTrait, ControllerTrait}

import scala.util.{Try, Failure}
  
object zLastElemCmdCOR extends CommandCORTrait:
  override val next: CommandCORTrait = this
  override val cmd: String = ""
  override val helpMsg: String = ""
  override def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait] =  Failure(IllegalArgumentException())
  
  override def listCmds: List[CommandCORTrait] = Nil

  override def getCmd(cmd: String): Option[CommandCORTrait] = None

  override val specHelpMsg: String = ""
