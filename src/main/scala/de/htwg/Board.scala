package de.htwg
import scala.util.Random

class Board(xStart : Int, yStart : Int, xSize : Int, ySize : Int, BombCount : Int) extends GameBoard {

  private class Field(Bomb: Boolean) {
    val isBomb: Boolean = this.Bomb
    private var isOpened : Boolean = false

    def getField : Char = {
      if !isOpened then 'c'
      else if isBomb then 'b'
      else 'f'
    }

    def openField : Boolean = {
      isOpened = true
      isBomb
    }
  }

  // Konstruktor
  private val Board : Array[Array[Field]] = Array.ofDim(xSize,ySize)
  private var inGame : Boolean = true
  initBoard

  private def initBoard: Unit = {
    var gfieldCount = (xSize * ySize) - 9
    var bombCount = BombCount
    for x <- 0 until xSize
        y <- 0 until ySize do
      if isNeighbour(xStart, yStart, x, y) then {
        Board(x)(y) = Field(false)
        Board(x)(y).openField
      } else
        val b = Random.nextInt(gfieldCount) < bombCount
        Board(x)(y) = Field(b)
        if b then bombCount -= 1
        gfieldCount -= 1
  }

  def openField(x: Int, y: Int) : Char =
    if !inGame then throw new IllegalArgumentException("You cannot open a field after loosing")
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
  def getField(x: Int, y: Int): Char =
    var out : Char = Board(x)(y).getField
    if out.equals('f') then
      getBombNeighbour(x, y).toString.head
    else out


  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)
}

