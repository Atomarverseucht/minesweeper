package de.htwg.winesmeeper.aView.TUI

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec
import scala.io.StdIn.{readLine, readInt}
import scala.util.{Failure, Success, Try}

class TUI(ctrl: ControllerTrait) extends Observer(ctrl):
  val initVals = new Array[String](6)
  update()
  nextTurn
  
  @tailrec
  final def nextTurn: Unit =
    println(turn(readLine, ctrl))
    nextTurn

  override def update(): Unit =
    println(getBoardString(ctrl))
    if !ctrl.inGame then
      println(gameEndMsg(ctrl))

  override def generate(): Unit =
    for i <- 1 until 6 do
      println(getPrintString(i-1))
      initVals(i) = readLine
    ctrl.doSysCmd(observerID, "generate", initVals.toVector)
    
  def getBoardString(ctrl: ControllerTrait): String = // TUI-design for the Board
    val b = ctrl.getBoard
    val size = ctrl.getSize
    (for y <- 0 until size._2
        x <- 0 until size._1
    yield
      emojify(b(x)(y)) + (if x == (size._1-1) then "\n" else "")
    ).mkString

  // TUI-design of one specific field
  def emojify(field: Int): String = 
    field match 
      case -1 => "\u001b[1;37m#\u001b[0m" 
      case -2 => "*" 
      case -3 => "\u001b[1;31m#\u001b[0m" 
      case _ => s"\u001b[1;94m${field}\u001b[0m"

  def turn(input: String, ctrl: ControllerTrait): String =
    val in = input.split("[^\\w\\d]+").toVector
    if ctrl.isSysCmd(in(0)) then
      ctrl.doSysCmd(observerID, in(0), in) match
        case Some(value) => value
        case None => ""
    else
      ctrl.turn(observerID, in(0), Try(in(1).toInt), Try(in(2).toInt)) match {
        case Success(value) => ""
        case Failure(ex) => "Invalid command!"
      }

  def gameEndMsg(ctrl: ControllerTrait): String =
    val out = ctrl.gameState match
      case "lose" =>
        "\u001b[1;31mGame lost\u001b[0m!"
      case "win" =>
        "\u001b[1;32mYou have won\u001b[0m!"
      case _ =>
        "???"
    out

  def getPrintString(indx: Int): String =
    indx match
      case 0 =>
        "Please enter the size of the x-coordinate. It must be >= 10"
      case 1 =>
        "Please enter the size of the y-coordinate. It must be >= 10"
      case 2 =>
        "Please enter your starting x-coordinate between 0 and " + (initVals(1).toInt - 1)
      case 3 =>
        "Please enter your starting y-coordinate between 0 and " + (initVals(2).toInt - 1)
      case 4 =>
        "Please enter the count of bombs. It must be between 1 and " + (initVals(1).toInt * initVals(2).toInt - 9)