package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.SysCommands.LastElemSysCommand
import de.htwg.winesmeeper.Controller.Commands.LastElemCmdCOR
import de.htwg.winesmeeper.Controller.SysCommands.SysCommandManager
import de.htwg.winesmeeper.Controller.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.Try

import java.nio.file.{Files, Paths}

class CommandSpec extends AnyWordSpec with Matchers:
  "The Command" should:
    val testCtrl = Controller(10, 10, 1, 1, 25)
    "throw Exceptions" in:
      LastElemCmdCOR.buildCmd("doesn't matter", 5, 5, testCtrl).isFailure shouldBe true
      LastElemSysCommand.execute(testCtrl,"invalid") shouldBe "No such command"

    "should discard with undo" in:
      testCtrl.undo.doCmd("flag", 9, 9) shouldBe true
      testCtrl.undo.doCmd("flag", 8, 8) shouldBe true
      testCtrl.undo.doCmd("open", 9, 9) shouldBe true
      testCtrl.doSysCmd("undo")
      testCtrl.doSysCmd("undo")

    "should be false with unvalid turns" in:
      testCtrl.undo.doCmd("error", 9, 9) shouldBe false

    "should have redo" in:
      testCtrl.doSysCmd("redo", Vector("", "2"))
      testCtrl.doSysCmd("undo", Vector("", "2"))

    "should checks version by loading" in:
      testCtrl.doSysCmd("save")
      testCtrl.doSysCmd("load", Vector("load", "file"))
      testCtrl.doSysCmd("load")
      Files.write(SysCommandManager.savedGame(Try("")), "version: invalid\n".getBytes())
      testCtrl.doSysCmd("load")

    "specific help messages:" in:
      testCtrl.doSysCmd("help", Vector("help", "open"))