package src

import src.ui.AdventureGUI

import scala.util.Random

class Adventure {

  val title = "Shipwrecked in Outer Space"
  val player = new Player()

  private var oxygenRemaining = 5
  private var spacePosition = Random.between(-3, 3)

  //Into sequence and the first part of the game
  private val intro0 = new Area("Intro0", """You're a noname maintenance employee for Northernmost Lane Co., a well-known space transportation company.
                                            |You were sent to operate on a model S490 medium sized ship that had broken down a few days into its route.
                                            |As you approached the ship you noticed that it was visually in good condition, no obvious faults to be seen.
                                            |While boarding the crew mentioned that the power had simply gone out for no apparent reason.
                                            |You felt like something was off. Usually the pilot had some sort of an idea of what had happened.""".stripMargin, this.player.startIntroSequence())

  private val intro1 = new Area("Intro1", """A day had passed without any clues as to what had happened.
                                            |As you walked down an aisle, you noticed the captain running towards you.
                                            |"Hey! There's a leak near the engine!" he yelled.
                                            |That was odd, why didn't the computer mention anything earlier?""".stripMargin)

  private val intro2 = new Area("Intro2", """We rushed into the engine bay. Now there was a considerable crack in the eastern corner.
                                            |It was leaking warm air, almost something gas-like. "This seems disastrous",
                                            |you silently thought to yourself as to not worry others. You knew that
                                            |there wasn't a lot of time and the whole situation felt bizarre because
                                            |a mess this big had not caused any alarms as one would have anticipated.
                                            |
                                            |You decided to trust your intuition here.""".stripMargin)

  private val intro3 = new Area("Intro3", """You got questionable looks from the crew members but they trusted your knowledge.
                                            |As you began to act, the crack spread wider.
                                            |Unbearable heat was coming out and you struggled to breathe.
                                            |"IT'S GOING TO EXPLODE!" someone yelled out in panic.""".stripMargin)

  private val intro4 = new Area("Intro4", """We all ran out to the escape pods.
                                            |You managed to launch out just in time to watch the ship blow up into flames.
                                            |You felt the heat from the burning debris passing by as you floated around.""".stripMargin)

  private val escapePod = new Area("Escape Pod", """The pod is surprisingly large for its purpose. You spot some equipment, like a radio.
                                                   |There's a hatch that leads to outside. You heard its mechanism lock itself when you boarded the pod.
                                                   |Right now you should find a way to get home safely.""".stripMargin)
  private val escapeHatch = new Area("Escape Pod Hatch", """A display on the hatch reads: 'This hatch is locked for security reasons.'
                                                       |There is a keypad to enter an override code.""".stripMargin)
  private val escapeHatch2 = new Area("Escape Pod Hatch", """There's an unlocked hatch to outer space. I can see something floating nearby. Is it debris?
                                                         |No, it looks like the small ship that engineers use to operate outside of the mothership.
                                                         |It must've become loose in the explosion and escaped from the dock. Could there be something useful inside?
                                                         |It is risky but I could probably make my way over there.""".stripMargin)
  private val escapePod2 = new Area("Escape Pod", """The pod is surprisingly large for its purpose. You spot some equipment, like a radio.
                                                    |There's a hatch that leads to outside. You heard its mechanism lock itself when you boarded the pod
                                                    |but it's unlocked now. Right now you should find a way to get home safely.""".stripMargin)

  //Outer space sequence
  private val outerSpace = new Area("Outer Space", """It's unimaginably vast. According to your quick estimations you have enough oxygen to
                                                     |make five adjustments as you float towards the engineering bay. You should run out of
                                                     |oxygen exactly as your land inside. While spacewalking you can only change your direction
                                                     |and the force of your movement but you will always move forwards.
                                                     |Decide which direction you want to jump off to or return to the hatch.""".stripMargin, { this.oxygenRemaining = 5
                                                                                                                                               this.spacePosition = Random.between(-3, 3) } )
  private val outerSpace2 = new Area("Outer Space", "[SMALL LEFT] This will be replaced by the current situation", { this.oxygenRemaining -= 1
                                                                                                                     this.spacePosition -= 1 })
  private val outerSpace3 = new Area("Outer Space", "[BIG LEFT] This will be replaced by the current situation", { this.oxygenRemaining -= 1
                                                                                                                   this.spacePosition -= 2 })
  private val outerSpace4 = new Area("Outer Space", "[SMALL RIGHT] This will be replaced by the current situation", { this.oxygenRemaining -= 1
                                                                                                                      this.spacePosition += 1 })
  private val outerSpace5 = new Area("Outer Space", "[BIG RIGHT] This will be replaced by the current situation", { this.oxygenRemaining -= 1
                                                                                                                    this.spacePosition += 2 })
  private val spaceGrave = new Area("Space Grave", """          __,---'     `--.__
                                                     |       ,-'                ; `.
                                                     |      ,'                  `--.`--.
                                                     |     ,'                       `._ `-.
                                                     |     ;                     ;     `-- ;
                                                     |   ,-'-_       _,-~~-.      ,--      `.
                                                     |   ;;   `-,;    ,'~`.__    ,;;;    ;  ;  You ran out of oxygen and died. Your body is left afloat in outer space.
                                                     |   ;;    ;,'  ,;;      `,  ;;;     `. ;  You may GO to 'hatch' to try again.
                                                     |   `:   ,'    `:;     __/  `.;      ; ;  Alternatively you may type QUIT to embrace your outcome.
                                                     |    ;~~^.   `.   `---'~~    ;;      ; ;
                                                     |    `,' `.   `.            .;;;     ;'
                                                     |    ,',^. `.  `._    __    `:;     ,'
                                                     |    `-' `--'    ~`--'~~`--.  ~    ,'
                                                     |   /;`-;_ ; ;. /. /   ; ~~`-.     ;
                                                     |  ; ;  ; `,;`-;__;---;      `----'
                                                     |  ``-`-;__;:  ;  ;__;
                                                     |          `-- `-'""".stripMargin, this.oxygenRemaining = 5)

  //Engineering ship
  private val engineeringShip = new Area("Engineering Ship Lobby", """Inside the ship you see a terminal, some desks and a cabinet what you assume is for tools.
                                                             |It's way roomier than the pod you escaped on. Unfortunately it's running on battery power
                                                             |so only the lights and basic equipment have electricity. Ships like these have their engines
                                                             |started by the mothership due to the amount of energy required.""".stripMargin, { this.oxygenRemaining = 5 })
  private val helm = new Area("Engineering Ship Helm", "The main controls for the ship are located here. None of the panels are lit.\nThe view from the window is magnificent, though.")

  //Endgame
  private val outerSpace6 = new Area("Outer Space", """Standing right at the exit, you see your target. Having gained some experience in spacewalking,
                                                      |you know exactly how to maneuver through the gap. Will you stay here or jump towards the hatch?""".stripMargin)
  private val escapeHatch3 = new Area("Escape Pod Hatch", """There's an unlocked hatch to outer space. You can reach the engineering ship from there.
                                                        |Otherwise, there really isn't anything interesting near the hatch.""".stripMargin)
  private val escapePod3 = new Area("Escape Pod", """The pod is surprisingly large for its purpose. You spot some equipment, like a radio.
                                                    |There's a hatch that leads to outside. You heard its mechanism lock itself when you boarded the pod
                                                    |but it's unlocked now. Right now you should find a way to get home safely.""".stripMargin)
  private val escapePod4 = new Area("Escape Pod ", """The pod now has a functioning radio that you can use to call for rescue. Just in time too, the heat is
                                                     |starting the get unbearable and you are lucky that the pod hasn't been hit my debris yet.""".stripMargin)
  private val escapeHatch4 = new Area("Escape Pod Hatch", """There's an unlocked hatch to outer space. You have no reason to head outside now.
                                                        |There isn't anything interesting near the hatch.""".stripMargin)
  private val win = new Area("Rescue Ship","""The rescue team arrives and safely takes you home.
                                              |
                                              |Congratulations! You have won the game!
                                              |
                                              |  *    .  *       .             *    *    .  *       .             *          *    .  *       .             *
                                              |                         *                                *                             *
                                              | *   .        *       .       .       *  *   .        *       .       .       *        *   .        *
                                              |   .     *                             .    __                    .     *   .     *                   .     *
                                              |           .     .  *        *              \ \______ .     .  *        *             .     .  *        *
                                              |       .                .        .       ###[==______>      .        .              .                .
                                              |.  *           *                     *      /_/           *                     *
                                              |                             .                                           .                                .
                                              |         *          .   *             *          .   *                 *          .   *              *
                                              |
                                              |
                                              |
                                              |
                                              |""".stripMargin)


  //Neighbors
  intro0.setNeighbors(Vector("next" -> intro1))
  //intro0.setHiddenNeighbors(Vector("skip" -> escapePod4))  //Used for debugging
  intro1.setNeighbors(Vector("next" -> intro2))
  intro2.setNeighbors(Vector("weld" -> intro3, "pour" -> intro3, "spray" -> intro3))
  intro3.setNeighbors(Vector("next" -> intro4))
  intro4.setNeighbors(Vector("next" -> escapePod))

  escapePod.setNeighbors(Vector("hatch" -> escapeHatch))
  escapeHatch.setNeighbors(Vector("escape pod" -> escapePod))
  escapeHatch.setHiddenNeighbors(Vector("hatch2" -> escapeHatch2))
  escapeHatch2.setNeighbors(Vector("outer space" -> outerSpace, "escape pod" -> escapePod2))
  escapePod2.setNeighbors(Vector("hatch" -> escapeHatch2))

  outerSpace.setNeighbors(Vector("left" -> outerSpace2, "right" -> outerSpace4, "hatch" -> escapeHatch2))
  outerSpace2.setNeighbors(Vector("left" -> outerSpace2, "big left" -> outerSpace3, "right" -> outerSpace4, "big right" -> outerSpace5))
  outerSpace3.setNeighbors(Vector("left" -> outerSpace2, "big left" -> outerSpace3, "right" -> outerSpace4, "big right" -> outerSpace5))
  outerSpace4.setNeighbors(Vector("left" -> outerSpace2, "big left" -> outerSpace3, "right" -> outerSpace4, "big right" -> outerSpace5))
  outerSpace5.setNeighbors(Vector("left" -> outerSpace2, "big left" -> outerSpace3, "right" -> outerSpace4, "big right" -> outerSpace5))
  spaceGrave.setNeighbors(Vector("hatch" -> escapeHatch2))

  engineeringShip.setNeighbors(Vector("helm" -> helm, "outer space" -> outerSpace6))
  helm.setNeighbors(Vector("lobby" -> engineeringShip))

  outerSpace6.setNeighbors(Vector("lobby" -> engineeringShip, "hatch" -> escapeHatch3))
  escapeHatch3.setNeighbors(Vector("escape pod" -> escapePod3, "outer space" -> outerSpace6))
  escapePod3.setNeighbors(Vector("hatch" -> escapeHatch3))
  escapePod4.setNeighbors(Vector("hatch" -> escapeHatch4))
  escapePod4.setHiddenNeighbors(Vector("win" -> win))
  escapeHatch4.setNeighbors(Vector("escape pod" -> escapePod4))

  //Items
  val toolkit = new Item("toolkit", "A powerful set of tools that can fix almost anything.", List(), List(), { if(player.location.name == "Escape Pod") {
                                                                                                                 player.drop("toolkit")
                                                                                                                 player.go(escapePod4, 3)
                                                                                                                 "You successfully fix the radio."
                                                                                                               } else {
                                                                                                                 "There is nothing to use the toolkit on."
                                                                                                               }})
  helm.addItem(new Item("duct tape", "Sticky tape that will hold anything together.", List[String]("hammer"), List[Item](toolkit)))

  //Interactables
  escapePod.addInteractable(new Interactable("Window", "You can see the remains of the ship afire in the distance. The heat can still be felt inside the pod."))
  escapePod.addInteractable(new Interactable("Radio", "It does not turn on but if I could fix it I could call for help."))
  escapePod.addInteractable(new Interactable("Box", { escapePod.addItem(new Item("memo", "Emergency override code for the escape pod hatch is 1637."))
                                                      escapePod.removeInteractable("Box")
                                                      "You find a box underneath your seat. A note falls out as you open it!" }))
  escapeHatch.addInteractable(new Interactable("Keypad", "Please enter a code"))
  escapePod2.addInteractable(new Interactable("Window", "You can see the remains of the ship afire in the distance. The heat can still be felt inside the pod."))
  escapePod2.addInteractable(new Interactable("Radio", "It does not turn on. If I could fix it I might be able to call for rescue."))
  engineeringShip.addInteractable(new Interactable("Terminal", { engineeringShip.addItem(new Item("repair guide", "Items required: Hammer, duct tape. Combine and use to fix a radio. Add percussive force where necessary."))
                                                                engineeringShip.removeInteractable("Terminal")
                                                                "The text flickers but it is usable. You print out a set of instructions on how to fix the radio." }))
  engineeringShip.addInteractable(new Interactable("Desk", "It's nice and tidy. Nothing interesting can be found."))
  engineeringShip.addInteractable(new Interactable("Cabinet", { engineeringShip.addItem(new Item("hammer", "Feels sturdy. This might prove itself useful when fixing the radio.", List[String]("duct tape"), List[Item](toolkit)))
                                                                engineeringShip.removeInteractable("Cabinet")
                                                                "There's only a hammer left. They must have taken the tools to fix the leak." }))
  escapePod3.addInteractable(new Interactable("Window", "You can see the remains of the ship afire in the distance. The heat can still be felt inside the pod."))
  escapePod3.addInteractable(new Interactable("Radio", "It does not turn on but if I could fix it I could call for help."))

  escapePod4.addInteractable(new Interactable("Window", "One final look at the ship. Its bright light and extreme heat is something that you wont forget."))
  escapePod4.addInteractable(new Interactable("Radio", "A functional radio. Call for rescue? YES / NO"))


  //Short descriptions
  intro0.setShortDescription()
  intro1.setShortDescription()
  intro2.setShortDescription()
  intro3.setShortDescription()
  intro4.setShortDescription()
  outerSpace.setShortDescription()
  outerSpace.specialShortDescription = "Oxygen remaining: 5"
  outerSpace2.setShortDescription()
  outerSpace2.specialShortDescription = "To be set."
  outerSpace3.setShortDescription()
  outerSpace3.specialShortDescription = "To be set."
  outerSpace4.setShortDescription()
  outerSpace5.specialShortDescription = "To be set."
  outerSpace5.setShortDescription()
  outerSpace5.specialShortDescription = "To be set."
  spaceGrave.setShortDescription()
  win.setShortDescription()

  this.player.go(intro0, 2)

  def isOver = this.player.location == win || this.player.hasQuit

  def welcomeMessage = "Type NEXT to continue."

  def playTurn(command: String) = {

    //Prepare to setup the turn
    this.player.location.useHelpDescription = false
    if(player.location == escapePod) {
      player.stopIntroSequence()
    }

    //Start setting up the turn
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)

    //Update location related things
    if(oxygenRemaining == 0) {
      if(spacePosition == 0) {
        player.go(engineeringShip, 0)
      } else {
        player.go(spaceGrave, 1)
      }
    }

    if (List[Area](outerSpace2, outerSpace3, outerSpace4, outerSpace5).contains(player.location)) {
      val text: String = spacePosition match {
        case 0 => "You're right on target but there's still distance to go."
        case -1 => "You are a bit too far to the left of your target."
        case -2 => "You are a too far to the left of your target."
        case x if x < -2 => "You are a way too far to the left of your target."
        case 1 => "You are a bit too far to the right of your target."
        case 2 => "You are a too far to the right of your target."
        case x if x > 2 => "You are a way too far to the right of your target."
      }

      player.location.description = text
      player.location.specialShortDescription = "Oxygen remaining: " + oxygenRemaining
    }

    this.player.location.updateInventoryList(this.player.inventoryAsAMap)

    //Finish turn setup
    outcomeReport.getOrElse({
      AdventureGUI.overrideEventText = true
      "Unknown command: \"" + command + "\"."
    })
  }
}

