package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.SysCommands.LastElemSysCommand
import de.htwg.winesmeeper.Controller.Commands.LastElemCmdCOR
import de.htwg.winesmeeper.Controller.SysCommands.SysCommandManager
import de.htwg.winesmeeper.Controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.file.{Files, Paths}
import scala.util.Failure

class CommandSpec extends AnyWordSpec with Matchers:
  "The Command" should:
    val testCtrl = Controller(10, 10, 1, 1, 25)
    "throw Exceptions" in:
      LastElemCmdCOR.buildCmd("doesn't matter", 5, 5, testCtrl).isFailure shouldBe true
      LastElemSysCommand.execute(testCtrl) shouldBe "No such command"

    "should discard with undo" in:
      testCtrl.undo.doCmd("flag", 9, 9) shouldBe true
      testCtrl.undo.doCmd("open", 9, 9) shouldBe true
      testCtrl.doSysCmd("undo")
      testCtrl.doSysCmd("undo")

    "should be false with unvalid turns" in:
      testCtrl.undo.doCmd("error", 9, 9) shouldBe false

    "should have redo" in:
      testCtrl.doSysCmd("redo")
      testCtrl.doSysCmd("redo")

    "should checks version by loading" in:
      Files.write(SysCommandManager.savedGame, "version: invalid\n".getBytes())
      testCtrl.doSysCmd("load")