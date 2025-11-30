package de.htwg.winesmeeper.aView

import de.htwg.winesmeeper.Controller.Controller

// View
object TUIHelper:
  val initVals = new Array[Int](5)

  def getPrintString(indx: Int): String =
    indx match
      case 0 =>
        "Please enter the size of the x coordinate. It must be >= 10"
      case 1 =>
        "Please enter the size of the y coordinate. It must be >= 10"
      case 2 =>
        "Please enter your x starting coordinate between 0 and " + (initVals(0) - 1)
      case 3 =>
        "Please enter your starting y coordinate between 0 and " + (initVals(1) - 1)
      case 4 =>
        "Please enter the count of bombs. It must be between 1 and " + (initVals(0) * initVals(1) - 9)

  def setStart(vec: Vector[Int]): Unit = for i <- initVals.indices do initVals(i) = vec(i)
  
  def initController: Controller = Controller(initVals(0), initVals(1), initVals(2), initVals(3), initVals(4))

  def getBoardString(ctrl: Controller): String = // TUI-design for the Board
    val b = ctrl.getBoard
    val size = ctrl.getSize
    (for y <- 0 until size._2
      x <- 0 until size._1
    yield
      emojify(b(x)(y)) + (if x == (size._1-1) then "\n" else "")
    ).mkString

  // TUI-design of one specific field
  def emojify(field: Int): String = field match {case -1 => "█" case -2 => "*" case -3 => "\u001b[1;31m█\u001b[0m" case _ => s"${field}"}

  def turn(input: String, ctrl: Controller): String =
    try
      val in = input.split("[^\\w\\d]+")
      if ctrl.isSysCmd(in(0)) then ctrl.doSysCmd(in(0))
      else
        ctrl.turn(in(0), in(1).toInt, in(2).toInt); ""
    catch
      case _ => "Nice try"

  def gameEndMsg(ctrl: Controller): String =
    val out = ctrl.gameState match
      case "lose" =>
        "\u001b[1;31mGame lost\u001b[0m!"
      case "win" =>
        "\u001b[1;32mYou have won\u001b[0m!"
      case _ =>
        "???"
    out
