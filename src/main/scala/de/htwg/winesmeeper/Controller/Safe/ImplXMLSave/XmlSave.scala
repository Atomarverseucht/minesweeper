package de.htwg.winesmeeper.Controller.Safe.ImplXMLSave

import de.htwg.winesmeeper.Controller.Safe.SaferTrait

object XmlSave extends SaferTrait:
  override val formatName: String = "xml"

  override def save(fileName: String): Unit = ???

  override def load(fileName: String): Unit = ???
