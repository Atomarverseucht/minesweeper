package de.htwg.winesmeeper.Controller.SysCommands
import de.htwg.winesmeeper.Controller.Controller

import java.nio.file.{Path, Paths}

trait SysCommandCOR:
  val cmd: String
  val helpMsg: String
  val next: SysCommandCOR
  def execute(ctrl: Controller, cmd: String, params: Vector[String] = Vector("no params")): String
  def getSysCmd(cmd: String): Option[SysCommandCOR] = if cmd == this.cmd then Some(this) else next.getSysCmd(cmd)
  def listCmds: List[SysCommandCOR] = this::next.listCmds

object SysCommandManager:
  private val firstSysCmd: SysCommandCOR = HelpCmd

  def isSysCmd(cmd: String): Boolean =
    firstSysCmd.getSysCmd(cmd).nonEmpty

  def doSysCmd(cntrl: Controller, cmd: String, params: Vector[String] = Vector("no params")): String =
    val com = firstSysCmd.getSysCmd(cmd)
    com.get.execute(cntrl, cmd, params)


  def savedGame(fileName: String): Path = {
    val fName: String = if fileName == "" then "savedGame" else fileName
    Paths.get(f"./saves/$fName.txt")
  }

object LastElemSysCommand extends SysCommandCOR:
  override val cmd: String = ""
  override val helpMsg: String = ""
  override val next: SysCommandCOR = this

  override def execute(ctrl: Controller, cmd: String, params: Vector[String]): String = "No such command"

  override def getSysCmd(cmd: String): Option[SysCommandCOR] = None

  override def listCmds: List[SysCommandCOR] = Nil
