/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Nick Steyer
 */
public abstract class MetaObject {
  protected static HashMap instances;
  protected Image image;
  protected float mass;
  protected ArrayList<float[]> outline;

  ArrayList<float[]> getOutline() {
    return outline;
  }

  Image getImage() {
    return image;
  }

  float getMass() {
    return mass;
  }
}
