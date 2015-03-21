package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.EnhancedVector;
import org.newdawn.slick.geom.Polygon;

/**
 * Created by Nick Steyer on 08/03/2015
 */
public abstract class RigidBody implements Renderable {
  protected EnhancedVector linearVelocity;
  protected float mass;
  protected Polygon vertices;

  public abstract EnhancedVector getCenter();

  public float getMass() {
    return mass;//TODO replace with mass loaded from Meta types
  }
}
