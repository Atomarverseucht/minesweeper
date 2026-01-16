package de.htwg.winesmeeper.Controller

import javafx.scene.input.KeyCode

trait SysCommandManagerTrait:
  def isSysCmd(cmd: String): Boolean

  def doSysCmd(ctrl: ControllerTrait, cmd: String, params: Vector[String]): Option[String]

  def doShortCut(ctrl: ControllerTrait, key: KeyCode): Option[String]
  def getSysCmdList: List[SysCommandCORTrait]

trait SysCommandCORTrait extends AbstractCmdCOR:
  val next: SysCommandCORTrait
  val shortcut: KeyCode
  def execute(ctrl: ControllerTrait, params: Vector[String] = Vector("no params")): Option[String]
  def getSysCmd(cmd: String): Option[SysCommandCORTrait] = if cmd == this.cmd then Some(this) else next.getSysCmd(cmd)
  def getSysCmd(key: KeyCode): Option[SysCommandCORTrait] = if key == shortcut then Some(this) else next.getSysCmd(key)
  def listCmds: List[SysCommandCORTrait] = this::next.listCmds