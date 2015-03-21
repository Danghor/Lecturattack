package Lecturattack.utilities;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.xmlHandling.LevelElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nick Steyer on 09/03/2015
 */
public class FileHandler {
  private static final String LAST_LEVEL_FILE_PATH = ".\\latestLevel.txt";
  private static final String[] PATH_TO_LEVELS = new String[]{""}; //TODO add LevelFiles

  public static List<LevelElement> getLevelData(int levelNumber) throws IllegalArgumentException, IOException {
    File file = new File("resources/TestXML.xml");//TODO mapping levelNumber to file
    JAXBContext jaxbContext = null;
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
