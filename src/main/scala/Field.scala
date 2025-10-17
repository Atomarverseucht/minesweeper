package de.htwg.se

case class Field(isHidden: Boolean, x: Int, y: Int, figure: FieldKind) {
  override def toString(): String = figure.symbol 
  /*if (figure.isBomb) {
    gameOver()
  }*/

}
sealed trait FieldKind {
  val isBomb: Boolean
  val symbol: String
}
case object Number extends FieldKind {
  val isBomb = false
  val symbol = "5"
}
case object Bomb extends FieldKind {
  val isBomb = true
  val symbol = "🍾"
}
