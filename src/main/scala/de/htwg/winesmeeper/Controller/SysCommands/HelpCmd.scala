package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Controller.Commands
import de.htwg.winesmeeper.Controller.Commands.CORFlag

object HelpCmd extends SysCommandCOR:
  override val cmd: String = "help"
  override val helpMsg: String = "made this message"
  override val next: SysCommandCOR = LoadCmd

  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String =
   val sysCmds = HelpCmd.listCmds
   val scString = (for cmd <- sysCmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   val cmds = CORFlag.listCmds
   val cmdString = (for cmd <- cmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   s"Commands without parameters:\n$scString \n\nCommands with coordinates:\n$cmdString"

