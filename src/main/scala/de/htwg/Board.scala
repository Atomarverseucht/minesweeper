package de.htwg
import scala.util.Random

class Board(xStart : Int = 5, yStart : Int = 5, xSize : Int = 10 , ySize : Int = 10, BombCount : Int = 10) extends GameBoard {

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
  // Errors
  val bMax = ((xSize * ySize) - 9)
  if xSize < 10 || ySize < 10 then
    throw new IllegalArgumentException("x and y size must be >= 10!")
  if xStart >= xSize || xStart < 0 || yStart >= ySize || yStart < 0 then
    throw new IllegalArgumentException("Starting position must be on the field")
  if BombCount < 1 || BombCount > bMax then
    throw new IllegalArgumentException("Bomb Count must be between 1 and " + bMax)
  
  private val gameBoard : Array[Array[Field]] = Array.ofDim(xSize,ySize)
  val inGame : Boolean = true

  initBoard

  private def initBoard: Unit = {
    var gfieldCount = bMax
    var bombCount = BombCount
    for x <- 0 until xSize
        y <- 0 until ySize do
      if isNeighbour(xStart, yStart, x, y) then {
        gameBoard(x)(y) = Field(false)
        gameBoard(x)(y).openField
      } else
        val b = Random.nextInt(gfieldCount) < bombCount
        gameBoard(x)(y) = Field(b)
        if b then bombCount -= 1
        gfieldCount -= 1
  }

  override def openField(x: Int, y: Int) : Char =
    if !inGame then throw new IllegalArgumentException("You cannot open a field after loosing")
    val bomb: Boolean = gameBoard(x)(y).openField
    if bomb then

      'b'
    else
      getBombNeighbour(x, y).toString.head

  private def getBombNeighbour(x: Int, y: Int): Int = {
    var bc : Int = 0
    for vx <- -1 to 1
        vy <- -1 to 1 do
      val x_ = x + vx
      val y_ = y + vy
      if inRange(x_, y_) && gameBoard(x_)(y_).isBomb then bc += 1
    bc
  }

  def inRange(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < gameBoard.length && y < gameBoard(1).length
  override def getField(x: Int, y: Int): Char =
    val out : Char = gameBoard(x)(y).getField
    if out.equals('f') then
      getBombNeighbour(x, y).toString.head
    else out


  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)
    
  override def getSize: (Int, Int) = (gameBoard.length, gameBoard(0).length)
    
}

