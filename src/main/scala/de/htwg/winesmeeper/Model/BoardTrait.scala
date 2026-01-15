package de.htwg.winesmeeper.Model

import play.api.libs.json.{Json, Writes}
import scala.xml.Node

trait BoardTrait(val board: Vector[Vector[FieldTrait]]):

  def getBoard: Vector[Vector[Int]]

  def getSize: (Int, Int)

  def getBombNeighbour(x: Int, y: Int): Int

  def updateField (indX: Int, indY: Int, field: FieldTrait): BoardTrait

  def getField(x: Int, y: Int): Int

  def getFieldAt (indX: Int, indY: Int): FieldTrait

  def isVictory: Boolean

  def in (x: Int, y: Int): Boolean

  def toXml: Node

  def fromXml(xml: Node): BoardTrait

