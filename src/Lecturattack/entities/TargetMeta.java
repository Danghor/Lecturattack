/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.xmlHandling.Positioning;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nick Steyer
 */
public class TargetMeta extends MetaObject {
  //TODO is the Implementation visibility intended?
  final int maxHits;//TODO isn't this always 3 per definition?
  ArrayList<Image> images;
  Positioning positioning;

  static {
    instances = new HashMap<TargetType, TargetMeta>();

    List<TargetStandard> targetStandards = FileHandler.loadTargetConfig();
    for (TargetStandard targetStandard : targetStandards) {
      ArrayList<Image> images = new ArrayList<>();
      try {
        //the images are not saved in a list because they are read from a config file
        //and having them in a list in a config file would be less readable (because the tag names would be the same)
        if (!targetStandard.getImageIntact().equals("")) {
          images.add(new Image(targetStandard.getImageIntact()));
        }
        if (!targetStandard.getImageSlightlyBroken().equals("")) {
          images.add(new Image(targetStandard.getImageSlightlyBroken()));
        }
        if (!targetStandard.getImageAlmostBroken().equals("")) {//TODO find another way than just leaving one field blank
          images.add(new Image(targetStandard.getImageAlmostBroken()));
        }
      } catch (SlickException e) {
        e.printStackTrace();
      }
      //TODO see if the "default" TargetType Emum can be reused here
      TargetType targetType;
      if (targetStandard.getTargetType() == XmlObjectType.ENEMY) {
        targetType = TargetType.ENEMY;
      } else if (targetStandard.getTargetType() == XmlObjectType.RAM) {
        targetType = TargetType.RAM;
      } else {//TODO there must be a new/"new" type
        targetType = TargetType.LIBRARY;
      }

      instances.put(targetType, new TargetMeta(images, targetStandard.getMaxHits(), targetStandard.getPositioning()));
    }
  }


  public TargetMeta(ArrayList<Image> images, int maxHits, Positioning positioning) {
    this.images = images;
    this.maxHits = maxHits;
    this.positioning = positioning;
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  Image getImage(int index) {
    return images.get(index); //no need for exception handling, since IndexOutOfBoundsException is already implemented
  }

  public enum TargetType {//TODO in external class --> allows use for XML
    LIBRARY,
    RAM,
    ENEMY
  }
}
