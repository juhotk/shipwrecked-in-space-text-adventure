package src

import scala.collection.mutable.Map

class Area(var name: String, var description: String, actionOnEnter: => Any = ()) {

  private val neighbors = Map[String, Area]()
  private val hiddenNeighbors = Map[String, Area]()
  private val items = Map[String, Item]()
  private val interactables = Map[String, Interactable]()
  private var copyOfInventory = Map[String, Item]()

  var useShortDescription = false
  var specialShortDescription = "N/A"

  var useHelpDescription = false

  var action: () => Any = () => actionOnEnter

  def startActionOnEnter() = action()

  def neighbor(direction: String) = this.neighbors.get(direction)

  def hiddenNeighbor(direction: String) = this.hiddenNeighbors.get(direction)

  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }

  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }

  def setHiddenNeighbor(direction: String, neighbor: Area) = {
    this.hiddenNeighbors += direction -> neighbor
  }

  def setHiddenNeighbors(exits: Vector[(String, Area)]) = {
    this.hiddenNeighbors ++= exits
  }

  def updateInventoryList(list: Map[String, Item]) = copyOfInventory = list
  def updateSpecialShortDescription(stringToUse: String) = specialShortDescription = stringToUse

  def inventoryList = this.copyOfInventory.keys.mkString(" / ")

  def fullDescription: String = {
    val itemList = if(items.nonEmpty) "\n\nItems here: " + this.items.keys.mkString(" / ") else "\n\nItems here: none"
    val interactableList = if(interactables.nonEmpty) "\nPossibly interesting: " + this.interactables.keys.mkString(" / ") else "\nPossibly interesting: nothing"
    val exitList = "\n\nLocations available: " + this.neighbors.keys.mkString(" / ")
    this.description + itemList + interactableList + exitList
  }

  def shortDescription = {
    var exitList = ""

    if(specialShortDescription == "N/A") {
      exitList = "\n\nCommands available: " + this.neighbors.keys.mkString(" / ")
    } else {
      exitList = "\n\n" + specialShortDescription + "\nLocations available: " + this.neighbors.keys.mkString(" / ")
    }

    this.description + exitList
  }

  def helpDescription: String = {
    val commandList = """List of available commands. Some alternative versions are also accepted (e.g. move instead of go).
                        |
                        |Go [location]      -     Move to specified location
                        |Get [item]         -     Picks up the specified item
                        |Drop [item]        -     Returns the item from your inventory to the location
                        |Use [item]         -     Physically use an item in your invetory
                        |Combine [item]     -     Combines the item with another one if there is an eligible pair in your inventory
                        |Examine [item]     -     Get information about an item in your inventory
                        |Interact [object]  -     Interacts with the specified object in the area
                        |Yes / No           -     Verifies your choice in certain situations
                        |Help               -     Shows this list of commands
                        |
                        |Type anything to return.""".stripMargin

    commandList
  }

  def setShortDescription() = {
    useShortDescription = true
  }

  def addInteractable(interactable: Interactable) = {
    interactables += interactable.name.toLowerCase() -> interactable
  }

  def useInteractable(name: String) = {
    val inter = interactables.get(name)

    inter match {
      case Some(x) => {
        val returnedValue = x.use()

        if(returnedValue.isInstanceOf[String]) {
          returnedValue.toString
        } else {
          "This case was not accounted for. Error 2"
        }
      }
      case None => "There is no " + name + " to interact with."
    }
  }

  def removeInteractable(name: String) = interactables -= name.toLowerCase

  def addItem(item: Item) = {
    items += item.name -> item
  }

  def listOfItems = items

  def removeItem(itemName: String): Option[Item] = {
    val itemFound = items.get(itemName.toLowerCase)
    items -= itemName.toLowerCase
    itemFound
  }

  def contains(itemName: String) = {
    items.contains(itemName)
  }

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)
}
