package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import javafx.scene.input.KeyCode

import scala.util.Try
import java.nio.file.Files

object SaveCmd extends SysCommandCOR:
  override val next: SysCommandCOR = UndoCmd
  override val cmd: String = "save"
  override val shortcut: KeyCode = KeyCode.S
  override val helpMsg: String = "saves your board"
  override val specHelpMsg: String =
    """save:
      |  saves the game at the standard file
      |save <fileName>:
      |  saves game at a given file (without the ending)
      |""".stripMargin
  
  override def execute(ctrl: Controller, params: Vector[String]): Option[String] =
    Files.write(SysCommandManager.savedGame(Try(params(1))), ctrl.toString.getBytes())
    Some("Board saved")

  

  
