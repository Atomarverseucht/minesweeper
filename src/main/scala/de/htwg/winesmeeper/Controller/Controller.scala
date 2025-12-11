package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.*
import de.htwg.winesmeeper.Observable
import de.htwg.winesmeeper.Controller.Commands.{OpenFieldCmd, UndoManager}
import scala.util.Try
import javafx.scene.input.KeyCode

sealed trait gameController:
  def inGame: Boolean
  def getBoard: Vector[Vector[Int]]
  def getSize: (Int, Int)
  def gameState: String

class Controller(var gb: Board) extends Observable with gameController:
 
  var state: GameState = Running(this)
  val undo: UndoManager = UndoManager(this)
  
  
  def turn(cmd: String, x: Try[Int], y: Try[Int]): Try[Boolean] = { 
    Try(state.turn(cmd.toLowerCase, x.get, y.get))
  }

  def changeState(newState: String): Unit = state.changeState(newState)

  def isSysCmd(cmd: String): Boolean = SysCommands.SysCommandManager.isSysCmd(cmd.toLowerCase())
  
  def doSysCmd(cmd: String, params: Vector[String] = Vector("no params")): Option[String] = 
    SysCommands.SysCommandManager.doSysCmd(this, cmd.toLowerCase(), params)
    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = state.inGame

  override def gameState: String = state.gameState
  
  def doShortCut(key: KeyCode): Option[String] = SysCommands.SysCommandManager.doShortCut(this, key)

  def isVictory: Boolean = 0 == (for x <- gb.board; f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum

  override def toString: String = // wird auch als save-Darstellung verwendet
    val version = s"version: ${de.htwg.winesmeeper.BuildInfo.version}\n"
    val stateS = s"state: $gameState\n"
    val boardS = s"board: ${gb.board.mkString(", ")}\n"
    val undoStackS = s"undo: §${undo.getStacks._1.mkString(", ")}\n"
    val redoStackS = s"redo: §${undo.getStacks._2.mkString(", ")}"
    version + stateS + boardS + undoStackS + redoStackS
  
object Controller:
  def apply(xStart: Int, yStart: Int, gb: Board): Controller =
    val out = new Controller(gb)
    for fx <- xStart - 1 to xStart + 1; fy <- yStart - 1 to yStart + 1 do
      if gb.in(fx, fy) then OpenFieldCmd(out, fx, fy).doStep()
    out
    
  def apply(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Controller =
    Controller(xStart, yStart, Board(xSize, ySize, xStart, yStart, bombCount))