package Lecturattack.utilities;

import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class Level {
  private final float groundLevel;
  private final ArrayList<Target> targets;
  private final float playerPositionX;
  private final float playerPositionY;

  public Level(ArrayList<Target> targets, float playerPositionX, float playerPositionY) {
    this.targets = targets;
    this.playerPositionX = playerPositionX;
    this.playerPositionY = playerPositionY;

    float lowestPoint = 0;
    for (Target target : targets) {
      float biggestY = target.getBiggestY();
      if (biggestY > lowestPoint) {
        lowestPoint = biggestY;
      }
    }

    groundLevel = lowestPoint;
  }

  public float getGroundLevel() {
    return groundLevel;
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
