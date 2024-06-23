package src

/** This class was created with support for multiple combinations in mind hence the lists.
  * However, to keep the scope reasonable I decided to not go further with the idea and
  * finished the class with hardcoded combinations */
class Item(val name: String, val description: String, combinesWith: List[String] = List(), combinationOutcomes: List[Item] = List(), useFunction: => Any = ()) {

  var combinations = combinesWith
  var outcomes = combinationOutcomes
  var action: () => Any = () => useFunction

  def checkInteraction(itemName: String): Boolean = combinations.contains(itemName)

  def use() = action()

  override def toString = this.name

}