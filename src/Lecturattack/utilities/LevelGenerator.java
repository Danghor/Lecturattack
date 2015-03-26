package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.*;
import Lecturattack.utilities.xmlHandling.Positioning;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Tim Adamek
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
      } else if (levelElement.getType() == XmlObjectType.RAM) {
        TargetMetaType targetMetaType;
        if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
          targetMetaType = TargetMetaType.RAMH;
        } else {
          targetMetaType = TargetMetaType.RAMV;
        }
        Target ram = new Target(TargetMeta.getInstance(targetMetaType), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(ram);
      } else if (levelElement.getType() == XmlObjectType.LIBRARY) {
        TargetMetaType targetMetaType;
        if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
          targetMetaType = TargetMetaType.LIBRARYH;
        } else {
          targetMetaType = TargetMetaType.LIBRARYV;
        }
        Target library = new Target(TargetMeta.getInstance(targetMetaType), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(library);
      } else {
        Target enemy = new Target(TargetMeta.getInstance(TargetMetaType.ENEMY), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(enemy);
      }
    }
  }
}
