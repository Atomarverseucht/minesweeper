package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.BuildInfo.version
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.{SysCommandCORTrait, TurnCmdManagerTrait, TurnCommandTrait}
import javafx.scene.input.KeyCode

import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}

object LoadCmd extends SysCommandCORTrait:
  override val cmd: String = "load"
  override val helpMsg: String = "Overrides the actual board with the saved file"
  override val next: SysCommandCORTrait = QuitCmd
  override val shortcut: KeyCode = KeyCode.L
  override val specHelpMsg: String =
    f"""load:
      |  overrides game with the standard file
      |load <fileName>:
      |  overrides game with a given file (without the ending)
      |
      |active file format: ${Config.saver.formatName.toUpperCase}
      |""".stripMargin

  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    ctrl.doSysCmd(observerID, "save", Vector("", "loadBackup"))
    val file = if params.length >= 2 then params(1) else Config.standardFileName
    Try(Config.saver.load(ctrl, file)) match
      case Success(data) =>
        ctrl.gb = data.board
        ctrl.changeState(data.state)
        ctrl.undo.overrideStacks(data.undoStack, data.redoStack)
        ctrl.notifyObservers()
        Some(f"Loaded: $file.${Config.saver.formatName} (v${data.version})" +
          f"\n  For bringing back the old file, type: 'load loadBackup'\n  active version: $version")
      case Failure(ex) =>
        Some(f"File wasn't compatible: ${ex.getMessage}")
