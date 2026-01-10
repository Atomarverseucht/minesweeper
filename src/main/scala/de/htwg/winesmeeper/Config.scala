package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller.{ControllerTrait, ImplController, SysCommandManagerTrait}
import de.htwg.winesmeeper.Controller.{ImplSysCommands, ImplTurnCommands, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}

object Config {
  def mkController (xStart: Int, yStart: Int, board: BoardTrait): ControllerTrait =
    ImplController.Controller(xStart, yStart, board)

  def mkField (isOpened: Boolean, isBomb: Boolean, isFlag: Boolean): FieldTrait =
    Model.ImplField.Field(isBomb, isOpened, isBomb)

  def mkBoard(board: Vector[Vector[FieldTrait]]) : BoardTrait =
    Model.ImplBoard.Board(board)

  def generateBoard (xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): BoardTrait =
    Model.ImplBoard.Board(xSize, ySize, xStart, yStart, bombCount)

  def startBoard: BoardTrait =
    Model.ImplBoard.Board(Vector.fill(10, 10)(Config.mkField(true, false, false)))

  def startController (board: BoardTrait): ControllerTrait =
    ImplController.Controller(board)

  def mkUndo(ctrl: ControllerTrait): TurnCmdManagerTrait =
    ImplTurnCommands.UndoManager(ctrl)

  val standardSysCmdMan: SysCommandManagerTrait =
    ImplSysCommands.SysCommandManager

  val stdBombCount: Int = 10
}
