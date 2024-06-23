package src

class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(actor: Player) = this.verb match {
    case "go" | "move" | "goto" if actor.currentIntroStage == -1   => Some(actor.go(this.modifiers))
    case "next"                                                    => Some(actor.specialCases("next"))
    case "weld" | "pour" | "spray"                                 => Some(actor.specialCases("weldPourSpray"))
    case "quit"                                                    => Some(actor.quit())
    case "get" | "pickup" | "take" | "pick"                        => Some(actor.get(this.modifiers))
    case "use"                                                     => Some(actor.use(this.modifiers))
    case "combine" | "comb" | "craft"                              => Some(actor.combine(this.modifiers))
    case "examine" | "look"                                        => Some(actor.examine(this.modifiers))
    case "drop"                                                    => Some(actor.drop(this.modifiers))
    case "interact" | "int"                                        => Some(actor.interact(this.modifiers))
    case "help" | "sos"                                            => Some(actor.help)
    case "1637" if actor.location.name == "Escape Pod Hatch"       => Some(actor.specialCases("1637"))
    case "yes" if actor.location.name == "Escape Pod "             => Some(actor.specialCases("yes"))
    case "no" if actor.location.name == "Escape Pod "              => Some(actor.specialCases("no"))
    case other                                                     => None
  }

  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

