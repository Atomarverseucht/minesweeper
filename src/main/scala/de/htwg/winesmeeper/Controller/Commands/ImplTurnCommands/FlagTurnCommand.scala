package de.htwg.winesmeeper.Controller.Commands.ImplTurnCommands

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Commands.{TurnCommandSingletonTrait, TurnCommandTrait}
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.util.{Failure, Success, Try}
import scala.xml.Node

case class FlagTurnCommand(observerID: Int, ctrl: ControllerTrait, x: Int, y: Int) extends TurnCommandTrait:
  
  override def doStep(): Try[String] =
    val f = ctrl.gb.getFieldAt(x, y)
    if !f.isOpened then
      ctrl.gb = ctrl.gb.updateField(x, y, Config.mkField(f.isBomb, f.isOpened, !f.isFlag))
      Success("flag successful")
    else Failure(IllegalArgumentException("flag cannot be set on a opened field"))

  override def undoStep(): String = doStep().get

  override def redoStep(): String = doStep().get

  override def startStep(): Try[String] = Failure(IllegalArgumentException("You cannot start with the flag command"))

  override def toXML: Node =
    <turn>
      <cmd>flag</cmd>
      <observer>{observerID}</observer>
      <x>{x}</x>
      <y>{y}</y>
    </turn>

  override def toJSON: JsObject = 
    Json.obj(
      "cmd" -> "flag",
      "observer" -> observerID,
      "x" -> x,
      "y" -> y
    )

object FlagSingleton extends TurnCommandSingletonTrait:
  override val cmd = "flag"
  override val helpMsg: String = "flag or unflag the given coordinate"
  override val next: TurnCommandSingletonTrait = OpenFieldSingleton
  override val specHelpMsg: String =
    """flag <x> <y>:
      |  mark this position as flag or remove the flag
      |""".stripMargin

  override def buildCmd(observerID: Int, cmd: String, x: Int, y: Int, ctrl: ControllerTrait): Try[TurnCommandTrait] =
    if cmd == this.cmd then Success(FlagTurnCommand(observerID, ctrl, x, y)) else next.buildCmd(observerID, cmd,x,y,ctrl)

  override def fromXML(xml: Node, ctrl: ControllerTrait): TurnCommandTrait =
    val obsID = (xml \ "observer").head.text.toInt
    val x = (xml \ "x").head.text.toInt
    val y = (xml \ "y").head.text.toInt
    FlagTurnCommand(obsID, ctrl, x, y)

  override def fromJSON(json: JsValue, ctrl: ControllerTrait): TurnCommandTrait =
    FlagTurnCommand(
      (json \ "observer").as[Int],
      ctrl,
      (json \ "x").as[Int],
      (json \ "y").as[Int]
    )

