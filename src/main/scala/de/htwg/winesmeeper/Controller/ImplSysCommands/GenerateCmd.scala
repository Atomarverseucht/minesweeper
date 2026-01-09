package de.htwg.winesmeeper.Controller.ImplSysCommands

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCORTrait}
import de.htwg.winesmeeper.Model.BoardTrait
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}
import de.htwg.winesmeeper.WinesmeeperModule
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions.*

import scala.collection.mutable.Stack

object GenerateCmd extends SysCommandCORTrait:
  override val cmd: String = "generate"
  override val next: SysCommandCORTrait = HelpCmd
  override val shortcut: KeyCode = KeyCode.G

  override def execute(observerID: Int, ctrl: ControllerTrait, params: Vector[String]): Option[String] =
    Try{
      val injector = Guice.createInjector(WinesmeeperModule)
      val bMake: (Int, Int, Int, Int, Int) => BoardTrait = injector.instance
      val gb = bMake(params(1).toInt, params(2).toInt, params(3).toInt, params(4).toInt, params(5).toInt)
      ctrl.gb = gb
      ctrl.changeState("Running")
      ctrl.turn(-1, "open", Try(params(3).toInt), Try(params(4).toInt))
      ctrl.undo.overrideStacks(Stack(), Stack())
    } match
      case Success(_) => Some("Generated!")
      case Failure(_) =>
        ctrl.generate(observerID); None

  override val helpMsg: String = "generates a new Board"
  override val specHelpMsg: String = """generate:
                                       |  starts the generation of a board
                                       |generate <x-size> <y-size> <x-start> <y-start> <bomb-count>:
                                       |  generates a board with the given parameters
                                       |
                                       |generate is not undo-able!
                                       |""".stripMargin