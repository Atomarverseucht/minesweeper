package de.htwg.winesmeeper.Model.ImplBoard

import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.WinesmeeperModule
import com.google.inject.{Injector, Guice}

import scala.annotation.tailrec
import scala.util.Random

case class Board (board: Vector[Vector[FieldTrait]]) extends BoardTrait:

  override def getBombNeighbour(x: Int, y: Int): Int =
    (for
      vx <- -1 to 1;
      vy <- -1 to 1
    yield {
      val nx = x + vx
      val ny = y + vy
      if in(nx, ny) && board(nx)(ny).isBomb then 1 else 0
    }).sum

  override def getField(x: Int, y: Int): Int =
    val f = getFieldAt(x, y)
    if f.isFlag then -3
    else if !f.isOpened then -1
    else if f.isBomb then -2
    else getBombNeighbour(x, y)

  override def getFieldAt(x: Int, y: Int): FieldTrait =
    require(in(x, y), "Coordinates out of range")
    board(x)(y)

  override def getSize: (Int, Int) = (board.length, board(0).length)
  
  def getBoard: Vector[Vector[Int]] =
    (for x <- board.indices yield
      (for y <- board(0).indices yield
        getField(x, y)).toVector).toVector

  override def in(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < board.length && y < board(0).length

  def nextField(x: Int, y: Int): (Int, Int) = if x + 1 < board.length then (x + 1, y) else (0, y+1)

  override def updateField (indX: Int, indY: Int, field: FieldTrait): BoardTrait =
    new Board(board.updated(indX, board(indX).updated(indY, field)))

  override def isVictory: Boolean = 0 == (for x <- board; f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum

  override def toString: String = board.mkString(", ")

object Board:

  def maxBombs(xSize: Int, ySize: Int): Int = (xSize * ySize) - 9

  def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)

  def apply(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Board =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart >= 0 && yStart <= ySize && yStart >= 0, "Starting position must be on the field")
    val ex =
      if isEdge(xSize, xStart) && isEdge(ySize, yStart) then 5
      else if isEdge(xSize, xStart) || isEdge(ySize, yStart) then 3
      else 0
    val bMax: Int = Board.maxBombs(xSize, ySize) + ex
    require(bombCount >= 1 && bombCount <= bMax, s"Bomb Count must be between 1 and $bMax")
    val boardv = initField(0, 0, xStart, yStart, Vector.fill(xSize, ySize)(Config.standardField(false, true, false)), bombCount, bMax)
    new Board(boardv)
    
  @tailrec
  private def initField(indx: Int, indy: Int, xStart: Int, yStart: Int, boardv: Vector[Vector[FieldTrait]], bombCount: Int, fieldCount: Int): Vector[Vector[FieldTrait]] =
    if fieldCount <= 0 then
      boardv
    else
      val injector = Guice.createInjector(WinesmeeperModule)
      val newV =
        if Board.isNeighbour(xStart, yStart, indx, indy) then
          (false, fieldCount, (false, false, false))
        else
          val isBomb = Random.nextInt(fieldCount) < bombCount
          (isBomb, fieldCount - 1, (isBomb, false, false))
      val field = 
      
      val nboard = boardv.updated(indx, boardv(indx).updated(indy, newV._3))
      val nbc = if newV._1 then bombCount - 1 else bombCount
      val nextC = if indx + 1 < boardv.length then (indx + 1, indy) else (0, indy + 1)
      initField(nextC._1, nextC._2, xStart, yStart, nboard, nbc, newV._2)

  private def isEdge(size: Int, x: Int): Boolean = x == 0 || x == size - 1