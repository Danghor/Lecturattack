package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.Player;
import Lecturattack.entities.Target;
import Lecturattack.entities.TargetMeta;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import Lecturattack.utilities.xmlHandling.levelLoading.Positioning;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Tim Adamek
 */
public class LevelGenerator {

  public static Level getGeneratedLevel(List<LevelElement> levelElements) throws SlickException {
    Player player = null;
    ArrayList<Target> targets = new ArrayList<>();

    for (LevelElement levelElement : levelElements) {

      if (levelElement.getType() == XmlObjectType.PLAYER) { //Player

        float posX = levelElement.getPositionX();
        float posY = levelElement.getPositionY();
        Image bodyImage = new Image(levelElement.getImage());

        player = new Player(posX, posY, bodyImage);

      } else { //Target

        TargetMeta targetMeta;
        float posX = levelElement.getPositionX();
        float posY = levelElement.getPositionY();

        TargetMeta.TargetType targetType;

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

        targetMeta = TargetMeta.getInstance(targetType);
        targets.add(new Target(targetMeta, posX, posY));

      }

    }

    return new Level(player, targets);
  }
}
