package de.htwg.winesmeeper


import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.assistedinject.Assisted

import Controller.{ControllerTrait, TurnCmdManagerTrait, SysCommandManagerTrait}
import Controller.{ImplController, ImplSysCommands, ImplTurnCommands}
import Model.{BoardTrait, FieldTrait}
import Model.{ImplBoard, ImplField}


object WinesmeeperModule extends AbstractModule with ScalaModule:
  override def configure(): Unit =
    // Controller-Bindings
    bind[(Int, Int, BoardTrait) => ControllerTrait]
      .toInstance((xSt, ySt, board) => ImplController.Controller(xSt, ySt, board))
    bind[SysCommandManagerTrait].toInstance(ImplSysCommands.SysCommandManager)
    bind[ControllerTrait => TurnCmdManagerTrait].toInstance(ImplTurnCommands.UndoManager(_))

    // Board-Bindings
    bind[Vector[Vector[FieldTrait]] => BoardTrait]
      .toInstance(ImplBoard.Board(_))
    bind[(Int, Int, Int, Int, Int) => BoardTrait]
      .toInstance((xSz, ySz, xSt, ySt, bc) => ImplBoard.Board(xSz, ySz, xSt, ySt, bc))
    
    // Field
    bind[(Boolean, Boolean, Boolean) => FieldTrait]
      .toInstance((opened, bomb, flag) => ImplField.Field(opened, bomb, flag))

