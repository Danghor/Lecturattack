/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nick Steyer
 * @author Tim Adamek
 * @author Andreas Geis
 */
public class TargetMeta extends MetaObject {
  private static final HashMap<TargetType, TargetMeta> instances;

  /**
   * This method will initialize all TargetMeta objects so that they can be used later.
   * They are accessed using the public getInstance method together with the desired TargetType.
   */
  static {
    instances = new HashMap<>();
    FileHandler fileHandler = new FileHandler();
    List<TargetStandard> targetStandards = fileHandler.loadTargetConfig();

    for (TargetStandard targetStandard : targetStandards) {
      ArrayList<Image> images = new ArrayList<>();
      try {
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

      TargetMeta targetMeta;
      // TODO: better solution
      targetMeta = new TargetMeta(type, images, targetStandard.getMaxHits(), targetStandard.getVerticesAsFloats(), targetStandard.getHitScore(), targetStandard.getSound1AsSound());
      targetMeta.mass = targetStandard.getMass();
      instances.put(type, targetMeta);
    }
  }

  private final int maxHits; //when the Target is hit as many times as maxHits, the Target is destroyed
  private final float hitScore; //the score received when a target of this type gets hit
  private final ArrayList<Image> images;
  private final TargetType type;
  private final Sound sound;

  private TargetMeta(TargetType type, ArrayList<Image> images, int maxHits, ArrayList<float[]> outline, float hitScore, Sound sound) {
    this.type = type;
    this.images = images;
    this.maxHits = maxHits;
    this.outline = outline;
    this.hitScore = hitScore;
    this.sound = sound;
  }

  public static TargetMeta getInstance(TargetType type) {
    return instances.get(type);
  }

  public float getHitScore() {
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

  Sound getSound() {
    return sound;
  }
}
