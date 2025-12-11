package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Commands.{Command, CommandCOR}
import de.htwg.winesmeeper.Controller.Controller
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}

object UndoCmd extends SysCommandCOR:
  override val cmd: String = "undo"
  override val helpMsg: String = "discards the last turn"
  override val shortcut: KeyCode = KeyCode.Z
  override val next: SysCommandCOR = LastElemSysCommand
  override val specHelpMsg: String =
    """undo:
      |  discards your latest action!
      |
      |redo <count>:
      |  discards your latest <count> actions!
      |""".stripMargin

  override def execute(ctrl: Controller, params: Vector[String]): String =
    val count: Int = Try(params(1).toInt) match
      case Failure(exception) => 1
      case Success(value) => value
    for i <- 1 to count do
      ctrl.undo.undoStep
    ctrl.notifyObservers()
    "undo"




  