package de.htwg

import scala.io.StdIn.readInt
import de.htwg.TUI

@main def start = {
  for i <- 0 until 5 do
    println(TUI.getPrintString(i))
    TUI.setStart(i, readInt)
  var gb = TUI.initGameBoard
}
