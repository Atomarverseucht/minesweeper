package de.htwg.winesmeeper.Controller.Commands

import de.htwg.winesmeeper.Controller.Controller
import scala.util.{Success, Failure}
import scala.collection.mutable.Stack

case class UndoManager(control: Controller):
  var undoStack: Stack[Command] = new Stack()
  var redoStack: Stack[Command] = new Stack()

  def doStep(cmd: Command): Boolean =
    val change = cmd.doStep()
    if change then
      undoStack.push(cmd)
      control.notifyObservers()
    change


  def undoStep =
    val step = undoStack.pop
    step.undoStep()
    redoStack.push(step)
    control.notifyObservers()


  def doCmd(cmd: String, x: Int, y: Int): Boolean =
    val command = CORFlag.buildCmd(cmd, x, y, control)
    command match
      case Success(value) => doStep(value)
      case _ => false