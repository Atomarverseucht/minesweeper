package de.htwg.winesmeeper.Model

import scala.xml.Elem

trait FieldTrait:
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean
  
  def toXml: Elem
