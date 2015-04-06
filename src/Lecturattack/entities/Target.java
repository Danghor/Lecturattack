package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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

  public TargetMeta.TargetType getType() {
    return metaObject.getType();
  }

  //todo: hit with a specific projectile to avoid determining whether it can be hit in the physics engine
  //just hit it here and then determine here if it gets damaged or not
  public void hit() {
    if (hitCounter < metaObject.getMaxHits()) {
      hitCounter++;
    }
  }

  public boolean isDestroyed() {
    return hitCounter >= metaObject.getMaxHits(); //>= instead of == just to be sure
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    if (!isDestroyed()) {
      graphics.drawImage(metaObject.getImage(hitCounter), vertices.get(0).x, vertices.get(0).y);
    }
  }
}
