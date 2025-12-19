package main.de.htwg.winesmeeper.tests.Model

import de.htwg.winesmeeper.Controller.ImplController.Controller
import de.htwg.winesmeeper.Model.BoardImplementation.Board
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers:
    "The Board" should:
        val b: Board = Controller(12, 12, 3, 4, 50).gb
        val bomb = b.findBomb
        "throw right Exceptions" in:
            an [IllegalArgumentException] should be thrownBy Controller(12, 12, 3, 4, 1000)

        "have the correct size" in:
            b.getSize shouldBe (12, 12)

        "have bomb neighbours" in:
            b.getBombNeighbour(3, 4) shouldBe 0
            b.getBombNeighbour(4,4) should (be >= 0 and be <= 8)
            b.getField(3, 4) shouldBe 0
            b.getSize should not equal 0
            
        "have a closed field" in:
            b.getField(7, 8) shouldBe -1
        "have next field" in:
          b.nextField(11, 5) shouldBe (0, 6)