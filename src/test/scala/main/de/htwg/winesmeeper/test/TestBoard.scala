package de.htwg.winesmeeper

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.winesmeeper.TUI._

class TestBoard extends AnyWordSpec with Matchers {
    "The Board" should:
        var b = Board(3, 4, 12, 12, 10)
        val bomb = b.findBomb
        "throw right Exceptions" in:
            an [IllegalArgumentException] should be thrownBy Board(3, 4, 12, 12, 1000)

        "have the correct size" in:
            b.getSize should be (12, 12)
        "have correct bomb count" in:
            b.bombCount shouldBe 10
            b.inGame shouldBe b.checkGameState
        "have bomb neighbours" in:
            b.getBombNeighbour(3, 4) shouldBe 0
            b.getBombNeighbour(4,4) should (be >= 0 and be <= 8)

            b.getField(3, 4) shouldBe 0
            
            b.getSize should not equal 0
        "open" in :
            b.openField(4, 4) shouldBe a[Board]
            b = b.openField(bomb._1, bomb._2)
            b shouldBe a[Board]
        "have bombs" in:
            b.getField(bomb._1, bomb._2) shouldBe -2
        "have a closed field" in:
            b.getField(7, 8) shouldBe -1

}
