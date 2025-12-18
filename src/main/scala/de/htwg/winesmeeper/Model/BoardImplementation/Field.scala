package de.htwg.winesmeeper.Model.BoardImplementation

import de.htwg.winesmeeper.Model.FieldTrait

case class Field(isBomb: Boolean, isOpened: Boolean, isFlag: Boolean = false) extends FieldTrait

