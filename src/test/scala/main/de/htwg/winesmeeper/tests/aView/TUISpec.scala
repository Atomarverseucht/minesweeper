package main.de.htwg.winesmeeper.tests.aView

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.{Config, Observer, start}
import de.htwg.winesmeeper.aView.TUI.TUIHelp
import de.htwg.winesmeeper.BuildInfo.version

import scala.util.Try
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec with Matchers:
  "The TUI" should:
    val sizeX = 25
    val sizeY = 25
    val c: ControllerTrait = buildController(10, 10, 1, 1, 20)
    val ctrl = buildController(sizeX, sizeY, 5, 5, 20)
    "have the right size" in:
      sizeX shouldBe ctrl.getSize._1
      sizeY shouldBe ctrl.getSize._2

    "have output strings" in:
      TUIHelp.initVals = Array("","25","25","1","1","10")
      (for i <- 0 until 5 yield TUIHelp.getPrintString(i)) shouldBe
        IndexedSeq("Please enter the size of the x-coordinate. It must be >= 10",
        "Please enter the size of the y-coordinate. It must be >= 10",
        "Please enter your starting x-coordinate between 0 and 24",
        "Please enter your starting y-coordinate between 0 and 24",
        "Please enter the count of bombs. It must be between 1 and 616")

    "have a String of the board" in:
      TUIHelp.getBoardString(ctrl) shouldBe a[String]

    "have the right bomb emoji" in:
      TUIHelp.emojify(-2) shouldBe "*"
      TUIHelp.emojify(-1) shouldBe "\u001b[1;37m#\u001b[0m"
      TUIHelp.emojify(1) shouldBe "\u001b[1;94m1\u001b[0m"

    "have right end-msgs" in:
      val w = buildController(10, 10, 5, 5, 91)
      TUIHelp.gameEndMsg(w) shouldBe "\u001b[1;32mYou have won\u001b[0m!"
      val lBoard = Config.mkBoard(Vector.fill(10, 10)(Config.mkField(true, false, false)))
      val l = Config.mkController(9, 9, lBoard.updateField(1, 1, Config.mkField(false, false, false)))
      TUIHelp.turn(-1, "flag 2 2", l) shouldBe "Invalid command!"
      TUIHelp.turn(-1, "open 2 2", l) shouldBe "Invalid command!"
      TUIHelp.gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!"
      TUIHelp.gameEndMsg(ctrl) shouldBe "???"
      TUIHelp.turn(-1, "open 2 2", l) shouldBe "Invalid command!"

    "checked invalid turn" in :
      val c: ControllerTrait = buildController(10, 10, 1, 1, 20)
      TUIHelp.turn(-1, "gfjzgfkf", c) shouldBe "Invalid command!"
      TUIHelp.turn(-1, "1000 1000", c) shouldBe "Invalid command!"
      TUIHelp.turn(-1, "save hi", c) shouldBe "Board saved"
      TUIHelp.turn(-1, "generate 10 10 1 1 10", c)
      TUIHelp.turn(-1, "load hi lul", c) shouldBe f"Loaded: hi.xml (v${version})\n  For bringing back the old file, type: 'load loadBackup'\n  active version: ${version}"
      c.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      val ctrl_ = buildController(20, 20, 1, 1, 100)
      val sub = dummySub(ctrl_)
      ctrl_.turn(-1, "flag", Try(10), Try(10)).isSuccess shouldBe true
      ctrl_.turn(-1, "open", Try(1), Try(1)).isSuccess shouldBe false
      ctrl_.turn(-1, "flag", Try(1), Try(1)).isSuccess shouldBe false
      ctrl_.doSysCmd(sub.observerID, "generate", Vector("nothing"))
      ctrl_.removeSub(sub)

  "an User Interface" should:
    "be useable" in:
      val fakeInput =
        """open 5 5
          |flag 7 7
          |open.10000usifduoiwstrhfgu9sfh10000
          |flag 9 9
          |flag 8 8
          |open.1,1
          |help
          |undo
          |undo
          |redo
          |save saveGame
          |load saveGame
          |quit
          |""".stripMargin

      val in = new ByteArrayInputStream(fakeInput.getBytes())
      Console.withIn(in) {
        start
      }

  class dummySub(ctrl: ControllerTrait) extends Observer(ctrl):
    override def update(): Unit = {}

    override def generate(): Unit = {}

def buildController(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): ControllerTrait =
  Config.mkController(xStart, yStart, Config.generateBoard(xSize, ySize, xStart, yStart, bombCount))