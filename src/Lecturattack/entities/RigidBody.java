package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.EnhancedVector;

import java.util.ArrayList;

/**
 * @author Nick Steyer
 */
public abstract class RigidBody implements Renderable {
  protected ArrayList<EnhancedVector> vertices;
  protected EnhancedVector linearVelocity;
  protected EnhancedVector force;

  protected RigidBody(MetaObject meta, float x, float y) {
    this.vertices = new ArrayList<EnhancedVector>();

    //TODO load outline from config --> currenty null pointer exeption
    //for (float[] vertexPosition : meta.getOutline()) {
    //  vertices.add(new EnhancedVector(vertexPosition[0], vertexPosition[1]));
   // }

    move(new EnhancedVector(x, y));

    linearVelocity = new EnhancedVector(0f, 0f);
    force = new EnhancedVector(0f, 0f);
  }

  public EnhancedVector getLinearVelocity() {
    return linearVelocity;
  }

  public void setLinearVelocity(EnhancedVector linearVelocity) {
    this.linearVelocity = linearVelocity;
  }

  public abstract EnhancedVector getCenter();

  public abstract float getMass();

  public void applyForce(float x, float y) {
    force.add(new EnhancedVector(x, y));
  }

  public void move(EnhancedVector direction) {
    for (EnhancedVector vertex : vertices) {
      vertex.add(direction);
    }
  }


}
