package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.Board

trait GameState:
    val context: Controller
    def gameState: String
    def inGame: Boolean = false
    def turn(cmd: String, x: Int, y: Int): Boolean = false
    def changeState(state: String): Unit = context.state = CORStatRunning.changeState(state, context)

class Running(override val context: Controller) extends GameState:
    override def gameState: String = "running"
    override def inGame: Boolean = true

    override def turn(cmd: String, x: Int, y: Int): Boolean =
      if !context.gb.in(x, y) then false
      else
        context.undo.doCmd(cmd, x, y)

class Won(override val context: Controller) extends GameState:
    override def gameState: String = "win"

class Lost(override val context: Controller) extends GameState:
    override def gameState: String = "lose"