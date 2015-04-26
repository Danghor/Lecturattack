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

  public Target(TargetMeta targetMeta, float x, float y) {
    super(targetMeta, x, y);
    metaObject = targetMeta;
    hitCounter = 0;
  }

  @Override
  public String toString() {
    return metaObject.getType().toString();
  }

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

  public float hit(Projectile projectile) {
    float scoreReturned = 0;
    float velocity = projectile.getLinearVelocity().length();
    int timesHit;

    if (velocity >= 80) {
      timesHit = 3;
    } else if (velocity >= 60) {
      timesHit = 2;
    } else {
      timesHit = 1;
    }

    for (int i = 0; i < timesHit; i++) {
      if (projectile.getDestroys().contains(getType()) && !isDestroyed()) {
        hitCounter++;
        scoreReturned += getHitScore();
        playSound();
      }
    }

    return scoreReturned;
  }

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

  public void playSound() {
    metaObject.getSound().play();
  }

}
