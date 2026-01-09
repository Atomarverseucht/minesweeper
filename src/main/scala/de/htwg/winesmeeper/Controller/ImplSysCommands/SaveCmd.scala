package de.htwg.winesmeeper.Controller.ImplSysCommands

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCORTrait}
import javafx.scene.input.KeyCode

import scala.util.Try
import java.nio.file.Files

object SaveCmd extends SysCommandCORTrait:
  override val next: SysCommandCORTrait = UndoCmd
  override val cmd: String = "save"
  override val shortcut: KeyCode = KeyCode.S
  override val helpMsg: String = "saves your board"
  override val specHelpMsg: String =
    """save:
      |  saves the game at the standard file
      |save <fileName>:
      |  saves game at a given file (without the ending)
      |""".stripMargin
  
  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    Files.write(SysCommandManager.savedGame(Try(params(1))), ctrl.toString.getBytes())
    Some("Board saved")

  

  
