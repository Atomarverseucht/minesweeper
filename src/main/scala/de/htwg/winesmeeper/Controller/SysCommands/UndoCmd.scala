package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Commands.{Command, CommandCOR}
import de.htwg.winesmeeper.Controller.Controller

import scala.util.Try

object UndoCmd extends SysCommandCOR:
  override val cmd: String = "undo"
  override val helpMsg: String = "discards the last turn"
  override val next: SysCommandCOR = LastElemSysCommand

  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String = 
    ctrl.undo.undoStep
    "undo"
  