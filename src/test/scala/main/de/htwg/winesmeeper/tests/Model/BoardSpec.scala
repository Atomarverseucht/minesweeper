package main.de.htwg.winesmeeper.tests.Model

import de.htwg.winesmeeper.Model.{BoardTrait, FieldTrait}
import de.htwg.winesmeeper.Config
import main.de.htwg.winesmeeper.tests.aView.buildController
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers:
    "The Board" should:
        val b: BoardTrait = Config.generateBoard(12, 12, 3, 4, 50)
        val b2: BoardTrait = Config.mkBoard(Vector.fill(12, 12)
          (Config.mkField(false, false, false))).updateField(1,1,Config.mkField(false, true, false))
        val bomb = (1,1)
        "throw right Exceptions" in:
            an [IllegalArgumentException] should be thrownBy buildController(12, 12, 3, 4, 1000)

        "have the correct size" in:
            b.getSize shouldBe (12, 12)

        "have bomb neighbours" in:
            b.getBombNeighbour(3, 4) shouldBe 0
            b.getBombNeighbour(4,4) should (be >= 0 and be <= 8)
            b.getField(3, 4)
            b.getSize should not equal 0
            
        "have a closed field" in:
            b.getField(7, 8) shouldBe -1