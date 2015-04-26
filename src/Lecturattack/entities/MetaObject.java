/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Nick Steyer
 */
abstract class MetaObject {
  public static HashMap<Enum, MetaObject> instances;
  float mass;
  ArrayList<float[]> outline;

  ArrayList<float[]> getOutline() {
    return outline;
  }

  float getMass() {
    return mass;
  }
}
