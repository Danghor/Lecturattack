/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import Lecturattack.entities.types.TargetType;
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
        if (!targetStandard.getImageAlmostBroken().equals("")) {
          images.add(new Image(targetStandard.getImageAlmostBroken()));
        }
      } catch (SlickException e) {
        e.printStackTrace();
      }
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

      TargetMeta targetMeta = new TargetMeta(type, images, targetStandard.getMaxHits(), targetStandard.getVerticesAsFloats());
      targetMeta.mass = targetStandard.getMass();
      instances.put(type, targetMeta);
    }
  }

  private final int maxHits; //when the Target is hit as many times as maxHits, the Target is destroyed
  private final int hitScore; //the score received when a target of this type gets hit
  private ArrayList<Image> images;
  private TargetType type;

  private TargetMeta(TargetType type, ArrayList<Image> images, int maxHits, ArrayList<float[]> outline) {
    this.type = type;
    this.images = images;
    this.maxHits = maxHits;
    this.outline = outline;

    switch (type) {
      case RAMH:
      case RAMV:
        hitScore = 10; //todo: in configs
        break;
      case LIBRARYH:
      case LIBRARYV:
        hitScore = 10;
        break;
      case ENEMY:
        hitScore = 100;
        break;
      default:
        hitScore = 0;
        break;
    }
  }

  public static TargetMeta getInstance(TargetType type) {
    return (TargetMeta) instances.get(type);
  }

  int getHitScore() {
    return hitScore;
  }

  TargetType getType() {
    return type;
  }

  Image getImage(int index) {
    //the IndexOutOfBoundsException should never occur in production
    return images.get(index);
  }

  int getMaxHits() {
    return maxHits;
  }
}
