package de.htwg.winesmeeper.Controller.SysCommands
import de.htwg.winesmeeper.Controller.Controller

import scala.::

trait SysCommandCOR:
  val cmd: String
  val helpMsg: String
  val next: SysCommandCOR
  def execute(ctrl: Controller): String
  def getSysCmd(cmd: String): Option[SysCommandCOR] = if cmd == this.cmd then Some(this) else next.getSysCmd(cmd)
  def listCmds: List[SysCommandCOR] = this::next.listCmds

object SysCommandHandler:
  private val firstSysCmd: SysCommandCOR = HelpCmd

  def isSysCmd(cmd: String): Boolean =
    firstSysCmd.getSysCmd(cmd).nonEmpty

  def doSysCmd(cntrl: Controller, cmd: String): String =
    val com = firstSysCmd.getSysCmd(cmd)
    com.get.execute(cntrl)

object LastElemSysCommand extends SysCommandCOR:
  override val cmd: String = ""
  override val helpMsg: String = ""
  override val next: SysCommandCOR = this

  override def execute(ctrl: Controller): String = "No such command"

  override def getSysCmd(cmd: String): Option[SysCommandCOR] = None

  override def listCmds: List[SysCommandCOR] = Nil
