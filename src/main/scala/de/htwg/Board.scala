package de.htwg
import scala.util.Random

sealed trait GameBoard {
  def openField(x: Int, y: Int) : Char
  def getField(x: Int, y: Int): Char
  def getSize: (Int, Int)
  def checkGameState: Boolean
}

case class Field(isBomb: Boolean, isOpened: Boolean)


case class Board private (
  xStart : Int,
  yStart : Int,
  xSize : Int,
  ySize : Int,
  bombCount : Int,
  board: Vector[Vector[Field]],
  inGame: Boolean
) extends GameBoard {

  override def checkGameState: Boolean = inGame

  //def decreaseBombCount(count: Int): Int = count - 1

  //def decreaseFieldCount(count: Int): Int = count - 1

  override def openField(x: Int, y: Int) : Char =
    require(inGame, "You cannot open a field after the game is over")
    val f = getFieldAt(x, y)
    if f.isBomb then 'b' else getBombNeighbour(x, y).toString.head

  def getBombNeighbour(x: Int, y: Int): Int =
    (for
      vx <- -1 to 1;
      vy <- -1 to 1
    yield {
      val nx = x + vx
      val ny = y + vy
      if nx >= 0 && ny >= 0 && nx < xSize && ny < ySize && board(nx)(ny).isBomb then 1 else 0
    }).sum

  override def getField(x: Int, y: Int): Char =
    val f = getFieldAt(x, y)
    if !f.isOpened then 'c'
    else if f.isBomb then 'b'
    else getBombNeighbour(x, y).toString.head

  private def getFieldAt(x: Int, y: Int): Field =
    require(x >= 0 && y >= 0 && x < xSize && y < ySize, "Coordinates out of range")
    board(x)(y)

  override def getSize: (Int, Int) = (board.length, if board.isEmpty then 0 else board(0).length)
}

object Board:

  def maxBombs(xSize: Int, ySize: Int): Int = (xSize * ySize) - 9

  def apply(xStart : Int, yStart : Int, xSize : Int, ySize : Int, bombCount : Int): Board =
    apply(xStart, yStart, xSize, ySize, bombCount, Random)

  def apply(xStart : Int, yStart : Int, xSize : Int, ySize : Int, bombCount : Int, rand: Random): Board =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart > 0 && yStart <= ySize && yStart > 0, "Starting position must be on the field")
    val bMax = maxBombs(xSize, ySize)
    require(bombCount > 1 && bombCount < bMax, s"Bomb Count must be between 1 and $bMax")

    val board: Vector[Vector[Field]] =
      Vector.tabulate(xSize, ySize) { (x, y) =>
        if isNeighbour(xStart, yStart, x, y) then Field(isBomb = false, isOpened = true)
        else
          val isBomb = rand.nextInt(bMax) < bombCount
          Field(isBomb, isOpened = false)
      }
    new Board(xStart, yStart, xSize, ySize, bombCount, board, inGame = true)

  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)

  def emojify(field: Int): String = s"${field}\ufe0f\u20e3"

