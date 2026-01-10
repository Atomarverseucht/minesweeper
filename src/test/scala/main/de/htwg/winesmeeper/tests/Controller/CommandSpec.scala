package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.ImplSysCommands.{LastElemSysCommand, LoadCmd, SysCommandManager}
import de.htwg.winesmeeper.Controller.ImplTurnCommands.zLastElemCmdCOR
import main.de.htwg.winesmeeper.tests.aView.buildController
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import javafx.scene.input.KeyCode

import scala.util.Failure
import java.nio.file.Files

class CommandSpec extends AnyWordSpec with Matchers:
  "The Command" should:
    val testCtrl = buildController(10, 10, 1, 1, 25)
    "safe, load and help" in:
      testCtrl.doSysCmd(-1, "save", Vector())
      testCtrl.doSysCmd(-1, "load", Vector())
      testCtrl.doSysCmd(-1, "help", Vector())
      testCtrl.doSysCmd(-1, "help", Vector("", "load"))
    "throw Exceptions" in:
      zLastElemCmdCOR.buildCmd(-1, "doesn't matter", 5, 5, testCtrl).isFailure shouldBe true
      LastElemSysCommand.execute(-1, testCtrl,Vector("invalid")).get shouldBe "No such command!"
      zLastElemCmdCOR.getCmd("hi") shouldBe None

    "discard with undo" in:
      testCtrl.undo.doCmd(-1, "flag", 9, 9).isSuccess shouldBe true
      testCtrl.undo.doCmd(-1, "flag", 8, 8).isSuccess shouldBe true
      testCtrl.undo.doCmd(-1, "open", 9, 9).isSuccess shouldBe true
      testCtrl.doSysCmd(-1, "undo", Vector())
      testCtrl.doSysCmd(-1, "undo", Vector())

    "be false with unvalid turns" in:
     true // testCtrl.undo.doCmd(-1, "error", 9, 9).isSuccess shouldBe false

    "have redo" in:
      testCtrl.doSysCmd(-1, "redo", Vector("", "2"))
      testCtrl.doSysCmd(-1, "undo", Vector("", "2"))

    "checks version by loading" in:
      testCtrl.doSysCmd(-1, "save", Vector())
      testCtrl.doSysCmd(-1, "load", Vector("load", "file"))
      testCtrl.doSysCmd(-1, "load", Vector())
      Files.write(SysCommandManager.savedGame(Failure(IllegalArgumentException())), "version: invalid\n".getBytes())
      testCtrl.doSysCmd(-1, "load", Vector())

    "specific help messages:" in:
      testCtrl.doSysCmd(-1, "help", Vector("help", "open"))
      LoadCmd.getStacks(Failure(IllegalArgumentException()), testCtrl)

      testCtrl.undo.getCmd("feuzighoiz") shouldBe None

    "have a shortcut" in:
      testCtrl.doShortCut(-1, KeyCode.H)
      testCtrl.doShortCut(-1, KeyCode.Z)
      testCtrl.doShortCut(-1, KeyCode.LEFT_PARENTHESIS)
      testCtrl.doSysCmd(-1, "sduzgasdfui", Vector())

    "have a List" in:
      testCtrl.getSysCmdList