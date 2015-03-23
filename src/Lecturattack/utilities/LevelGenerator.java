package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.Player;
import Lecturattack.entities.Target;
import Lecturattack.utilities.xmlHandling.LevelElement;
import Lecturattack.utilities.xmlHandling.XmlObjectType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nick Steyer on 09/03/2015
 */
public class LevelGenerator {

  //TODO --> real datatype for levelData
  public static void generateLevel(List<LevelElement> levelElements, Player player, ArrayList<Target> targets) {
    for (LevelElement levelElement : levelElements) {
      if (levelElement.getType() == XmlObjectType.PLAYER) {
        player.setPositionX(levelElement.getPositionX());
        player.setPositionY(levelElement.getPositionY());
        try {
          player.setBodyImage(new Image(levelElement.getImage()));
        } catch (SlickException e) {
          e.printStackTrace();
        }
      }else if(levelElement.getType() == XmlObjectType.RAM){

      }
    }
  }
}
