package main.de.htwg.winesmeeper.tests.aView

import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.{Observer, getPrintString}
import de.htwg.winesmeeper.aView.TUI.TUIHelper.*
import de.htwg.winesmeeper.{start, Config}
import scala.util.Try
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec with Matchers:
  "The TUI" should:
    val sizeX = 25
    val sizeY = 25
    val gb = buildController(sizeX, sizeY, 5, 5, 20)
    "have the right size" in:
      sizeX shouldBe gb.getSize._1
      sizeY shouldBe gb.getSize._2

    "have output strings" in:
      (for i <- 0 until 5 yield getPrintString(i)).toVector shouldBe
        Vector("Please enter the size of the x coordinate. It must be >= 10",
        "Please enter the size of the y coordinate. It must be >= 10",
        "Please enter your x starting coordinate between 0 and 24",
        "Please enter your starting y coordinate between 0 and 24",
        "Please enter the count of bombs. It must be between 1 and 616")

    "have a String of the board" in:
      getBoardString(gb) shouldBe a[String]

    "have the right bomb emoji" in:
      emojify(-2) shouldBe "*"
      emojify(-1) shouldBe "\u001b[1;37m#\u001b[0m"
      emojify(1) shouldBe "\u001b[1;94m1\u001b[0m"

    "have right end-msgs" in:
      val w = buildController(10, 10, 5, 5, 91)
      gameEndMsg(w) shouldBe "\u001b[1;32mYou have won\u001b[0m!"
      val lBoard = Config.standardBoard(Vector.fill(10, 10)(Config.standardField(true, false, false)))
      val l = Config.standardController(9, 9, lBoard.updateField(1, 1, Config.standardField(false, false, false)))
      turn("flag 2 2", l) shouldBe ""
      turn("open 2 2", l) shouldBe ""
      gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!"
      gameEndMsg(gb) shouldBe "???"
      turn("open 2 2", l) shouldBe ""

    "checked unvalid turn" in :
      val c: ControllerTrait = buildController(10, 10, 1, 1, 20)
      turn("gfjzgfkf", c) shouldBe "Invalid command!"
      turn("1000 1000", c) shouldBe "Invalid command!"
      turn("load hi lul", c)
      c.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      val ctrl = buildController(20, 20, 1, 1, 100)
      ctrl.addSub(dummySub)
      ctrl.turn("flag", Try(10), Try(10)).get shouldBe true
      ctrl.turn("open", Try(1), Try(1)).get shouldBe false
      ctrl.turn("flag", Try(1), Try(1)).get shouldBe false
      ctrl.removeSub(dummySub)

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

  object dummySub extends Observer:
    override def update(): Unit = {}
    
def buildController(xSize: Int, ySize: Int, xStart: Int, yStart: Int, bombCount: Int): ControllerTrait =
  Config.standardController(xStart, yStart, Config.standardBoardGenerate(xSize, ySize, xStart, yStart, bombCount))