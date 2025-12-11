package de.htwg.winesmeeper.aView.GUI

import scalafx.application.{JFXApp3, Platform}
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

  private var fieldSize: Int = 32
  private val widthConst = 15
  private val heightConst = 39

  override def start(): Unit =

    val bSize = ctrl.getSize
    stage = new JFXApp3.PrimaryStage:
      title = "Winesmeeper - A Minesweeper Saga"
      width.onChange(resize())
      height.onChange(resize())
    update()
    ctrl.addSub(this)

  private def boardUI: GridPane =
    val img: Vector[Image] = (for i <- -3 to 8 yield new Image(f"file:src/main/resources/fields/$i.png")).toVector
    val i = 0
    val grid = new GridPane
    val board: Vector[Vector[Int]] = ctrl.getBoard
    for x <- board.indices; y <- board(0).indices do
      val value = board(x)(y)
      val imView = ImageView(img(value+3))
      imView.fitWidth = fieldSize
      imView.fitHeight = fieldSize
      grid.add(imView, x, y)
    grid

  override def update(): Unit =
    Platform.runLater{
      stage.scene = new Scene:
          fill = Color.LightBlue
          content = HBox(boardUI)

    }

  private def resize(): Unit =
    val bSize = ctrl.getSize
    val w: Int = (stage.width.toInt - widthConst) / bSize._1
    val h: Int = (stage.height.toInt - heightConst) / bSize._2
    fieldSize = w.min(h)
    update()
