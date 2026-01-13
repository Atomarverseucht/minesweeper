package de.htwg.winesmeeper.Controller.Commands.ImplSysCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.BoardTrait
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.SysCommandCORTrait

import scala.collection.mutable.Stack

object GenerateCmd extends SysCommandCORTrait:
  override val cmd: String = "generate"
  override val next: SysCommandCORTrait = HelpCmd
  override val shortcut: KeyCode = KeyCode.G

  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    Try{
      ctrl.gb = Config.generateBoard(params(1).toInt, params(2).toInt, params(3).toInt, params(4).toInt, params(5).toInt)
      ctrl.changeState("running")
      ctrl.turn(observerID, "open", Try(params(3).toInt), Try(params(4).toInt))
      ctrl.undo.overrideStacks(Stack(), Stack())
    } match
      case Success(_) => Some("Generated!")
      case Failure(_) =>
        Try {
          Config.bombCount4Generate = params(3).toInt
          ctrl.gb = Config.startBoard(params(1).toInt, params(2).toInt)
          ctrl.changeState("start")
        } match
          case Success(_) => Some("Open the field to generate!")
          case Failure(_) => ctrl.generate(observerID); None


  override val helpMsg: String = "generates a new Board"
  override val specHelpMsg: String = """generate:
                                       |  starts the generation of a board
                                       |
                                       |generate <x-size> <y-size> <x-start> <y-start> <bomb-count>:
                                       |  generates a board with the given parameters
                                       |
                                       |generate <x-size> <y-size> <bomb-count>
                                       |
                                       |generate is not undo-able!
                                       |""".stripMargin