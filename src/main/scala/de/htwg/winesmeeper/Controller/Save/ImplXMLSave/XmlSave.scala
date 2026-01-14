package de.htwg.winesmeeper.Controller.Save.ImplXMLSave

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Save.SaverTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.BuildInfo.version
import scala.xml.XML

object XmlSave extends SaverTrait:
  override val formatName: String = "xml"

  override def save(ctrl: ControllerTrait, fileName: String): Option[String] =
    val metadata = <version>{version}</version><state>{ctrl.gameState}</state>
    val boardXML = ctrl.gb.toXml
    val saveString = <minesweeper>
      {metadata}
      {boardXML}</minesweeper>.toString
    write(fileName, saveString)
    None

  override def load(ctrl: ControllerTrait, fileName: String): Option[String] =
    val out = (XML.loadFile(f"${Config.savePath}$fileName.$formatName") \\ "minesweeper").head
    val versionFile = (out \ "version").text
    save(ctrl, "loadBackup")
    ctrl.gb = ctrl.gb.fromXml(out)
    ctrl.changeState((out \ "state").text)
    Some(f"Loaded: $fileName.$formatName(v${versionFile})\nFor bringing back the old file, type: 'load loadBackup' \n active version: $version")
