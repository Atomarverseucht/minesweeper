package de.htwg.winesmeeper.Controller.Commands.ImplTurnCommands

import de.htwg.winesmeeper.Controller.Commands.{TurnCommandSingletonTrait, TurnCommandTrait, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Controller.ControllerTrait

import scala.util.{Failure, Success, Try}
import scala.collection.mutable.Stack

case class UndoManager (control: ControllerTrait) extends TurnCmdManagerTrait:
  private val undoStack: Stack[TurnCommandTrait] = new Stack()
  private val redoStack: Stack[TurnCommandTrait] = new Stack()
  private val firstCommandCOR: TurnCommandSingletonTrait = FlagSingleton
  
  private def doStep(cmd: TurnCommandTrait): Try[String] =
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
    buildCmd(observerID,cmd, x, y) match
      case Success(value) => doStep(value)
      case Failure(value) => Failure(value)

  override def getStacks: (Stack[TurnCommandTrait], Stack[TurnCommandTrait]) = (undoStack.clone, redoStack.clone)
  
  override def overrideStacks(undoSt: Stack[TurnCommandTrait], redoSt: Stack[TurnCommandTrait]): Unit =
    undoStack.popAll()
    redoStack.popAll()
    for element <- undoSt do
      undoStack.push(element)
    for elementR <- redoSt do 
      redoStack.push(elementR)

  override def listCmds: List[TurnCommandSingletonTrait] = firstCommandCOR.listCmds
  
  override def getCmd(cmd: String): Option[TurnCommandSingletonTrait] = firstCommandCOR.getCmd(cmd)

  override def startCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
    val command = firstCommandCOR.buildCmd(observerID, cmd, x, y, control)
    command match
      case Success(value) => value.startStep()
      case Failure(value) => Failure(value)

  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int): Try[TurnCommandTrait] =
    firstCommandCOR.buildCmd(observerID, cmd, x, y, control)