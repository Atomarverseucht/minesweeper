package de.htwg.winesmeeper

trait Observer(observable: Observable):
  val observerID: Int = observable.addSub(this)
  def update(): Unit
  def generate(): Unit

trait Observable:
  private var subscribers: Vector[Observer] = Vector()
  def addSub(s: Observer): Int =
    subscribers = subscribers :+ s
    subscribers.size - 1
  def removeSub(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(): Unit = subscribers.foreach(o => o.update())
  def generate(subID: Int): Unit = subscribers(subID).generate()