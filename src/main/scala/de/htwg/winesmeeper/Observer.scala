package de.htwg.winesmeeper

import scalafx.application.Platform


trait Observer {
  def update(): Unit
}

trait Observable {
  private var subscribers: Vector[Observer] = Vector()
  def addSub(s: Observer): Unit = subscribers = subscribers :+s
  def removeSub(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(): Unit = Platform.runLater(subscribers.foreach(o => o.update()))
}
