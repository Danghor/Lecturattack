package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.EnhancedVector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public class Projectile extends RigidBody {
  private float torque;
  private float angularVelocity;
  private float angle;
  private ProjectileMeta metaObject;

  public Projectile(ProjectileMeta projectileMeta, float x, float y) {
    super(projectileMeta, x, y);
    metaObject = projectileMeta;
    torque = 0f;
    angle = 0f;
  }

  @Override
  public void update(float scaledDelta) {
    super.update(scaledDelta);
    float angularAcceleration = torque / getInertia();
    angularVelocity += angularAcceleration * scaledDelta;
    rotate(angularVelocity * scaledDelta, getCenter());
    torque = 0;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Image image = metaObject.getImage();
    image.rotate(getAngle());
    try {
      graphics.drawImage(image, getSmallestX(), getSmallestY());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void applyTorque(float value) {
    torque += value;
  }

  private void rotate(float angle, EnhancedVector center) {
    for (EnhancedVector vertex : vertices) {
      vertex.rotate(angle, center);
    }
    this.angle += angle;
  }

  public ArrayList<TargetType> getDestroys() {
    return metaObject.getDestroys();
  }

  public void invertRotation() {
    angularVelocity = -angularVelocity;
  }

  private float getAngle() {
    return angle;
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  protected float getInertia() {
    double length = (new EnhancedVector(getCenter().x - vertices.get(0).x, getCenter().y - vertices.get(0).y)).length();
    return (getMass() * (float) Math.pow(length, 4)) / 120000;
  }

  @Override
  public void reflect(RigidBody partner) {
    super.reflect(partner);
    invertRotation();
  }

}
