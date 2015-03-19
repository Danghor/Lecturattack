package entities;/*
 * Copyright (c) 2015.
 */

import NoPackageCreatedYetPackage.Renderable;
import org.newdawn.slick.geom.Polygon;
import utilities.EnhancedVector;

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
