/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Nick Steyer
 *         <p/>
 *         A class that all meta objects inherit from.
 *         Basically exists to avoid code redundancy.
 */
abstract class MetaObject {
  /**
   * The meta object instances are initialized in a static constructor.
   * They can then be accessed by using a hash map that maps the TargetType or the ProjectileType to the corresponding
   * meta object instance.
   */
  static HashMap<Enum, MetaObject> instances;
  float mass;

  /**
   * A list of arrays of floating point numbers, where each array consist of two numbers.
   * The first number indicates the x-value, the second one indicates the y-value of the vertex.
   * All arrays in the list (representing all vertices) form the outline of the object this meta object belongs to.
   */
  ArrayList<float[]> outline;

  ArrayList<float[]> getOutline() {
    return outline;
  }

  float getMass() {
    return mass;
  }
}
