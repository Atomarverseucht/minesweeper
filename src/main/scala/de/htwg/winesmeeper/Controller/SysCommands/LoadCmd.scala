package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Controller.Commands.{Command, CommandManager}
import de.htwg.winesmeeper.Model.{Board, Field}

import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}

object LoadCmd extends SysCommandCOR:
  override val cmd: String = "load"
  override val helpMsg: String = "Overrides the actual board with the saved file"
  override val next: SysCommandCOR = QuitCmd
  override val specHelpMsg: String =
    """load:
      |  overrides game with the standard file
      |load <fileName>:
      |  overrides game with a given file (without the ending)
      |""".stripMargin

  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String =
    val savedVal = Files.readString(SysCommandManager.savedGame(Try(params(1)))).split("\n")
    val savedVals = Try(savedVal.map(sv => sv.split(": ")(1)))
    if savedVals.get(0) != de.htwg.winesmeeper.BuildInfo.version then
      s"not the same version \n Saved game version: ${savedVals.get(0)} != ${de.htwg.winesmeeper.BuildInfo.version}"
    else
      ctrl.gb = getBoard(savedVals.get(2))
      ctrl.changeState(savedVals.get(1))

      val unStack = getStacks(Try(savedVals.get(3).replace("§","")), ctrl)
      val reStack = getStacks(Try(savedVals.get(4).replace("§","")), ctrl)
      ctrl.undo.overrideStacks(unStack, reStack)
      ctrl.notifyObservers()
      "loaded new game"

  private def getBoard(boardString: String): Board =
    val vecBuild = boardString.split("\\), Vector\\(")
    val vector = (for vec <- vecBuild yield
      val victorS = vec.replace("Vector(","").split("Field\\(")
      (for fieldS <- victorS if fieldS != "" yield
        val boolS = fieldS.replace(")", "").split(",")
        val boolVal = for bs <- boolS yield
          if bs == "true" then true
          else false
        Field(boolVal(0), boolVal(1), boolVal(2))).toVector).toVector
    Board(vector)

  def getStacks(input: Try[String], ctrl: Controller): Stack[Command] =
    val inputStack = new Stack[Command]
    input match {
      case Success(value) =>
        if value != "" then
          val inputs = value.split("\\), ")
          for cmdS <- inputs do
            val cmd = cmdS.split("\\(")
            val inputInt = cmd(1).replace(")","").split(", ")
            val element = CommandManager.buildCmd(cmd(0), inputInt(0).toInt, inputInt(1).toInt, ctrl).get
            inputStack.push(element)

      case Failure(exception) =>
    }

    inputStack


