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
public class TargetMeta {
  private static HashMap<TargetType, TargetMeta> targetTypeInstances;
  private Image image;
  private float mass;
  private ArrayList<float[]> targetOutline;

  static {
    //todo: initialize instances with data from config file
  }

  private TargetMeta() {
  }

  public static TargetMeta getInstance(TargetType type) {
    return targetTypeInstances.get(type);
  }

  Image getImage() {
    return image;
  }

  float getMass() {
    return mass;
  }

  ArrayList<float[]> getTargetOutline() {
    return targetOutline;
  }

  public enum TargetType {
    LIBRARY,
    RAM,
    ENEMY
  }
}
