package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.types.TargetType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Nick Steyer
 * @author Andreas Geis
 */
public class Target extends RigidBody {
  private final TargetMeta metaObject;
  private int hitCounter;

  /**
   * Initializes this target. Saves the given metaObject, initializes the hitCounter with 0 and places the target at the
   * given position.
   *
   * @param targetMeta The meta object containing necessary information about this target.
   * @param x          The x-value of the position this target is to be placed at.
   * @param y          The y-value of the position this target is to be placed at.
   */
  public Target(TargetMeta targetMeta, float x, float y) {
    super(targetMeta, x, y);
    metaObject = targetMeta;
    hitCounter = 0;
  }

  /**
   * Uses the type of this target as its String representation.
   *
   * @return The type of this target retrieved from the meta object.
   */
  @Override
  public String toString() {
    return metaObject.getType().toString();
  }

  /**
   * Displays this target on the screen.
   *
   * @param gameContainer  Not used.
   * @param stateBasedGame Not used.
   * @param graphics       Not used.
   */
  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    /**
     * The image is being rendered if:
     *  1. The target is not destroyed, OR
     *  2. The target is an ENEMY that has not been fallen out of the game frame yet.
     */

    if (!isDestroyed() || (getType() == TargetType.ENEMY && !isUnreachable())) {
      metaObject.getImage(hitCounter).draw(getSmallestX(), getSmallestY());
    }
  }

  /**
   * Hits this target with the given projectile. A sound will always be played. Depending on the type of projectile,
   * this target will also be damaged and the achieved score for the hit will be returned. Otherwise, the returned score
   * is 0.
   *
   * @param projectile The projectile hitting this target.
   *
   * @return The score achieved by this hit.
   */
  public float hit(Projectile projectile) {
    float scoreReturned = 0;
    float velocity;
    int timesHit;

    if (projectile.getDestroys().contains(getType())) {
      velocity = projectile.getLinearVelocity().length();

      if (velocity >= 80) {
        timesHit = 3;
      } else if (velocity >= 60) {
        timesHit = 2;
      } else {
        timesHit = 1;
      }

      for (int i = 0; i < timesHit; i++) {
        if (!isDestroyed()) {
          hitCounter++;
          scoreReturned += getHitScore();
        }
      }

      playSound(hitCounter - 1, 1f);
    } else {
      if (!(getType() == TargetType.ENEMY)) {
        playSound(hitCounter, 0.5f);
      }
    }

    return scoreReturned;
  }

  /**
   * Determined whether or not this target has been completely destroyed.
   *
   * @return True, if the target is destroyed or false otherwise.
   */
  public boolean isDestroyed() {
    return hitCounter >= metaObject.getMaxHits();
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  private float getHitScore() {
    return metaObject.getHitScore();
  }

  public TargetType getType() {
    return metaObject.getType();
  }

  /**
   * Plays one of the sounds that belong to this target with the given pitch. The given index determines, which
   * sound is played.
   *
   * @param index The index of the sound to be played.
   * @param pitch The pitch with which the sound is to be played.
   */
  private void playSound(int index, float pitch) {
    try {
      metaObject.playSound(index, pitch, 1f);
    } catch (NullPointerException | IndexOutOfBoundsException ex) {
      System.out.println("Could not play sound with index " + index + " for " + this.toString() + ".");
    }
  }

  /**
   * Stops the sound currently playing by this target.
   */
  public void stopSound() {
    metaObject.stopSound();
  }

}
