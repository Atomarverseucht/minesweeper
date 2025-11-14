package de.htwg.winesmeeper.Model

import scala.annotation.tailrec

// all in some cases useful functions of the Board in a short overview.
//    Names should be self-explanatory
sealed trait GameBoard {
  def getField(x: Int, y: Int): Int
  def getSize: (Int, Int)
  def getBombNeighbour(x: Int, y: Int): Int
  def findBomb: (Int, Int)
}

case class Field(isBomb: Boolean, isOpened: Boolean)

case class Board (
  board: Vector[Vector[Field]],
  inGame: Boolean
) extends GameBoard:

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

  def getFieldAt(x: Int, y: Int): Field =
    require(in(x, y), "Coordinates out of range")
    board(x)(y)

  override def getSize: (Int, Int) = (board.length, board(0).length)

  override def findBomb: (Int, Int) = {
    findBomb(0, 0)
  }
  @tailrec
  private def findBomb(x: Int, y: Int): (Int, Int) = 
    if board(x)(y).isBomb then (x, y) else {val c: (Int, Int) = nextField(x, y); findBomb(c._1, c._2)}
  
  def getBoard: Vector[Vector[Int]] = {
    (for x <- board.indices yield
      (for y <- board(0).indices yield
        getField(x, y)).toVector).toVector
  }

  def in(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < board.length && y < board(0).length

  def nextField(x: Int, y: Int): (Int, Int) = if x + 1 < board.length then (x + 1, y) else (0, y+1)

object Board:

  def maxBombs(xSize: Int, ySize: Int): Int = (xSize * ySize) - 9

  def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)