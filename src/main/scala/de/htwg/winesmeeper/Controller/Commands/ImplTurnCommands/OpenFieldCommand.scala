package de.htwg.winesmeeper.Controller.Commands.ImplTurnCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.{TurnCommandSingletonTrait, TurnCommandTrait}
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.util.{Failure, Success, Try}
import scala.xml.Node

case class OpenFieldCommand(observerID : Int, ctrl: ControllerTrait, x: Int, y: Int) extends TurnCommandTrait:

  val isFlag: Boolean = ctrl.gb.getFieldAt(x, y).isFlag

  override def startStep(): Try[String] =
    val size = ctrl.getSize
    Try(ctrl.doSysCmd(observerID, "generate", 
      Vector("generate", size._1.toString, size._2.toString, x.toString, y.toString, Config.bombCount4Generate.toString)).get)

  override def doStep(): Try[String] =
    step(true)

  override def undoStep(): String =
    step(false).getOrElse("")

  override def redoStep(): String =
    step(true).getOrElse("")

  private def step(discover: Boolean): Try[String] =
    val gb = ctrl.gb
    val f = gb.getFieldAt(x, y)
    if discover == f.isOpened then Failure(new IllegalArgumentException("Field is already open"))
    else
      ctrl.gb = gb.updateField(x, y, Config.mkField(f.isBomb, discover, !discover && isFlag))
      if !discover && !ctrl.inGame then ctrl.changeState("running")
      if f.isBomb && discover then ctrl.changeState("lost")
      else if gb.getBombNeighbour(x, y) == 0 then
          for fx <- x - 1 to x + 1
            fy <- y - 1 to y + 1 do
            if gb.in(fx, fy) && !gb.getFieldAt(fx, fy).isOpened == discover then
              OpenFieldCommand(observerID, ctrl, fx, fy).step(discover)
      if discover && ctrl.isVictory && !f.isBomb then ctrl.changeState("win")
      Success("")

  override def toXML: Node =
    <turn>
      <cmd>open</cmd>
      <observer>{observerID}</observer>
      <x>{x}</x>
      <y>{y}</y>
    </turn>

  override def toJSON: JsObject =
    Json.obj(
      "cmd" -> "open",
      "observer" -> observerID,
      "x" -> x,
      "y" -> y
    )

object OpenFieldSingleton extends TurnCommandSingletonTrait:
  override val cmd = "open"
  override val helpMsg = "opens the field of the given coordinate"
  override val next: TurnCommandSingletonTrait = zLastElemTurnCmdSingleton
  override val specHelpMsg: String =
    """open <x> <y>:
      |  Opens a field and if you hit a bomb, you loose!
      |  But no pressure you can undo your fault!
      |""".stripMargin

  override def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[TurnCommandTrait] =
    if cmd == this.cmd then Success(OpenFieldCommand(observerID: Int, ctrl, x, y))
    else next.buildCmd(observerID: Int, cmd, x, y, ctrl)

  override def fromXML(xml: Node, ctrl: ControllerTrait): TurnCommandTrait =
    val obsID = (xml \ "observer").head.text.toInt
    val x = (xml \ "x").head.text.toInt
    val y = (xml \ "y").head.text.toInt
    OpenFieldCommand(obsID, ctrl, x, y)

  override def fromJSON(json: JsValue, ctrl: ControllerTrait): TurnCommandTrait =
    OpenFieldCommand(
      (json \ "observer").as[Int],
      ctrl,
      (json \ "x").as[Int],
      (json \ "y").as[Int]
    )
