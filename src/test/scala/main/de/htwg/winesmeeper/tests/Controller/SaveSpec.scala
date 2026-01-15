package main.de.htwg.winesmeeper.tests.Controller

import de.htwg.winesmeeper.Controller.Save.ImplXMLSave.XmlSave
import de.htwg.winesmeeper.Controller.Save.ImplJSONSave.JSONSave
import play.api.libs.json.Json
import de.htwg.winesmeeper.Config
import de.htwg.winesmeeper.Controller.ControllerTrait
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Try

class SaveSpec extends AnyWordSpec with Matchers:
  val testCtrl: ControllerTrait = Config.mkController(1,1,Config.generateBoard(10,10,1,1,10))
  testCtrl.turn(-1,"flag",Try(9), Try(9))
  testCtrl.turn(-1,"flag",Try(9), Try(8))
  testCtrl.turn(-1,"flag",Try(8), Try(8))
  testCtrl.turn(-1,"flag",Try(8), Try(7))
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