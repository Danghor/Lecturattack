/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;

/**
 * Created by Nick Steyer on 09/03/2015
 */
public class FileHandler {
  private final String LAST_LEVEL_FILE_PATH = ".\\latestLevel.txt";
  //todo: save filepath to tiled level files

  public TiledMap getTiledMap(int levelNumber) throws IllegalArgumentException, IOException {
    return null;
  }

  public int getLastLevelNumber() {
    return 0;
  }

  public void setLastLevelNumber(int level) {
    //todo: alles in %APPDATA%
  }
}
