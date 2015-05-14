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
 * The meta object assigned to targets. It contains necessary information about the target such as the outline and the
 * image used for rendering.
 *
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
    //the xml configs are loaded
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

  /**
   * Returns the corresponding meta object for the given TargetType.
   *
   * @param type The TargetType for which the corresponding meta object should be retrieved.
   *
   * @return The instance of the retrieved meta object.
   */
  public static TargetMeta getInstance(TargetType type) {
    return instances.get(type);
  }

  public float getHitScore() {
    return hitScore;
  }

  TargetType getType() {
    return type;
  }

  /**
   * Returns the image with the given index used for rendering.
   *
   * @param index The index indicating which image should be returned.
   *
   * @return The instance of the requested image object.
   */
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

  /**
   * Plays the sound with the given index.
   *
   * @param index  The index indicating which sound should be played.
   * @param pitch  The pitch with which the sound should be played.
   * @param volume The volume with which the sound should be played.
   */
  public void playSound(int index, float pitch, float volume) {
    try {
      sounds.get(index).play(pitch, volume);
    } catch (IndexOutOfBoundsException ex) {
      System.out.println("Could not retrieve sound with index " + index + " for " + this.toString() + ".");
    } catch (NullPointerException ex) {
      System.out.println("Could play sound with index " + index + " for " + this.toString() + ". Sound is null.");
    }
  }

  /**
   * Stops the currently playing sound.
   */
  public void stopSound() {
    for (Sound sound : sounds) {
      if (sound != null) {
        sound.stop();
      }
    }
  }

}
