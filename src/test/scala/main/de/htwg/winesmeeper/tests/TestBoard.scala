package main.de.htwg.winesmeeper.tests

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.Board
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.winesmeeper.aView.TUI.*

class TestBoard extends AnyWordSpec with Matchers:
    "The Board" should:
        val b: Board = Controller(12, 12, 3, 4, 10).gb
        val bomb = b.findBomb
        "throw right Exceptions" in:
            an [IllegalArgumentException] should be thrownBy Controller(12, 12, 3, 4, 1000)

        "have the correct size" in:
            b.getSize should be (12, 12)

        "have bomb neighbours" in:
            b.getBombNeighbour(3, 4) shouldBe 0
            b.getBombNeighbour(4,4) should (be >= 0 and be <= 8)
            b.getField(3, 4) shouldBe 0
            b.getSize should not equal 0
            
        "have a closed field" in:
            b.getField(7, 8) shouldBe -1
        "have next field" in:
          b.nextField(11, 5) shouldBe (0, 6)