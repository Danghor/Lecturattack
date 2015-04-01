package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.EnhancedVector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Nick Steyer
 */
public class Projectile extends RigidBody {
  protected float angularVelocity;
  protected float torque;
  private ProjectileMeta metaObject;

  public Projectile(ProjectileMeta projectileMeta, float x, float y) {
    super(projectileMeta, x, y);
    metaObject = projectileMeta;
    torque = 0f;
  }

  public float getAngularVelocity() {
    return angularVelocity;
  }

  public void setAngularVelocity(float angularVelocity) {
    this.angularVelocity = angularVelocity;
  }

  private float getAngle() {
    EnhancedVector vertexA = getCenter();
    EnhancedVector vertexB = vertices.get(0);
    EnhancedVector vertexC = new EnhancedVector(metaObject.outline.get(0)[0], metaObject.outline.get(0)[1]);

    EnhancedVector edgeA = (EnhancedVector) (new EnhancedVector(vertexC.x, vertexC.y)).sub(vertexB);
    EnhancedVector edgeB = (EnhancedVector) (new EnhancedVector(vertexC.x, vertexC.y)).sub(vertexA);
    EnhancedVector edgeC = (EnhancedVector) (new EnhancedVector(vertexB.x, vertexB.y)).sub(vertexA);

    float lengthA = edgeA.length();
    float lengthB = edgeB.length();
    float lengthC = edgeC.length();

    return (float) Math.acos((lengthB * lengthB + lengthC * lengthC - lengthA * lengthA) / (2 * lengthB * lengthC));
  }

  @Override
  public float getMass() {
    return metaObject.getMass();
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Image image = metaObject.getImage();

    image.rotate(getAngle());

    try {
      //the start position for drawing the figure is assumed to be the upper-left corner of the polygon
      graphics.drawImage(image, vertices.get(0).x, vertices.get(0).y);
    } catch (Exception e) {
      e.printStackTrace();
    }
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

  @Override
  public void update(int delta) {
    super.update(delta);

    float angularAcceleration = torque / getInertia();
    angularVelocity += angularAcceleration * delta;
    rotate(angularVelocity * delta, getCenter());

    torque = 0;
  }
}
