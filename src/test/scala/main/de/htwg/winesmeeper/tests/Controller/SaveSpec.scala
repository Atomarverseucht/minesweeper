package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.Save.ImplXMLSave.XmlSave
import de.htwg.winesmeeper.Controller.Save.ImplJSONSave.JSONSave
import play.api.libs.json.Json
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.ControllerTrait
import de.htwg.winesmeeper.Model.BoardTrait
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try

class SaveSpec extends AnyWordSpec with Matchers:
  var testBoard: BoardTrait = Config.mkBoard(Vector.fill(10,10)(Config.mkField(true, false, false)))
  testBoard = testBoard.updateField(1,1,Config.mkField(false, false, false))
  testBoard = testBoard.updateField(5,5,Config.mkField(false, false, false))
  testBoard = testBoard.updateField(6,6,Config.mkField(false, false, false))
  val testCtrl: ControllerTrait = Config.mkController(1, 1, testBoard)
  testCtrl.turn(-1,"flag", Try(9), Try(9))
  testCtrl.turn(-1,"flag", Try(9), Try(8))
  testCtrl.turn(-1,"flag", Try(8), Try(8))
  testCtrl.turn(-1,"open", Try(6), Try(6))
  testCtrl.turn(-1,"flag", Try(8), Try(8))
  testCtrl.turn(-1,"open", Try(5), Try(5))
  testCtrl.doSysCmd(-1,"undo",Vector("undo","2"))

  "XML" should:
    "be save-able" in:
      XmlSave.save(testCtrl, "test")
      XmlSave.save(testCtrl)
    "be load-able" in:
      XmlSave.load(testCtrl, "test")
      XmlSave.load(testCtrl)
    "Know if something is no cmd" in:
      XmlSave.loadCommand(testCtrl, <cmd>müll</cmd>)

  "JSON" should:
    "be save-able" in:
      JSONSave.save(testCtrl, "test")
      JSONSave.save(testCtrl)
    "be load-able" in:
      JSONSave.load(testCtrl, "test")
      JSONSave.load(testCtrl)
    "Know if something is no cmd" in:
      JSONSave.loadCommand(testCtrl, Json.obj("cmd" -> "müll"))