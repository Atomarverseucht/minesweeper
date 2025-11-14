package de.htwg.winesmeeper.aView

import de.htwg.winesmeeper.Board

// View
object TUI:
  val start = new Array[Int](5)

  def getPrintString(indx: Int): String =
    indx match
      case 0 =>
        "Please enter the size of the x coordinate. It must be >= 10"
      case 1 =>
        "Please enter the size of the y coordinate. It must be >= 10"
      case 2 =>
        "Please enter your x starting coordinate between 0 and " + (start(0) - 1)
      case 3 =>
        "Please enter your starting y coordinate between 0 and " + (start(1) - 1)
      case 4 =>
        "Please enter the count of bombs. It must be between 1 and " + (start(0) * start(1) - 9)

  def setStart(vek: Vector[Int]): Unit = for i <- start.indices do start(i) = vek(i)
  def initGameBoard: Board = {
    Board(start(2), start(3), start(0), start(1), start(4))
  }

  def getBoardString(gb: Board): String =
    val b = gb.getBoard
    val size = gb.getSize
    (for y <- 0 until size._2
      x <- 0 until size._1
    yield
      emojify(b(x)(y)) + (if x == (size._1-1) then "\n" else "")
    ).mkString

  def emojify(field: Int): String = field match {case -1 => "⬛" case -2 => "\uD83C\uDF77" case _ => s"${field}\ufe0f\u20e3"}

  def turn(input: String, gb: Board): Board =
    try
     val coordinates = input.split("[^\\d]")
      gb.openField(coordinates(0).toInt, coordinates(1).toInt)
    catch
      case _ => gb
    
  def gameEndMsg(gb: Board): String =
    (if !gb.inGame then "\u001b[1;31mGame lost\u001b[0m!"
    else if gb.isVictory then "\u001b[1;32mYou have won\u001b[0m!"
    else "???")
    + "\n" + getBoardString(gb)
