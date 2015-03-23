package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.EnhancedVector;
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

  void hit() {
    if (hitCounter < metaObject.maxHits) {
      hitCounter++;
    }
  }

  boolean destroyed() {
    return hitCounter >= metaObject.maxHits; //>= instead of == just to be sure
  }

  @Override
  public EnhancedVector getCenter() {
    return null;
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

  }
}
