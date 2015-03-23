/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Nick Steyer
 */
public abstract class MetaObject {
  protected static HashMap instances;
  protected float mass;
  protected ArrayList<float[]> outline;

  ArrayList<float[]> getOutline() {
    return outline;
  }

  float getMass() {
    return mass;
  }
}
