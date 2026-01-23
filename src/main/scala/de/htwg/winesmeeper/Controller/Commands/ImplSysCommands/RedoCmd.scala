package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.Controller.Commands.SysCommandCORTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}

object RedoCmd extends SysCommandCORTrait:
  override val cmd: String = "redo"
  override val shortcut: KeyCode = KeyCode.Y
  override val helpMsg: String = "redo your latest undo move"
  override val next: SysCommandCORTrait = SaveCmd
  override val specHelpMsg: String =
    """redo:
      |  make your last undo done!
      |
      |redo <count>:
      |  makes your last <count> undos done!
      |""".stripMargin

  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    val count: Int = Try(params(1).toInt) match
      case Failure(exception) => 1
      case Success(value) => value
    for i <- 1 to count do
      Try(ctrl.undo.redoStep())
    ctrl.notifyObservers()
    None