package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller.ImplTurnCommands.UndoManager
import de.htwg.winesmeeper.Controller.{ControllerTrait, ImplController, SysCommandManagerTrait, ImplSysCommands, ImplTurnCommands, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}

object Config:
  val standardController: (Int, Int, BoardTrait) => ControllerTrait = ImplController.Controller(_, _, _)

  val standardField: (isOpened: Boolean, isBomb: Boolean, isFlag: Boolean) => FieldTrait = Model.ImplField.Field(_, _, _)

  val standardBoard = Model.BoardImplementation.Board(_)

  val standardBoardGenerate = Model.BoardImplementation.Board(_, _, _, _, _)

  val standardUndo: (ControllerTrait) => TurnCmdManagerTrait = ImplTurnCommands.UndoManager(_)

  val standardSysCmdMan: SysCommandManagerTrait = ImplSysCommands.SysCommandManager
