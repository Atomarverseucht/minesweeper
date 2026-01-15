package de.htwg.winesmeeper.Controller.Save.ImplJSONSave

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Controller.Save.{SavedData, SaverTrait}
import de.htwg.winesmeeper.Model.BoardTrait
import play.api.libs.json.*

object JSONSave extends SaverTrait:
  override val formatName: String = "json"

  override def save(ctrl: ControllerTrait, fileName: String): Option[String] =
    
    None

  override def load(ctrl: ControllerTrait, fileName: String): SavedData = ???
