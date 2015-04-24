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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Laura Hillenbrand
 * @author Stefanie Raschke
 */
public class FileHandler {
  private static final String ERROR_WHILE_WRITING_IN_TEXT_FILE = "Error while writing in text file";
  private static final String ERROR_WHEN_TRYING_TO_READ_FILE = "Error when trying to read file ";
  private static final String[] PATH_TO_LEVELS = new String[]{"resources/level/Level1.xml", "resources/level/Level2.xml", "resources/level/Level3.xml", "resources/level/Level4.xml", "resources/level/Level5.xml", "resources/level/Level6.xml",};
  private static final String BACKGROUND_MUSIC_PATH = "resources\\sounds\\bgMusic.wav";
  private static final String GAME_FOLDER = "CoffeeProductions";
  private static final String GAME_NAME = "\\Lecturattack.txt";
  private static String LAST_LEVEL_FILE_PATH = "";


  static {
    // This sets the file path according to the used system
    String sysName = System.getProperty("os.name");
    String saveDirectory = "";
    if (sysName.contains("Windows")) {
      saveDirectory = System.getProperty("user.home") + "\\AppData\\Roaming\\" + GAME_FOLDER;
    } else if (sysName.contains("Linux")) {
      saveDirectory = System.getenv("APPDATA") + GAME_FOLDER;
    } else if (sysName.contains("Mac")) {
      saveDirectory = "~/Documents/Saved Games/" + GAME_FOLDER;
    }
    LAST_LEVEL_FILE_PATH = saveDirectory + GAME_NAME;

    boolean success = false;
    if (!Files.exists(FileSystems.getDefault().getPath(saveDirectory))) {
      // create the directory if it doesn't exist
      success = new File(saveDirectory).mkdir();
    }

    //create the savegame if it doesn't exist
    if (success) {
      File saveGame = new File(LAST_LEVEL_FILE_PATH);
      if (!saveGame.exists()) {
        try {
          //noinspection ResultOfMethodCallIgnored
          saveGame.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

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
   * @return the elements in the level
   * @throws IllegalArgumentException
   */
  public static LevelData getLevelData(int levelNumber) throws IllegalArgumentException {
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
    return level;
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
   * @return The number of the latest unlocked level retrieved from the save-file.
   */
  public static int getLastLevelNumber() {
    int returnedLevelNumber = 1;

    File file = new File(LAST_LEVEL_FILE_PATH);
    if (file.exists() && !file.isDirectory()) {
      FileReader fileReader;
      BufferedReader bufferedReader;
      try {
        fileReader = new FileReader(LAST_LEVEL_FILE_PATH);
        bufferedReader = new BufferedReader(fileReader);
        // read lines in file
        String lastLevelNumber;
        lastLevelNumber = bufferedReader.readLine();
        returnedLevelNumber = Integer.parseInt(lastLevelNumber);
        fileReader.close();
      } catch (IOException e) {
        System.out.println(ERROR_WHEN_TRYING_TO_READ_FILE + LAST_LEVEL_FILE_PATH);
        System.out.println(e.toString());
        returnedLevelNumber = 1;
      } catch (NumberFormatException e) {
        returnedLevelNumber = 1;
      }
    }

    return returnedLevelNumber;
  }

  /**
   * @param level the new last level
   */
  public static void setLastUnlockedLevel(int level) {
    try {
      String lastLevelNumber = Integer.toString(level);
      BufferedWriter out = new BufferedWriter(new FileWriter(LAST_LEVEL_FILE_PATH));
      out.write(lastLevelNumber);
      out.close();
    } catch (IOException e) {
      System.out.println(ERROR_WHILE_WRITING_IN_TEXT_FILE + ": " + LAST_LEVEL_FILE_PATH);
    }
  }

  /**
   * This method resets the current game progress by setting the last unlocked level to 1.
   * This is used if the user wishes to start all over again.
   */
  public static void resetGameProgress() {
    setLastUnlockedLevel(1);
  }


  public static Image loadImage(String fileName) {
    Image image = null;
    try {
      image = new Image("resources/images/" + fileName + ".png");
    } catch (SlickException e) {
      System.out.println("Error while loading image:" + fileName);
      e.printStackTrace();
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
