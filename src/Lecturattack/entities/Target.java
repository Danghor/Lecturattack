package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.EnhancedVector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Nick Steyer
 */
public class Target extends RigidBody {
  private TargetMeta metaObject;
  private int hitCounter;

  public Target(TargetMeta targetMeta, float x, float y) {
    super(targetMeta, x, y);
    metaObject = targetMeta;
    hitCounter = 0;
  }

  public TargetType getType() {
    return metaObject.getType();
  }

  public int hit(Projectile projectile) {
    int scoreReturned = 0;

    if (projectile.getDestroys().contains(getType()) && hitCounter < metaObject.getMaxHits()) {
      hitCounter++;
      scoreReturned = getHitScore();
    }

    return scoreReturned;
  }

  public boolean isDestroyed() {
    return hitCounter >= metaObject.getMaxHits(); //>= instead of == just to be sure
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  public int getHitScore() {
    return metaObject.getHitScore();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    if (!isDestroyed()) {
      metaObject.getImage(hitCounter).draw(getSmallestX(), getSmallestY());
    }

    //TODO remove if not needed anymore
    // This shows the hitbox of the targets
    Polygon poly = new Polygon();
    for (EnhancedVector point : vertices) {
      poly.addPoint(point.getX(), point.getY());
    }
    graphics.draw(poly);
  }
}
