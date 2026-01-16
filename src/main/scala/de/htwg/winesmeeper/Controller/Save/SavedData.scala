package de.htwg.winesmeeper.Controller.Save

import de.htwg.winesmeeper.Controller.Commands.TurnCommandTrait
import de.htwg.winesmeeper.Model.BoardTrait
import scala.collection.mutable.Stack

case class SavedData(version: String, 
                     state: String, 
                     board: BoardTrait,
                     undoStack: Stack[TurnCommandTrait],
                     redoStack: Stack[TurnCommandTrait])
