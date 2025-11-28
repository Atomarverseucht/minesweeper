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