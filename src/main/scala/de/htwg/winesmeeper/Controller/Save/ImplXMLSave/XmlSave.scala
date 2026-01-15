package de.htwg.winesmeeper.Controller.Save.ImplXMLSave

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.Save.{SavedData, SaverTrait}
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.BuildInfo.version
import de.htwg.winesmeeper.Controller.Commands.TurnCommandTrait

import scala.collection.mutable.Stack
import scala.xml.{Node, NodeSeq, XML}

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

  override def load(ctrl: ControllerTrait, fileName: String): SavedData =
    val out = (XML.loadFile(f"${Config.savePath}$fileName.$formatName") \\ "minesweeper").head
    val version = (out \ "version").text
    val undoStack = Stack[TurnCommandTrait]()
    val redoStack = Stack[TurnCommandTrait]()
    val stackLoader: (NodeSeq, Stack[TurnCommandTrait]) => Unit =
      (xml, stack) => (xml \\ "turn")
        .map(loadCommand(ctrl, _))
        .filter(_.nonEmpty)
        .foreach(el => stack.push(el.get))
    stackLoader(out \\ "undoStack", undoStack)
    stackLoader(out \\ "redoStack", redoStack)
    val board = ctrl.gb.fromXml(out)
    val state = (out \ "state").text
    SavedData(version, state, board, undoStack, redoStack)

  def loadCommand(ctrl: ControllerTrait, xml: Node): Option[TurnCommandTrait] =
    val cmd = (xml \\ "cmd").text.replace(" ", "")
    ctrl.undo.getCmd(cmd) match
      case None => None
      case Some(cmdSingle) => Some(cmdSingle.fromXML(xml, ctrl))
