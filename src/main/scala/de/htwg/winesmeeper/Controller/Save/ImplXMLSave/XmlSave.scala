package de.htwg.winesmeeper.Controller.Save.ImplXMLSave

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Save.SaverTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import scala.xml.XML

object XmlSave extends SaverTrait:
  override val formatName: String = "xml"

  override def save(ctrl: ControllerTrait, fileName: String): Unit =
    val saveString = ctrl.gb.toXml.toString
    write(fileName, saveString)

  override def load(ctrl: ControllerTrait, fileName: String): Unit =
    val out = XML.loadFile(f"${Config.savePath}$fileName.$formatName")
    ctrl.gb = ctrl.gb.fromXml(out)
