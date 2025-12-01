package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller

import java.nio.file.{Files, Paths}

object SaveCmd extends SysCommandCOR:
  override val next: SysCommandCOR = UndoCmd
  override val cmd: String = "save"
  override val helpMsg: String = "save your board"

  override def execute(ctrl: Controller): String =
    Files.write(Paths.get("./person.txt"), ctrl.toString.getBytes())
    "Board saved"
