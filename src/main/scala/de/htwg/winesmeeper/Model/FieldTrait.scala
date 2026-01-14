package de.htwg.winesmeeper.Model

import scala.xml.Node

trait FieldTrait:
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean

  def toXml: Node

  def fromXml(elem: Node): FieldTrait
