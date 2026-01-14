package de.htwg.winesmeeper.Controller.Save.ImplJSONSave

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Controller.Save.SaverTrait

object JSONSave extends SaverTrait:
  override val formatName: String = "json"

  override def save(ctrl: ControllerTrait, fileName: String): Unit = ??? 

  override def load(ctrl: ControllerTrait, fileName: String): Unit = ??? 
