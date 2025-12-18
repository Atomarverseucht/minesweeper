package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller.Implementation.Controller
import de.htwg.winesmeeper.Model.BoardImplementation.Board
import de.htwg.winesmeeper.aView.TUI.TUI

import scala.io.StdIn.readInt

val initVals = new Array[Int](5)

def startTUI: Controller =
  for i <- 0 until 5 do
    println(getPrintString(i))
    initVals(i) = readInt
  val gb = Board(initVals(0), initVals(1), initVals(2), initVals(3), initVals(4))
  val ctrl = Controller(initVals(2), initVals(3), gb)
  new Thread(() => {
    val tui = TUI(ctrl)
  }).start()
  ctrl
  
@main def start: Unit = aView.GUI.GUI(startTUI).main(Array())

def getPrintString(indx: Int): String =
  indx match
    case 0 =>
      "Please enter the size of the x coordinate. It must be >= 10"
    case 1 =>
      "Please enter the size of the y coordinate. It must be >= 10"
    case 2 =>
      "Please enter your x starting coordinate between 0 and " + (initVals(0) - 1)
    case 3 =>
      "Please enter your starting y coordinate between 0 and " + (initVals(1) - 1)
    case 4 =>
      "Please enter the count of bombs. It must be between 1 and " + (initVals(0) * initVals(1) - 9)
  