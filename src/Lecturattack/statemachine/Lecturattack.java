package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 * @author Tim Adamek
 */
public class Lecturattack extends StateBasedGame {
  public static final int MAINMENU_STATE = 0;
  public static final int LEVELSELECT_STATE = 1;
  public static final int GAME_STATE = 2;
  public static final int PAUSE_STATE = 3;

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  private static final boolean TOGGLE_FULLSCREEN = false;
  private static final int TARGET_FPS = 80;

  private Lecturattack() {
    super("Lecturattack");
  }

  public static void main(String[] args) {
    try {
      AppGameContainer appGameContainer = new AppGameContainer(new Lecturattack());
      appGameContainer.setTargetFrameRate(TARGET_FPS);
      appGameContainer.setDisplayMode(WIDTH, HEIGHT, TOGGLE_FULLSCREEN);
      appGameContainer.setShowFPS(false);
      startBackgroundMusic();
      appGameContainer.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

  private static void startBackgroundMusic() {
    Music bgMusic = FileHandler.getBackgroundMusic();
    bgMusic.loop(1f, 0.5f);
  }

  @Override
  public void initStatesList(GameContainer gameContainer) throws SlickException {
    addState(new MainMenuState(MAINMENU_STATE));
    addState(new LevelSelectState(LEVELSELECT_STATE));
    addState(new GameState(GAME_STATE));
    addState(new PauseState(PAUSE_STATE));
  }
}
