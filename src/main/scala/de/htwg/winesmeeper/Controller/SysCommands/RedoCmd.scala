package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Commands.UndoManager
import de.htwg.winesmeeper.Controller.Controller
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}

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

  override def execute(ctrl: Controller, params: Vector[String]): String =
    val count: Int = Try(params(1).toInt) match
      case Failure(exception) => 1
      case Success(value) => value
    for i <- 1 to count do
      ctrl.undo.redoStep
    ctrl.notifyObservers()
    ""

  override val shortcut: KeyCode = KeyCode.Y


