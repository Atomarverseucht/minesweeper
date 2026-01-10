package de.htwg.winesmeeper.Controller.ImplTurnCommands

import de.htwg.winesmeeper.Controller.{CommandCORTrait, CommandTrait, ControllerTrait, TurnCmdManagerTrait}

import scala.util.{Failure, Success, Try}
import scala.collection.mutable.Stack

case class UndoManager (control: ControllerTrait) extends TurnCmdManagerTrait:
  private val undoStack: Stack[CommandTrait] = new Stack()
  private val redoStack: Stack[CommandTrait] = new Stack()
  val firstCommandCOR: CommandCORTrait = FlagCOR
  
  private def doStep(cmd: CommandTrait): Try[String] =
    val step = cmd.doStep()
    if step.isSuccess then
      undoStack.push(cmd)
      control.notifyObservers()
    step

  override def redoStep(): Unit =
    val cmd = redoStack.pop
    cmd.redoStep()
    undoStack.push(cmd)

  override def undoStep(): Unit =
    val step = undoStack.pop
    step.undoStep()
    redoStack.push(step)


  override def doCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
    firstCommandCOR.buildCmd(observerID,cmd, x, y, control) match
      case Success(value) => doStep(value)
      case Failure(value) => Failure(value)

  override def getStacks: (Stack[CommandTrait], Stack[CommandTrait]) = (undoStack.clone, redoStack.clone)
  
  override def overrideStacks(undoSt: Stack[CommandTrait], redoSt: Stack[CommandTrait]): Unit =
    undoStack.popAll()
    redoStack.popAll()
    for element <- undoSt do
      undoStack.push(element)
    for elementR <- redoSt do 
      redoStack.push(elementR)

  override def listCmds: List[CommandCORTrait] = firstCommandCOR.listCmds
  
  override def getCmd(cmd: String): Option[CommandCORTrait] = firstCommandCOR.getCmd(cmd)

  override def startCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
    val command = firstCommandCOR.buildCmd(observerID, cmd, x, y, control)
    command match
      case Success(value) => value.startStep()
      case Failure(value) => Failure(value)

  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait] =
    firstCommandCOR.buildCmd(observerID, cmd, x, y, ctrl)