package de.htwg.winesmeeper.Model

trait BoardTrait:

  def getBoard: Vector[Vector[Int]]

  def getSize: (Int, Int)

  def getBombNeighbour(x: Int, y: Int): Int

  def updateField (indX: Int, indY: Int, isBomb: Boolean, isOpened: Boolean, isFlag: Boolean): BoardTrait

  def getFieldAt (indX: Int, indY: Int): FieldTrait

  def isVictory: Boolean

  def in (x: Int, y: Int): Boolean
