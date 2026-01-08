package de.htwg.winesmeeper.Controller.ImplSysCommands

import de.htwg.winesmeeper.Controller.{CommandTrait, ControllerTrait, SysCommandCORTrait, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.WinesmeeperModule

import javafx.scene.input.KeyCode
import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.util.{Failure, Success, Try}
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._

object LoadCmd extends SysCommandCORTrait:
  override val cmd: String = "load"
  override val helpMsg: String = "Overrides the actual board with the saved file"
  override val next: SysCommandCORTrait = QuitCmd
  override val shortcut: KeyCode = KeyCode.L
  override val specHelpMsg: String =
    """load:
      |  overrides game with the standard file
      |load <fileName>:
      |  overrides game with a given file (without the ending)
      |""".stripMargin

  override def execute(ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    Try{
      val savedVal = Files.readString(SysCommandManager.savedGame(Try(params(1)))).split("\n")
      val savedVals = Try(savedVal.map(sv => sv.split(": ")(1)))
      val isForced: Boolean = params.size >= 3 && params(2).toLowerCase.equals("forced")
      if !isForced && savedVals.get(0) != de.htwg.winesmeeper.BuildInfo.version then
        Some(s"not the same version \n Saved game version: ${savedVals.get(0)} != ${de.htwg.winesmeeper.BuildInfo.version}")
      else
        ctrl.gb = getBoard(savedVals.get(2))
        ctrl.changeState(savedVals.get(1))

        val unStack = getStacks(Try(savedVals.get(3).replace("§","")), ctrl)
        val reStack = getStacks(Try(savedVals.get(4).replace("§","")), ctrl)
        ctrl.undo.overrideStacks(unStack, reStack)
        ctrl.notifyObservers()
        Some("loaded new game")}
    match
      case Success(value) => value
      case Failure(ex) => None


  private def getBoard(boardString: String): BoardTrait =
    val injector = Guice.createInjector(WinesmeeperModule)
    val fMake: (Boolean, Boolean, Boolean) => FieldTrait = injector.instance
    val bMake: Vector[Vector[FieldTrait]] => BoardTrait = injector.instance
    val vecBuild = boardString.split("\\), Vector\\(")
    val vector = (for vec <- vecBuild yield
      val victorS = vec.replace("Vector(","").split("Field\\(")
      (for fieldS <- victorS if fieldS != "" yield
        val boolS = fieldS.replace(")", "").split(",")
        val boolVal = for bs <- boolS yield
          if bs == "true" then true
          else false
        fMake(boolVal(0), boolVal(1), boolVal(2))).toVector).toVector
    bMake(vector)

  def getStacks(input: Try[String], ctrl: ControllerTrait): Stack[CommandTrait] =
    val inputStack = new Stack[CommandTrait]
    input match {
      case Success(value) =>
        if value != "" then
          val inputs = value.split("\\), ")
          for cmdS <- inputs do
            val cmd = cmdS.split("\\(")
            val inputInt = cmd(1).replace(")","").split(", ")
            val element = ctrl.undo.buildCmd(inputInt(0).toInt, cmd(0), inputInt(1).toInt, inputInt(2).toInt, ctrl).get
            inputStack.push(element)

      case Failure(exception) =>
    }
    inputStack
