/*
 * Copyright (c) 2015.
 */

package Lecturattack.entities;

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
import Lecturattack.utilities.xmlHandling.configLoading.XmlVertice;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nick Steyer, Tim Adamek
 */
public class TargetMeta extends MetaObject {
  private final int maxHits;
  ArrayList<Image> images;
  TargetType targetType;

  static {
    instances = new HashMap<>();

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
        if (!targetStandard.getImageAlmostBroken().equals("")) {//TODO find another way than just leaving one field blank --> maybe really list
          images.add(new Image(targetStandard.getImageAlmostBroken()));
        }
      } catch (SlickException e) {
        e.printStackTrace();
      }

      instances.put(targetStandard.getTargetMetaType(), new TargetMeta(images, targetStandard.getMaxHits(),targetStandard.getTargetType(),targetStandard.getVertices()));//TODO this is strange --> it would also accept TargetType instead of targetMetaType
    }
  }

  public TargetMeta(ArrayList<Image> images, int maxHits,TargetType targetType,List<XmlVertice> vertices) {
    this.images = images;
    this.maxHits = maxHits;
    this.targetType = targetType;
    for(XmlVertice vertice:vertices){
      float[] verticePosition = {vertice.getX(),vertice.getY()};
      this.outline=new ArrayList<>();
      this.outline.add(verticePosition);
    }
  }

  public static TargetMeta getInstance(TargetMetaType type) {
    return (TargetMeta) instances.get(type);
  }

  int getMaxHits() {
    return maxHits;
  }

  Image getImage(int index) {
    //no need for exception handling, since IndexOutOfBoundsException is already implemented
    //this exception should never occur in production
    return images.get(index);
  }

  public enum TargetType {
    LIBRARY,
    RAM,
    ENEMY
  }
}
