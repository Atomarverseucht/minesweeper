package de.htwg.winesmeeper.Controller.ImplController

import de.htwg.winesmeeper.Controller.*


trait CORStat:
  val next: CORStat
  def changeState(state: String, context: Controller): GameState

object CORStatRunning extends CORStat:
  override val next: CORStat = CORStatWon
  override def changeState(state: String, context: Controller): GameState = 
    if state == "running" then Running(context) else next.changeState(state, context)

object CORStatWon extends CORStat:
  override val next: CORStat  = CORStatLose
  override def changeState(state: String, context: Controller): GameState =
    if state == "win" then Won(context) else next.changeState(state, context)

object CORStatLose extends CORStat:
  override val next: CORStat = CORStateEnd
  override def changeState(state: String, context: Controller): GameState =
    if state == "lose" then Lost(context) else next.changeState(state, context)

object CORStateEnd extends CORStat:
  override val next: CORStat = CORStateEnd
  override def changeState(state: String, context: Controller): GameState =
    throw IllegalArgumentException(s"No such state: $state")