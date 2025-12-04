package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller

import java.nio.file.Files

object SaveCmd extends SysCommandCOR:
  override val next: SysCommandCOR = UndoCmd
  override val cmd: String = "save"
  override val helpMsg: String = "save your board"

  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String =
    Files.write(SysCommandManager.savedGame(params(1)), ctrl.toString.getBytes())
    "Board saved"
