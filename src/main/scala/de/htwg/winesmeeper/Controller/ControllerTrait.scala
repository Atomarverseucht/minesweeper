package de.htwg.winesmeeper.Controller

import de.htwg.winesmeeper.Observable
import javafx.scene.input.KeyCode

import scala.util.Try

trait ControllerTrait extends Observable:
  def inGame: Boolean

  def getBoard: Vector[Vector[Int]]

  def getSize: (Int, Int)

  def gameState: String

  def isSysCmd(cmd: String): Boolean

  def doSysCmd(cmd: String, params: Vector[String]): Option[String]

  def turn(cmd: String, x: Try[Int], y: Try[Int]): Try[Boolean]

  def doShortcut(key: KeyCode): Option[String]

  def getSysCmdList: Vector[String]
