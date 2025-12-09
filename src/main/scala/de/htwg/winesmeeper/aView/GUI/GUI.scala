package de.htwg.winesmeeper.aView.GUI

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Text


object WinesmeeperGUI extends JFXApp3:
  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Winesmeeper - A Minesweeper Saga"
      width = 500
      height = 300
      scene = new Scene:
        fill = Color.rgb(40, 40, 40)
        content = HBox(20, new Text("Hello, World!") {
          fill = Color.White
        })


