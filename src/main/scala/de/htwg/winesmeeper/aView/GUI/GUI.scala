package de.htwg.winesmeeper.aView.GUI

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import de.htwg.winesmeeper.Controller.Controller

import scala.language.postfixOps


case class GUI(ctrl: Controller) extends JFXApp3:
  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Winesmeeper - A Minesweeper Saga"
      width = 1000
      height = 300
      scene = new Scene:
        fill = Color.LightBlue
        content = new HBox{
          val board: Seq[Vector[Int]] = ctrl.getBoard
          children ++= (for bo <- board yield new Text{text = f"${bo(0)} "})
          }
