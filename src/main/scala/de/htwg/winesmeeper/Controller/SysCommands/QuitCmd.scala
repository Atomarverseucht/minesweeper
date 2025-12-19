package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCOR}
import javafx.scene.input.KeyCode

object QuitCmd extends SysCommandCOR:
  override val cmd: String = "quit"
  override val helpMsg: String = "end the game"
  override val next: SysCommandCOR = RedoCmd
  override val shortcut: KeyCode = KeyCode.Q
  override val specHelpMsg: String =
    """quit:
      |  closes the game
      |""".stripMargin
  
  override def execute(ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    System.exit(0)
    Some("Successfully quitted the game")



  
  