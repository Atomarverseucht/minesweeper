package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import scala.util.Try
import java.nio.file.Files

object SaveCmd extends SysCommandCOR:
  override val next: SysCommandCOR = UndoCmd
  override val cmd: String = "save"
  override val helpMsg: String = "save your board"
  override val specHelpMsg: String =
    """save:
      |  overrides game with the standard file
      |save <fileName>:
      |  overrides game with a given file (without the ending)
      |""".stripMargin
  
  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String =
    Files.write(SysCommandManager.savedGame(Try(params(1))), ctrl.toString.getBytes())
    "Board saved"

  
