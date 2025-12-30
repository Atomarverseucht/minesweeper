package de.htwg.winesmeeper.Model
import com.google.inject.assistedinject.Assisted

trait FieldTrait:
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean
