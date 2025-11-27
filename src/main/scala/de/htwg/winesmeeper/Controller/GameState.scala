package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.Board

trait GameState:
    val context: Controller
    def gameState: String

class Running(override val context: Controller) extends GameState:
    override def gameState: String = "running"

class Won(override val context: Controller) extends GameState:
    override def gameState: String = "win"

class Lost(override val context: Controller) extends GameState:
    override def gameState: String = "lose"