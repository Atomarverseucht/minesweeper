package de.htwg.winesmeeper.Controller

import scala.collection.mutable.Stack
import scala.util.Try

trait TurnCmdManagerTrait:
  def doCmd(cmd: String, x: Int, y: Int): Boolean

  def getStacks: (Stack[Command], Stack[Command])

  def overrideStacks(undoSt: Stack[Command], redoSt: Stack[Command]): Unit
  
  def listCmds: List[CommandCOR]
  
  def getCmd(cmd: String): Option[CommandCOR]
  
  def redoStep(): Unit
  
  def undoStep(): Unit
  
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command]
  
val standardUndo = TurnCommands.UndoManager

trait Command:
  def doStep(): Boolean
  def undoStep(): Boolean
  def redoStep(): Boolean

trait CommandCOR extends AbstractCmdCOR: // für Hilfsmethode erbt CommandCOR und SysCommandCOR von einem Interface
  val cmd: String
  val helpMsg: String
  val next: CommandCOR
  def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command]
  def listCmds: List[CommandCOR] = this::next.listCmds
  def getCmd(cmd: String): Option[CommandCOR] = if cmd == this.cmd then Some(this) else next.getCmd(cmd)

trait AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val specHelpMsg: String