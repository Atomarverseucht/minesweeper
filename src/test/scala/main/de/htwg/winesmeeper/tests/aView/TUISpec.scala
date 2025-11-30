package main.de.htwg.winesmeeper.tests.aView

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.{Board, Field}
import de.htwg.winesmeeper.{Observer, aView}
import de.htwg.winesmeeper.aView.TUIHelper.*
import de.htwg.winesmeeper.start
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec with Matchers:
  "The TUI" should:
    setStart(Vector(25, 25, 5, 5, 20))
    val gb = initController
    "have the right size" in:
      initVals(0) = gb.getSize._1
      initVals(1) = gb.getSize._2

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
      emojify(-1) shouldBe "█"
      emojify(1) shouldBe "1"

    "have right end-msgs" in:
      val w = Controller(10, 10, 5, 5, 91)
      gameEndMsg(w) shouldBe "\u001b[1;32mYou have won\u001b[0m!"
      val lVec = Vector.fill(10, 10)(Field(true, false))
      val l = new Controller(new Board(lVec.updated(1, lVec(1).updated(1, Field(false, false)))))
        Controller(10, 10, 5, 5, 90)
      turn("flag 2 2", l) shouldBe ""
      turn("open 2 2", l) shouldBe ""
      gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!"
      gameEndMsg(gb) shouldBe "???"
      turn("open 2 2", l) shouldBe "Invalid command!"

    "checked unvalid turn" in :
      val c: Controller = Controller(10, 10, 1, 1, 20)
      turn("gfjzgfkf", c) shouldBe "No such command"
      turn("1000 1000", c) shouldBe "No such command"
      c.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      val ctrl = Controller(20, 20, 1, 1, 100)
      ctrl.addSub(dummySub)
      ctrl.turn("flag", 10, 10) shouldBe true
      ctrl.turn("open", 1, 1) shouldBe false
      ctrl.turn("flag", 1, 1) shouldBe false
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
          |quit
          |""".stripMargin

      val in = new ByteArrayInputStream(fakeInput.getBytes())

      Console.withIn(in){
         start()
      }

  object dummySub extends Observer:
    override def update(): Unit = {}