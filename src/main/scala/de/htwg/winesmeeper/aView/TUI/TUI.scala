package de.htwg.winesmeeper.aView.TUI

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Observer

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class TUI(ctrl: ControllerTrait) extends Observer(ctrl):
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

  override def generate(): Unit = {}

  def getBoardString(ctrl: ControllerTrait): String = // TUI-design for the Board
    val b = ctrl.getBoard
    val size = ctrl.getSize
    (for y <- 0 until size._2
        x <- 0 until size._1
    yield
      emojify(b(x)(y)) + (if x == (size._1-1) then "\n" else "")
    ).mkString

  // TUI-design of one specific field
  def emojify(field: Int): String = field match {case -1 => "\u001b[1;37m#\u001b[0m" case -2 => "*" case -3 => "\u001b[1;31m#\u001b[0m" case _ => s"\u001b[1;94m${field}\u001b[0m"}

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