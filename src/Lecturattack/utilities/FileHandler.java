package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.menu.AnimatedButton;
import Lecturattack.utilities.xmlHandling.configLoading.TargetConfig;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
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
 * @author Andreas Geis
 */
public class FileHandler {
  private static final String LAST_LEVEL_FILE_PATH = ".\\latestLevel.txt";
  private static final String[] PATH_TO_LEVELS = new String[]{"resources/level/Level1.xml", "resources/level/Level2.xml", "resources/level/Level3.xml", "resources/level/Level4.xml", "resources/level/Level5.xml", "resources/level/Level6.xml",}; //TODO add LevelFiles

  public static List<TargetStandard> loadTargetConfig() {
    // because the JAXB marshalling need classes with XML annotations these "data classes" are nessesary
    // they do nothing but hold the date that is read from the config file
    File file = new File("resources/config/target.xml");//TODO save in final var --> method for opening/vrating --> code dup
    JAXBContext jaxbContext;
    TargetConfig targets = null;
    try {//TODO projectils
      jaxbContext = JAXBContext.newInstance(TargetConfig.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      targets = (TargetConfig) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return targets.getTargetStandards();
  }

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

  public static int getLastLevelNumber() {
    return 0;
  }

  public static void setLastLevelNumber(int level) {
    //todo: alles in %APPDATA%
  }

  public static AnimatedButton[] createMainMenuButtons() {
    AnimatedButton[] menuButton = new AnimatedButton[3];
    try {
      // TODO: check if the player is in level 2 or higher and replace the
      // "Spiel Starten" image with "Spiel fortsetzen" for Button 1
      menuButton[0] = new AnimatedButton(245, 500, new Image("resources/images/startGame_down.png"), new Image("resources/images/startGame.png"));
      menuButton[1] = new AnimatedButton(495, 500, new Image("resources/images/levelSelect_down.png"), new Image("resources/images/levelSelect.png"));
      menuButton[2] = new AnimatedButton(745, 500, new Image("resources/images/endGame_down.png"), new Image("resources/images/endGame.png"));
    } catch (SlickException e) {
      System.out.println("Error while loading images.");
    }
    return menuButton;
  }

  public static AnimatedButton[] createLevelSelectButtons() {
    AnimatedButton[] menuButton = new AnimatedButton[7];
    try {
      menuButton[0] = new AnimatedButton(245, 50, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[1] = new AnimatedButton(495, 50, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[2] = new AnimatedButton(745, 50, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[3] = new AnimatedButton(245, 300, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[4] = new AnimatedButton(495, 300, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[5] = new AnimatedButton(745, 300, new Image("resources/images/level1_down.png"), new Image("resources/images/level1.png"));
      menuButton[6] = new AnimatedButton(245, 600, new Image("resources/images/back_down.png"), new Image("resources/images/back.png"));
    } catch (SlickException e) {
      System.out.println("Error while loading images.");
    }
    return menuButton;
  }
  
  public static AnimatedButton[] createPauseMenuButtons() {
    AnimatedButton[] menuButton = new AnimatedButton[2];
    try {
      menuButton[0] = new AnimatedButton(245, 500, new Image("resources/images/continue_down.png"), new Image("resources/images/continue.png"));
      menuButton[1] = new AnimatedButton(495, 500, new Image("resources/images/backToMenu_down.png"), new Image("resources/images/backToMenu.png"));
    } catch (SlickException e) {
      System.out.println("Error while loading images.");
    }
    return menuButton;
  }

  public static Image createMenuBackground() {
    Image background = null;
    try {
      background = new Image("resources/images/backgroundMenu.png");
    } catch (SlickException e) {
      System.out.println("Error while loading images.");
    }
    return background;
  }

  public static Image createMenuLogo() {
    Image logo = null;
    try {
      logo = new Image("resources/images/logo.png");
    } catch (SlickException e) {
      System.out.println("Error while loading images.");
    }
    return logo;
  }

}
