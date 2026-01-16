package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.ControllerTrait

import scala.collection.mutable.Stack
import scala.util.Try
import scala.xml.Node
import play.api.libs.json._

trait TurnCmdManagerTrait:
  def doCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String]
  def startCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String]
  def listCmds: List[TurnCommandSingletonTrait]
  def getCmd(cmd: String): Option[TurnCommandSingletonTrait]
  def redoStep(): Unit
  def undoStep(): Unit
  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[TurnCommandTrait]
  def getStacks: (Stack[TurnCommandTrait], Stack[TurnCommandTrait])
  def overrideStacks(undoSt: Stack[TurnCommandTrait], redoSt: Stack[TurnCommandTrait]): Unit

trait TurnCommandTrait:
  def doStep(): Try[String]
  def undoStep(): String
  def redoStep(): String
  def startStep(): Try[String]
  def toXML: Node
  def toJSON: JsObject

trait TurnCommandSingletonTrait extends AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val next: TurnCommandSingletonTrait
  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[TurnCommandTrait]
  def listCmds: List[TurnCommandSingletonTrait] = this::next.listCmds
  def getCmd(cmd: String): Option[TurnCommandSingletonTrait] = if cmd == this.cmd then Some(this) else next.getCmd(cmd)
  def fromXML(xml: Node, ctrl: ControllerTrait): TurnCommandTrait
  def fromJSON(json: JsValue, ctrl: ControllerTrait): TurnCommandTrait 