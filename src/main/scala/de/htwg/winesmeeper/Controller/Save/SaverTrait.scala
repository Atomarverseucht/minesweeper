package de.htwg.winesmeeper.Controller.Save

import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.ControllerTrait

import java.nio.file.{Files, Path, Paths}

trait SaverTrait:
  private val standardFileName = "winesmeeper-SaveFile"
  val formatName: String

  def save(ctrl: ControllerTrait, fileName: String = standardFileName): Option[String]

  def load(ctrl: ControllerTrait, fileName: String = standardFileName): SavedData
  
  protected def write(file: String, content: String): Unit =
    Files.write(Paths.get(f"${Config.savePath}$file.$formatName"), content.getBytes)