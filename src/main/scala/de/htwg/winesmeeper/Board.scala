package de.htwg.winesmeeper.Model
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
  xSize : Int,
  ySize : Int,
  xStart : Int,
  yStart : Int,
  bombCount : Int,
  board: Vector[Vector[Field]],
  inGame: Boolean
) extends GameBoard {

  override def checkGameState: Boolean = inGame && !isVictory

  def isVictory: Boolean =
    0 == (for x <- board
        f <- x yield if !f.isBomb && !f.isOpened then 1 else 0).sum

  override def openField(x: Int, y: Int): Board =
    require(inGame, "You cannot open a field after the game is over")
    val f = getFieldAt(x, y).isBomb
    val newVector = board.updated(x, board(x).updated(y,Field(f, true)))
    var newB = new Board(xSize, ySize, xStart, yStart, bombCount, newVector, inGame && !f)
    if getBombNeighbour(x, y) == 0 then
      for ix <- -1 to 1
          iy <- -1 to 1 do
          val fx = x + ix
          val fy = y + iy
          if fx >= 0 && fy >= 0 && fx < xSize && fy < ySize && !newB.board(fx)(fy).isOpened then {
            newB = newB.openField(fx, fy)
          }
    newB

  override def getBombNeighbour(x: Int, y: Int): Int =
    (for
      vx <- -1 to 1;
      vy <- -1 to 1
    yield {
      val nx = x + vx
      val ny = y + vy
      if nx >= 0 && ny >= 0 && nx < xSize && ny < ySize && board(nx)(ny).isBomb then 1 else 0
    }).sum

  override def getField(x: Int, y: Int): Int =
    val f = getFieldAt(x, y)
    if !f.isOpened then -1
    else if f.isBomb then -2
    else getBombNeighbour(x, y)

  private def getFieldAt(x: Int, y: Int): Field =
    require(x >= 0 && y >= 0 && x < xSize && y < ySize, "Coordinates out of range")
    board(x)(y)

  override def getSize: (Int, Int) = (board.length, board(0).length)

  override def findBomb: (Int, Int) = {
    var b : (Int, Int) = (0, 0)
    for x <- 0 until xSize
      y <- 0 until ySize do
      if board(x)(y).isBomb then b = (x, y)
    b
  }
  
  def getBoard: Vector[Vector[Int]] = {
    (for x <- 0 until xSize yield
      (for y <- 0 until ySize yield
        getField(x, y)).toVector).toVector
  }
}

object Board:

  def maxBombs(xSize: Int, ySize: Int): Int = (xSize * ySize) - 9

  def apply(xStart : Int, yStart : Int, xSize : Int, ySize : Int, bombCount : Int): Board =
    apply(xStart, yStart, xSize, ySize, bombCount, Random)

  def apply(xStart : Int, yStart : Int, xSize : Int, ySize : Int, bombCount : Int, rand: Random): Board =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart > 0 && yStart <= ySize && yStart > 0, "Starting position must be on the field")
    val bMax = maxBombs(xSize, ySize)
    require(bombCount >= 1 && bombCount <= bMax, s"Bomb Count must be between 1 and $bMax")
    var bC = bombCount
    var fC = bMax
    val board: Vector[Vector[Field]] = {
      Vector.tabulate(xSize, ySize) { (x, y) =>
        if isNeighbour(xStart, yStart, x, y) then
          Field(isBomb = false, isOpened = true)
        else
          val isBomb = rand.nextInt(fC) < bC
          fC -= 1 // Change later to functional
          if(isBomb) bC = bC - 1; /* Change later to functional*/ Field(isBomb, isOpened = false)
      }
    }
    new Board(xSize, ySize, xStart, yStart, bombCount, board, inGame = true)

  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)


