package de.htwg.winesmeeper.Controller.Implementation

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Controller.TurnCommands.{OpenFieldCmd, UndoManager}
import de.htwg.winesmeeper.Controller.SysCommands.SysCommandManager
import de.htwg.winesmeeper.Controller.SysCommands
import de.htwg.winesmeeper.Model.*
import javafx.scene.input.KeyCode

import scala.util.Try

class Controller(var gb: BoardTrait) extends ControllerTrait():
 
  var state: GameState = Running(this)
  override val undo: UndoManager = UndoManager(this)
  
  
  override def turn(cmd: String, x: Try[Int], y: Try[Int]): Try[Boolean] = {
    Try(state.turn(cmd.toLowerCase, x.get, y.get))
  }

  override def changeState(newState: String): Unit = state.changeState(newState)

  override def isSysCmd(cmd: String): Boolean = SysCommands.SysCommandManager.isSysCmd(cmd.toLowerCase())
  
  override def doSysCmd(cmd: String, params: Vector[String] = Vector("no params")): Option[String] =
    SysCommands.SysCommandManager.doSysCmd(this, cmd.toLowerCase(), params)
    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = state.inGame

  override def gameState: String = state.gameState
  
  override def getSysCmdList: Vector[String] = SysCommandManager.getSysCmdList.map(sys => sys.cmd)
  
  override def doShortcut(key: KeyCode): Option[String] = SysCommands.SysCommandManager.doShortCut(this, key)

  override def isVictory: Boolean = gb.isVictory

  override def toString: String = // wird auch als save-Darstellung verwendet
    val version = s"version: ${de.htwg.winesmeeper.BuildInfo.version}\n"
    val stateS = s"state: $gameState\n"
    val boardS = s"board: $gb\n"
    val undoStackS = s"undo: §${undo.getStacks._1.mkString(", ")}\n"
    val redoStackS = s"redo: §${undo.getStacks._2.mkString(", ")}"
    version + stateS + boardS + undoStackS + redoStackS
  
object Controller:
  def apply(xStart: Int, yStart: Int, gb: BoardTrait): Controller =
    val out = new Controller(gb)
    for fx <- xStart - 1 to xStart + 1; fy <- yStart - 1 to yStart + 1 do
      if gb.in(fx, fy) then OpenFieldCmd(out, fx, fy).doStep()
    out