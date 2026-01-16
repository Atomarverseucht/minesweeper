package de.htwg.winesmeeper.Controller.ImplController

trait CORStat:
  val next: CORStat
  val stateCmd: String
  val state: Controller => GameState
  def changeState(stateString: String, context: Controller): GameState =
    if stateString == stateCmd then state(context) else next.changeState(stateString, context)

object CORStatRunning extends CORStat:
  override val next: CORStat = CORStatWon
  override val stateCmd: String = "running"
  override val state: Controller => GameState = Running(_)

object CORStatWon extends CORStat:
  override val next: CORStat  = CORStatLost
  override val stateCmd: String = "win"
  override val state: Controller => GameState = Won(_)

object CORStatLost extends CORStat:
  override val next: CORStat = CORStatStart
  override val stateCmd: String = "lost"
  override val state: Controller => GameState = Lost(_)

object CORStatStart extends CORStat:
  override val next: CORStat = CORStatEnd
  override val stateCmd: String = "start"
  override val state: Controller => GameState = Start(_)


object CORStatEnd extends CORStat:
  override val next: CORStat = CORStatEnd
  override val stateCmd: String = "error"
  override val state: Controller => GameState = Lost(_)
  
  override def changeState(state: String, context: Controller): GameState =
    throw IllegalArgumentException(s"No such state: $state")
  