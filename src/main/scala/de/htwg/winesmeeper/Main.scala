package de.htwg.winesmeeper

import de.htwg.winesmeeper.aView.{TUI, View}
import de.htwg.winesmeeper.aView.TUI.initVals
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.Board

import scala.io.StdIn.readInt

@main def start(): Unit =
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.initVals(i) = readInt
  val gb = Board(initVals(0), initVals(1), initVals(2), initVals(3), initVals(4))
  val ctrl = Controller(initVals(2), initVals(3), gb)
  val tui = View(ctrl)