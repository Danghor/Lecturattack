package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.xmlHandling.configLoading.TargetConfig;
import Lecturattack.utilities.xmlHandling.configLoading.TargetStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelData;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Tim Adamek,
 */
public class FileHandler {
  private static final String LAST_LEVEL_FILE_PATH = ".\\latestLevel.txt";
  private static final String[] PATH_TO_LEVELS = new String[]{"resources/level/Level1.xml", "resources/level/Level2.xml", "resources/level/Level3.xml", "resources/level/Level4.xml", "resources/level/Level5.xml", "resources/level/Level6.xml",}; //TODO add LevelFiles

  public static List<TargetStandard> loadTargetConfig() {
    // because the JAXB marshalling need classes with XML annotations these "data classes" are nessesary
    // they do nothing but hold the date that is read from the config file
    File file = new File("resources/config/target.xml");//TODO save in final var
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

}
