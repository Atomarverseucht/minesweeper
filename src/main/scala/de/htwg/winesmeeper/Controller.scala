package de.htwg.winesmeeper
import scala.util.Random
import de.htwg.winesmeeper.*

import javax.swing.text.FieldView
import scala.annotation.tailrec
import scala.language.postfixOps

class Controller(val gb: Board):
  
  def openField(x: Int, y:Int): Controller =
    new Controller(gb.openField(x, y))
    
  def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  def getSize: (Int, Int) = gb.getSize
  def inGame: Boolean = gb.checkGameState
  
  def gameState: String = if gb.inGame then "run" else if gb.isVictory then "win" else "loose"

object Controller:
  def initController(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): Controller =
    require(xSize >= 10 && ySize >= 10, "x and y size must be >= 10")
    require(xStart <= xSize && xStart > 0 && yStart <= ySize && yStart > 0, "Starting position must be on the field")
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
      val nbc = if newV._1 then bombCount + 1 else bombCount
      val nextC = if indx + 1 < boardv.length then (indx + 1, indy) else (0, indy+1)
      initField(nextC._1, nextC._2, xStart, yStart, nboard, nbc, newV._2)