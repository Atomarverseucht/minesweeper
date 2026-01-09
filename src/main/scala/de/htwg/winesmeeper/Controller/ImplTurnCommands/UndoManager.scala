package de.htwg.winesmeeper.Controller.ImplTurnCommands

import de.htwg.winesmeeper.Controller.{CommandTrait, CommandCORTrait, ControllerTrait, TurnCmdManagerTrait}

import jakarta.inject.Inject
import scala.util.{Success, Try}
import scala.collection.mutable.Stack

case class UndoManager @Inject() (control: ControllerTrait) extends TurnCmdManagerTrait:
  private val undoStack: Stack[CommandTrait] = new Stack()
  private val redoStack: Stack[CommandTrait] = new Stack()
  val firstCommandCOR: CommandCORTrait = FlagCOR
  
  private def doStep(cmd: CommandTrait): Boolean =
    val change = cmd.doStep()
    if change then
      undoStack.push(cmd)
      control.notifyObservers()
    change

  override def redoStep(): Unit =
    val cmd = redoStack.pop
    cmd.redoStep()
    undoStack.push(cmd)

  override def undoStep(): Unit =
    val step = undoStack.pop
    step.undoStep()
    redoStack.push(step)


  override def doCmd(observerID: Int, cmd: String, x: Int, y: Int): Boolean =
    val command = firstCommandCOR.buildCmd(observerID,cmd, x, y, control)
    command match
      case Success(value) => doStep(value)
      case _ => false

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

  def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[CommandTrait] = 
    firstCommandCOR.buildCmd(observerID, cmd, x, y, ctrl)