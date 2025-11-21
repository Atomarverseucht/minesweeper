package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.{Board, Field}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TestController extends AnyWordSpec with Matchers:
  "The Controller" should:
    val bVec = Vector(Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(true,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)))
    val ctrl = new Controller(new Board(bVec, true))

    "have an init function" in:
      Controller(10,10,1,1,94).gb.getField(9,9) shouldBe -1
      Controller(10,10,0,0,96).gb.getField(9,9) shouldBe -1
      Controller(10,10,0,4,94).gb.getField(9,9) shouldBe -1
    "have an output vector" in:
      ctrl.getBoard shouldBe ctrl.gb.getBoard

    "have the right size" in:
      ctrl.getSize shouldBe(10, 10)

    "should be ingame" in:
      ctrl.inGame shouldBe true

    "should have the right game-state" in:
      ctrl.gameState shouldBe "run"
      val w = Controller(10, 10, 5, 5, 91)
      w.gameState shouldBe "win"
      val l = Controller(10, 10, 5, 5, 90); l.openField(1,1)
      l.gameState shouldBe "lose"
      l.getBoard
