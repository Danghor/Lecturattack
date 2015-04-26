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
  private final ProjectileMeta metaObject;
  private float torque;
  private float angularVelocity;
  private float angle;

  public Projectile(ProjectileMeta projectileMeta) {
    super(projectileMeta, 0f, 0f);
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

  public void invertRotation(float scaling) {
    angularVelocity = -(angularVelocity * scaling);
  }

  private float getAngle() {
    return angle;
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  private float getInertia() {
    float length = (new EnhancedVector(getCenter().getX() - vertices.get(0).getX(), getCenter().getY() - vertices.get(0).getY())).length();
    return (getMass() * (float) Math.pow(length, 4)) / 120000;
  }

  @Override
  public void reflect(RigidBody partner) {
    try {
      super.reflect(partner);
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
      ex.printStackTrace();
    }
    invertRotation(RigidBody.BOUNCINESS);
  }

}
