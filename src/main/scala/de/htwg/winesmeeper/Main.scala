package de.htwg.winesmeeper

import de.htwg.winesmeeper.aView.TUI.TUIHelper.initVals
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.Board
import de.htwg.winesmeeper.aView.TUI.{TUI, TUIHelper}

import scala.io.StdIn.readInt

@main def start(): Unit =
  for i <- 0 until 5 do
    println(TUIHelper.getPrintString(i))
    TUIHelper.initVals(i) = readInt
  val gb = Board(initVals(0), initVals(1), initVals(2), initVals(3), initVals(4))
  val ctrl = Controller(initVals(2), initVals(3), gb)
  val tui = TUI(ctrl)