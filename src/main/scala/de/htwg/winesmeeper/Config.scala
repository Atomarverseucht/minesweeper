package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller.Commands.{SysCommandManagerTrait, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Controller.{ControllerTrait, ImplController}
import de.htwg.winesmeeper.Controller.Commands.{ImplSysCommands, ImplTurnCommands}
import de.htwg.winesmeeper.Controller.Save.{SaverTrait, ImplXMLSave, ImplJSONSave}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}

object Config:
  def mkController (xStart: Int, yStart: Int, board: BoardTrait): ControllerTrait =
    ImplController.Controller(xStart, yStart, board)

  def mkField (isBomb: Boolean, isOpened: Boolean,  isFlag: Boolean): FieldTrait =
    Model.ImplField.Field(isBomb, isOpened, isFlag)

  def mkBoard(board: Vector[Vector[FieldTrait]]) : BoardTrait =
    Model.ImplBoard.Board(board)

  def generateBoard (xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): BoardTrait =
    Model.ImplBoard.Board(xSize, ySize, xStart, yStart, bombCount)

  def startBoard(xSize: Int, ySize: Int): BoardTrait =
    Model.ImplBoard.Board(Vector.fill(xSize, ySize)(Config.mkField(true, false, false)))
    
  def startBoard: BoardTrait = startBoard(10,10)

  def startController(board: BoardTrait): ControllerTrait =
    ImplController.Controller(board)

  def mkUndo(ctrl: ControllerTrait): TurnCmdManagerTrait =
    ImplTurnCommands.UndoManager(ctrl)

  val saver: SaverTrait = ImplXMLSave.XmlSave

  val standardSysCmdMan: SysCommandManagerTrait =
    ImplSysCommands.SysCommandManager

  var bombCount4Generate: Int = 10 // is a memory for the generator to know how many bombs they are to generate in future

  val savePath = "saves/"
  val standardFileName = "winesmeeper-SaveFile"
