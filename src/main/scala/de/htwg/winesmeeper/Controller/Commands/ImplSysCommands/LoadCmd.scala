package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.{TurnCommandTrait, SysCommandCORTrait, TurnCmdManagerTrait}

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
    val out = if params.length >= 2 then Config.saver.load(ctrl, params(1)) else Config.saver.load(ctrl)
    ctrl.notifyObservers()
    out