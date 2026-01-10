package de.htwg.winesmeeper.Controller.ImplController

import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandManagerTrait, TurnCmdManagerTrait}
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.Config
import javafx.scene.input.KeyCode

import scala.util.Try

case class Controller (var gb: BoardTrait) extends ControllerTrait:
 
  var state: GameState = Start(this)
  override val undo: TurnCmdManagerTrait = Config.mkUndo(this)
  override val sysCmd: SysCommandManagerTrait = Config.standardSysCmdMan
  
  override def turn(observerID: Int, cmd: String, x: Try[Int], y: Try[Int]): Try[String] = {
    state.turn(observerID, cmd.toLowerCase, x.get, y.get)
  }

  override def changeState(newState: String): Unit = state.changeState(newState)

  override def isSysCmd(cmd: String): Boolean = sysCmd.isSysCmd(cmd.toLowerCase())
  
  override def doSysCmd(observerID: Int, cmd: String, params: Vector[String]): Option[String] =
    sysCmd.doSysCmd(observerID, this, cmd.toLowerCase(), params)
    
  override def getBoard: Vector[Vector[Int]] = gb.getBoard
  
  override def getSize: (Int, Int) = gb.getSize
  
  override def inGame: Boolean = state.inGame

  override def gameState: String = state.gameState
  
  override def getSysCmdList: List[String] = sysCmd.getSysCmdList.map(sys => sys.cmd)
  
  override def doShortCut(observerID: Int, key: KeyCode): Option[String] = sysCmd.doShortCut(observerID, this, key)

  override def isVictory: Boolean = gb.isVictory

  override def toString: String = // wird auch als save-Darstellung verwendet
    val version = s"version: ${de.htwg.winesmeeper.BuildInfo.version}\n"
    val stateS = s"state: $gameState\n"
    val boardS = s"board: $gb\n"
    val undoStackS = s"undo: §${undo.getStacks._1.mkString(", ")}\n"
    val redoStackS = s"redo: §${undo.getStacks._2.mkString(", ")}"
    version + stateS + boardS + undoStackS + redoStackS
  
object Controller:
  def apply(xStart: Int, yStart: Int, gb: BoardTrait): Controller =
    val out = new Controller(gb)
    val undo = Config.mkUndo(out)
    for fx <- xStart - 1 to xStart + 1; fy <- yStart - 1 to yStart + 1 do
      if gb.in(fx, fy) then undo.doCmd(-1,"open", fx, fy)
    out