package de.htwg.winesmeeper.Controller.Save.ImplJSONSave

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Controller.Save.{SavedData, SaverTrait}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.BuildInfo.version
import play.api.libs.json.*

object JSONSave extends SaverTrait:
  override val formatName: String = "json"

  override def save(ctrl: ControllerTrait, fileName: String): Option[String] =
    val stacks = ctrl.undo.getStacks
    val jsonObj = Json.obj(
      "version" -> version,
      "state" -> ctrl.gameState,
      "board" -> ctrl.gb,
      "undo-Stack" -> stacks._1.map(c => c.toJSON)
    )
    val json = Json.prettyPrint(jsonObj)
    write(fileName,json)
    None

  override def load(ctrl: ControllerTrait, fileName: String): SavedData = ???

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