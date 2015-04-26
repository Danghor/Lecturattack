package Lecturattack.utilities;

import Lecturattack.entities.Target;
import Lecturattack.entities.TargetMeta;
import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelData;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import Lecturattack.utilities.xmlHandling.levelLoading.Positioning;
import Lecturattack.utilities.xmlHandling.levelLoading.XmlObjectType;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Tim Adamek
 */
public class LevelGenerator {
  /**
   * Creates a level for the given LevelElements
   *
   * @param levelData all elements in the level, these objects are the loaded XMLobjects, the score
   *
   * @return a Level object, which holds the information about the entire level
   */
  public static Level getGeneratedLevel(LevelData levelData) {
    float playerPositionX = 0f;
    float playerPositionY = 0f;
    ArrayList<Target> targets = new ArrayList<>();

    for (LevelElement levelElement : levelData.getLevelElements()) {

      if (levelElement.getType() == XmlObjectType.PLAYER) { //Player

        playerPositionX = levelElement.getPositionX();
        playerPositionY = levelElement.getPositionY();

      } else { //Target
        //the Targets need a target Meta in their initializer, to specify their type
        TargetMeta targetMeta;
        float posX = levelElement.getPositionX();
        float posY = levelElement.getPositionY();
        TargetType targetType;

        //the targets levelElements are distinguished by their XmlObjectType, which specifies what target they are (or if they are a player).
        //after that for RAM and LIBRARY the position must be checked, because there are different targetMetas for them
        if (levelElement.getType() == XmlObjectType.RAM) {
          if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
            targetType = TargetType.RAMH;
          } else {
            targetType = TargetType.RAMV;
          }
        } else if (levelElement.getType() == XmlObjectType.LIBRARY) {
          if (levelElement.getPositioning() == Positioning.HORIZONTAL) {
            targetType = TargetType.LIBRARYH;
          } else {
            targetType = TargetType.LIBRARYV;
          }
        } else {
          targetType = TargetType.ENEMY;
        }
        targetMeta = TargetMeta.getInstance(targetType);
        targets.add(new Target(targetMeta, posX, posY));
      }

    }
    return new Level(targets, playerPositionX, playerPositionY, levelData.getScore());
  }
}
