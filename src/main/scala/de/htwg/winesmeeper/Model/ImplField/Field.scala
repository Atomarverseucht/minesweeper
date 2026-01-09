package de.htwg.winesmeeper.Model.ImplField

import de.htwg.winesmeeper.Model.FieldTrait

case class Field (isBomb: Boolean,
                            isOpened: Boolean,
                            isFlag: Boolean = false) 
  extends FieldTrait
