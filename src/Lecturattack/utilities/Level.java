/*
 * Copyright (c) 2015.
 */

package Lecturattack.utilities;

import Lecturattack.entities.Player;
import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class Level {
  private Player player;
  private ArrayList<Target> targets;

  public Level(Player player, ArrayList<Target> targets) {
    this.player = player;
    this.targets = targets;
  }

  public ArrayList<Target> getTargets() {
    return targets;
  }

  public Player getPlayer() {
    return player;
  }
}
