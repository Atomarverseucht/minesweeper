package de.htwg.winesmeeper.aView.GUI
import scalafx.Includes._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.geometry.Insets
import scalafx.application.Platform

// Data structure for the 5 input values
case class GeneratorData(val1: String, val2: String, val3: String, val4: String, val5: String)

class GeneratorGUI extends Dialog[GeneratorData] {
  title = "Board-Generator"
  headerText = "Please enter the required information to generate a board"

  // Define buttons
  val saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OKDone)
  dialogPane().buttonTypes = Seq(saveButtonType, ButtonType.Cancel)

  // Create input fields
  val field1 = new TextField() { promptText = "x-Size" }
  val field2 = new TextField() { promptText = "y-Size" }
  val field3 = new TextField() { promptText = "x-Start" }
  val field4 = new TextField() { promptText = "y-Start" }
  val field5 = new TextField() { promptText = "bombCount" }

  // Layout using a GridPane
  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 10)

    add(new Label("X-Size:"), 0, 0)
    add(field1, 1, 0)
    add(new Label("Y-Size:"), 0, 1)
    add(field2, 1, 1)
    add(new Label("X-Start:"), 0, 2)
    add(field3, 1, 2)
    add(new Label("Y-Start:"), 0, 3)
    add(field4, 1, 3)
    add(new Label("Bomb count:"), 0, 4)
    add(field5, 1, 4)
  }

  dialogPane().content = grid

  // Automatically focus the first field when opened
  Platform.runLater(field1.requestFocus())

  // Convert the text field contents into the InputData case class when "Save" is clicked
  resultConverter = {
    case `saveButtonType` =>
      GeneratorData(field1.text(), field2.text(), field3.text(), field4.text(), field5.text())
  }
}
