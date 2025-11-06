import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.TUI.*
import de.htwg.Board

class TestTUI extends AnyWordSpec with Matchers {
  "The TUI" should:
    setStart(Vector(10, 10, 5, 5, 10))
    val gb = initGameBoard
    "have the right size" in:
      start(0) = gb.xSize
      start(1) = gb.ySize
    "have output strings" in:
      (for i <- 0 until 5 yield getPrintString(i)).toVector shouldBe
        Vector("Please enter the size of the x coordinate. It must be >= 10",
      "Please enter the size of the y coordinate. It must be >= 10",
      "Please enter your x starting coordinate between 0 and 9",
      "Please enter your starting y coordinate between 0 and 9",
      "Please enter the count of bombs. It must be between 1 and 91")
    "have a String of the board" in:
      getBoardString(gb) shouldBe a[String]
    "have made a turn" in:
      turn("1 1", gb) shouldBe a[Board]

    "dont change if unvalid turn" in:
      turn("gfjzgfkf", gb) shouldBe gb
}
