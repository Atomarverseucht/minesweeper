package de.htwg.winesmeeper.Controller.ImplSysCommands

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCORTrait}
import javafx.scene.input.KeyCode

object HelpCmd extends SysCommandCORTrait:
  override val cmd: String = "help"
  override val helpMsg: String = "made this message"
  override val next: SysCommandCORTrait = LoadCmd
  override val specHelpMsg: String =
    """help:
      |  prints an overview of all commands
      |
      |help <cmd>:
      |  prints a specific help message for this message
      |""".stripMargin


  override def execute(ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    val command = if params.length > 1 then SysCommandManager.getAbstractCmd(params(1), ctrl) else None
    command match
      case Some(value) => Some(value.specHelpMsg)
      case None => Some(standardHelp(ctrl))

  private def standardHelp(ctrl: ControllerTrait): String =
   val sysCmds = SysCommandManager.firstSysCmd.listCmds
   val scString = (for cmd <- sysCmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   val cmds = ctrl.undo.listCmds
   val cmdString = (for cmd <- cmds yield s"  ${cmd.cmd}: ${cmd.helpMsg}").mkString("\n")
   s"Commands without parameters:\n$scString \n\nCommands with coordinates:\n$cmdString\n\nFor more help type: help + wanted command"

  override val shortcut: KeyCode = KeyCode.H



