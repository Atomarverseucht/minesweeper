package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.Controller.Commands.SysCommandCORTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Config
import javafx.scene.input.KeyCode


object SaveCmd extends SysCommandCORTrait:
  override val next: SysCommandCORTrait = UndoCmd
  override val cmd: String = "save"
  override val shortcut: KeyCode = KeyCode.S
  override val helpMsg: String = "saves your board"
  override val specHelpMsg: String =
    f"""save:
      |  saves the game at the standard file
      |save <fileName>:
      |  saves game at a given file (without the ending)
      |
      |active file format: ${Config.saver.formatName.toUpperCase}
      |""".stripMargin
  
  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    if params.length >= 2 then Config.saver.save(ctrl, params(1)) else Config.saver.save(ctrl)
    Some("Board saved")