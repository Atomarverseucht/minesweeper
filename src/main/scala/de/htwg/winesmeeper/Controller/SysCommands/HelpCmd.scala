package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller

object HelpCmd extends SysCommandCOR:
  override val cmd: String = "help"
  override val helpMsg: String = "made this message"
  override val next: SysCommandCOR = QuitCmd

  override def execute(ctrl: Controller): String =
   val sysCmds = HelpCmd.listCmds
   val scString = (for cmd <- sysCmds yield s"${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   s"Commands without parameters:\n$scString"

