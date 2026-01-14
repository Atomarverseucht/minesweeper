package de.htwg.winesmeeper.Model.ImplField

import de.htwg.winesmeeper.Model.FieldTrait

import scala.xml.Elem

case class Field (isBomb: Boolean,
                  isOpened: Boolean,
                  isFlag: Boolean = false)
  extends FieldTrait:
  override def toXml: Elem =
    <field>
      <isBomb> {isBomb} </isBomb>
      <isOpened> {isOpened} </isOpened>
      <isFlag> {isFlag} </isFlag>
    </field>
