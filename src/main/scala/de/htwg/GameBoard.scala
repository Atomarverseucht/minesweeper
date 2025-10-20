package de.htwg

trait GameBoard {
  def openField(x: Int, y: Int) : Char
  def getField(x: Int, y: Int): Char
}
