package Lecturattack.utilities;

import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class Level {
  private ArrayList<Target> targets;
  private float playerPositionX;
  private float playerPositionY;

  public Level(ArrayList<Target> targets, float playerPositionX, float playerPositionY) {
    this.targets = targets;
    this.playerPositionX = playerPositionX;
    this.playerPositionY = playerPositionY;
  }

  public ArrayList<Target> getTargets() {
    return targets;
  }

  public float getPlayerPositionY() {
    return playerPositionY;
  }

  public float getPlayerPositionX() {
    return playerPositionX;
  }
}
