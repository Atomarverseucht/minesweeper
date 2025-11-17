package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.*
import de.htwg.winesmeeper.{Observable, Observer}

import scala.annotation.tailrec
import scala.util.Random

sealed trait gameController:
  def openField(x: Int, y: Int): Boolean
  def inGame: Boolean
  def getBoard: Vector[Vector[Int]]
  def getSize: (Int, Int)
  def gameState: String

class Controller(var gb: Board) extends Observable with gameController:
  override def openField(x: Int, y: Int): Boolean = openField(x, y, true)
  
  // returns if turn made a change
  def openField(x: Int, y: Int, notify: Boolean = true): Boolean =
    if !gb.in(x, y) || gb.getField(x, y) != -1 then false
    else
      require(inGame, "You cannot open a field after the game is over")
      val xSize = gb.getSize._1
      val ySize = gb.getSize._2
      val f = gb.getFieldAt(x, y).isBomb
      val newVector = gb.board.updated(x, gb.board(x).updated(y, Field(f, true)))
      gb = new Board(newVector, inGame && !f)
      if gb.getBombNeighbour(x, y) == 0 then
        for i <- -1 to 1; ij <- -1 to 1 do
          val fx = x + i
          val fy = y + ij
          if gb.in(fx, fy) && !gb.board(fx)(fy).isOpened then
            openField(fx, fy, false)
      if notify then notifyObservers()
      true
    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = gb.inGame && !isVictory
  
  override def gameState: String = if isVictory then "win" else if gb.inGame then "run" else "loose"

  private def isVictory: Boolean =
    0 == (for x <- gb.board; f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum
  
object Controller:
  def initController(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Controller =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart >= 0 && yStart <= ySize && yStart >= 0, "Starting position must be on the field")
    val bMax = Board.maxBombs(xSize, ySize)
    require(bombCount >= 1 && bombCount <= bMax, s"Bomb Count must be between 1 and $bMax")
    val boardv = initField(0, 0, xStart, yStart, Vector.fill(xSize, ySize)(Field(false, true)), bombCount, bMax)
    new Controller(new Board(boardv, inGame = true))

  @tailrec
  private def initField(indx: Int, indy: Int, xStart: Int, yStart: Int, boardv: Vector[Vector[Field]], bombCount: Int, fieldCount: Int): Vector[Vector[Field]] =
    if fieldCount <= 0 then
      boardv
    else
      val newV: (Boolean, Int, Field) = if Board.isNeighbour(xStart, yStart, indx, indy) then
        (false, fieldCount, Field(false, true))
      else
        val isBomb = Random.nextInt(fieldCount) < bombCount
        (isBomb, fieldCount - 1, Field(isBomb, false))
      
      val nboard = boardv.updated(indx, boardv(indx).updated(indy, newV._3))
      val nbc = if newV._1 then bombCount - 1 else bombCount
      val nextC = if indx + 1 < boardv.length then (indx + 1, indy) else (0, indy+1)
      initField(nextC._1, nextC._2, xStart, yStart, nboard, nbc, newV._2)