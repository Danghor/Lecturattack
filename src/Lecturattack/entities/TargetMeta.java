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
      ArrayList<Sound> sounds = new ArrayList<>();
      try {
        //add images
        if (!targetStandard.getImageIntact().equals("")) {
          images.add(new Image(targetStandard.getImageIntact()));
        }
        if (!targetStandard.getImageSlightlyBroken().equals("")) {
          images.add(new Image(targetStandard.getImageSlightlyBroken()));
        }
        if (!targetStandard.getImageAlmostBroken().equals("")) {
          images.add(new Image(targetStandard.getImageAlmostBroken()));
        }

        //add sounds
        sounds.add(targetStandard.getSound1AsSound());
        sounds.add(targetStandard.getSound2AsSound());
        sounds.add(targetStandard.getSound3AsSound());
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
      targetMeta = new TargetMeta(type, images, targetStandard.getMaxHits(), targetStandard.getVerticesAsFloats(), targetStandard.getHitScore(), sounds);
      targetMeta.mass = targetStandard.getMass();
      instances.put(type, targetMeta);
    }
  }

  private final int maxHits; //when the Target is hit as many times as maxHits, the Target is destroyed
  private final float hitScore; //the score received when a target of this type gets hit
  private final ArrayList<Image> images;
  private final TargetType type;
  private final ArrayList<Sound> sounds;

  private TargetMeta(TargetType type, ArrayList<Image> images, int maxHits, ArrayList<float[]> outline, float hitScore, ArrayList<Sound> sounds) {
    this.type = type;
    this.images = images;
    this.maxHits = maxHits;
    this.outline = outline;
    this.hitScore = hitScore;
    this.sounds = sounds;
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
    try {
      return images.get(index);
    } catch (IndexOutOfBoundsException ex) {
      System.out.println("Could not retrieve image with index " + index + " for " + this.toString() + ".");
      return images.get(0); //image 0 should always be set, might fix the problem and prevent crashing the application
    }
  }

  @Override
  public String toString() {
    return getType().toString();
  }

  int getMaxHits() {
    return maxHits;
  }

  Sound getSound(int index) {
    try {
      return sounds.get(index);
    } catch (IndexOutOfBoundsException ex) {
      System.out.println("Could not retrieve sound with index " + index + " for " + this.toString() + ".");
      return sounds.get(0);
    }
  }
}
