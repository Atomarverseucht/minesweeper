package de.htwg.winesmeeper.Controller.SysCommands
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Controller.Commands.CommandManager

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
  def execute(ctrl: Controller, params: Vector[String] = Vector("no params")): String
  def getSysCmd(cmd: String): Option[SysCommandCOR] = if cmd == this.cmd then Some(this) else next.getSysCmd(cmd)
  def getSysCmd(key: KeyCode): Option[SysCommandCOR] = if key == shortcut then Some(this) else next.getSysCmd(key)
  def listCmds: List[SysCommandCOR] = this::next.listCmds

object SysCommandManager:
  private val firstSysCmd: SysCommandCOR = HelpCmd

  def isSysCmd(cmd: String): Boolean =
    firstSysCmd.getSysCmd(cmd).nonEmpty

  def doSysCmd(cntrl: Controller, cmd: String, params: Vector[String]): Try[String] =
    val com = firstSysCmd.getSysCmd(cmd)
    Try(com.get.execute(cntrl, params))

  def savedGame(fileName: Try[String]): Path =
    val fName: String = fileName match
      case Success(value) => value
      case Failure(_) => "savedGame"
    Paths.get(f"./saves/$fName.txt")

  def getAbstractCmd(cmd: String): Option[AbstractCmdCOR] =
    val sysCmd = firstSysCmd.getSysCmd(cmd)
    if sysCmd.nonEmpty then sysCmd
    else CommandManager.firstCommandCOR.getCmd(cmd)
    
  def doShortCut(ctrl: Controller, key: KeyCode): Option[String] =
    firstSysCmd.getSysCmd(key).map[String](_.execute(ctrl))

object LastElemSysCommand extends SysCommandCOR:
  override val cmd: String = ""
  override val helpMsg: String = ""
  override val next: SysCommandCOR = this

  override def execute(ctrl: Controller, params: Vector[String]): String = "No such command"

  override def getSysCmd(cmd: String): Option[SysCommandCOR] = None

  override def getSysCmd(key: KeyCode): Option[SysCommandCOR] = None

  override def listCmds: List[SysCommandCOR] = Nil

  override val specHelpMsg: String = ""
  override val shortcut: KeyCode = KeyCode.UNDEFINED
