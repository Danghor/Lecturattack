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
public class TargetMeta extends MetaObject {

  private final int maxHits;
  private ArrayList<Image> images;

  private TargetMeta(ArrayList<Image> images, int maxHits) {
    this.images = images;
    this.maxHits = maxHits;
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  int getMaxHits() {
    return maxHits;
  }

  static {
    instances = new HashMap<TargetType, TargetMeta>();
    //todo: initialize instances with data from config file
  }

  Image getImage(int index) {
    //no need for exception handling, since IndexOutOfBoundsException is already implemented
    //this exception should never occur in production
    return images.get(index);
  }

  enum TargetType {
    LIBRARY,
    RAM,
    ENEMY
  }
}
