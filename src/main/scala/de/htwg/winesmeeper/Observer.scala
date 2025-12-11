package de.htwg.winesmeeper


trait Observer {
  def update(): Unit
}

trait Observable {
  private var subscribers: Vector[Observer] = Vector()
  def addSub(s: Observer): Unit = subscribers = subscribers :+ s
  def removeSub(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(): Unit = subscribers.foreach(o => o.update())
}
