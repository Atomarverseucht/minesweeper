package de.htwg

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.Board._

class TestInitializeBoard extends AnyWordSpec with Matchers {
    "The Board" should:
        "be initialized" in:
            val b = Board(3, 4, 12, 12, 10)
            an [IllegalArgumentException] should be thrownBy Board(3, 4, 12, 12, 1000)

            b.getSize should be (12, 12)
            b.bombCount should (be > 1 and be < maxBombs(b.xSize, b.ySize))
            emojify(8) should not be empty
            emojify(8) shouldBe "8️⃣"
            b.inGame shouldBe b.checkGameState
            b.getBombNeighbour(3, 4) shouldBe 0
            b.openField(4, 4) should (equal('b') or equal('0') or
                equal('1') or equal('2') or equal('3') or equal('4') or
                equal('5') or equal('6') or equal('7') or equal('8'))

            b.getField(3, 4) shouldBe '0'
            
            b.getSize should not equal 0

            //neu:
            val bomb = b.findBomb(11, 0)
            an [Exception] should be thrownBy b.findBomb(11,11)
            //alt:
            b.openField(bomb._1, bomb._2) shouldBe b.getField(bomb._1, bomb._2)
            b.getField(bomb._1, bomb._2) shouldBe 'b'
            b.getField(bomb._1+1, bomb._2+1) shouldBe 'c'
}
