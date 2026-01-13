package de.htwg.winesmeeper.Controller.Safe

trait SaferTrait:
  private val standardFileName = "winesmeeper-SaveFile"
  val formatName: String

  def save(fileName: String = standardFileName): Unit

  def load(fileName: String = standardFileName): Unit

