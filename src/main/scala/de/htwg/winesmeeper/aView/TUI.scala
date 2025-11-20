package de.htwg.winesmeeper.aView

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Observer

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

  def setStart(vec: Vector[Int]): Unit = for i <- start.indices do start(i) = vec(i)
  
  def initController: Controller = Controller.initController(start(0), start(1), start(2), start(3), start(4))

  def getBoardString(ctrl: Controller): String = // TUI-design for the Board
    val b = ctrl.getBoard
    val size = ctrl.getSize
    (for y <- 0 until size._2
      x <- 0 until size._1
    yield
      emojify(b(x)(y)) + (if x == (size._1-1) then "\n" else "")
    ).mkString

  // TUI-design of one specific field
  def emojify(field: Int): String = field match {case -1 => "█" case -2 => "*" case _ => s"${field}"}

  def turn(input: String, ctrl: Controller): Boolean =
    try
      val coordinates = input.split("[^\\d]+")
      ctrl.openField(coordinates(0).toInt, coordinates(1).toInt)
    catch
      case _ => false

  def gameEndMsg(ctrl: Controller): String =
    val out = ctrl.gameState match
      case "loose" =>
        "\u001b[1;31mGame lost\u001b[0m!"
      case "win" =>
        "\u001b[1;32mYou have won\u001b[0m!"
      case _ =>
        "???"
    out
