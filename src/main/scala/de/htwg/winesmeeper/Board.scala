package de.htwg.winesmeeper
import scala.util.Random

// all in some cases useful functions of the Board in a short overview.
//    Names should be self-explanatory
sealed trait GameBoard {
  def openField(x: Int, y: Int) : Board
  def getField(x: Int, y: Int): Int
  def getSize: (Int, Int)
  def checkGameState: Boolean
  def getBombNeighbour(x: Int, y: Int): Int
  def findBomb: (Int, Int)
}

case class Field(isBomb: Boolean, isOpened: Boolean)

case class Board (
  board: Vector[Vector[Field]],
  inGame: Boolean
) extends GameBoard {

  override def checkGameState: Boolean = inGame && !isVictory

  def isVictory: Boolean =
    0 == (for x <- board
        f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum

  override def openField(x: Int, y: Int): Board =
    require(inGame, "You cannot open a field after the game is over")
    if !in(x, y) then this
    else
      val xSize = board.length
      val ySize = board(0).length
      val f = getFieldAt(x, y).isBomb
      val newVector = board.updated(x, board(x).updated(y,Field(f, true)))
      val newB = new Board(newVector, inGame && !f)
      if getBombNeighbour(x, y) == 0 then
        (-1 to 1).foldLeft(newB){(b, i) => (-1 to 1).foldLeft(b){(b2, ii) =>
          val fx = x + i
          val fy = y + ii
          if in(fx, fy) && !newB.board(fx)(fy).isOpened then
            b2.openField(fx, fy)
          else b2
        }}
      else newB
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
    if !f.isOpened then -1
    else if f.isBomb then -2
    else getBombNeighbour(x, y)

  private def getFieldAt(x: Int, y: Int): Field =
    require(in(x, y), "Coordinates out of range")
    board(x)(y)

  override def getSize: (Int, Int) = (board.length, board(0).length)

  override def findBomb: (Int, Int) = {
    var b: (Int, Int) = (0, 0)
    for x <- 0 to board.length
      y <- 0 to board(0).length do
      if board(x)(y).isBomb then b = (x, y)
    b
  }
  
  def getBoard: Vector[Vector[Int]] = {
    (for x <- board.indices yield
      (for y <- board(0).indices yield
        getField(x, y)).toVector).toVector
  }
  
  def in(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < board.length && y < board(0).length
}

object Board:

  def maxBombs(xSize: Int, ySize: Int): Int = (xSize * ySize) - 9

  def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)