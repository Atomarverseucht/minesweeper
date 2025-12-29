package de.htwg.winesmeeper.Model
import com.google.inject.assistedinject.Assisted

trait FieldTrait:
  def apply(
             @Assisted("opened") isOpened: Boolean,
             @Assisted("bomb") isBomb: Boolean,
             @Assisted("flag") isFlag: Boolean
           ): FieldTrait
  def isFlag: Boolean
  
  def isBomb: Boolean
  
  def isOpened: Boolean
