package de.htwg.winesmeeper.Controller.TurnCommands

import de.htwg.winesmeeper.Controller.{Command, CommandCOR, ControllerTrait, TurnCmdManagerTrait}

import scala.collection.mutable
import scala.util.{Success, Try}
import scala.collection.mutable.Stack

case class UndoManager(control: ControllerTrait) extends TurnCmdManagerTrait:
  private val undoStack: mutable.Stack[Command] = new Stack()
  private val redoStack: mutable.Stack[Command] = new Stack()
  val firstCommandCOR: CommandCOR = FlagCOR
  
  private def doStep(cmd: Command): Boolean =
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


  override def doCmd(cmd: String, x: Int, y: Int): Boolean =
    val command = FlagCOR.buildCmd(cmd, x, y, control)
    command match
      case Success(value) => doStep(value)
      case _ => false

  override def getStacks: (Stack[Command], Stack[Command]) = (undoStack.clone, redoStack.clone)
  
  override def overrideStacks(undoSt: Stack[Command], redoSt: Stack[Command]): Unit =
    undoStack.popAll()
    redoStack.popAll()
    for element <- undoSt do
      undoStack.push(element)
    for elementR <- redoSt do 
      redoStack.push(elementR)

  override def listCmds: List[CommandCOR] = firstCommandCOR.listCmds
  
  override def getCmd(cmd: String): Option[CommandCOR] = firstCommandCOR.getCmd(cmd)

  def buildCmd(cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[Command] = 
    firstCommandCOR.buildCmd(cmd, x, y, ctrl)