package de.htwg
import scala.util.Random

sealed trait GameBoard {
  def openField(x: Int, y: Int) : Char
  def getField(x: Int, y: Int): Char
  def getSize: (Int, Int)
  //def checkGameState: Boolean
}

sealed trait Field() {}
case class OpenField() extends Field {}
case class ClosedField() extends Field {}


class Board(xStart : Int, yStart : Int, xSize : Int, ySize : Int, bombCount : Int) extends GameBoard {
  //override def checkGameState: Boolean =

  private class Field(Bomb: Boolean) {
    val isBomb: Boolean = this.Bomb
    private var isOpened : Boolean = false

    def getField : Char = {
      if !isOpened then 'c'
      else if isBomb then 'b'
      else 'f'
    }

    def openField : Boolean = {
      true
      isBomb
    }
  }

  // Konstruktor
  // Errors
  val bMax = ((xSize * ySize) - 9)

  require(xSize > 10 && ySize > 10, "x and y size must be >= 10")
  require(xStart <= xSize && xStart > 0 && yStart <= ySize && yStart > 0, "Starting position must be on the field")
  require(bombCount > 1 && bombCount < bMax, s"Bomb Count must be between 1 and $bMax")

  private val Board : Array[Array[Field]] = Array.ofDim(xSize,ySize)
  private var inGame : Boolean = true
  val a: Array[Int] = Array(1,2,3,4)
  a.clone().update(a(2), 5)


  initBoard(bMax, bombCount)

  private def initBoard(gFieldCount: Int, bombCount: Int): Unit = {
    var g = gFieldCount
    var bo = bombCount
    for x <- 0 until xSize
        y <- 0 until ySize do
      if isNeighbour(xStart, yStart, x, y) then {
        Board(x)(y) = Field(false)
        Board(x)(y).openField
      } else
        val b: Boolean = Random.nextInt(g) < bombCount
        Board(x)(y) = Field(b)
        if b then bo -= 1
        g -= 1
  }

  override def openField(x: Int, y: Int) : Char =
    require(!inGame, "You cannot open a field after losing")
    val bomb: Boolean = Board(x)(y).openField
    if bomb then
      inGame = false
      'b'
    else
      getBombNeighbour(x, y).toString.head

  private def getBombNeighbour(x: Int, y: Int): Int = {
    var bc : Int = 0
    for vx <- -1 to 1
        vy <- -1 to 1 do
      val x_ = x + vx
      val y_ = y + vy
      if x_ >= 0 && y_ >= 0 && Board(x_)(y_).isBomb then bc += 1
    bc
  }
  override def getField(x: Int, y: Int): Char =
    var out : Char = Board(x)(y).getField
    if out.equals('f') then
      getBombNeighbour(x, y).toString.head
    else out


  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)

  override def getSize: (Int, Int) = (Board.length, Board(0).length)
}

