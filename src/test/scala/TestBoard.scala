package de.htwg

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.Board._

class TestBoard extends AnyWordSpec with Matchers {
    "The Board" should:
        val b = Board(3, 4, 12, 12, 10)
        val bomb = b.findBomb
        "throw right Exceptions" in:
            an [IllegalArgumentException] should be thrownBy Board(3, 4, 12, 12, 1000)

        "have the correct size" in:
            b.getSize should be (12, 12)
        "have correct bomb count" in:
            b.bombCount shouldBe 10
            emojify(8) should not be empty
            emojify(8) shouldBe "8️⃣"
            b.inGame shouldBe b.checkGameState
        "bomb neighbours" in:
            b.getBombNeighbour(3, 4) shouldBe 0
            b.getBombNeighbour(4,4) should (be >= 0 and be <= 8)
            b.openField(4, 4) shouldBe Board

            b.getField(3, 4) shouldBe '0'
            
            b.getSize should not equal 0

        "have bombs" in:
            print(bomb)
            b.openField(bomb._1, bomb._2) shouldBe b.getField(bomb._1, bomb._2)
            b.getField(bomb._1, bomb._2) shouldBe 'b'
        "h" in:
            b.getField(7, 8) shouldBe 'c'
}
