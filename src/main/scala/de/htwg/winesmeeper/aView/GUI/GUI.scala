package de.htwg.winesmeeper.aView.GUI

import de.htwg.winesmeeper.Controller.ControllerTrait
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, GridPane}
import scalafx.scene.paint.Color
import scalafx.scene.input.InputIncludes.jfxMouseEvent2sfx
import scalafx.scene.input.MouseButton
import scalafx.scene.control.{Alert, Button, ToolBar}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.text.Text
import javafx.scene.input.KeyEvent
import scalafx.scene.text.{Font, FontWeight}

import scala.language.postfixOps
import scala.util.Try
import de.htwg.winesmeeper.Observer

case class GUI(ctrl: ControllerTrait) extends JFXApp3 with Observer(ctrl):

  private val heightToolBar = 30
  private var fieldSize: Int = 32
  private val widthConst = 15
  private val heightConst = 39 + heightToolBar

  override def start(): Unit =
    val bSize = ctrl.getSize
    stage = new JFXApp3.PrimaryStage:
      title = "Winesmeeper - A Minesweeper Saga"
      width = fieldSize * bSize._1 + widthConst
      height = fieldSize * bSize._2 + heightConst
      width.onChange(resize())
      height.onChange(resize())
    stage.scene = new Scene:
      fill = Color.LightBlue
      onKeyPressed = keyListener(_)
      onMouseClicked = e =>
        val cmd = if e.button == MouseButton.Primary then "open" else "flag"
        turn(cmd, e.getX.toInt, e.getY.toInt)
    update()
    ctrl.addSub(this)

  private def boardUI: GridPane =
    val img: Vector[Image] = (for i <- -3 to 8 yield new Image(f"file:src/main/resources/fields/$i.png")).toVector
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
      val newRoot = new BorderPane {
        top = getToolBar
        center = boardUI
      }
      stage.scene.get().setRoot(newRoot)
      if !ctrl.inGame then
        new Alert(AlertType.Information) {
          headerText = None
          title = "Game \"end\" message"
          dialogPane().setContent(gameEndMsg)
        }.showAndWait()
    }

  private def resize(): Unit =
    val bSize = ctrl.getSize
    val w: Int = (stage.width.toInt - widthConst) / bSize._1
    val h: Int = (stage.height.toInt - heightConst) / bSize._2
    fieldSize = w.min(h)
    update()

  private def getIndex(x: Int, y: Int): (Int, Int) = (x / fieldSize, (y- heightToolBar) / fieldSize)

  private def turn(cmd: String, x: Int, y: Int): Unit =
    val index = getIndex(x, y)
    ctrl.turn(observerID, cmd, Try(index._1), Try(index._2))

  private def keyListener(event: KeyEvent): Unit =
    if event.isControlDown then
      outputWindowSysCmd(ctrl.doShortCut(observerID: Int, event.getCode))

  private def outputWindowSysCmd(output: Option[String]): Unit =
    output match
      case Some(value) =>
        new Alert(AlertType.Information) {
          title = "Winesmeeper Info"
          headerText = value }.showAndWait()
      case None =>

  private def gameEndMsg: Text =
    val f = Font("Arial", FontWeight.Bold, 30)
    ctrl.gameState match
      case "lose" =>
        new Text("Game lost!") {
          fill = Color.DarkRed; font = f}
      case "win" =>
        new Text("You have won!") {fill = Color.Green; font = f}
      case _ =>
        Text("???")

  private def getToolBar: ToolBar =
    val sysCmds = ctrl.getSysCmdList
    val cmds: Seq[Button] = (for cmd <- sysCmds yield
      new Button(cmd){
        onAction = _ => outputWindowSysCmd(ctrl.doSysCmd(observerID, cmd, Vector("")))})
    new ToolBar{content = cmds}

  override def generate(): Unit =
    val dialog = new GeneratorGUI
    val result = dialog.showAndWait()
    result match {
      case Some(data: GeneratorData) =>
         ctrl.doSysCmd(observerID, "generate", Vector("", data.val1, data.val2, data.val3, data.val4, data.val5))
      case _ =>
    }


