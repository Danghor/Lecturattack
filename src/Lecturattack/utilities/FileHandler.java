package Lecturattack.utilities;

import Lecturattack.utilities.xmlHandling.configLoading.*;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelData;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 */
public class FileHandler {
  private static final String LAST_LEVEL_FILE_PATH = ".\\latestLevel.txt";
  private static final String[] PATH_TO_LEVELS = new String[]{"resources/level/Level1.xml", "resources/level/Level2.xml", "resources/level/Level3.xml", "resources/level/Level4.xml", "resources/level/Level5.xml", "resources/level/Level6.xml",}; //TODO add LevelFiles


  /**
   * This method loads the target.xml as a config for the TargetMeta instances
   * @return the loaded configs for the Targets
   */
  public static List<TargetStandard> loadTargetConfig() {
    File file = new File("resources/config/target.xml");//TODO save in final var --> method for opening/vrating --> code dup
    JAXBContext jaxbContext;
    TargetConfig targets = null;
    try {
      jaxbContext = JAXBContext.newInstance(TargetConfig.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      targets = (TargetConfig) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return targets.getTargetStandards();
  }
  /**
   * This method loads the target.xml as a config for the ProjectileMeta instances
   * @return the loaded configs for the Projectiles
   */
  public static List<ProjectileStandard> loadProjectileStandards() {
    File file = new File("resources/config/projectile.xml");//TODO save in final var --> method for opening/vrating --> code dup
    JAXBContext jaxbContext;
    ProjectileConfig projectiles = null;
    try {
      jaxbContext = JAXBContext.newInstance(ProjectileConfig.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      projectiles = (ProjectileConfig) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return projectiles.getProjectileStandards();
  }

  /**
   * This method loads a Level and returns a list of elements in the level, this includes all targets and the position of the player
   * @param levelNumber the level which should be loaded
   * @return the elements in the level
   * @throws IllegalArgumentException
   * @throws IOException
   */
  public static List<LevelElement> getLevelData(int levelNumber) throws IllegalArgumentException, IOException {
    File file;
    if (levelNumber >= 1 && levelNumber <= 6) {
      file = new File(PATH_TO_LEVELS[levelNumber - 1]);//TODO mapping levelNumber to file
    } else {
      throw new IllegalArgumentException("The Level must be between 1 and 6");
    }
    JAXBContext jaxbContext;
    LevelData level = null;
    try {
      jaxbContext = JAXBContext.newInstance(LevelData.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      level = (LevelData) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return level.getLevelElements();
  }

  /**
   * Loads the configs for the player
   * @return the loaded players objects which resemble the xml
   */
  public static List<PlayerStandard> getPlayerData() {
    File file = new File("resources/config/player.xml");//TODO save in final var --> method for opening/vrating --> code dup
    JAXBContext jaxbContext;
    PlayerConfig players = null;
    try {
      jaxbContext = JAXBContext.newInstance(PlayerConfig.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      players = (PlayerConfig) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return players.getPlayerStandards();
  }

  public static int getLastLevelNumber() {
    return 0;
  }

  public static void setLastLevelNumber(int level) {
    //todo: alles in %APPDATA%
  }

  public static Image loadImage(String fileName) {
    Image image = null;
    try {
      image = new Image("resources/images/" + fileName + ".png");
    } catch (SlickException e) {
      System.out.println("Error while loading image.");
    }
    return image;
  }
}
