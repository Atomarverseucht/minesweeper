package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.{Board, Field}
import java.nio.file.{Files, Paths}

object LoadCmd extends SysCommandCOR:
  override val cmd: String = "load"
  override val helpMsg: String = "Overrides the actual board with the saved file"
  override val next: SysCommandCOR = QuitCmd

  override def execute(ctrl: Controller): String =
    val savedVal = Files.readString(Paths.get("./person.txt")).split("\n")
    val savedVals = savedVal.map(sv => sv.split(": ")(1))
    if savedVals(0) != de.htwg.winesmeeper.BuildInfo.version then
      s"not the same version \n Saved game version: ${savedVals(0)} != ${de.htwg.winesmeeper.BuildInfo.version}"
    else
      ctrl.gb = getBoard(savedVals(2))
      ctrl.changeState(savedVals(1))
      ctrl.notifyObservers()
      "loaded new game"

  def getBoard(boardString: String): Board =
    val vecBuild = boardString.split("\\), Vector\\(")
    val vector = (for vec <- vecBuild yield
      val victorS = vec.replace("Vector(","").split("Field\\(")
      (for fieldS <- victorS if fieldS != "" yield
        val boolS = fieldS.replace("\\)", "").split(",")
        val boolVal = for bs <- boolS yield
          if bs == "true" then true
          else false
        Field(boolVal(0), boolVal(1), boolVal(2))).toVector).toVector
    Board(vector)