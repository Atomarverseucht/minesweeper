package de.htwg.winesmeeper

import de.htwg.winesmeeper.aView.TUI.TUI
import de.htwg.winesmeeper.Config

import java.awt.GraphicsEnvironment

val initVals = new Array[Int](5)
  
@main def start: Unit =
  val gb = Config.startBoard
  val ctrl = Config.startController(gb)
  new Thread(() => {
  val tui = TUI(ctrl)
  }).start()
  if scala.util.Try(GraphicsEnvironment.isHeadless).isSuccess then
    aView.GUI.GUI(ctrl).main(Array())
