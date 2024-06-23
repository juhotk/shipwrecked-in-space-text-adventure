package src.ui

import src.Adventure
import java.io.IOException
import java.awt.{Font, FontFormatException, GraphicsEnvironment}

import scala.swing._
import scala.swing.event._
import javax.swing.text.{MutableAttributeSet, SimpleAttributeSet, StyleConstants}

object AdventureGUI extends SimpleSwingApplication {

  import javax.swing.UIManager

  //Colors
  val cBlack = new Color(0,0,0)
  val cWhite = new Color(255, 255, 255)
  var cText = cWhite

  try for (info <- UIManager.getInstalledLookAndFeels) {
    if ("Nimbus" == info.getName) {
      UIManager.setLookAndFeel(info.getClassName)
    }
  }
  catch {
    case e: Exception => {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName)  //If Nimbus cannot be found we'll use Metal
      cText = cBlack
    }
  }

  val ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
  var defaultFont: swing.Font = new Font("Monospaced", Font.PLAIN, 12)  //Set a default font incase PressStart2P wont load

  try {
    defaultFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass.getResourceAsStream("/PressStart2P.ttf"))
    defaultFont = defaultFont.deriveFont(10f)
    ge.registerFont(defaultFont)
  } catch {
    case e: FontFormatException => println("Error reading font format.")
    case e: IOException => println("IOException occurred while reading font. Verify that the font exists in the correct location.")
  }

  UIManager.put("nimbusBase", cBlack)
  UIManager.put("nimbusBase", cBlack)
  UIManager.put("nimbusFocus", cBlack)
  UIManager.put("nimbusInfo", cBlack)
  UIManager.put("control", cBlack)
  UIManager.put("nimbusLightBackground", cBlack)
  UIManager.put("text", cText)
  UIManager.put("TextArea.font", defaultFont)
  UIManager.put("TextPane.font", defaultFont)
  UIManager.put("Label.font", defaultFont)

  var overrideEventText = false

  def top = new MainFrame {

    // Access to the application’s internal logic
    val game = new Adventure
    val player = game.player

    // Components
    val locationTitle = new TextPane() {
      editable = false
      focusable = false
    }

    val inventoryList = new TextPane() {
      editable = false
      focusable = false
    }

    val locationSize = new Dimension(1500, 375)
    val locationInfo = new TextPane() {
      editable = true
      focusable = false
      minimumSize = locationSize
      maximumSize = locationSize
      preferredSize = locationSize
    }

    val outputSize = new Dimension(1500, 40)
    val turnOutput = new TextPane() {
      editable = false
      focusable = false
      minimumSize = outputSize
      maximumSize = outputSize
      preferredSize = outputSize
    }

    val set: MutableAttributeSet = new SimpleAttributeSet()
    StyleConstants.setForeground(set, cText)
    StyleConstants.setLineSpacing(set, 1f)
    StyleConstants.setFontFamily(set, "Press Start 2P")

    locationInfo.styledDocument.setParagraphAttributes(0, locationInfo.styledDocument.getLength, set, true)
    locationTitle.styledDocument.setParagraphAttributes(0, locationTitle.styledDocument.getLength, set, true)
    inventoryList.styledDocument.setParagraphAttributes(0, locationTitle.styledDocument.getLength, set, true)
    turnOutput.styledDocument.setParagraphAttributes(0, turnOutput.styledDocument.getLength, set, true)

    def newLabel(text: String): Label = {
      val label = new Label(text) {
        font = defaultFont.deriveFont(12f)
      }

      label
    }

    val input = new TextField(40) {
      minimumSize = preferredSize
    }

    this.listenTo(input.keys)

    // Events
    this.reactions += {
      case keyEvent: KeyPressed =>
        if (keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver) {
          val command = this.input.text.trim
          if (command.nonEmpty) {
            this.input.text = ""
            this.playTurn(command)
          }
        }
    }

    // Layout
    this.contents = new GridBagPanel {
      import scala.swing.GridBagPanel.Anchor._
      import scala.swing.GridBagPanel.Fill
      layout += newLabel("Location:")     -> new Constraints(0, 0, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += newLabel("Information:")  -> new Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += newLabel("Inventory:")    -> new Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += newLabel("Command:")      -> new Constraints(0, 3, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += newLabel("Events:")       -> new Constraints(0, 4, 1, 1, 0, 0, NorthWest.id, Fill.None.id, new Insets(8, 5, 5, 5), 0, 0)
      layout += locationTitle             -> new Constraints(1, 0, 1, 1, 1, 0, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += locationInfo              -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.Both.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += inventoryList             -> new Constraints(1, 2, 1, 1, 1, 0, NorthWest.id, Fill.Both.id, new Insets(5, 5, 3, 5), 0, 0)
      layout += input                     -> new Constraints(1, 3, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput                -> new Constraints(1, 4, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, new Insets(5, 5, 25, 5), 0, 0)
    }

    // Menu
    this.menuBar = new MenuBar {
      contents += new Menu("Program") {
        val quitAction = Action("Quit") { dispose() }
        contents += new MenuItem(quitAction)
      }
    }

    // Set up the GUI’s initial state
    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = new Point(50, 50)
    this.minimumSize = new Dimension(200, 200)

    this.pack()
    this.input.requestFocusInWindow()

    def playTurn(command: String) = {
      val turnReport = this.game.playTurn(command)
      if (this.player.hasQuit) {
        this.dispose()
      } else {
        this.updateInfo(turnReport)
        this.input.enabled = !this.game.isOver
      }
    }

    def updateInfo(info: String) = {
      if(!overrideEventText) {  //Special turnOutput cases for intro and winning sequences
        if(player.currentIntroStage == 1 || player.currentIntroStage == 3) {
          this.turnOutput.text = "Type NEXT to continue."
        } else if(player.currentIntroStage == 2) {
          this.turnOutput.text = "Did you decide to WELD, POUR filling material or SPRAY water?"
        } else if(player.currentIntroStage == 4) {
          this.turnOutput.text = "Type NEXT to start your adventure."
        } else if(player.currentIntroStage == 5) {
          this.turnOutput.text = "You start your adventure inside the escape pod. Type HELP for command list."
          player.stopIntroSequence()
        } else if (!this.game.isOver) {
          this.turnOutput.text = info
        } else {
          this.turnOutput.text = "You opt to call for rescue."
        }
      } else {
        this.turnOutput.text = info
        overrideEventText = false
      }

      if(player.location.useShortDescription) {
        this.locationInfo.text = this.player.location.shortDescription
      } else if(player.location.useHelpDescription) {
        this.locationInfo.text = this.player.location.helpDescription
      } else {
        this.locationInfo.text = this.player.location.fullDescription
      }

      this.inventoryList.text = this.game.player.location.inventoryList

      if(player.currentIntroStage == -1) {
        this.locationTitle.text = this.game.player.location.name
      } else {
        this.locationTitle.text = "Intro Sequence"
      }
    }
  }
}