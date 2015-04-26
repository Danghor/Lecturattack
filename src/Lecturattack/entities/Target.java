package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import Lecturattack.entities.types.TargetType;

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

    //TODO remove if not needed anymore
    // This shows the hitbox of the targets
    /*Polygon poly = new Polygon();
    for (EnhancedVector point : vertices) {
      poly.addPoint(point.getX(), point.getY());
    }
    graphics.draw(poly);

    graphics.drawRect(getCenter().getX(), getCenter().getY(), 5, 5);*/
  }

  public float hit(Projectile projectile) {
    float scoreReturned = 0;

    if (projectile.getDestroys().contains(getType()) && !isDestroyed()) {
      hitCounter++;
      scoreReturned = getHitScore();
      playSound();
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
