package main.de.htwg.winesmeeper.test
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.winesmeeper.aView.TUI._
import de.htwg.winesmeeper.Controller.Controller

class TestTUI extends AnyWordSpec with Matchers:
  "The TUI" should:
    setStart(Vector(10, 10, 5, 5, 10))
    val gb = initController
    "have the right size" in:
      start(0) = gb.getSize._1
      start(1) = gb.getSize._2

    "have output strings" in:
      (for i <- 0 until 5 yield getPrintString(i)).toVector shouldBe
        Vector("Please enter the size of the x coordinate. It must be >= 10",
      "Please enter the size of the y coordinate. It must be >= 10",
      "Please enter your x starting coordinate between 0 and 9",
      "Please enter your starting y coordinate between 0 and 9",
      "Please enter the count of bombs. It must be between 1 and 91")

    "have a String of the board" in:
      getBoardString(gb) shouldBe a[String]

    "have the right bomb emoji" in:
      emojify(-2) shouldBe "\uD83C\uDF77"
    "have made a turn" in:
      turn("1 1", gb) shouldBe a[Controller]

    "have right end-msgs" in:
      val w = Controller.initController(10, 10, 5, 5, 91)
      gameEndMsg(w) shouldBe "\u001b[1;32mYou have won\u001b[0m!\n" + getBoardString(w)
      val l = Controller.initController(10, 10, 5, 5, 90).openField(2, 2)
      gameEndMsg(l) shouldBe "\u001b[1;31mGame lost\u001b[0m!\n" + getBoardString(l)
      gameEndMsg(gb) shouldBe "???\n" + getBoardString(gb)

    "dont change if unvalid turn" in:
      turn("gfjzgfkf", gb) shouldBe gb
      turn("1000 1000", gb) shouldBe gb
      gb.inGame shouldBe true

    "opens a lot of fields when field zero" in:
      Controller.initController(20, 20, 1, 1, 1).openField(4, 4)
