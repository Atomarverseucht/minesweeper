package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.SysCommands.LastElemSysCommand
import de.htwg.winesmeeper.Controller.Commands.LastElemCmdCOR
import de.htwg.winesmeeper.Controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CommandSpec extends AnyWordSpec with Matchers:
  "The Command" should:
    val testCtrl = Controller(10, 10, 1, 1, 25)
    "throw Exceptions" in:
      an[IllegalArgumentException] should be thrownBy LastElemCmdCOR.buildCmd("dosn't matter", 5, 5, testCtrl)
      LastElemSysCommand.execute(testCtrl) shouldBe "No such command"