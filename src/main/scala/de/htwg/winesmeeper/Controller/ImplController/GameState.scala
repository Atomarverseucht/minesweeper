package de.htwg.winesmeeper.Controller.ImplController

import scala.util.{Try, Failure}

trait GameState:
    val context: Controller
    def gameState: String
    def inGame: Boolean = false
    def turn(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
        Failure(IllegalArgumentException(f"cannot make turn in state: $gameState"))
    def changeState(state: String): Unit = context.state = CORStatRunning.changeState(state, context)

class Running(override val context: Controller) extends GameState:
    override def gameState: String = "running"
    override def inGame: Boolean = true

    override def turn(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
      if !context.gb.in(x, y) then Failure(IndexOutOfBoundsException(f"$x or $y is out of bound!"))
      else
        context.undo.doCmd(observerID, cmd, x, y)

class Won(override val context: Controller) extends GameState:
    override def gameState: String = "win"

class Lost(override val context: Controller) extends GameState:
    override def gameState: String = "lost"

class Start(override val context: Controller) extends GameState:
    override def gameState: String = "start"

    override def inGame: Boolean = true

    override def turn(observerID: Int, cmd: String, x: Int, y: Int): Try[String] =
        if !context.gb.in(x, y) then Failure(IndexOutOfBoundsException(f"$x or $y is out of bound!"))
        else
            context.undo.startCmd(observerID, cmd, x, y)