package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.*;
import Lecturattack.utilities.xmlHandling.levelLoading.Positioning;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nick Steyer on 09/03/2015
 */
public class LevelGenerator {

  public static void generateLevel(List<LevelElement> levelElements, Player player, ArrayList<Target> targets) {
    //Test
    for (LevelElement levelElement : levelElements) {
      if (levelElement.getType() == XmlObjectType.PLAYER) {
        player.setPositionX(levelElement.getPositionX());
        player.setPositionY(levelElement.getPositionY());
        try {
          player.setBodyImage(new Image(levelElement.getImage()));
        } catch (SlickException e) {
          e.printStackTrace();
        }
      } else {
        TargetMeta.TargetType targetType;
        Target target;
        if (levelElement.getType() == XmlObjectType.RAM) {
          if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
            targetType = TargetMeta.TargetType.RAMH;
          } else {
            targetType = TargetMeta.TargetType.RAMV;
          }
        } else if (levelElement.getType() == XmlObjectType.LIBRARY) {
          if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
            targetType = TargetMeta.TargetType.LIBRARYH;
          } else {
            targetType = TargetMeta.TargetType.LIBRARYV;
          }
        } else {
          targetType = TargetMeta.TargetType.ENEMY;
        }
        TargetMeta m = TargetMeta.getInstance(targetType);
        target = new Target(TargetMeta.getInstance(targetType), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(target);
      }
    }
  }
}
