package de.htwg
import scala.util.Random

class Board(xStart : Int, yStart : Int, xSize : Int, ySize : Int, BombCount : Int) {
  class Field(Bomb: Boolean) {
    val isBomb: Boolean = this.Bomb
    private var isOpened : Boolean = false

    def getField() : String = {
      if !isOpened then "closed"
      else if isBomb then "bomb"
      else "field"
    }

    def openField() : Boolean = {
      isOpened = true
      isBomb
    }
  }

  // Konstruktor
  val Board : Array[Array[Field]] = Array.ofDim(xSize,ySize)
  private var inGame : Boolean = true
  initBoard()

  private def initBoard(): Unit = {
    var gfieldCount = (xSize * ySize) - 9
    var bombCount = BombCount
    for x <- 0 until xSize
        y <- 0 until ySize do
      if isNeighbour(xStart, yStart, x, y) then { 
        Board(x)(y) = Field(false)
        Board(x)(y).openField()
      } else
        val b = Random.nextInt(gfieldCount) < bombCount
        Board(x)(y) = Field(b)
        if b then bombCount -= 1
        gfieldCount -= 1
  }

  def openField(x: Int, y: Int) : Char = {
    if !inGame then throw new IllegalArgumentException("You cannot open a field after loosing")
    val bomb: Boolean = Board(x)(y).openField()
    if bomb then
      inGame = false
      'b'
    else
      'k' // Hier kommt noch die Berechnung der Bomben-Nachbarn
  }

  def getField(x: Int, y: Int): String = Board(x)(y).getField()
  
  private def isNeighbour(x0: Int, y0: Int, x1: Int, y1: Int): Boolean =
    ((x0-x1).abs <= 1) && ((y0-y1).abs <= 1)
}

