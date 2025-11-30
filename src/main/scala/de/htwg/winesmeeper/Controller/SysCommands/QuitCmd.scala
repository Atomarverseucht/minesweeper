package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller

object QuitCmd extends SysCommandCOR:
  override val cmd: String = "quit"
  override val helpMsg: String = "end the game"
  override val next: SysCommandCOR = RedoCmd

  override def execute(ctrl: Controller): String = 
    ctrl.isQuitted = true
    "Successfully quitted the game"
  