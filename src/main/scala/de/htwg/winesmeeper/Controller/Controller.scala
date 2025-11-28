package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.*
import de.htwg.winesmeeper.Observable

import scala.annotation.tailrec
import scala.util.Random

sealed trait gameController:
  def turn(cmd: String, x: Int, y: Int): Boolean
  def inGame: Boolean
  def getBoard: Vector[Vector[Int]]
  def getSize: (Int, Int)
  def gameState: String

class Controller(var gb: Board) extends Observable with gameController:
 
  var state: GameState = Running(this)
  override def turn(cmd: String, x: Int, y: Int): Boolean =
    state.turn(cmd, x, y)

  def changeState(newState: String): Unit = state.changeState(newState)
    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = state.inGame

  override def gameState: String = state.gameState

  def isVictory: Boolean = 0 == (for x <- gb.board; f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum
  
object Controller:
  def apply(xStart: Int, yStart: Int, gb: Board): Controller =
    val out = new Controller(gb)
    for fx <- xStart - 1 to xStart + 1; fy <- yStart - 1 to yStart + 1 do
      if gb.in(fx, fy) then out.gb = OpenFieldStrategy.execute(fx, fy, out)
    out
    
  def apply(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Controller =
    Controller(xStart, yStart, Board(xSize, ySize, xStart, yStart, bombCount))