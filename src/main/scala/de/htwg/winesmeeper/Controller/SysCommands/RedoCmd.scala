package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Commands.UndoManager
import de.htwg.winesmeeper.Controller.Controller

object RedoCmd extends SysCommandCOR:
  override val cmd: String = "redo"
  override val helpMsg: String = "redo your latest undo move"
  override val next: SysCommandCOR = SaveCmd
  override val specHelpMsg: String =
    """redo:
      |  make your last undo done!
      |
      |redo <count>:
      |  makes your last <count> undos done!
      |""".stripMargin
  
  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String =
    ctrl.undo.redoStep
    ""

  
