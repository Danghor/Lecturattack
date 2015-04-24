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

  private int score;

  public Level(ArrayList<Target> targets, float playerPositionX, float playerPositionY, int score) {
    this.targets = targets;
    this.playerPositionX = playerPositionX;
    this.playerPositionY = playerPositionY;
    this.score = score;

    float lowestPoint = 0;
    for (Target target : targets) {
      float biggestY = target.getBiggestY();
      if (biggestY > lowestPoint) {
        lowestPoint = biggestY;
      }
    }

    groundLevel = lowestPoint;
  }

  public void reduceScore(int scoreReduction) {
    score -= scoreReduction;
  }

  public void addScore(int scoreAddition) {
    score += scoreAddition;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
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
