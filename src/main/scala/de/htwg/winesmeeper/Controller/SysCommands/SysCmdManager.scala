package de.htwg.winesmeeper.Controller.SysCommands
import de.htwg.winesmeeper.Controller.TurnCommands.CommandManager
import de.htwg.winesmeeper.Controller.ControllerTrait

import javafx.scene.input.KeyCode
import scala.util.{Failure, Success, Try}
import java.nio.file.{Path, Paths}

trait AbstractCmdCOR:
  val cmd: String
  val helpMsg: String
  val specHelpMsg: String

trait SysCommandCOR extends AbstractCmdCOR:
  val next: SysCommandCOR
  val shortcut: KeyCode
  def execute(ctrl: ControllerTrait, params: Vector[String] = Vector("no params")): Option[String]
  def getSysCmd(cmd: String): Option[SysCommandCOR] = if cmd == this.cmd then Some(this) else next.getSysCmd(cmd)
  def getSysCmd(key: KeyCode): Option[SysCommandCOR] = if key == shortcut then Some(this) else next.getSysCmd(key)
  def listCmds: List[SysCommandCOR] = this::next.listCmds

object SysCommandManager:
  val firstSysCmd: SysCommandCOR = HelpCmd

  def isSysCmd(cmd: String): Boolean =
    firstSysCmd.getSysCmd(cmd).nonEmpty

  def doSysCmd(cntrl: ControllerTrait, cmd: String, params: Vector[String]): Option[String] =
    val com = firstSysCmd.getSysCmd(cmd)
    com match
      case Some(value) => value.execute(cntrl, params)
      case None => None

  def savedGame(fileName: Try[String]): Path =
    val fName: String = fileName match
      case Success(value) => value
      case Failure(_) => "savedGame"
    Paths.get(f"./saves/$fName.txt")
    
  def getSysCmdList: Vector[SysCommandCOR] = firstSysCmd.listCmds.toVector

  def getAbstractCmd(cmd: String): Option[AbstractCmdCOR] =
    val sysCmd = firstSysCmd.getSysCmd(cmd)
    if sysCmd.nonEmpty then sysCmd
    else CommandManager.firstCommandCOR.getCmd(cmd)
    
  def doShortCut(ctrl: ControllerTrait, key: KeyCode): Option[String] =
    val out = firstSysCmd.getSysCmd(key).map[Option[String]](_.execute(ctrl))
    out match
      case Some(None) => None
      case Some(Some(value)) => Some(value)
      case None => None

object LastElemSysCommand extends SysCommandCOR:
  override val cmd: String = ""
  override val helpMsg: String = ""
  override val next: SysCommandCOR = this

  override def execute(ctrl: ControllerTrait, params: Vector[String]): Option[String] = Some("No such command!")

  override def getSysCmd(cmd: String): Option[SysCommandCOR] = None

  override def getSysCmd(key: KeyCode): Option[SysCommandCOR] = None

  override def listCmds: List[SysCommandCOR] = Nil

  override val specHelpMsg: String = ""
  override val shortcut: KeyCode = KeyCode.UNDEFINED
