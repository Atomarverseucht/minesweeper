package de.htwg.winesmeeper.Controller.Commands.ImplTurnCommands

import de.htwg.winesmeeper.Controller.Commands.{TurnCommandSingletonTrait, TurnCommandTrait}
import de.htwg.winesmeeper.Controller.ControllerTrait
import play.api.libs.json.{JsObject, JsValue}

import scala.util.{Failure, Try}
import scala.xml.Node
  
object zLastElemTurnCmdSingleton extends TurnCommandSingletonTrait:
  override val next: TurnCommandSingletonTrait = this
  override val cmd: String = ""
  override val helpMsg: String = ""
  override def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[TurnCommandTrait] =  Failure(IllegalArgumentException())
  
  override def listCmds: List[TurnCommandSingletonTrait] = Nil

  override def getCmd(cmd: String): Option[TurnCommandSingletonTrait] = None

  override val specHelpMsg: String = ""

  override def fromXML(xml: Node, ctrl: ControllerTrait): TurnCommandTrait = throw IllegalArgumentException("No such command!")

  override def fromJSON(json: JsValue, ctrl: ControllerTrait): TurnCommandTrait = throw IllegalArgumentException("No such command!")
