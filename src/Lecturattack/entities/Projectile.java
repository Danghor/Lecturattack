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
  private float angleInDegrees;

  /**
   * Saves the given metaObject, sets the starting position to 0, 0 and initializes the torque and angle with 0.
   *
   * @param projectileMeta The meta object containing necessary information about this projectile such as the outline.
   */
  public Projectile(ProjectileMeta projectileMeta) {
    super(projectileMeta, 0f, 0f);
    metaObject = projectileMeta;
    torque = 0f;
    angleInDegrees = 0f;
  }

  /**
   * Updates this object, i.e. calculates its next physical position based on the forces applied to it and the
   * given delta regardless of any possible collision. This also takes the applied torque into account and makes this
   * object rotate around its center.
   *
   * @param scaledDelta The delta given by the physics engine in order to calculate this step.
   */
  @Override
  public void update(float scaledDelta) {
    super.update(scaledDelta);
    float angularAcceleration = torque / getInertia();
    angularVelocity += angularAcceleration * scaledDelta;
    rotate(angularVelocity * scaledDelta, getCenter());
    torque = 0;
  }

  /**
   * Displays this object on the screen.
   *
   * @param gameContainer  Not used.
   * @param stateBasedGame Not used.
   * @param graphics       The Graphics object used to draw the image on.
   */
  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Image image = metaObject.getImage();
    image.rotate(getAngleInDegrees());
    try {
      graphics.drawImage(image, getCenter().getX() - (image.getWidth() / 2), getCenter().getY() - (image.getHeight() / 2));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the torque based on the torque already applied and this one.
   *
   * @param value The torque to be applied to this projectile.
   */
  public void applyTorque(float value) {
    torque += value;
  }

  /**
   * Rotates this projectile by the given angle around the given center point.
   *
   * @param angleInDegrees The angle by which this projectile should be rotated.
   * @param center         The center around which the projectile should be rotated.
   */
  private void rotate(float angleInDegrees, EnhancedVector center) {
    for (EnhancedVector vertex : vertices) {
      vertex.rotate(angleInDegrees, center);
    }
    this.angleInDegrees += angleInDegrees;
  }

  /**
   * Rotates this projectile around its center by the given angle.
   *
   * @param angleInDegrees The angle by which this projectile should be rotated.
   */
  public void setRotation(float angleInDegrees) {
    rotate(angleInDegrees - this.angleInDegrees, getCenter());
  }

  public ArrayList<TargetType> getDestroys() {
    return metaObject.getDestroys();
  }

  /**
   * Inverts the angular velocity of this projectile while slowing it down based on the given scaling.
   * Used for collision response when this projectile collides with a target or the ground.
   *
   * @param scaling A floating point number used for slowing the rotation down. 1 means no change, 0.5 means half as fast.
   */
  public void invertRotation(float scaling) {
    angularVelocity = -(angularVelocity * scaling);
  }

  private float getAngleInDegrees() {
    return angleInDegrees;
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  /**
   * Calculates the inertia of this projectile. Used for rotating it based on the applied torque.
   *
   * @return The calculated inertia.
   */
  private float getInertia() {
    float length = (new EnhancedVector(getCenter().getX() - vertices.get(0).getX(), getCenter().getY() - vertices.get(0).getY())).length();
    return (getMass() * (float) Math.pow(length, 4)) / 120000;
  }

  /**
   * Makes this projectile "bounce" of the given partner body.
   * Also, inverts its current rotation.
   *
   * @param partner The RigidBody this object is colliding with each other.
   */
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
