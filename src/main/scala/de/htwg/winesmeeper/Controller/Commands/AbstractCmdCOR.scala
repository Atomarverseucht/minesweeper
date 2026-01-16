package de.htwg.winesmeeper.Controller.Commands


trait AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val specHelpMsg: String