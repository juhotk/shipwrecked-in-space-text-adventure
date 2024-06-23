package src

class Interactable (val name: String, useFunction: => Any) {

  var action: () => Any = () => useFunction

  def use() = action()
}
