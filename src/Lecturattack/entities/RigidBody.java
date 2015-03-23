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


  protected RigidBody() {
    force = new EnhancedVector(0f, 0f);
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
