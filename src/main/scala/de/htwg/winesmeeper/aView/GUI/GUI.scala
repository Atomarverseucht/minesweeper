package de.htwg.winesmeeper.aView.GUI

import scalafx.application.JFXApp3
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.Scene
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

import scala.language.postfixOps


case class GUI(ctrl: Controller) extends JFXApp3 with Observer:
  ctrl.addSub(this)

  override def start(): Unit =
    update()

  private def boardUI: GridPane =
    val img: Vector[Image] = (for i <- -3 to 8 yield new Image(f"file:src/main/resources/fields/$i.png")).toVector
    val i = 0
    val grid = new GridPane
    val board: Vector[Vector[Int]] = ctrl.getBoard
    for x <- board.indices; y <- board(0).indices do
      val value = board(x)(y)
      val imView = ImageView(img(value+3))
      imView.fitWidth = 32
      imView.fitHeight = 32
      grid.add(imView, x, y)
    grid

  override def update(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Winesmeeper - A Minesweeper Saga"
      width = 1000
      height = 300
      scene = new Scene:
        fill = Color.LightBlue
        content = HBox(boardUI)