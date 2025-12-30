package de.htwg.winesmeeper.Model

trait FieldTrait:
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean
