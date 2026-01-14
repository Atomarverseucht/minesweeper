package de.htwg.winesmeeper.Model.ImplField

import de.htwg.winesmeeper.Model.FieldTrait

import scala.xml.{Elem, Node, NodeSeq}

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

  override def fromXml(elem: Node): FieldTrait =
    Field(Node2Bool (elem \ "isBomb"),
      Node2Bool (elem \ "isOpened"),
      Node2Bool (elem \ "isFlag"))

  private def Node2Bool(n: NodeSeq): Boolean =
    n.head.text.replace(" ", "").toBoolean