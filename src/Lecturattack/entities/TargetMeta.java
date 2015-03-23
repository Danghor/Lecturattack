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

  final int maxHits;

  static {
    instances = new HashMap<TargetType, TargetMeta>();
    //todo: initialize instances with data from config file
  }

  ArrayList<Image> images;

  private TargetMeta(ArrayList<Image> images, int maxHits) {
    this.images = images;
    this.maxHits = maxHits;
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  Image getImage(int index) {
    return images.get(index); //no need for exception handling, since IndexOutOfBoundsException is already implemented
  }

  enum TargetType {
    LIBRARY,
    RAM,
    ENEMY
  }
}
