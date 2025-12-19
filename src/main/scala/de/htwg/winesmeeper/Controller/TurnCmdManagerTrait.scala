package de.htwg.winesmeeper.Controller

import scala.collection.mutable.Stack
import scala.util.Try

trait TurnCmdManagerTrait:
  def doCmd(cmd: String, x: Int, y: Int): Boolean

  def getStacks: (Stack[CommandTrait], Stack[CommandTrait])

  def overrideStacks(undoSt: Stack[CommandTrait], redoSt: Stack[CommandTrait]): Unit
  
  def listCmds: List[CommandCORTrait]
  
  def getCmd(cmd: String): Option[CommandCORTrait]
  
  def redoStep(): Unit
  
  def undoStep(): Unit
  
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait]

trait CommandTrait:
  def doStep(): Boolean
  def undoStep(): Boolean
  def redoStep(): Boolean

trait CommandCORTrait extends AbstractCmdCOR: // für Hilfsmethode erbt CommandCOR und SysCommandCOR von einem Interface
  val cmd: String
  val helpMsg: String
  val next: CommandCORTrait
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait]
  def listCmds: List[CommandCORTrait] = this::next.listCmds
  def getCmd(cmd: String): Option[CommandCORTrait] = if cmd == this.cmd then Some(this) else next.getCmd(cmd)

trait AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val specHelpMsg: String