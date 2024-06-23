package src

import src.ui.AdventureGUI

import scala.collection.mutable.Map

class Player(startingArea: Area = null) {

  private var currentLocation = startingArea
  private var quitCommandGiven = false
  private var inv = Map[String, Item]()

  private var introStage = 0

  def hasQuit = this.quitCommandGiven

  def currentIntroStage: Int = introStage

  def location = this.currentLocation

  def examine(itemName: String) = {
    if(inv.contains(itemName)) {
      "You look closely at the " + itemName + ".\n" + inv(itemName).description
    } else {
      "If you want to examine something, you need to pick it up first."
    }
  }

  def inventoryAsAMap = inv

  def drop(itemName: String) = {
    if(inv.contains(itemName.toLowerCase)) {
      currentLocation.addItem(inv(itemName.toLowerCase))
      inv -= itemName
      "You drop the " + itemName + "."
    } else {
      "You don't have that!"
    }
  }

  def get(itemName: String) = {
    var output = "Either there is no " + itemName + " here to pick up or it cannot be picked up."

    currentLocation.removeItem(itemName).foreach(x => {
      inv += itemName -> x
      output = "You pick up the " + itemName + "."
    })

    output
  }

  def help = {
    if (!currentLocation.useShortDescription) {
      currentLocation.useHelpDescription = true
      "Showing a list of commands."
    } else {  //Prevent access during the Outer Space puzzle
      "You're too busy to check the commands right now. Return to a calmer area to do so."
    }
  }

  def interact(objectToInteractWith: String) = currentLocation.useInteractable(objectToInteractWith)

  def has(itemName: String): Boolean = inv.contains(itemName)

  def go(direction: String, bringItems: Boolean = false) = {
    var destination = this.location.neighbor(direction)

    if(destination.isEmpty) {
      destination = this.location.hiddenNeighbor(direction)
    }

    if(bringItems) {
      currentLocation.listOfItems.foreach(println)
      currentLocation.listOfItems.foreach(x => destination.get.addItem(x._2))
    }

    this.currentLocation = destination.getOrElse(this.currentLocation)

    if(!bringItems) {
      if (destination.isDefined) {
        this.currentLocation.startActionOnEnter()
        if(this.currentLocation.name != "Rescue Ship") {
          "You go " + direction + "."
        } else {
          "You opt to call for rescue."
        }
      } else {
        "You can't go that way right now."
      }
    } else {  //Bring items to the next screen. This is only used for scenarios where codes are needed.
      if (destination.isDefined) "The code was correct!" else "The code was correct but there was an issue loading the next area. Error 4."
    }
  }

  //Used for special cases where Adventure.scala redirects the player directly to somewhere
  def go(destination: Area, printOutcomeCase: Int) = {
    this.currentLocation = destination
    this.currentLocation.startActionOnEnter()

    AdventureGUI.overrideEventText = true
    printOutcomeCase match {
      case 0 => "You safely make it to the Engineering Bay."
      case 1 => ""
      case 2 => "Type START to begin the game."
      case 3 => "You fix the radio succesfully."
      case default => "Event outcome could not be loaded. Error 1."
    }
  }

  def use(itemName: String) = {
    val item = inv.get(itemName)

    item match {
      case Some(x) => {
        val returnedValue = x.use()

        if(returnedValue.isInstanceOf[String]) {
          returnedValue.toString
        } else {
          "This item cannot be used in any interesting way.\nPerhaps you could EXAMINE it for more information?"
        }
      }
      case None => "There is no " + itemName + " in your inventory."
    }
  }

  //This was supposed to be more elaborate originally, short explanation in Item.scala.
  //The result is somewhat messy for the actual use case but it functions.
  def combine(itemName: String) = {
    val item = inventoryAsAMap.get(itemName)

    item match {
      case Some(itemFound) => {
        var found = false
        var itemMatch: Item = null

        for(item <- inventoryAsAMap) {
          if(!found) {
            found = item._2.checkInteraction(itemName)

            if(found) {
              itemMatch = item._2
            }
          }
        }

        if(found) {
          val resultingItem: Item = itemMatch.outcomes.head
          inv -= itemName
          inv -= itemMatch.name
          inv += resultingItem.name -> resultingItem
          "Combined " + itemName + " with " + itemMatch.name + " resulting in " + resultingItem.name + "."
        } else {
          "No combinations could be made with " + itemName + " considering the current inventory."
        }
      }

      case None => "Cannot combine " + itemName + " with anything because it is not in your inventory."
    }
  }

  def specialCases(caseName: String) = {
    caseName match {
      case "next" => advanceIntroStage(this.location.neighbor("next"))
      case "weldPourSpray" => advanceIntroStage(this.location.neighbor("weld"))
      case "yes" => go("win")
      case "no" => "You opt to not call for help yet."
      case "1637" => {
        inv -= "memo"
        go("hatch2", true)
      }

      case default => "Error fetching event outcome. Error 6."
    }
  }

  private def advanceIntroStage(destination: Option[Area]) = {
    this.currentLocation = destination.getOrElse(this.currentLocation)

    if (destination.isDefined) {
      introStage += 1
      "" + "You go " + destination.get.name + "."
    } else {
      ui.AdventureGUI.overrideEventText = true
      "You can't use that command right now."
    }
  }

  def stopIntroSequence() = {
    introStage = -1
  }

  def startIntroSequence() = {  //Used for debugging reasons
    introStage = 0
  }

  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  override def toString = "Now at: " + this.location.name

}


