package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Model.Board
import de.htwg.winesmeeper.Model.Field

// Following is given: x and y is in the field and inGame = true
trait TurnStrategy:
  def execute(x: Int, y: Int, gb: Board): Board
  val cmd: String
  val helpMsg: String

val stratList: Vector[TurnStrategy] = Vector(OpenFieldStrategy, FlagStrategy)

object OpenFieldStrategy extends TurnStrategy:
  override val cmd = "open"
  override val helpMsg = "opens the field of the given coordinate"

  override def execute(x: Int, y: Int, gb: Board): Board =
    val xSize = gb.board.length
    val ySize = gb.board(0).length
    val f = gb.getFieldAt(x, y)
    val newVector = gb.board.updated(x, gb.board(x).updated(y, Field(f.isBomb, true, f.isFlag)))
    val newB = new Board(newVector, gb.notLost && !f.isBomb)
    if gb.getBombNeighbour(x, y) == 0 then
      (-1 to 1).foldLeft(newB) { (b, i) =>
        (-1 to 1).foldLeft(b) { (b2, ii) =>
          val fx = x + i
          val fy = y + ii
          if gb.in(fx, fy) && !newB.board(fx)(fy).isOpened then
            this.execute(fx, fy, b2)
          else b2
        }
      }
    else newB


object FlagStrategy extends TurnStrategy:
  override val cmd = "flag"
  override val helpMsg: String = "sets a flag at the given coordinate"

  override def execute(x: Int, y: Int, gb: Board): Board =
    val f = gb.getFieldAt(x, y)
    if(f.isOpened) then gb
    else Board(gb.board.updated(x, gb.board(x).updated(y, Field(f.isBomb, f.isOpened, !f.isFlag))), gb.notLost)