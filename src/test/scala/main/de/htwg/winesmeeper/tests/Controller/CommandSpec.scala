package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.SysCommands.{LastElemSysCommand, LoadCmd, SysCommandManager}
import de.htwg.winesmeeper.Controller.TurnCommands.{CommandManager, LastElemCmdCOR}
import de.htwg.winesmeeper.Controller.StandardController.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import javafx.scene.input.KeyCode

import scala.util.Failure
import java.nio.file.{Files, Paths}

class CommandSpec extends AnyWordSpec with Matchers:
  "The Command" should:
    val testCtrl = Controller(10, 10, 1, 1, 25)
    "should safe and load" in:
      testCtrl.doSysCmd("save")
      testCtrl.doSysCmd("load")
      testCtrl.doSysCmd("help")
      testCtrl.doSysCmd("help", Vector("", "load"))
    "throw Exceptions" in:
      LastElemCmdCOR.buildCmd("doesn't matter", 5, 5, testCtrl).isFailure shouldBe true
      LastElemSysCommand.execute(testCtrl,Vector("invalid")).get shouldBe "No such command!"
      LastElemCmdCOR.getCmd("hi") shouldBe None

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
      Files.write(SysCommandManager.savedGame(Failure(IllegalArgumentException())), "version: invalid\n".getBytes())
      testCtrl.doSysCmd("load")

    "specific help messages:" in:
      testCtrl.doSysCmd("help", Vector("help", "open"))
      LoadCmd.getStacks(Failure(IllegalArgumentException()), testCtrl)

      CommandManager.firstCommandCOR.getCmd("feuzighoiz") shouldBe None

    "have a shortcut" in:
      testCtrl.doShortCut(KeyCode.H)
      testCtrl.doShortCut(KeyCode.Z)
      testCtrl.doShortCut(KeyCode.LEFT_PARENTHESIS)
      testCtrl.doSysCmd("sduzgasdfui")

    "have a List" in:
      testCtrl.getSysCmdList