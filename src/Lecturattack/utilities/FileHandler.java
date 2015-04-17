package Lecturattack.utilities;

import Lecturattack.utilities.xmlHandling.configLoading.*;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelData;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Laura Hillenbrand
 * @author Stefanie Raschke
 */
public class FileHandler {
  private static final String[] PATH_TO_LEVELS = new String[]{"resources/level/Level1.xml", "resources/level/Level2.xml", "resources/level/Level3.xml", "resources/level/Level4.xml", "resources/level/Level5.xml", "resources/level/Level6.xml",}; // TODO
  // add
  // LevelFiles
  private static final String BACKGROUND_MUSIC_PATH = "resources\\sounds\\bgMusic.wav";
  // todo: create folder "Coffee Productions" in appdata/roaming
  private static String LAST_LEVEL_FILE_PATH = "";
  private static int lastLevelNumber = -1;

  /**
   * This method loads the target.xml as a config for the TargetMeta instances
   *
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
   *
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
   *
   * @param levelNumber the level which should be loaded
   *
   * @return the elements in the level
   * @throws IllegalArgumentException
   */
  public static List<LevelElement> getLevelData(int levelNumber) throws IllegalArgumentException {
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
   *
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

  /**
   * @return
   */
  public static int getLastLevelNumber() {
    if (lastLevelNumber == -1) {
      lastLevelNumber = getLastLevelFromFile();
    }
    return lastLevelNumber;
  }

  /**
   * @param level
   */
  public static void setLastLevelNumber(int level) {
    if (level > lastLevelNumber) {
      lastLevelNumber = level;
      try {
        String text = Integer.toString(lastLevelNumber);
        BufferedWriter out = new BufferedWriter(new FileWriter(LAST_LEVEL_FILE_PATH));
        out.write(text);
        out.close();
      } catch (IOException e) {
        System.out.println("Error while writing in text file");
      }
    }
  }

  public static int getLastLevelFromFile() {
    File f = new File(LAST_LEVEL_FILE_PATH);
    if (f.exists() && !f.isDirectory()) {
      FileReader fr;
      BufferedReader br;
      try {
        fr = new FileReader(LAST_LEVEL_FILE_PATH);
        br = new BufferedReader(fr);
        // read lines in file
        String text;
        text = br.readLine();
        lastLevelNumber = Integer.parseInt(text);
        fr.close();
      } catch (IOException e) {
        System.out.println("Error when trying to read file " + LAST_LEVEL_FILE_PATH);
        System.out.println(e.toString());
      }
    } else {
      lastLevelNumber = 1;
    }
    return lastLevelNumber;
  }

  public static void resetLastLevelNumber() {
    lastLevelNumber = 1;
    try {
      String text = "1";
      BufferedWriter out = new BufferedWriter(new FileWriter(LAST_LEVEL_FILE_PATH));
      out.write(text);
      out.close();
    } catch (IOException e) {
      System.out.println("Error while writing in text file");
    }
  }

  /**
   * This method sets the file path according to the used system
   */
  public static void getSystem() {
    String sysName = System.getProperty("os.name");
    if (sysName.contains("Windows")) {
      LAST_LEVEL_FILE_PATH = System.getProperty("user.home") + "\\AppData\\Roaming\\Lecturattack.txt";
    } else if (sysName.contains("Linux")) {
      LAST_LEVEL_FILE_PATH = System.getenv("APPDATA") + "/Lecturattack.txt";
    } else if (sysName.contains("Mac")) {
      LAST_LEVEL_FILE_PATH = "~/Documents/Saved Games/GAMENAME/Lecturattack.txt";
    }
    File levelFile = new File(LAST_LEVEL_FILE_PATH);
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

  public static Music getBackgroundMusic() {
    Music bgMusic = null;

    try {
      bgMusic = new Music(BACKGROUND_MUSIC_PATH);
    } catch (SlickException e) {
      System.out.println("Could not process file " + BACKGROUND_MUSIC_PATH);
      e.printStackTrace();
    }

    return bgMusic;
  }
}
