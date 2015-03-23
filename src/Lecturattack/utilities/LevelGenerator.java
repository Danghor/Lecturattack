package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.Player;
import Lecturattack.entities.Target;
import Lecturattack.entities.TargetMeta;
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
      } else if (levelElement.getType() == XmlObjectType.RAM) {
        Target ram = new Target(TargetMeta.getInstance(TargetMeta.TargetType.RAM), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(ram);
      } else if (levelElement.getType() == XmlObjectType.LIBRARY) {
        Target library = new Target(TargetMeta.getInstance(TargetMeta.TargetType.LIBRARY), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(library);
      } else {
        Target enemy = new Target(TargetMeta.getInstance(TargetMeta.TargetType.ENEMY), levelElement.getPositionX(), levelElement.getPositionY());
        targets.add(enemy);
      }
    }
  }
}
