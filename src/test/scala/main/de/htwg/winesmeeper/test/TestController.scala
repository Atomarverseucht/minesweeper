package main.de.htwg.winesmeeper.test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.{Field,Board}

class TestController extends AnyWordSpec with Matchers:
  "The Controller" should:
    val bVec = Vector(Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(true,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)))
    val ctrl = new Controller(new Board(bVec, true))

    "have an init function" in:
      val initC = Controller.initController(10,10,10,1,1)

    "have an output vector" in:
      ctrl.getBoard shouldBe ctrl.gb.getBoard
      
    "have the right size" in:
      ctrl.getSize shouldBe(10, 10)
      
    "should be ingame" in:
      ctrl.inGame shouldBe true
      
    "should have the right game-state" in:
      ctrl.gameState shouldBe "run"
      val w = Controller.initController(10, 10, 5, 5, 91)
      w.gameState shouldBe "win"
      val l = Controller.initController(10, 10, 5, 5, 90); l.openField(1,1)
      l.gameState shouldBe "loose"