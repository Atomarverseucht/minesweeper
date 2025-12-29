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
    bind[ControllerTrait].to[ImplController.Controller]
    bind[SysCommandManagerTrait].toInstance(ImplSysCommands.SysCommandManager)
    bind[TurnCmdManagerTrait].to[ImplTurnCommands.UndoManager]

    // Board-Bindings
    bind[BoardTrait].to[ImplBoard.Board]
    install(
      new FactoryModuleBuilder()
        .implement(classOf[FieldTrait], classOf[ImplField.Field])
        .build(classOf[FieldFactory])
    )
    bind[(Boolean, Boolean, Boolean) => FieldTrait]
      .toProvider { factory: FieldFactory =>
        (opened, bomb, flag) => factory(opened, bomb, flag)
      }

  trait FieldFactory {
    def apply(
               @Assisted("opened") isOpened: Boolean,
               @Assisted("bomb") isBomb: Boolean,
               @Assisted("flag") isFlag: Boolean
             ): FieldTrait
  }
