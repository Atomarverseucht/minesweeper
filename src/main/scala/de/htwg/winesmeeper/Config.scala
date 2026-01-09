package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller
import de.htwg.winesmeeper.Controller.ImplController.Controller
import de.htwg.winesmeeper.Controller.{ControllerTrait, ImplController, SysCommandManagerTrait}
import de.htwg.winesmeeper.Controller.{ImplSysCommands, ImplTurnCommands, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Model.ImplBoard.Board
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}

object Config {
  val standardController: (Int, Int, BoardTrait) => ControllerTrait = ImplController.Controller(_, _, _)

  val standardField: (isOpened: Boolean, isBomb: Boolean, isFlag: Boolean) => FieldTrait =
    Model.ImplField.Field(_, _, _)

  val standardBoard: Vector[Vector[FieldTrait]] => BoardTrait =
    Model.ImplBoard.Board(_)

  val standardBoardGenerate: (Int, Int, Int, Int, Int) => BoardTrait =
    Model.ImplBoard.Board(_, _, _, _, _)

  val startBoard: BoardTrait = Model.ImplBoard.Board(Vector.fill(10, 10)(Config.standardField(true, false, false)))

  val startController: BoardTrait => ControllerTrait = ImplController.Controller(_)

  val standardUndo: ControllerTrait => TurnCmdManagerTrait =
    ImplTurnCommands.UndoManager(_)

  val standardSysCmdMan: SysCommandManagerTrait =
    ImplSysCommands.SysCommandManager

  val stdBombCount: Int = 10
}
