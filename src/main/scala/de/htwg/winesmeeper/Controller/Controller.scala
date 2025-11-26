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
  override def turn(cmd: String, x: Int, y: Int): Boolean =
    require(gb.notLost, "You cannot make a turn if you have lost")
    if !gb.in(x, y) then false
    else
      val oldGB = gb
      val strat = stratList.filter(strategy => strategy.cmd == cmd)(0)
      gb = strat.execute(x, y, gb)
      if oldGB != gb then
        notifyObservers(); true
      else false

    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = gb.notLost && !isVictory
  
  override def gameState: String = if isVictory then "win" else if gb.notLost then "run" else "lose"

  private def isVictory: Boolean =
    0 == (for x <- gb.board; f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum
  
object Controller:
  def apply(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Controller =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart >= 0 && yStart <= ySize && yStart >= 0, "Starting position must be on the field")
    val ex =
      if isEdge(xSize, xStart) && isEdge(ySize,yStart) then 5
      else if isEdge(xSize, xStart) || isEdge(ySize, yStart) then 3
      else 0
    val bMax: Int = Board.maxBombs(xSize, ySize) + ex
    require(bombCount >= 1 && bombCount <= bMax, s"Bomb Count must be between 1 and $bMax")
    val boardv = initField(0, 0, xStart, yStart, Vector.fill(xSize, ySize)(Field(false, true)), bombCount, bMax)
    val out = new Controller(new Board(boardv, notLost = true))
    for fx <- xStart - 1 to xStart + 1; fy <- yStart - 1 to yStart + 1 do
        out.gb = OpenFieldStrategy.execute(fx, fy, out.gb)
    out

  @tailrec
  private def initField(indx: Int, indy: Int, xStart: Int, yStart: Int, boardv: Vector[Vector[Field]], bombCount: Int, fieldCount: Int): Vector[Vector[Field]] =
    if fieldCount <= 0 then
      boardv
    else
      val newV: (Boolean, Int, Field) =
        if Board.isNeighbour(xStart, yStart, indx, indy) then
          (false, fieldCount, Field(false, false))
        else
          val isBomb = Random.nextInt(fieldCount) < bombCount
          (isBomb, fieldCount - 1, Field(isBomb, false))
      
      val nboard = boardv.updated(indx, boardv(indx).updated(indy, newV._3))
      val nbc = if newV._1 then bombCount - 1 else bombCount
      val nextC = if indx + 1 < boardv.length then (indx + 1, indy) else (0, indy+1)
      initField(nextC._1, nextC._2, xStart, yStart, nboard, nbc, newV._2)

  private def isEdge(size: Int, x: Int): Boolean =  x == 0 || x == size - 1