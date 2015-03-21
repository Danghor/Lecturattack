package utilities;/*
 * Copyright (c) 2015.
 */

import entities.Player;
import entities.Target;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.xmlHandling.LevelElement;
import utilities.xmlHandling.XmlObjectType;

import javax.xml.bind.JAXB;
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
