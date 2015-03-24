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

  float x;
  float y;
  private TargetMeta metaObject;
  private int hitCounter;


  public Target(TargetMeta targetMeta, float x, float y) {
    super(targetMeta, x, y);
    metaObject = targetMeta;
    hitCounter = 0;
    this.x = x;
    this.y = y;
  }

  void hit() {
    if (hitCounter < metaObject.getMaxHits()) {
      hitCounter++;
    }
  }

  boolean isDestroyed() {
    return hitCounter >= metaObject.getMaxHits(); //>= instead of == just to be sure
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    //TODO just dummy implementation
    metaObject.getImage(0).draw(this.x, this.y);
  }
}
