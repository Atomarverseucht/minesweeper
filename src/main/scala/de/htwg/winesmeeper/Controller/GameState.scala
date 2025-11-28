package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.Board

trait GameState:
    val context: Controller
    def gameState: String
    def inGame: Boolean = false
    def turn(cmd: String, x: Int, y: Int): Boolean = false
    def changeState(state: String): GameState =
        state match
            case "win" => Won(context)
            case "lose" => Lost(context)
            case "running" => Running(context)
            case _ => throw IllegalStateException("No such state")

class Running(override val context: Controller) extends GameState:
    override def gameState: String = "running"
    override def inGame: Boolean = true

    override def turn(cmd: String, x: Int, y: Int): Boolean =
      if !context.gb.in(x, y) then false
      else
        val oldGB = context.gb
        val strat = stratList.filter(strategy => strategy.cmd == cmd)(0)
        context.gb = strat.execute(x, y, context)
        if oldGB != context.gb then
            context.notifyObservers();
            true
        else false

class Won(override val context: Controller) extends GameState:
    override def gameState: String = "win"

class Lost(override val context: Controller) extends GameState:
    override def gameState: String = "lose"