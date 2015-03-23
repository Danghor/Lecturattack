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

  public Target(TargetMeta targetMeta) {
    super();//TODO needs some way to set the position, so that it is possible to define where the targets are when creating them in the LevelGenerator
    metaObject = targetMeta;
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
