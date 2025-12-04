package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.Controller

import scala.collection.mutable
import scala.util.{Failure, Success}
import scala.collection.mutable.Stack

case class UndoManager(control: Controller):
  private val undoStack: mutable.Stack[Command] = new Stack()
  private val redoStack: mutable.Stack[Command] = new Stack()

  def doStep(cmd: Command): Boolean =
    val change = cmd.doStep()
    if change then
      undoStack.push(cmd)
      control.notifyObservers()
    change

  def redoStep =
    val cmd = redoStack.pop
    cmd.redoStep()
    undoStack.push(cmd)
    control.notifyObservers()


  def undoStep =
    val step = undoStack.pop
    step.undoStep()
    redoStack.push(step)
    control.notifyObservers()


  def doCmd(cmd: String, x: Int, y: Int): Boolean =
    val command = FlagCOR.buildCmd(cmd, x, y, control)
    command match
      case Success(value) => doStep(value)
      case _ => false

  def getStacks: (Stack[Command], Stack[Command]) = (undoStack.clone, redoStack.clone)
  
  def overrideStacks(undoSt: Stack[Command], redoSt: Stack[Command]): Unit =
    undoStack.popAll()
    redoStack.popAll()
    for element <- undoSt do
      undoStack.push(element)
    for elementR <- redoSt do 
      redoStack.push(elementR)