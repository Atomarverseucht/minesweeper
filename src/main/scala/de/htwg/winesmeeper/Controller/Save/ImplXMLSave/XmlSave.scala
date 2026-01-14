package de.htwg.winesmeeper.Controller.Save.ImplXMLSave

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Save.SaverTrait
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.BuildInfo.version
import de.htwg.winesmeeper.Controller.Commands.TurnCommandTrait
import scala.collection.mutable.Stack
import scala.xml.{XML,Node, NodeSeq}

object XmlSave extends SaverTrait:
  override val formatName: String = "xml"

  override def save(ctrl: ControllerTrait, fileName: String): Option[String] =
    val metadataXML = <version>{version}</version><state>{ctrl.gameState}</state>
    val boardXML = ctrl.gb.toXml
    val stacks = ctrl.undo.getStacks
    val stacksXML =
      <undoStack>
        {stacks._1.map(turn => turn.toXML)}
      </undoStack>
      <redoStack>
        {stacks._2.map(turn => turn.toXML)}
      </redoStack>

    val saveString =
      <minesweeper>
        {metadataXML}
        {boardXML}
        {stacksXML}
      </minesweeper>.toString
    write(fileName, saveString)
    None

  override def load(ctrl: ControllerTrait, fileName: String): Option[String] =
    val out = (XML.loadFile(f"${Config.savePath}$fileName.$formatName") \\ "minesweeper").head
    val versionFile = (out \ "version").text
    val undoStack = Stack[TurnCommandTrait]()
    val redoStack = Stack[TurnCommandTrait]()
    val stackLoader: (NodeSeq, Stack[TurnCommandTrait]) => Unit =
      (xml, stack) => (xml \\ "turn")
        .map(loadCommand(ctrl, _))
        .filter(_.nonEmpty)
        .foreach(el => stack.push(el.get))
    stackLoader(out \\ "undoStack", undoStack)
    stackLoader(out \\ "redoStack", redoStack)
    println(undoStack.size)
    save(ctrl, "loadBackup")
    ctrl.gb = ctrl.gb.fromXml(out)
    ctrl.changeState((out \ "state").text)
    ctrl.undo.overrideStacks(undoStack, redoStack)
    Some(f"Loaded: $fileName.$formatName (v$versionFile)\n  For bringing back the old file, type: 'load loadBackup'\n  active version: $version")

  private def loadCommand(ctrl: ControllerTrait, xml: Node): Option[TurnCommandTrait] =
    val cmd = (xml \\ "cmd").text.replace(" ", "")
    ctrl.undo.getCmd(cmd) match
      case None => None
      case Some(cmdSingle) => Some(cmdSingle.fromXML(xml, ctrl))
