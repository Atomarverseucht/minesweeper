package de.htwg.winesmeeper

import scala.io.StdIn.{readInt, readLine}
import de.htwg.winesmeeper.Controller.wsController

@main def start(): Unit =
  wsController.control()

