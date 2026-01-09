package de.htwg.winesmeeper.Controller

import scala.collection.mutable.Stack
import scala.util.Try

trait TurnCmdManagerTrait:
  def doCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String]
  
  def startCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String]

  def listCmds: List[CommandCORTrait]
  
  def getCmd(cmd: String): Option[CommandCORTrait]
  
  def redoStep(): Unit
  
  def undoStep(): Unit
  
  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait]
  
  def getStacks: (Stack[CommandTrait], Stack[CommandTrait])

  def overrideStacks(undoSt: Stack[CommandTrait], redoSt: Stack[CommandTrait]): Unit

trait CommandTrait(val observerID: Int):
  def doStep(): Try[String]
  def undoStep(): String
  def redoStep(): String
  def startStep(): Try[String]

trait CommandCORTrait extends AbstractCmdCOR: // für Hilfsmethode erbt CommandCOR und SysCommandCOR von einem Interface
  val cmd: String
  val helpMsg: String
  val next: CommandCORTrait
  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait]
  def listCmds: List[CommandCORTrait] = this::next.listCmds
  def getCmd(cmd: String): Option[CommandCORTrait] = if cmd == this.cmd then Some(this) else next.getCmd(cmd)

trait AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val specHelpMsg: String