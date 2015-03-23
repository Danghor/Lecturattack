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
public class Projectile extends RigidBody {

  protected float angularVelocity;
  protected float torque;
  private ProjectileMeta metaObject;

  public Projectile(ProjectileMeta projectileMeta, float x, float y) {
    super();
    metaObject = projectileMeta;
    move(new EnhancedVector(x, y));
    torque = 0f;
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

  protected float getInertia() {
    double length = (new EnhancedVector(getCenter().x - vertices.get(0).x, getCenter().y - vertices.get(0).y)).length();
    return (getMass() * (float) Math.pow(length, 4)) / 120000;
  }

  public void applyTorque(float value) {
    torque += value;
  }

  public void rotate(float angle, EnhancedVector center) {
    for (EnhancedVector vertex : vertices) {
      vertex.rotate(angle, center);
    }
  }
}
