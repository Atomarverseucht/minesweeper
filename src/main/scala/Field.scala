package de.htwg.se

case class Field(isHidden: Boolean, x: Int, y: Int, figure: FieldType) {
  override def toString(): String = figure.symbol 
  /*if (figure.isBomb) {
    gameOver()
  }*/

}
sealed trait FieldType {
  val isBomb: Boolean
  val symbol: String
}
case class Number(val surroundingBombs: Int) extends FieldType {
  val isBomb = false
  val symbol = surroundingBombs.toString()
}
case class Bomb() extends FieldType {
  val isBomb = true
  val symbol = "💣"
}
