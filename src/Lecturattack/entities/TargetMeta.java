/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import java.util.HashMap;

/**
 * @author Nick Steyer
 */
public class TargetMeta extends MetaObject {

  static {
    instances = new HashMap<TargetType, TargetMeta>();
    //todo: initialize instances with data from config file
  }

  private TargetMeta() {
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  enum TargetType {
    LIBRARY,
    RAM,
    ENEMY
  }
}
