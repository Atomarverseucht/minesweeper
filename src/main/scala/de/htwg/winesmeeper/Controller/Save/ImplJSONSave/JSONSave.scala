package de.htwg.winesmeeper.Controller.Save.ImplJSONSave

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Controller.Save.{SavedData, SaverTrait}
import de.htwg.winesmeeper.Controller.Commands.TurnCommandTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.BuildInfo.version
import de.htwg.winesmeeper.Config

import scala.collection.mutable.Stack
import scala.collection.Seq
import play.api.libs.json.*

import java.nio.file.{Files, Paths}

object JSONSave extends SaverTrait:
  override val formatName: String = "json"

  override def save(ctrl: ControllerTrait, fileName: String): Option[String] =
    val stacks = ctrl.undo.getStacks
    val jsonObj = Json.obj(
      "version" -> version,
      "state" -> ctrl.gameState,
      "board" -> ctrl.gb,
      "undo-Stack" -> stacks._1.map(c => c.toJSON),
      "redo-Stack" -> stacks._2.map(c => c.toJSON)
    )
    val json = Json.prettyPrint(jsonObj)
    write(fileName,json)
    None

  implicit val boardTraitWrites: Writes[BoardTrait] = Writes { board =>
    Json.toJson(board.board)
  }
  implicit val fieldTraitWrites: Writes[FieldTrait] = Writes { field =>
    Json.obj(
      "isBomb" -> field.isBomb,
      "isOpened" -> field.isOpened,
      "isFlag" -> field.isFlag
    )
  }

  override def load(ctrl: ControllerTrait, fileName: String): SavedData =
    val input = Files.readString(Paths.get(f"${Config.savePath}$fileName.$formatName"))
    val out = Json.parse(input)

    val version: String= (out \\ "version").head.as
    println(f"v$version")
    val undoStack = Stack[TurnCommandTrait]()
    val redoStack = Stack[TurnCommandTrait]()
    val stackLoader: (JsValue, Stack[TurnCommandTrait]) => Unit =
      (json, stack) =>
        json.as[Seq[JsValue]].map(loadCommand(ctrl, _))
        .filter(_.nonEmpty)
        .foreach(el => stack.push(el.get))
    val board: BoardTrait = (out \ "board").get.as
    val state: String = (out \ "state").get.as
    stackLoader((out \ "undo-Stack").get, undoStack)
    stackLoader((out \ "redo-Stack").get, redoStack)
    SavedData(version, state, board, undoStack, redoStack)

  def loadCommand(ctrl: ControllerTrait, json: JsValue): Option[TurnCommandTrait] =
    val cmd: String = (json \\ "cmd").head.as
    ctrl.undo.getCmd(cmd) match
      case None => None
      case Some(cmdSingle) => Some(cmdSingle.fromJSON(json, ctrl))

  implicit val boardTraitReads: Reads[BoardTrait] = Reads { board =>
    board.validate[Vector[Vector[FieldTrait]]].map(Config.mkBoard)
  }

  implicit val fieldTraitReads: Reads[FieldTrait] = Reads { json =>
    for
      isBomb <- (json \ "isBomb").validate[Boolean]
      isOpened <- (json \ "isOpened").validate[Boolean]
      isFlag <- (json \ "isFlag").validate[Boolean]
    yield Config.mkField(isBomb, isOpened, isFlag)
  }
