package de.htwg.winesmeeper.Controller.ImplSysCommands
import de.htwg.winesmeeper.Controller.{ControllerTrait, SysCommandCORTrait, SysCommandManagerTrait}
import javafx.scene.input.KeyCode

import scala.util.{Failure, Success, Try}
import java.nio.file.{Path, Paths}

object SysCommandManager extends SysCommandManagerTrait:
  val firstSysCmd: SysCommandCORTrait = HelpCmd

  override def isSysCmd(cmd: String): Boolean =
    firstSysCmd.getSysCmd(cmd).nonEmpty

  override def doSysCmd(observerID: Int, ctrl: ControllerTrait, cmd: String, params: Vector[String]): Option[String] =
    val com = firstSysCmd.getSysCmd(cmd)
    com match
      case Some(value) => value.execute(ctrl, params)
      case None => None

  def savedGame(fileName: Try[String]): Path =
    val fName: String = fileName match
      case Success(value) => value
      case Failure(_) => "savedGame"
    Paths.get(f"./saves/$fName.txt")
    
  override def getSysCmdList: List[SysCommandCORTrait] = firstSysCmd.listCmds

  def getAbstractCmd(cmd: String, ctrl: ControllerTrait): Option[de.htwg.winesmeeper.Controller.AbstractCmdCOR] =
    val sysCmd = firstSysCmd.getSysCmd(cmd)
    if sysCmd.nonEmpty then sysCmd
    else ctrl.undo.getCmd(cmd)
    
  override def doShortCut(ctrl: ControllerTrait, key: KeyCode): Option[String] =
    val out = firstSysCmd.getSysCmd(key).map[Option[String]](_.execute(ctrl))
    out match
      case Some(None) => None
      case Some(Some(value)) => Some(value)
      case None => None

object LastElemSysCommand extends SysCommandCORTrait:
  override val cmd: String = ""
  override val helpMsg: String = ""
  override val next: SysCommandCORTrait = this

  override def execute(ctrl: ControllerTrait, params: Vector[String]): Option[String] = Some("No such command!")

  override def getSysCmd(cmd: String): Option[SysCommandCORTrait] = None

  override def getSysCmd(key: KeyCode): Option[SysCommandCORTrait] = None

  override def listCmds: List[SysCommandCORTrait] = Nil

  override val specHelpMsg: String = ""
  override val shortcut: KeyCode = KeyCode.UNDEFINED
