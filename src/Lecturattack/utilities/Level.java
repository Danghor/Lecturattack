package Lecturattack.utilities;

import Lecturattack.entities.Target;

import java.util.ArrayList;

/**
 * Contains all necessary information for the currently played level.
 * Used to collect the level information from the FileHandler and pass it to the GameState, so the level can
 * be loaded and made playable.
 *
 * @author Nick Steyer
 */
public class Level {
  private final float groundLevel; //the y-value indicating at which point the ground begins
  private final ArrayList<Target> targets;
  private final float playerPositionX;
  private final float playerPositionY;

  private int score;

  public Level(ArrayList<Target> targets, float playerPositionX, float playerPositionY, int score) {
    this.targets = targets;
    this.playerPositionX = playerPositionX;
    this.playerPositionY = playerPositionY;
    this.score = score;

    /**
     * The ground level is calculated as the highest [sic.] y-value a vertex of a targets has.
     * I.e., the lowest target is assumed to be standing on the ground.
     */
    float lowestPoint = 0;
    for (Target target : targets) {
      float biggestY = target.getBiggestY();
      if (biggestY > lowestPoint) {
        lowestPoint = biggestY;
      }
    }

    groundLevel = lowestPoint;
  }

  /**
   * Decrements the score by the given value.
   *
   * @param scoreReduction The value by which the score should be decremented.
   */
  public void reduceScore(int scoreReduction) {
    score -= scoreReduction;
  }

  /**
   * Increments the score by the given value.
   *
   * @param scoreAddition The value by which the score should be incremented.
   */
  public void addScore(int scoreAddition) {
    score += scoreAddition;
  }

  public int getScore() {
    return score;
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
