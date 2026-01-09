package main.de.htwg.winesmeeper.tests.aView

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.{Observer, WinesmeeperModule, start}
import de.htwg.winesmeeper.aView.TUI.TUI

import scala.util.Try
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions.*
import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec with Matchers:
  val injector: Injector = Guice.createInjector(WinesmeeperModule)
  val ctrlMaker: (Int, Int, BoardTrait) => ControllerTrait = injector.instance
  val boardMaker: Vector[Vector[FieldTrait]] => BoardTrait = injector.instance
  val boardGenerator: (Int, Int, Int, Int, Int) => BoardTrait = injector.instance
  val fieldMaker: (Boolean, Boolean, Boolean) => FieldTrait = injector.instance
  "The TUI" should:
    val sizeX = 25
    val sizeY = 25
    val ctrl = buildController(sizeX, sizeY, 5, 5, 20)
    val tui = TUI(ctrl)
    "have the right size" in:
      sizeX shouldBe ctrl.getSize._1
      sizeY shouldBe ctrl.getSize._2

    "have output strings" in:
      (for i <- 0 until 5 yield tui.getPrintString(i)).toVector shouldBe
        Vector("Please enter the size of the x coordinate. It must be >= 10",
        "Please enter the size of the y coordinate. It must be >= 10",
        "Please enter your x starting coordinate between 0 and 24",
        "Please enter your starting y coordinate between 0 and 24",
        "Please enter the count of bombs. It must be between 1 and 616")

    "have a String of the board" in:
      tui.getBoardString(ctrl) shouldBe a[String]

    "have the right bomb emoji" in:
      tui.emojify(-2) shouldBe "*"
      tui.emojify(-1) shouldBe "\u001b[1;37m#\u001b[0m"
      tui.emojify(1) shouldBe "\u001b[1;94m1\u001b[0m"

    "have right end-msgs" in:
      val w = buildController(10, 10, 5, 5, 91)
      tui.gameEndMsg(w) shouldBe "\u001b[1;32mYou have won\u001b[0m!"
      val lBoard = boardMaker(Vector.fill(10, 10)(fieldMaker(true, false, false)))
      val l = ctrlMaker(9, 9, lBoard.updateField(1, 1, fieldMaker(false, false, false)))
      tui.turn("flag 2 2", l) shouldBe ""
      tui.turn("open 2 2", l) shouldBe ""
      tui.gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!"
      tui.gameEndMsg(ctrl) shouldBe "???"
      tui.turn("open 2 2", l) shouldBe ""

    "checked unvalid turn" in :
      val c: ControllerTrait = buildController(10, 10, 1, 1, 20)
      tui.turn("gfjzgfkf", c) shouldBe "Invalid command!"
      tui.turn("1000 1000", c) shouldBe "Invalid command!"
      tui.turn("load hi lul", c)
      c.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      val ctrl_ = buildController(20, 20, 1, 1, 100)
      val sub = dummySub(ctrl_)
      ctrl_.turn(-1, "flag", Try(10), Try(10)).get shouldBe true
      ctrl_.turn(-1, "open", Try(1), Try(1)).get shouldBe false
      ctrl_.turn(-1, "flag", Try(1), Try(1)).get shouldBe false
      ctrl_.removeSub(sub)

  "an User Interface" should:
    "be useable" in:
      val fakeInput =
        """10
          |10
          |5
          |5
          |90
          |flag 7 7
          |open.10000usifduoiwstrhfgu9sfh10000
          |open.1,1
          |help
          |undo
          |redo
          |save
          |load
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
  val injector: Injector = Guice.createInjector(WinesmeeperModule)
  val ctrlMaker: (Int, Int, BoardTrait) => ControllerTrait = injector.instance
  val boardGenerator: (Int, Int, Int, Int, Int) => BoardTrait = injector.instance
  ctrlMaker(xStart, yStart, boardGenerator(xSize, ySize, xStart, yStart, bombCount))