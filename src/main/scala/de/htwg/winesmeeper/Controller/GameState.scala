package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.Board

trait GameState:
    val context: Controller
    def gameState: String
    def inGame: Boolean
    def changeState(state: String): GameState =
        state match
            case "win" => Won(context)
            case "lose" => Lost(context)
            case "running" => Running(context)
            case _ => throw IllegalStateException("No such state")

class Running(override val context: Controller) extends GameState:
    override def gameState: String = "running"

    override def inGame: Boolean = true

class Won(override val context: Controller) extends GameState:
    override def gameState: String = "win"

    override def inGame: Boolean = false

class Lost(override val context: Controller) extends GameState:
    override def gameState: String = "lose"
    override def inGame: Boolean = false