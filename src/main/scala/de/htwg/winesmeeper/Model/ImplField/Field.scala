package de.htwg.winesmeeper.Model.ImplField

import de.htwg.winesmeeper.Model.FieldTrait
import jakarta.inject.Inject

case class Field @Inject() (isBomb: Boolean,
                            isOpened: Boolean,
                            isFlag: Boolean = false) 
  extends FieldTrait
