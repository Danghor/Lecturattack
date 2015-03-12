package entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.geom.Polygon;
import utilities.EnhancedVector;

/**
 * Created by Nick Steyer on 08/03/2015
 */
public class RigidBody {
  private EnhancedVector linearVelocity;
  private EnhancedVector angularVelocity;

  private float mass;

  private Polygon vertices;
}
