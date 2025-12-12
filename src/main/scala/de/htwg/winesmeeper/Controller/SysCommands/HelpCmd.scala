package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Controller.Commands
import de.htwg.winesmeeper.Controller.Commands.FlagCOR
import javafx.scene.input.KeyCode

object HelpCmd extends SysCommandCOR:
  override val cmd: String = "help"
  override val helpMsg: String = "made this message"
  override val next: SysCommandCOR = LoadCmd
  override val specHelpMsg: String =
    """help:
      |  prints an overview of all commands
      |
      |help <cmd>:
      |  prints a specific help message for this message
      |""".stripMargin


  override def execute(ctrl: Controller, params: Vector[String]): Option[String] =
    val command = if params.length > 1 then SysCommandManager.getAbstractCmd(params(1)) else None
    command match
      case Some(value) => Some(value.specHelpMsg)
      case None => Some(standardHelp)

  private def standardHelp: String =
   val sysCmds = SysCommandManager.firstSysCmd.listCmds
   val scString = (for cmd <- sysCmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   val cmds = FlagCOR.listCmds
   val cmdString = (for cmd <- cmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   s"Commands without parameters:\n$scString \n\nCommands with coordinates:\n$cmdString\n\nFor more help type: help + wanted command"

  override val shortcut: KeyCode = KeyCode.H



