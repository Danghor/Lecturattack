/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nick Steyer
 * @author Tim Adamek
 */
public class TargetMeta extends MetaObject {
  static {
    instances = new HashMap<>();

    List<TargetStandard> targetStandards = FileHandler.loadTargetConfig();

    for (TargetStandard targetStandard : targetStandards) {
      ArrayList<Image> images = new ArrayList<>();
      try {
        //the images are not saved in a list because they are read from a config file
        //and having them in a list in a config file would be less readable (because the tag names would be the same)
        //TODO maybe other way of doing this

        if (!targetStandard.getImageIntact().equals("")) {
          images.add(new Image(targetStandard.getImageIntact()));
        }
        if (!targetStandard.getImageSlightlyBroken().equals("")) {
          images.add(new Image(targetStandard.getImageSlightlyBroken()));
        }
        if (!targetStandard.getImageAlmostBroken().equals("")) {//TODO find another way than just leaving one field blank --> maybe really list
          images.add(new Image(targetStandard.getImageAlmostBroken()));
        }
      } catch (SlickException e) {
        e.printStackTrace();
      }
      //TODO
      TargetType type;
      switch (targetStandard.getTargetType()) {
        case "ENEMY":
          type = TargetType.ENEMY;
          break;
        case "RAM":
          if (targetStandard.getPositioning().equals("HORIZONTAL")) {
            type = TargetType.RAMH;
          } else {
            type = TargetType.RAMV;
          }
          break;
        case "LIBRARY":
          if (targetStandard.getPositioning().equals("HORIZONTAL")) {
            type = TargetType.LIBRARYH;
          } else {
            type = TargetType.LIBRARYV;
          }
          break;
        default:
          throw new RuntimeException("Invalid TargetType given.");
      }

      TargetMeta targetMeta = new TargetMeta(images, targetStandard.getMaxHits(), targetStandard.getVerticesAsFloats());
      targetMeta.mass = targetStandard.getMass();
      instances.put(type, targetMeta);
    }
  }

  private final int maxHits;
  private ArrayList<Image> images;

  private TargetMeta(ArrayList<Image> images, int maxHits, ArrayList<float[]> outline) {
    this.images = images;
    this.maxHits = maxHits;
    this.outline = outline;
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  int getMaxHits() {
    return maxHits;
  }

  Image getImage(int index) {
    //the IndexOutOfBoundsException should never occur in production
    return images.get(index);
  }

  public enum TargetType {
    LIBRARYV,
    LIBRARYH,
    RAMV,
    RAMH,
    ENEMY
  }
}
