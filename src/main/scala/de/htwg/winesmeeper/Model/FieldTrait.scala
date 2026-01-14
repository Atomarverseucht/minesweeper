package de.htwg.winesmeeper.Model

import scala.xml.{Elem, Node}

trait FieldTrait:
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean
  
  def toXml: Elem
  
  def fromXml(elem: Node): FieldTrait
