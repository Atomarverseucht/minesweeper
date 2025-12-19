package de.htwg.winesmeeper

import de.htwg.winesmeeper.Controller.{ControllerTrait, ImplController, SysCommandManagerTrait, SysCommands, TurnCommands}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.Model

object Config:
  val standardController: (Int, Int, BoardTrait) => ControllerTrait = ImplController.Controller(_: Int, _: Int, _: BoardTrait)

  val standardField: (Boolean, Boolean, Boolean) => FieldTrait = Model.ImplField.Field(_: Boolean, _: Boolean, _: Boolean)

  val standardBoard = Model.BoardImplementation.Board(_: Vector[Vector[FieldTrait]])
  
  val standardBoardGenerate = Model.BoardImplementation.Board(_: Int, _: Int, _: Int, _: Int, _: Int)

  val standardUndo = TurnCommands.UndoManager

  val standardSysCmdMan: SysCommandManagerTrait = SysCommands.SysCommandManager
