package de.htwg.winesmeeper.Controller.ImplSysCommands

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCORTrait}
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}

object UndoCmd extends SysCommandCORTrait:
  override val cmd: String = "undo"
  override val helpMsg: String = "discards the last turn"
  override val shortcut: KeyCode = KeyCode.Z
  override val next: SysCommandCORTrait = LastElemSysCommand
  override val specHelpMsg: String =
    """undo:
      |  discards your latest action!
      |
      |redo <count>:
      |  discards your latest <count> actions!
      |""".stripMargin

  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    val count: Int = Try(params(1).toInt) match
      case Failure(exception) => 1
      case Success(value) => value
    for i <- 1 to count do
      ctrl.undo.undoStep()
    ctrl.notifyObservers()
    None




  