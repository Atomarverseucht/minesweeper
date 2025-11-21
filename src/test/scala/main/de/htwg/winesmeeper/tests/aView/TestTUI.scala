package main.de.htwg.winesmeeper.tests.aView

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.{Observer, aView}
import de.htwg.winesmeeper.aView.TUI.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream

class TestTUI extends AnyWordSpec with Matchers:
  "The TUI" should:
    setStart(Vector(25, 25, 5, 5, 20))
    val gb = initController
    "have the right size" in:
      start(0) = gb.getSize._1
      start(1) = gb.getSize._2

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
      val l = Controller(10, 10, 5, 5, 90)
      turn("2 2", l) shouldBe true
      gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!"
      gameEndMsg(gb) shouldBe "???"

    "checked unvalid turn" in :
      val c: Controller = Controller(10, 10, 1, 1, 10)
      turn("gfjzgfkf", c) shouldBe false
      turn("1000 1000", c) shouldBe false
      c.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      val ctrl = Controller(20, 20, 1, 1, 1)
      ctrl.addSub(dummySub)
      ctrl.openField(4, 4) shouldBe true
      ctrl.removeSub(dummySub)

  "an User Interface" should:
    "be useable" in:
      val fakeInput =
        """10
          |10
          |5
          |5
          |90
          |10000usifduoiwstrhfgu9sfh10000
          |1,1
          |""".stripMargin

      val in = new ByteArrayInputStream(fakeInput.getBytes())

      Console.withIn(in){
         aView.start()
      }

  object dummySub extends Observer:
    override def update(): Unit = {}