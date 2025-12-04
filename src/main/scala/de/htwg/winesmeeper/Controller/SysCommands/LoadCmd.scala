package de.htwg.winesmeeper.Controller.SysCommands

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Controller.Commands.{CommandManager, Command}
import de.htwg.winesmeeper.Model.{Board, Field}
import java.nio.file.{Files, Paths}
import scala.collection.mutable.Stack
import scala.util.{Try, Success, Failure}

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
    val savedVals = savedVal.map(sv => sv.split(": ")(1))
    if savedVals(0) != de.htwg.winesmeeper.BuildInfo.version then
      s"not the same version \n Saved game version: ${savedVals(0)} != ${de.htwg.winesmeeper.BuildInfo.version}"
    else
      ctrl.gb = getBoard(savedVals(2))
      ctrl.changeState(savedVals(1))
      val unStack = getStacks(savedVals(3), ctrl)
      val reStack = getStacks(savedVals(4), ctrl)
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

  private def getStacks(input: String, ctrl: Controller): Stack[Command] =
    val inputStack = new Stack[Command]
    val inputs = input.split("\\), ")
    for cmdS <- inputs do
      val cmd = cmdS.split("\\(")
      val inputInt = cmd(1).replace(")","").split(", ")
      val element = CommandManager.buildCmd(cmd(0), inputInt(0).toInt, inputInt(1).toInt, ctrl) match
        case Success(value) => value
        case Failure(_) => throw IllegalArgumentException()
      inputStack.push(element)
    inputStack


