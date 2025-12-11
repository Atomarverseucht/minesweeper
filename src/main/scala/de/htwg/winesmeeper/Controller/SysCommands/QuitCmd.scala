package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
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
  
  override def execute(ctrl: Controller, params: Vector[String]): Option[String] = 
    ctrl.isQuitted = true
    Some("Successfully quitted the game")

  

  
  