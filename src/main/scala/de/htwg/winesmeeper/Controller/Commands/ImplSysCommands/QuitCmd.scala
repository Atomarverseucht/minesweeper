package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.Controller.Commands.SysCommandCORTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import javafx.scene.input.KeyCode

object QuitCmd extends SysCommandCORTrait:
  override val cmd: String = "quit"
  override val helpMsg: String = "end the game"
  override val next: SysCommandCORTrait = RedoCmd
  override val shortcut: KeyCode = KeyCode.Q
  override val specHelpMsg: String =
    """quit:
      |  closes the game
      |""".stripMargin
  
  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    System.exit(0)
    None