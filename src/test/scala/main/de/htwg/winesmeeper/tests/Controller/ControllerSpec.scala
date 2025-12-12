package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.Controller
import de.htwg.winesmeeper.Model.{Board, Field}
import scala.util.Try
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers:
  "The Controller" should:
    val bVec = Vector(Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,true), Field(false,true), Field(false,true), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)), Vector(Field(false,false), Field(true,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false), Field(false,false)))
    val ctrl = new Controller(new Board(bVec))
    ctrl.gb.findBomb
    "have an init function" in:
      Controller(10,10,1,1,91).gb.getField(9,9) shouldBe -1
      Controller(10,10,0,0,91).gb.getField(9,9) shouldBe -1
      Controller(10,10,0,4,91).gb.getField(9,9) shouldBe -1
    "have an output vector" in:
      ctrl.getBoard shouldBe ctrl.gb.getBoard

    "have the right size" in:
      ctrl.getSize shouldBe(10, 10)

    "should be ingame" in:
      ctrl.inGame shouldBe true

    "should have the right game-state" in:
      ctrl.gameState shouldBe "running"
      val w = Controller(10, 10, 5, 5, 91)
      w.gameState shouldBe "win"
      val l = Controller(10, 10, 5, 5, 90); l.turn("open", Try(1524), Try(1243)); l.turn("open", Try(1), Try(1))
      l.gameState shouldBe "lose"
      l.getBoard


    "should have right states" in:
      ctrl.changeState("running")
      an[IllegalArgumentException] should be thrownBy ctrl.changeState("fzjhtexhzt")
      
      